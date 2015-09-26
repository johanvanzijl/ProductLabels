package za.co.consnet.labels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import za.co.consnet.products.Product;
import za.co.consnet.producttypes.ProductTypeCol;
import za.co.consnet.suppliers.Supplier.Rounding;
import za.co.consnet.suppliers.Supplier.SuppType;
import za.co.consnet.suppliers.SupplierCol;

public class LabelPrinter {
    protected ProductTypeCol productTypes = new ProductTypeCol();
    protected SupplierCol suppliers = new SupplierCol();
	protected ArrayList<Product> products = new ArrayList<Product>();
	private   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	public LabelPrinter() {
	  //Initialize our configuration
	  productTypes.add(1000, 1099, new BigDecimal("0.5"), 9); //Fruit
	  productTypes.add(1100, 1199, new BigDecimal("0.3"), 14); //Apples
	  productTypes.add(1200, 1299, new BigDecimal("0.4"), 6); //Bananas
	  productTypes.add(1300, 1399, new BigDecimal("0.55"), 9); //Berries
	  productTypes.add(1400, 1999, new BigDecimal("0.5"), 9); //Fruit
	  
	  suppliers.add(114, SuppType.PREMIUM, BigDecimal.ZERO, new BigDecimal("0.1"), 0, Rounding.RANDS);
	  suppliers.add(166, SuppType.PREMIUM, BigDecimal.ZERO, new BigDecimal("0.1"), 0, Rounding.RANDS);
	  suppliers.add(64, SuppType.POOR, new BigDecimal("-2"), BigDecimal.ZERO, -3, Rounding.CENTS);
	  suppliers.add(302, SuppType.POOR, new BigDecimal("-2"), BigDecimal.ZERO, -3, Rounding.CENTS);
	}
	
	
	public BufferedReader getReader() throws FileNotFoundException  {
	  return new BufferedReader(new FileReader("products.txt"));
	}
	
	public Product getProduct(String[] columns) throws NumberFormatException, ParseException {
	    return new Product(new Integer(columns[1]),                   //product code
	    		           suppliers.get(new Integer(columns[0])),    //supplier 
	    		           productTypes.get(new Integer(columns[1])), //product type
	    		           columns[2],                                //description
	    		           (Date) dateFormat.parse(columns[3]),					  //delivery date
	    		           new BigDecimal(new Integer(columns[4])),                   //unit price cents
	    		           new Integer(columns[5]));                  //qty
	}
	
	public String getOutLine(Product product) {
		DecimalFormat df = new DecimalFormat("####0.00");
		String str = String.format("%1$8s",df.format(product.getPrice()));
		str = "R".concat(str.concat(dateFormat.format(product.getExpiryDate())));
		return str.concat(product.getDescription(31)).concat("\n");
	}
	
	public void printLabels(String inFile, String outFile) {
		try {
		      BufferedReader reader = new BufferedReader(new FileReader(inFile));
		      FileWriter writer = new FileWriter(new File(outFile));
			  reader.readLine(); //get rid of headings
			  String line = reader.readLine();
			  
			  while (line != null) {
				  String[] columns = line.split("\\t");
				  Product product = this.getProduct(columns);
				  for(int i=1; i<=product.getQty(); i++ ) {
				      writer.write(this.getOutLine(product));;
				  }
				  line = reader.readLine();
			  }
			  reader.close();
			  writer.close();
		    } catch (IOException e) {
				e.printStackTrace();
	        } catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.print("Usage LabelPrinter [input-file] [output-file]");
			return;
		}
		LabelPrinter printer = new LabelPrinter();
		printer.printLabels(args[0], args[1]);
	    
    }
}
