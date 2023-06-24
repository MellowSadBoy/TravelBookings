package hcmuaf.edu.vn.bookingtravel.api.client;

import hcmuaf.edu.vn.bookingtravel.api.base.client.BaseClient;
import hcmuaf.edu.vn.bookingtravel.api.base.controller.ClientException;
import hcmuaf.edu.vn.bookingtravel.api.client.model.LocationResult;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 24/04/2023, Thứ Hai
 **/
public class LocationClient extends BaseClient {
    private String token;
    private String code;

    public LocationClient(String service, String token, String code) {
        super(service);
        this.token = token;
        this.code = code;
    }

    public LocationResult getLocation(int type, String parentId) throws ClientException {
        return get("api/v1/locations/bykindandparentid?kind=" + type + "&parentId=" + parentId,
                LocationResult.class, token, code);
    }

}
