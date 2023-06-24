package hcmuaf.edu.vn.bookingtravel.api.client;

import hcmuaf.edu.vn.bookingtravel.api.base.client.BaseClient;
import hcmuaf.edu.vn.bookingtravel.api.base.controller.ClientException;
import hcmuaf.edu.vn.bookingtravel.api.client.model.DistrictGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.ProvinceGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.ResultGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.WardGHN;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 12/04/2023, Thứ Tư
 **/
public class AreaCodeVNClient extends BaseClient {
    public AreaCodeVNClient(String service) {
        super(service);
    }

    public ResultGHN<ProvinceGHN> getProvinceGHNs(String token) throws ClientException {
        return getResponsePackList("master-data/province", ProvinceGHN.class, token);
    }

    public ResultGHN<DistrictGHN> getDistrictGHNs(String token, int province_id) throws ClientException {
        return getResponsePackList("master-data/district?province_id=" + province_id, DistrictGHN.class, token);
    }

    public ResultGHN<WardGHN> getWardGHNs(String token, int district_id) throws ClientException {
        return getResponsePackList("master-data/ward?district_id=" + district_id, WardGHN.class, token);
    }

}
