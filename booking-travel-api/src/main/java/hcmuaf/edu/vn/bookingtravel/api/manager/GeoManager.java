package hcmuaf.edu.vn.bookingtravel.api.manager;


import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Geo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public class GeoManager extends BaseManager<Geo> {
    public GeoManager() {
        super(Geo.class.getSimpleName(), Geo.class);
    }

    public Geo createGeo(Geo geo) {
        geo.setCreatedAt(new Date());
        geo.setId(generateId());
        return create(geo);
    }

    public List<Geo> getGeoType(GeoType type) {
        return getList("type", type.toString());
    }

    public Geo getGeoChild(GeoType type, int child_id) {
        return getByQuery(Query.query(Criteria.where("child_id").is(child_id).and("type").is(type.toString())));

    }

    public List<Geo> getGeoParent(GeoType type, int parent_id) {
        return getListByQuery(Query.query(Criteria.where("parent_id").is(parent_id).and("type").is(type.toString())));

    }

}
