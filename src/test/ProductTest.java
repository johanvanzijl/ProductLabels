package test;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import za.co.consnet.products.Product;
import za.co.consnet.producttypes.ProductTypeCol;
import za.co.consnet.suppliers.Supplier.Rounding;
import za.co.consnet.suppliers.Supplier.SuppType;
import za.co.consnet.suppliers.SupplierCol;

public class ProductTest {
	protected static ProductTypeCol productTypes = new ProductTypeCol();
    protected static SupplierCol suppliers = new SupplierCol(); 
    
    protected void scaffold() {
    	//Setup the scaffolding for test
    	productTypes.clear();
    	suppliers.clear();
    	productTypes.add(1000, 1099, new BigDecimal("0.5"), 9); //Fruit
	    productTypes.add(1100, 1199, new BigDecimal("0.3"), 14); //Apples
		productTypes.add(1200, 1299, new BigDecimal("0.4"), 6); //Bananas
	    productTypes.add(1300, 1399, new BigDecimal("0.55"), 9); //Berries
		productTypes.add(1400, 1999, new BigDecimal("0.5"), 9); //Fruit
		suppliers.add(166, SuppType.PREMIUM, BigDecimal.ZERO, new BigDecimal("0.1"), 0, Rounding.RANDS);
		suppliers.add(64, SuppType.POOR, new BigDecimal("-2"), BigDecimal.ZERO, -3, Rounding.CENTS);
    }
    
    protected Product getProduct(int id, int suppId, String desc, Date delDate, BigDecimal unitPrice, int qty) {
    	return new Product(id, suppliers.get(suppId), productTypes.get(id), desc,delDate, unitPrice, qty);
    }
    
    protected void testPrices(int prodId, int supplier, int unitPrice, String description, String answer) {
    	Product product = getProduct(prodId, supplier, description,new Date(), new BigDecimal(unitPrice), 5);
		if(product.getPrice().compareTo((new BigDecimal(answer))) != 0) {
		  String price = product.getPrice().toString();
		  fail("The price for ".concat(description).concat(" is calculated incorrectly:").concat(price));
		}		
	}
    
    protected void testExpDate(int prodId, int supplier, Date delDate, String description, int answer) {
    	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(delDate);
		c.add(Calendar.DATE, answer);
		Date answerDate = c.getTime();
    	Product product = getProduct(prodId, supplier, description, delDate, new BigDecimal(1000), 5);
    	if(!fmt.format(product.getExpiryDate()).equals(fmt.format(answerDate))) {
    		fail("The expiry date for ".concat(description).concat(" is calculated incorrectly:").concat(product.getExpiryDate().toString()));
    	}		
	}
    
   
	@Test
	public void testGetPrice() {
    	scaffold();
    	testPrices(1110, 100, 1000, "Apple 1110", "13.00");
    	testPrices(1210, 100, 1000, "Banana 1210", "14.00");
    	testPrices(1310, 100, 1000, "Berry 1310", "15.50");
    	testPrices(1410, 100, 1000, "Fruit 1410", "15.00");	
    	//Premium
    	testPrices(1110, 166, 1000, "Apple 1110 - Premium", "14.00");
    	testPrices(1210, 166, 1000, "Banana 1210 - Premium", "15.00");
    	testPrices(1310, 166, 1000, "Berry 1310 - Premium", "17.00");  //testing roundup as well.
    	testPrices(1410, 166, 1000, "Fruit 1410- Premium", "16.00");
    	//Poor
    	testPrices(1110, 64, 1000, "Apple 1110 - Poor", "11.00");
    	testPrices(1210, 64, 1000, "Banana 1210 - Poor", "12.00");
    	testPrices(1310, 64, 1000, "Berry 1310 - Poor", "13.50");
    	testPrices(1410, 64, 1000, "Fruit 1410- Poor", "13.00");
    	//test negative
    	testPrices(1110, 64, 10, "Apple 1110 - Negative", "0.00");
	}

	@Test
	public void testGetExpiryDate() {
    	scaffold();
    	//Normal
    	testExpDate(1110, 100, new Date(), "Apple 1110", 14);
    	testExpDate(1210, 100, new Date(), "Banana 1210", 6);
    	testExpDate(1310, 100, new Date(), "Berry 1310", 9);
    	testExpDate(1410, 100, new Date(), "Fruit 1410", 9);
    	//Poor
    	testExpDate(1110, 64, new Date(), "Apple 1110", 11);
    	testExpDate(1210, 64, new Date(), "Banana 1210", 3);
    	testExpDate(1310, 64, new Date(), "Berry 1310", 6);
    	testExpDate(1410, 64, new Date(), "Fruit 1410", 6);
	}

}
