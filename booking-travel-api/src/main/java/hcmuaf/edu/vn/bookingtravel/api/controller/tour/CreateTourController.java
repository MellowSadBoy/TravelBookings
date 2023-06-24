package hcmuaf.edu.vn.bookingtravel.api.controller.tour;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ExceptionCode;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.manager.PricingValueManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.ScheduleManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.TourManager;
import hcmuaf.edu.vn.bookingtravel.api.manager.VehicleManager;
import hcmuaf.edu.vn.bookingtravel.api.model.input.tour.*;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.*;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourStatus;
import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TourType;
import hcmuaf.edu.vn.bookingtravel.api.model.user.enums.AgeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 14/04/2023, Thứ Sáu
 **/
@Component
public class CreateTourController extends BaseController {
    @Autowired
    private TourManager tourManager;
    @Autowired
    private VehicleManager vehicleManager;
    @Autowired
    private ScheduleManager scheduleManager;
    @Autowired
    private PricingValueManager pricingValueManager;

    private void validTourInput(CreateTourInput createTourInput) throws ServiceException {
        // Check TourInput
        TourInput tourInput = createTourInput.getTourInput();
        if (tourInput == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour input is missing");
        }
        if (!StringUtils.hasText(tourInput.getName())) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour name is missing or empty");
        }
        if (tourInput.getType() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour type is missing or empty");
        }
        if (!TourType.isExist(tourInput.getType())) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour type is not exist " + TourType.getListName());
        }
        if (null == tourInput.getAddressStart()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour starting address is missing or empty");
        }
        if (tourInput.getHotelId() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour hotel ID is missing or null");
        }
        if (tourInput.getImgUrls() == null || tourInput.getImgUrls().isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour images are missing or empty");
        }
        if (tourInput.getLocation() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour location is missing or null");
        }
        if (tourInput.getTimeHowLong() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour duration is missing or null");
        }
        if (tourInput.getStartDate() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour start date is missing or null");
        }
        if (tourInput.getPriceStandard() == null) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour standard price is missing or null");
        }
        if (tourInput.getQuantityVisit() == 0) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Tour visit quantity is missing or null");
        }

        // Check PricingValueInput
        List<PricingValueInput> pricingValues = createTourInput.getPricingValues();
        if (pricingValues == null || pricingValues.isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Pricing values are missing");
        }
        int pricingChildren = 0, pricingAdult = 0;

        for (PricingValueInput pricingInput : pricingValues) {
            if (pricingInput.getPrice() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Pricing price is missing");
            }
            if (pricingInput.getAboutAgeType() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Pricing age type is missing");
            }

            if (pricingInput.getSale() < 0 || pricingInput.getSale() > 1) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Pricing sale value is invalid");
            }
            if (pricingInput.getAboutAgeType().equals(AgeType.CHILDREN)) {
                pricingChildren++;
            } else {
                pricingAdult++;
            }
        }
        if (pricingAdult != 1 || pricingChildren != 1) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Chỉ có giá tiền của trẻ em và người lớn");

        }

        List<ScheduleInput> scheduleInputs = createTourInput.getScheduleInput();
        if (scheduleInputs == null || scheduleInputs.isEmpty()) {
            throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule inputs are missing");
        }
        for (ScheduleInput scheduleInput : scheduleInputs) {
            if (scheduleInput.getTitle() == null || scheduleInput.getTitle().isEmpty()) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule tile is missing");
            }
            if (scheduleInput.getNameLocation() == null || scheduleInput.getNameLocation().isEmpty()) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule location name is missing");
            }
            if (scheduleInput.getLocationStop() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule location stop is missing");
            }
            if (scheduleInput.getDescription() == null || scheduleInput.getDescription().isEmpty()) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule description is missing");
            }
            if (scheduleInput.getArrivalTime() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule arrival time is missing");
            }
            if (scheduleInput.getLeaveTime() == null) {
                throw new ServiceException(ExceptionCode.INVALID_DATA_EXCEPTION.toString(), "Schedule leave time is missing");
            }
        }
    }

    public Tour createTour(CreateTourInput createTourInput) throws ServiceException {
        //valid tour input
        validTourInput(createTourInput);

        TourInput tourInput = createTourInput.getTourInput();
        Tour tour = new Tour();
        tour.setDescription(tourInput.getDescription());
        tour.setAddressStart(tourInput.getAddressStart());
        tour.setDiscount(tourInput.getDiscount());
        tour.setHotelId(tourInput.getHotelId());
        tour.setImgUrls(tourInput.getImgUrls());
        tour.setFeatureImgUrl(tourInput.getFeatureImgUrl());
        tour.setLocation(tourInput.getLocation());
        tour.setName(tourInput.getName());
        tour.setNote(tourInput.getNote());
        tour.setTimeHowLong(tourInput.getTimeHowLong());
        tour.setStatus(TourStatus.ACTIVE);
        tour.setType(tourInput.getType());
        tour.setStartDate(tourInput.getStartDate());
        tour.setPriceStandard(tourInput.getPriceStandard());
        tour.setQuantityVisit(tourInput.getQuantityVisit());
        tour.setQuantityEmpty(tourInput.getQuantityVisit());
        VehicleInput vehicleInput = createTourInput.getVehicleInput();
        Vehicle vehicle = new Vehicle();
        vehicle.setAutomaker(vehicleInput.getAutomaker());
        vehicle.setName(vehicleInput.getName());
        vehicle.setVehicleType(vehicleInput.getVehicleType());
        vehicle.setNumberOfSeats(vehicleInput.getNumberOfSeats());
        vehicle.setCode(vehicleInput.getCode());
        //create vehicle
        vehicle = vehicleManager.createVehicle(vehicle);
        //set vehicle Id
        tour.setVehicleId(vehicle.getId());

        //create tour
        tour = tourManager.createTour(tour);
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleInput scheduleInput : createTourInput.getScheduleInput()) {
            Schedule schedule = new Schedule();
            schedule.setDescription(scheduleInput.getDescription());
            schedule.setArrivalTime(scheduleInput.getArrivalTime());
            schedule.setLeaveTime(scheduleInput.getLeaveTime());
            schedule.setLocationStop(scheduleInput.getLocationStop());
            schedule.setNameLocation(scheduleInput.getNameLocation());
            schedule.setTitle(scheduleInput.getTitle());
            schedule.setTourId(tour.getId());
            schedules.add(schedule);
        }
        scheduleManager.createSchedules(schedules);
        List<PricingValue> pricingValues = new ArrayList<>();
        for (PricingValueInput pricingInput : createTourInput.getPricingValues()) {
            PricingValue price = new PricingValue();
            price.setTourId(tour.getId());
            price.setNote(pricingInput.getNote());
            price.setPrice(pricingInput.getPrice());
            price.setSale(pricingInput.getSale());
            if (pricingInput.getAboutAgeType().equals(AgeType.ADULTS)) {
                price.setPrice(tourInput.getPriceStandard());
                price.setSale(tourInput.getDiscount());
            }
            price.setAboutAgeType(pricingInput.getAboutAgeType());
            pricingValues.add(price);
        }
        pricingValueManager.createPricingValues(pricingValues);

        return tour;

    }

    public Tour updateTour(String tourId, CreateTourInput createTourInput) throws ServiceException {
        //valid tour input
        Tour tourCheck = tourManager.getTour(tourId);
        if (null == tourCheck) {
            throw new ServiceException(ExceptionCode.NOT_FOUND_EXCEPTION.toString(),
                    "Không tìm thấy thông tin tài khoản");
        }
        validTourInput(createTourInput);

        TourInput tourInput = createTourInput.getTourInput();
        HashMap<String, Object> updateDocument = new HashMap<>();
        updateDocument.put("updatedAt", new Date());

//        return update(userId, updateDocument, null, "Cập nhật thông tin tài khoản " + userId);
        Tour tour = new Tour();
        tour.setDescription(tourInput.getDescription());
        tour.setAddressStart(tourInput.getAddressStart());
        tour.setDiscount(tourInput.getDiscount());
        tour.setHotelId(tourInput.getHotelId());
        tour.setImgUrls(tourInput.getImgUrls());
        tour.setFeatureImgUrl(tourInput.getFeatureImgUrl());
        tour.setLocation(tourInput.getLocation());
        tour.setName(tourInput.getName());
        tour.setNote(tourInput.getNote());
        tour.setTimeHowLong(tourInput.getTimeHowLong());
        tour.setStatus(TourStatus.ACTIVE);
        tour.setType(tourInput.getType());
        tour.setStartDate(tourInput.getStartDate());
        tour.setPriceStandard(tourInput.getPriceStandard());
        tour.setQuantityVisit(tourInput.getQuantityVisit());
        tour.setQuantityEmpty(tourInput.getQuantityVisit());
        VehicleInput vehicleInput = createTourInput.getVehicleInput();
        Vehicle vehicle = new Vehicle();
        vehicle.setAutomaker(vehicleInput.getAutomaker());
        vehicle.setName(vehicleInput.getName());
        vehicle.setVehicleType(vehicleInput.getVehicleType());
        vehicle.setNumberOfSeats(vehicleInput.getNumberOfSeats());
        vehicle.setCode(vehicleInput.getCode());
        //create vehicle
        vehicle = vehicleManager.createVehicle(vehicle);
        //set vehicle Id
        tour.setVehicleId(vehicle.getId());

        //create tour
        tour = tourManager.createTour(tour);
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleInput scheduleInput : createTourInput.getScheduleInput()) {
            Schedule schedule = new Schedule();
            schedule.setDescription(scheduleInput.getDescription());
            schedule.setArrivalTime(scheduleInput.getArrivalTime());
            schedule.setLeaveTime(scheduleInput.getLeaveTime());
            schedule.setLocationStop(scheduleInput.getLocationStop());
            schedule.setNameLocation(scheduleInput.getNameLocation());
            schedule.setTitle(scheduleInput.getTitle());
            schedule.setTourId(tour.getId());
            schedules.add(schedule);
        }
        scheduleManager.createSchedules(schedules);
        List<PricingValue> pricingValues = new ArrayList<>();
        for (PricingValueInput pricingInput : createTourInput.getPricingValues()) {
            PricingValue price = new PricingValue();
            price.setTourId(tour.getId());
            price.setNote(pricingInput.getNote());
            price.setPrice(pricingInput.getPrice());
            price.setSale(pricingInput.getSale());
            if (pricingInput.getAboutAgeType().equals(AgeType.ADULTS)) {
                price.setPrice(tourInput.getPriceStandard());
                price.setSale(tourInput.getDiscount());
            }
            price.setAboutAgeType(pricingInput.getAboutAgeType());
            pricingValues.add(price);
        }
        pricingValueManager.createPricingValues(pricingValues);
        return tour;
    }
}

