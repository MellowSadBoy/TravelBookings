package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.ScoreType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.Score;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;

@Repository
public class ScoreManager extends BaseManager<Score> {

    public ScoreManager() {
        super(Score.class.getSimpleName(), Score.class);
    }

    public void createScore(Score score) {
        score.setId(generateId());
        score.setCreatedAt(new Date());
        score.setType(ScoreType.RANK_BRONZE);
        create(score);
    }

    public Score getScore(String id) {
        return getById(id);
    }
    public Score getScoreByUser(String userId) {
        return getByQuery(Query.query(Criteria.where("userId").is(userId)));
    }
    public void updateScore(String userId, long count) {
        Update update = new Update();
        update.inc("score", count);
        update(Query.query(Criteria.where("userId").is(userId)), update);
    }

    public void updateScoreType(String userId, ScoreType type) {
        HashMap<String, Object> document = new HashMap<String, Object>();
        document.put("updatedAt", new Date());
        document.put("type", type.toString());
        update(Query.query(Criteria.where("userId").is(userId)), document, "Cập nhật loại hạng thành viên cho " + userId);
    }
    public void deleteScore(String userId, String byUser) {
        String note = "Xoá tích điểm của tài khoản " + userId;
        deleteByAttribute("userId", userId, byUser, note);
    }
}
