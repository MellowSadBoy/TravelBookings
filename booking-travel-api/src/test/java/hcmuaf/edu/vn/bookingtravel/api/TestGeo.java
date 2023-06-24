package hcmuaf.edu.vn.bookingtravel.api;

import hcmuaf.edu.vn.bookingtravel.api.enums.CountryCode;
import hcmuaf.edu.vn.bookingtravel.api.enums.GeoType;
import hcmuaf.edu.vn.bookingtravel.api.manager.GeoManager;
import hcmuaf.edu.vn.bookingtravel.api.model.geo.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 11/05/2023, Thứ Năm
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BookingTravelApiApplication.class)
@AutoConfigureMockMvc
public class TestGeo {
    @Autowired
    GeoManager geoManager;

    @Test
    public void testGeo() throws Exception {
//        Geo province1 = new Geo();
//        province1.setName("Phnom Penh");
//        province1.setChild_id(1);
//        province1.setParent_id(1);
//        province1.setCode("PNH");
//        province1.setCountryCode(CountryCode.KH);
//        province1.setType(GeoType.PROVINCE);
//        province1.setDescription("Capital city of Cambodia");
//        province1 = geoManager.createGeo(province1);
//        Geo district1 = new Geo();
//        district1.setName("Daun Penh");
//        district1.setChild_id(101);
//        district1.setParent_id(1);
//        district1.setCode("DP");
//        district1.setCountryCode(CountryCode.KH);
//        district1.setType(GeoType.DISTRICT);
//        district1.setDescription("District in Phnom Penh");
//        district1 = geoManager.createGeo(district1);
//
//        Geo ward1 = new Geo();
//        ward1.setName("Phsar Chas");
//        ward1.setChild_id(1001);
//        ward1.setParent_id(101);
//        ward1.setCode("PC");
//        ward1.setCountryCode(CountryCode.KH);
//        ward1.setType(GeoType.WARD);
//        ward1.setDescription("Ward in Daun Penh");
//        ward1 = geoManager.createGeo(ward1);
//
//        Geo ward2 = new Geo();
//        ward2.setName("Srah Chak");
//        ward2.setChild_id(1002);
//        ward2.setParent_id(101);
//        ward2.setCode("SC");
//        ward2.setCountryCode(CountryCode.KH);
//        ward2.setType(GeoType.WARD);
//        ward2.setDescription("Ward in Daun Penh");
//        ward2 = geoManager.createGeo(ward2);
//        Geo province2 = new Geo();
//        province2.setName("Siem Reap");
//        province2.setChild_id(2);
//        province2.setParent_id(1);
//        province2.setCode("REP");
//        province2.setCountryCode(CountryCode.KH);
//        province2.setType(GeoType.PROVINCE);
//        province2.setDescription("Famous for Angkor Wat");
//        province2 = geoManager.createGeo(province2);
//        Geo district2 = new Geo();
//        district2.setName("Chamkar Mon");
//        district2.setChild_id(102);
//        district2.setParent_id(2);
//        district2.setCode("CM");
//        district2.setCountryCode(CountryCode.KH);
//        district2.setType(GeoType.DISTRICT);
//        district2.setDescription("District in Phnom Penh");
//        district2 = geoManager.createGeo(district2);


//        // Tạo dữ liệu cho các tỉnh thành phố của Hàn Quốc
//        Geo seoul = new Geo();
//        seoul.setName("Seoul");
//        seoul.setChild_id(1);
//        seoul.setParent_id(2);
//        seoul.setCode("SEO");
//        seoul.setCountryCode(CountryCode.KR);
//        seoul.setType(GeoType.PROVINCE);
//        seoul.setDescription("Capital city of South Korea");
//        seoul = geoManager.createGeo(seoul);
//
//        Geo busan = new Geo();
//        busan.setName("Busan");
//        busan.setChild_id(2);
//        busan.setParent_id(2);
//        busan.setCode("BSN");
//        busan.setCountryCode(CountryCode.KR);
//        busan.setType(GeoType.PROVINCE);
//        busan.setDescription("Second-largest city in South Korea");
//        busan = geoManager.createGeo(busan);
//
//// Tạo dữ liệu cho các quận/huyện của Seoul
//        Geo gangnam = new Geo();
//        gangnam.setName("Gangnam-gu");
//        gangnam.setChild_id(101);
//        gangnam.setParent_id(1);
//        gangnam.setCode("GNG");
//        gangnam.setCountryCode(CountryCode.KR);
//        gangnam.setType(GeoType.DISTRICT);
//        gangnam.setDescription("District in Seoul");
//        gangnam = geoManager.createGeo(gangnam);
//
//        Geo jongno = new Geo();
//        jongno.setName("Jongno-gu");
//        jongno.setChild_id(102);
//        jongno.setParent_id(1);
//        jongno.setCode("JNO");
//        jongno.setCountryCode(CountryCode.KR);
//        jongno.setType(GeoType.DISTRICT);
//        jongno.setDescription("District in Seoul");
//        jongno = geoManager.createGeo(jongno);
//
//// Tạo dữ liệu cho các xã/phường của Gangnam-gu
//        Geo cheongdam = new Geo();
//        cheongdam.setName("Cheongdam-dong");
//        cheongdam.setChild_id(1001);
//        cheongdam.setParent_id(101);
//        cheongdam.setCode("CDM");
//        cheongdam.setCountryCode(CountryCode.KR);
//        cheongdam.setType(GeoType.WARD);
//        cheongdam.setDescription("Ward in Gangnam-gu");
//        cheongdam = geoManager.createGeo(cheongdam);
//
//        Geo yeoksam = new Geo();
//        yeoksam.setName("Yeoksam-dong");
//        yeoksam.setChild_id(1002);
//        yeoksam.setParent_id(101);
//        yeoksam.setCode("YKS");
//        yeoksam.setCountryCode(CountryCode.KR);
//        yeoksam.setType(GeoType.WARD);
//        yeoksam.setDescription("Ward in Gangnam-gu");
//        cheongdam = geoManager.createGeo(yeoksam);

        // Tạo dữ liệu cho các tỉnh thành phố của Thái Lan
//        Geo bangkok = new Geo();
//        bangkok.setName("Bangkok");
//        bangkok.setChild_id(1);
//        bangkok.setParent_id(3);
//        bangkok.setCode("BKK");
//        bangkok.setCountryCode(CountryCode.TH);
//        bangkok.setType(GeoType.PROVINCE);
//        bangkok.setDescription("Capital city of Thailand");
//        bangkok = geoManager.createGeo(bangkok);
//
//        Geo chiangMai = new Geo();
//        chiangMai.setName("Chiang Mai");
//        chiangMai.setChild_id(2);
//        chiangMai.setParent_id(3);
//        chiangMai.setCode("CM");
//        chiangMai.setCountryCode(CountryCode.TH);
//        chiangMai.setType(GeoType.PROVINCE);
//        chiangMai.setDescription("City in Northern Thailand");
//        chiangMai = geoManager.createGeo(chiangMai);
//
//// Tạo dữ liệu cho các quận/huyện của Bangkok
//        Geo pathumWan = new Geo();
//        pathumWan.setName("Pathum Wan");
//        pathumWan.setChild_id(101);
//        pathumWan.setParent_id(1);
//        pathumWan.setCode("PW");
//        pathumWan.setCountryCode(CountryCode.TH);
//        pathumWan.setType(GeoType.DISTRICT);
//        pathumWan.setDescription("District in Bangkok");
//        pathumWan = geoManager.createGeo(pathumWan);
//
//        Geo bangRak = new Geo();
//        bangRak.setName("Bang Rak");
//        bangRak.setChild_id(102);
//        bangRak.setParent_id(1);
//        bangRak.setCode("BR");
//        bangRak.setCountryCode(CountryCode.TH);
//        bangRak.setType(GeoType.DISTRICT);
//        bangRak.setDescription("District in Bangkok");
//        bangRak = geoManager.createGeo(bangRak);
//
//// Tạo dữ liệu cho các xã/phường của Pathum Wan
//        Geo lumpini = new Geo();
//        lumpini.setName("Lumpini");
//        lumpini.setChild_id(1001);
//        lumpini.setParent_id(101);
//        lumpini.setCode("LP");
//        lumpini.setCountryCode(CountryCode.TH);
//        lumpini.setType(GeoType.WARD);
//        lumpini.setDescription("Ward in Pathum Wan");
//        lumpini = geoManager.createGeo(lumpini);
//
//        Geo siLom = new Geo();
//        siLom.setName("Si Lom");
//        siLom.setChild_id(1002);
//        siLom.setParent_id(101);
//        siLom.setCode("SL");
//        siLom.setCountryCode(CountryCode.TH);
//        siLom.setType(GeoType.WARD);
//        siLom.setDescription("Ward in Pathum Wan");
//        siLom = geoManager.createGeo(siLom);
//
//


        // Tạo dữ liệu cho các tỉnh thành phố của Indonesia
        Geo jakarta = new Geo();
        jakarta.setName("Jakarta");
        jakarta.setChild_id(1);
        jakarta.setParent_id(4);
        jakarta.setCode("JKT");
        jakarta.setCountryCode(CountryCode.ID);
        jakarta.setType(GeoType.PROVINCE);
        jakarta.setDescription("Capital city of Indonesia");
        jakarta = geoManager.createGeo(jakarta);

        Geo surabaya = new Geo();
        surabaya.setName("Surabaya");
        surabaya.setChild_id(2);
        surabaya.setParent_id(4);
        surabaya.setCode("SRB");
        surabaya.setCountryCode(CountryCode.ID);
        surabaya.setType(GeoType.PROVINCE);
        surabaya.setDescription("Second-largest city in Indonesia");
        surabaya = geoManager.createGeo(surabaya);

// Tạo dữ liệu cho các quận/huyện của Jakarta
        Geo southJakarta = new Geo();
        southJakarta.setName("South Jakarta");
        southJakarta.setChild_id(101);
        southJakarta.setParent_id(1);
        southJakarta.setCode("SJK");
        southJakarta.setCountryCode(CountryCode.ID);
        southJakarta.setType(GeoType.DISTRICT);
        southJakarta.setDescription("District in Jakarta");
        southJakarta = geoManager.createGeo(southJakarta);

        Geo westJakarta = new Geo();
        westJakarta.setName("West Jakarta");
        westJakarta.setChild_id(102);
        westJakarta.setParent_id(1);
        westJakarta.setCode("WJK");
        westJakarta.setCountryCode(CountryCode.ID);
        westJakarta.setType(GeoType.DISTRICT);
        westJakarta.setDescription("District in Jakarta");
        westJakarta = geoManager.createGeo(westJakarta);

// Tạo dữ liệu cho các xã/phường của South Jakarta
        Geo kebayoranLama = new Geo();
        kebayoranLama.setName("Kebayoran Lama");
        kebayoranLama.setChild_id(1001);
        kebayoranLama.setParent_id(101);
        kebayoranLama.setCode("KBL");
        kebayoranLama.setCountryCode(CountryCode.ID);
        kebayoranLama.setType(GeoType.WARD);
        kebayoranLama.setDescription("Ward in South Jakarta");
        kebayoranLama = geoManager.createGeo(kebayoranLama);

        Geo kebayoranBaru = new Geo();
        kebayoranBaru.setName("Kebayoran Baru");
        kebayoranBaru.setChild_id(1002);
        kebayoranBaru.setParent_id(101);
        kebayoranBaru.setCode("KBB");
        kebayoranBaru.setCountryCode(CountryCode.ID);
        kebayoranBaru.setType(GeoType.WARD);
        kebayoranBaru.setDescription("Ward in South Jakarta");
        kebayoranBaru = geoManager.createGeo(kebayoranBaru);
    }

    @Test
    public void testCountry() {
        Geo vn = new Geo();
        vn.setName("Việt Nam");
        vn.setChild_id(0);
        vn.setParent_id(0);
        vn.setCode("VN");
        vn.setCountryCode(CountryCode.VN);
        vn.setType(GeoType.COUNTRY);
        vn.setDescription("Việt Nam");
        vn = geoManager.createGeo(vn);

        Geo campuchia = new Geo();
        campuchia.setName("Campuchia");
        campuchia.setChild_id(1);
        campuchia.setParent_id(0);
        campuchia.setCode("PNH");
        campuchia.setCountryCode(CountryCode.KH);
        campuchia.setType(GeoType.COUNTRY);
        campuchia.setDescription("Campuchia");
        campuchia = geoManager.createGeo(campuchia);

// Tạo dữ liệu cho các xã/phường của South Jakarta
        Geo hanquoc = new Geo();
        hanquoc.setName("Hàn Quốc");
        hanquoc.setChild_id(2);
        hanquoc.setParent_id(0);
        hanquoc.setCode("KP");
        hanquoc.setCountryCode(CountryCode.KP);
        hanquoc.setType(GeoType.COUNTRY);
        hanquoc.setDescription("Hàn Quốc");
        hanquoc = geoManager.createGeo(hanquoc);
        Geo thailan = new Geo();
        thailan.setName("Thái Lan");
        thailan.setChild_id(3);
        thailan.setParent_id(0);
        thailan.setCode("TH");
        thailan.setCountryCode(CountryCode.TH);
        thailan.setType(GeoType.COUNTRY);
        thailan.setDescription("Thái Lan");
        thailan = geoManager.createGeo(thailan);
        Geo indonesia = new Geo();
        indonesia.setName("Indonesia");
        indonesia.setChild_id(4);
        indonesia.setParent_id(0);
        indonesia.setCode("ID");
        indonesia.setCountryCode(CountryCode.ID);
        indonesia.setType(GeoType.COUNTRY);
        indonesia.setDescription("Indonesia");
        indonesia = geoManager.createGeo(indonesia);
    }

}
