package hcmuaf.edu.vn.bookingtravel.api.restcontroller;

import hcmuaf.edu.vn.bookingtravel.api.base.controller.BaseController;
import hcmuaf.edu.vn.bookingtravel.api.base.controller.ClientException;
import hcmuaf.edu.vn.bookingtravel.api.base.exception.ServiceException;
import hcmuaf.edu.vn.bookingtravel.api.base.utils.NumberUtils;
import hcmuaf.edu.vn.bookingtravel.api.client.AreaCodeVNClient;
import hcmuaf.edu.vn.bookingtravel.api.client.model.DistrictGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.ProvinceGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.ResultGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.WardGHN;
import hcmuaf.edu.vn.bookingtravel.api.enums.CountryCode;
import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import hcmuaf.edu.vn.bookingtravel.api.manager.GeoManager;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Geo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/geo/1.0.0/")
public class GeoController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(GeoController.class);
    @Autowired
    private GeoManager geoManager;
    @Value("${area.vn}")
    private String areaVN;
    @Value("${area.token}")
    private String token;

    @ApiOperation(value = "generate geo")
    @GetMapping("generate-geo")
    public List<Geo> generateGeo() throws ServiceException {
        AreaCodeVNClient client = new AreaCodeVNClient(areaVN);
        List<Geo> geos = new ArrayList<Geo>();
        ResultGHN<ProvinceGHN> provinceGHNs = null;
        ResultGHN<DistrictGHN> districtGHNs = null;
        ResultGHN<WardGHN> wardGHNs = null;
        try {
            provinceGHNs = client.getProvinceGHNs(token);

        } catch (ClientException e) {
            e.printStackTrace();
        }
        for (ProvinceGHN province : provinceGHNs.getData()) {
            Geo geoProvince = new Geo();
            geoProvince.setCountryCode(CountryCode.VN);
            geoProvince.setChild_id(province.getProvinceID());
            geoProvince.setType(GeoType.PROVINCE);
            geoProvince.setName(province.getProvinceName());
            geoProvince.setCode(province.getCode());
            Geo geoProvinceData = geoManager.getGeoChild(GeoType.PROVINCE, province.getProvinceID());
            if (null == geoProvinceData)
                geoProvince = geoManager.createGeo(geoProvince);
            geos.add(geoProvince);


            try {
                districtGHNs = client.getDistrictGHNs(token, geoProvince.getChild_id());

            } catch (ClientException e) {
                throw new ServiceException(e.getErrorCode(), e.getErrorMessage());


            }
            if (districtGHNs != null && districtGHNs.getData() != null && 0 != districtGHNs.getData().size()) {
                for (DistrictGHN district : districtGHNs.getData()) {
                    Geo geoDistrict = new Geo();
                    String isNUmber = String.valueOf(district.getDistrictID());
                    if (!NumberUtils.isNumeric(isNUmber)) {
                        continue;
                    }
                    geoDistrict.setCountryCode(CountryCode.VN);
                    geoDistrict.setChild_id(district.getDistrictID());
                    geoDistrict.setParent_id(district.getProvinceID());
                    geoDistrict.setCode(district.getCode());
                    geoDistrict.setName(district.getDistrictName());
                    geoDistrict.setType(GeoType.DISTRICT);
                    Geo geoDistrictData = geoManager.getGeoChild(GeoType.DISTRICT, district.getDistrictID());
                    if (null == geoDistrictData)
                        geoDistrict = geoManager.createGeo(geoDistrict);
                    geos.add(geoDistrict);

                    try {
                        wardGHNs =client.getWardGHNs(token, geoDistrict.getChild_id());

                    } catch (ClientException e) {
                        continue;
                    }
                    if (null != wardGHNs &&
                            wardGHNs.getData() != null
                            && 0 != wardGHNs.getData().size()) {
                        for (WardGHN ward : wardGHNs.getData()) {
                            Geo geoWard = new Geo();
                            geoWard.setCountryCode(CountryCode.VN);
                            geoWard.setChild_id(ward.getWardCode());
                            geoWard.setCode(String.valueOf(ward.getWardCode()));
                            geoWard.setName(ward.getWardName());
                            geoWard.setParent_id(district.getDistrictID());
                            geoWard.setType(GeoType.WARD);
                            Geo geoWardData = geoManager.getGeoChild(GeoType.WARD, ward.getWardCode());
                            if (null == geoWardData)
                                geoWard = geoManager.createGeo(geoWard);
                            geos.add(geoWard);

                        }
                    }

                }
            }

        }


        return geos;
    }

    @ApiOperation(value = "get list province")
    @GetMapping("province-list")
    public List<Geo> getProvicnes() {
        return geoManager.getGeoType(GeoType.PROVINCE);
    }

    @ApiOperation(value = "get list district by province id")
    @GetMapping("district-list")
    public List<Geo> getDistrict(@RequestParam(value = "province-id") int provinceId) {
        return geoManager.getGeoParent(GeoType.DISTRICT, provinceId);
    }

    @ApiOperation(value = "get list ward by district id")
    @GetMapping("ward-list")
    public List<Geo> getWards(@RequestParam(value = "district-id") int districtId) {
        return geoManager.getGeoParent(GeoType.WARD, districtId);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllServiceException(ServiceException e) {
        LOGGER.error("ServiceException error.", e);
        return error(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return error("internal_server_error", e.getMessage());
    }
}
