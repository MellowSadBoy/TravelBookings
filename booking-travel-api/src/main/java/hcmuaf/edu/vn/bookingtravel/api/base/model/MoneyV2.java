package hcmuaf.edu.vn.bookingtravel.api.base.model;


import hcmuaf.edu.vn.bookingtravel.api.enums.CurrencyCode;
import lombok.Data;

@Data
public class MoneyV2 {
    private double amount;
    private CurrencyCode currencyCode;

    public MoneyV2() {
    }


}
