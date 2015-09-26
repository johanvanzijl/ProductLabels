package za.co.consnet.products;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import za.co.consnet.producttypes.ProductType;
import za.co.consnet.suppliers.Supplier;
import za.co.consnet.suppliers.Supplier.Rounding;

public class Product {
	protected int id;
	protected Supplier supplier;
	protected ProductType productType;
	protected String description;
	protected Date deliveryDate;
	protected BigDecimal unitPrice;
	protected int qty;
	
	public Product(int id, Supplier supplier, ProductType productType, String description, Date deliveryDate, BigDecimal unitPrice, int qty) {
		this.id = id;
		this.supplier = supplier;
		this.productType = productType;
		this.description = description;
	    this.deliveryDate = deliveryDate;
	    this.unitPrice = unitPrice;
	    this.qty = qty;
	}
	
	public BigDecimal getPrice() {
		BigDecimal markup = BigDecimal.ONE.add(productType.markup).add(supplier.markupPerc);
		BigDecimal price = this.getUnitPrice().multiply(markup).divide(new BigDecimal(100)).add(supplier.markupAmt); 
		if(price.compareTo(BigDecimal.ZERO) < 0) {
			return BigDecimal.ZERO;
		}
		if(supplier.round == Rounding.RANDS ) {
			return price.setScale(0, BigDecimal.ROUND_HALF_UP);
		} else {
			return price.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	public Date getExpiryDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(deliveryDate);
		c.add(Calendar.DATE, productType.expiry);
		c.add(Calendar.DATE, supplier.expiry);
		return c.getTime();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public String getDescription(int length) {
		if(length>=description.length()) {
			return description;
		} else {
		    return description.substring(0, length);
		}
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
