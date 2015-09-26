package za.co.consnet.producttypes;

import java.math.BigDecimal;

public class ProductType {
    public final int from;
    public final int to;
    public final BigDecimal markup;
    public final int expiry;
    
    
    public ProductType(int from, int to, BigDecimal markup, int expiry) {
    	this.from = from;
    	this.to = to;
    	this.markup = markup;
    	this.expiry = expiry;
    }
}
