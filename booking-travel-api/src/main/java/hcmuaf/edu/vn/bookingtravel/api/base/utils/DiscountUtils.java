package hcmuaf.edu.vn.bookingtravel.api.base.utils;

import java.util.Random;

/**
 * @author : Vũ Văn Minh
 * @mailto : duanemellow19@gmail.com
 * @created : 27/04/2023, Thứ Năm
 **/
public class DiscountUtils {
    public static double discount() {
        double number = (new Random().nextInt(100) + 1);
        return number / 100;
    }

}
