package za.co.consnet.suppliers;

import java.math.BigDecimal;

public class Supplier {
	    public static enum SuppType { PREMIUM, POOR, NORMAL};
	    public static enum Rounding { RANDS, CENTS};
	    
	    public final int id;
	    public final SuppType type;
	    public final BigDecimal markupAmt;
	    public final BigDecimal markupPerc;
	    public final int expiry;
	    public final Rounding round;
	    
	    
	    public Supplier(int id,  SuppType type, BigDecimal markupAmt, BigDecimal markupPerc, int expiry, Rounding round ) {
	    	this.id = id;
	    	this.type = type;
	    	this.markupAmt = markupAmt;
	    	this.markupPerc = markupPerc;
	    	this.expiry = expiry;
	    	this.round = round;
	    }
	    
	}


