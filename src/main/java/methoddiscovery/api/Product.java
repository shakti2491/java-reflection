package methoddiscovery.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Product {
    private double price;
    private String name;
    private long quantity;
    private Date expirationDate;
}
