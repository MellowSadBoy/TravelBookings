package hcmuaf.edu.vn.bookingtravel.api.manager;

import hcmuaf.edu.vn.bookingtravel.api.base.manager.BaseManager;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.Schedule;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.Date;
import java.util.List;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 13/04/2023, Thứ Năm
 **/
@Repository
public class ScheduleManager extends BaseManager<Schedule> {
    public ScheduleManager() {
        super(Schedule.class.getSimpleName(), Schedule.class);
    }

    public Schedule getSchedule(String id) {
        return getById(id);
    }

    public Schedule createSchedule(Schedule schedule) {
        schedule.setId(generateId());
        schedule.setCreatedAt(new Date());
        return create(schedule);
    }

    public List<Schedule> getScheduleList(String tourId) {
        return getList("tourId", tourId);

    }

    public void createSchedules(List<Schedule> schedules) {
        create(schedules, "Vũ Văn Minh", "Tạo danh sách lịch trình của tour");
    }

}
