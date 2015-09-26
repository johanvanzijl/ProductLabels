package za.co.consnet.producttypes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class ProductTypeCol {
	protected TreeMap<Integer,ProductType> productTypes = new TreeMap<Integer, ProductType>();
	
	public void clear() {
		productTypes.clear();
	}
	
	public void add(int from, int to, BigDecimal markup, int expiry) {
		ProductType productType = new ProductType(from, to, markup, expiry);
		productTypes.put(from, productType);
	}
	
	public ProductType get(int key) {
		Map.Entry<Integer,ProductType> entry = productTypes.floorEntry(key);
		return entry.getValue();		
	}
}


