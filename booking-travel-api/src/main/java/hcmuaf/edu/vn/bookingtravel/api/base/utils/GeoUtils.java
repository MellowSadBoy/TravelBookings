package hcmuaf.edu.vn.bookingtravel.api.base.utils;


import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import hcmuaf.edu.vn.bookingtravel.api.manager.GeoManager;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Geo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class GeoUtils {
    @Autowired
    private GeoManager geoManager;


    public HashMap<String, String> generateGeo() {
        List<Geo> provinces = geoManager.getGeoType(GeoType.PROVINCE);
        Geo geoProvince = provinces.get(new Random().nextInt(provinces.size() - 1));
        List<Geo> districts = geoManager.getGeoParent(GeoType.DISTRICT, geoProvince.getChild_id());
        Geo geoDistrict = districts.get(new Random().nextInt(districts.size() - 1));
        List<Geo> geoWards = geoManager.getGeoParent(GeoType.WARD, geoDistrict.getChild_id());
        Geo geoWard = geoWards.get(new Random().nextInt(geoWards.size() - 1));
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("address", geoWard.getName() + ", " + geoDistrict.getName() + ", " + geoProvince.getName());
        map.put("province", String.valueOf(geoProvince.getChild_id()));
        map.put("district", String.valueOf(geoDistrict.getChild_id()));
        map.put("ward", String.valueOf(geoWard.getChild_id()));
        return map;

    }
}
