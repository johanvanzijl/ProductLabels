package za.co.consnet.suppliers;

import java.math.BigDecimal;
import java.util.HashMap;

import za.co.consnet.suppliers.Supplier.Rounding;
import za.co.consnet.suppliers.Supplier.SuppType;

public class SupplierCol {
	protected HashMap<Integer,Supplier> suppliers = new HashMap<Integer, Supplier>();
	
	public void clear() {
		suppliers.clear();
	}
	
	public void add(int id, SuppType type, BigDecimal markup, BigDecimal markupPerc, int expiry, Rounding round) {
		Supplier supplier = new Supplier(id, type, markup, markupPerc, expiry,round);
		suppliers.put(id, supplier);
	}
	
	public Supplier get(int key) {
		Supplier supplier = suppliers.get(key);	
		if(supplier == null) {
	      //Return a new supplier with default behavior if nothing found
			return new Supplier(key, SuppType.NORMAL, BigDecimal.ZERO, BigDecimal.ZERO, 0, Rounding.CENTS);
		}
		return supplier;
	}
}


