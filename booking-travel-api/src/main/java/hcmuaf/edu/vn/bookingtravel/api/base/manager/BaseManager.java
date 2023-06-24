package hcmuaf.edu.vn.bookingtravel.api.base.manager;


import com.mongodb.client.result.DeleteResult;
import hcmuaf.edu.vn.bookingtravel.api.base.filter.ResultList;
import hcmuaf.edu.vn.bookingtravel.api.base.logs.ActivityLog;
import hcmuaf.edu.vn.bookingtravel.api.base.model.BaseModel;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.DateUtils;
import hcmuaf.edu.vn.bookingtravel.api.enums.ActivityLogType;
import hcmuaf.edu.vn.bookingtravel.api.model.Enterprise;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.util.Assert;


import java.util.*;

public class BaseManager<T> {
    @Autowired
    protected MongoTemplate mongoTemplate;
    protected String collectionName;
    protected Class<T> tClass;

    public BaseManager(String collectionName, Class<T> tClass) {
        this.collectionName = collectionName.toLowerCase();
        this.tClass = tClass;
    }


    private String getRandomNumber() {
        int number = (new Random()).nextInt(999);
        String id = number + "";
        if (number < 10) {
            id = "00" + number;
        } else if (number < 100) {
            id = "0" + number;
        }

        return id;
    }

    public String generateId() {
        return System.currentTimeMillis() + getRandomNumber();
    }

    protected T create(T entity, String createdBy, String note) {
        BaseModel baseEntity = (BaseModel) entity;
        baseEntity.setCreatedAt(new Date());
        baseEntity.setCreatedBy(createdBy);

        if (null == baseEntity.getId() || baseEntity.getId().length() == 0) {
            baseEntity.setId(generateId());
        }
        mongoTemplate.insert(entity);
        this.mongoTemplate.insert(ActivityLog.addActivityLog(baseEntity.getId(),
                this.collectionName, ActivityLogType.CREATE, createdBy, note));
        return entity;
    }

    protected T create(T entity) {
        BaseModel baseEntity = (BaseModel) entity;
        baseEntity.setCreatedAt(new Date());
        if (null == baseEntity.getId() || baseEntity.getId().length() == 0) {
            baseEntity.setId(generateId());
        }
        return this.mongoTemplate.insert(entity);
    }

    protected List<T> create(List<T> entityList, String createdBy, String note) {
        for (T entity : entityList) {
            if (entity instanceof BaseModel) {
                BaseModel baseEntity = (BaseModel) entity;
                baseEntity.setCreatedAt(new Date());
                if (null == baseEntity.getId() || baseEntity.getId().length() == 0) {
                    baseEntity.setId(generateId());

                }
                baseEntity.setCreatedBy(createdBy);
            }
        }
        this.mongoTemplate.insertAll(entityList);
        return entityList;
    }

    public T getEntity() {
        return  this.mongoTemplate.findAll(tClass).get(0);

    }

    protected T getById(String id) {
        return this.mongoTemplate.findById(id, this.tClass);
    }

    protected List<T> getList(String id) {
        return this.mongoTemplate.find(Query.query(Criteria.where("_id").
                is(id)), this.tClass);
    }

    protected List<T> getList(String filterKey, Object filterValue) {
        return getListByQuery(Query.query(Criteria.where(filterKey).is(filterValue)));
    }

    protected List<T> getListByQuery(Query query) {
        return this.mongoTemplate.find(query, tClass);
    }

    protected T getByQuery(Query query) {
        return this.mongoTemplate.findOne(query, this.tClass);
    }

    protected T update(String id, HashMap<String, Object> updateData, String updateBy, String note) {
        Update update = new Update();
        for (String key : updateData.keySet()) {
            update.set(key, updateData.get(key));
        }
        update.set("updatedAt", new Date());
        update.set("updatedBy", updateBy);
        update(Query.query(Criteria.where("_id").is(id)), update);
        this.mongoTemplate.insert(ActivityLog.addActivityLog(id,
                this.collectionName, ActivityLogType.UPDATE_INFO, updateBy, note));
        return getById(id);
    }

    protected void update(Query query, Update update) {
        update.set("updatedAt", new Date());
        this.mongoTemplate.updateFirst(query, update, this.tClass);

    }

    public void updateMultiple(Query query, Update update) {
        this.mongoTemplate.updateMulti(query, update, this.tClass);
    }

    protected void update(Query query, HashMap<String, Object> updateData, String note) {
        Update update = new Update();
        String userId = null;
        for (String key : updateData.keySet()) {
            update.set(key, updateData.get(key));
            if (key.equals("userId")) {
                userId = updateData.get(key).toString();
            }
        }
        update.set("updatedAt", new Date());
        this.mongoTemplate.updateFirst(query, update, this.tClass);
        this.mongoTemplate.insert(ActivityLog.addActivityLog(userId,
                this.collectionName, ActivityLogType.UPDATE_INFO, null, note));

    }

    protected void update(Query query, HashMap<String, Object> updateData, String byUser, String note) {
        Update update = new Update();
        String userId = null;
        for (String key : updateData.keySet()) {
            update.set(key, updateData.get(key));
            if (key.equals("userId")) {
                userId = updateData.get(key).toString();
            }
        }
        update.set("updatedAt", new Date());
        this.mongoTemplate.updateFirst(query, update, this.tClass);
        this.mongoTemplate.insert(ActivityLog.addActivityLog(userId,
                this.collectionName, ActivityLogType.UPDATE_INFO, byUser, note));

    }

    protected void updateAll(String filterName, String filterValue, String updateName, String updateValue, String updateBy, String note) {
        Update update = new Update();
        update.set(updateName, updateValue);
        update.set("updatedAt", new Date());
        update.set("updatedBy", updateBy);
        this.mongoTemplate.updateFirst(Query.query(Criteria.where(filterName).is(filterValue)), update, this.collectionName);
        this.mongoTemplate.insert(ActivityLog.addActivityLog(filterValue,
                this.collectionName, ActivityLogType.UPDATE_INFO, updateBy, note));
    }

    public void delete(String id, String updateBy, String note) {
        DeleteResult result = this.mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), this.tClass, this.collectionName);
        Assert.isTrue(result.getDeletedCount() != 0L, "Record id " + id + " for collection + " + this.collectionName + " is not exits!");
        this.mongoTemplate.insert(ActivityLog.addActivityLog(id,
                this.collectionName, ActivityLogType.UPDATE_INFO, updateBy, note));
    }

    public void deleteByAttribute(String name, Object value, String updateBy, String note) {
        DeleteResult result = this.mongoTemplate.remove(Query.query(Criteria.where(name).is(value)), this.tClass, this.collectionName);
        Assert.isTrue(result.getDeletedCount() != 0L, "No record exist!");
        this.mongoTemplate.insert(ActivityLog.addActivityLog(name,
                this.collectionName, ActivityLogType.UPDATE_INFO, updateBy, note));
    }

    protected ResultList<T> getResultList(
            String searchText, Date createFrom,
            Date createTo, int offsetIndex,
            int maxResult,
            Query query) {
        if (query == null)
            query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        if (null != searchText && searchText.length() > 0) {
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(searchText);
            query.addCriteria(criteria);
        }
        if (null != createFrom) {
            if (null == createTo) {
                query.addCriteria(Criteria.where("createdAt").gte(createFrom));
            } else {
                query.addCriteria(Criteria.where("createdAt").gte(Objects.requireNonNull(DateUtils.getStartDay(createFrom))).
                        lte(Objects.requireNonNull(DateUtils.getEndDay(createTo))));
            }
        } else {
            if (null != createTo) {
                query.addCriteria(Criteria.where("createdAt").lt(createTo));
            }
        }
        long count = mongoTemplate.count(query, tClass);
        ResultList<T> resultList = new ResultList<>();
        resultList.setSearchList(new ArrayList<T>());
        if (count > 0) {
            if (maxResult > 0) {
                query.limit(maxResult);
                query.skip(offsetIndex);
            }
            List<T> list = mongoTemplate.find(query, tClass);
            resultList.setSearchList(list);
            resultList.setMaxResult(maxResult);
            resultList.setIndex(offsetIndex);
            resultList.setTotal(count);
        }
        return resultList;
    }


}
