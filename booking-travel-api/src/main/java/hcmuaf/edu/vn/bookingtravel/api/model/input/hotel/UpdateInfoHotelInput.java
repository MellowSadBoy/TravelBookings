package hcmuaf.edu.vn.bookingtravel.api.model.input.hotel;

import hcmuaf.edu.vn.bookingtravel.api.model.geo.Address;
import hcmuaf.edu.vn.bookingtravel.api.model.hotel.enums.RankHotelType;

import java.util.List;

public class UpdateInfoHotelInput {
    private String name;
    private String fax;
    private Address address;
    private RankHotelType type;
    private List<String> imgUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RankHotelType getType() {
        return type;
    }

    public void setType(RankHotelType type) {
        this.type = type;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
