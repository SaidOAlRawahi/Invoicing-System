import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class Shop {
	
	static Stack<Menu> menues = new Stack<Menu>();
	static LinkedList<Product> products = new LinkedList<Product>();
	static ArrayList<Invoice> invoices = new ArrayList<Invoice>();
	static Scanner sc = new Scanner(System.in);
	static String shopName = "Shop";
	static int tel;
	static int fax;
	static String email;
	static String website;
	
	static String url = "jdbc:sqlserver://localhost:1433;" +
            "databaseName=Invoice_System;" +
            "encrypt=true;" +
            "trustServerCertificate=true";
	static String user = "sa";
	static String pass = "root";
    static Connection con = null;
    
	public static void main(String[] args) {
		
		MainMenu mainMenu = new MainMenu();
		menues.push(mainMenu);
		
		while(!menues.isEmpty()) {
			int input = getMenuInput(menues.peek());
			menues.peek().menuParts.get(input).triggerAction();
			
			if (menues.isEmpty()) {
				if(!repeatProcess("Are you sure you want to exit (Y/N)? ")) {
					menues.push(mainMenu);
				}
			}
		}
		
		System.out.println("\nGoodbye");
		sc.close();
		System.gc();
        System.runFinalization();
	}
	
	static int getMenuInput(Menu menu) {
		while(true) {
			menu.showMenu();
			String input = sc.next();
			try {
				Integer inputInt = Integer.parseInt(input);
				
				if (inputInt <= 0 || inputInt >= menu.menuParts.size()+1) {
					System.out.println("Invalid Input");
				}
				else {
					return inputInt-1;
				}
			}
			catch(Exception e) {
				System.out.println("Invalid Input: " + e);
			}
		}
	}
	
	static String getStringInput(String text) {
		System.out.print(text);
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		return input;
	}
	
	static int getIntInput(String text) {
		while(true) {
			System.out.print(text);
			String input = sc.next();
			try {
				Integer inputInt = Integer.parseInt(input);
				return inputInt;
			}
			catch(Exception e) {
				System.out.println("Invalid Input: " + e);
			}
		}
	}
	
	static float getFloatInput(String text) {
		while(true) {
			System.out.print(text);
			String input = sc.next();
			try {
				Float inputFloat = Float.parseFloat(input);
				return inputFloat;
			}
			catch(Exception e) {
				System.out.println("Invalid Input: " + e);
			}
		}
	}
	
	static boolean repeatProcess(String text) {
		while(true) {
			System.out.print(text);
			switch(sc.next()) {
			case "y":
			case "Y":
				return true;
				
			case "n":
			case "N":
				return false;
				
			default:
				System.out.println("Invalid Input");
				break;
			}
		}
	}
	
	static Product getProductById(int productId) {
		for (Product i: products) {
			if(i.getId() == productId) {
				return i;
			}
		}
		return null;
	}
	
	static Invoice getInvoiceById(int invoiceNo) {
		for (Invoice i : invoices) {
			if (i.getInvoiceNo() == invoiceNo) {
				return i;
			}
		}
		return null;
	}
	
	static void printInvoice(Invoice invoice) {
		System.out.print("\n " + "=".repeat(50) + Shop.shopName + "=".repeat(50) + "\n");
		int headingLength = Shop.shopName.length()+102;
		System.out.printf("|%" + ((headingLength/8)*7) + "s%-" + (headingLength/8) + "s|\n","Invoice NO. ",invoice.getInvoiceNo());
		System.out.printf("|%-" + ((headingLength/4)-2) + "s%" + (headingLength/4) + "s| %-" + ((headingLength/4)) + "s%"
				+ (headingLength/4) + "s|\n", "Telephone:", Shop.tel, "Fax:", Shop.fax);
		System.out.printf("|%-" + ((headingLength/4)-2) + "s%" + (headingLength/4) + "s| %-" + ((headingLength/4)) + "s%"
				+ (headingLength/4) + "s|\n", "Email:", Shop.email, "Website:", Shop.website);
		System.out.println("=".repeat(headingLength-1));
		System.out.printf("|%-" + ((headingLength/5)) + "s%-" + (headingLength/5) + "s%-" + ((headingLength/5)) + "s%-"
		+ ((headingLength/5)-1) + "s%-" + (headingLength/5) + "s|\n", "Product Id","Product Name", "Unit Price", "Quantity", "Price");
		for (InvoiceItem i: invoice.items) {
			System.out.printf("|%-" + ((headingLength/5)) + "s%-" + (headingLength/5) + "s%-" + ((headingLength/5)) + "s%-" 
					+ ((headingLength/5)-1) + "s%-" + (headingLength/5) + "s|\n", 
					i.getId(),i.getName(),i.getPricePerUnit(),i.getQuantity(),i.getTotalAmount());
		}
		System.out.println("-".repeat(headingLength-1));
		System.out.printf("|%-" + (((headingLength/5)*3)-1) + "s%" + (headingLength/5) + "s%-" + (headingLength/5) + "s|\n"
				,invoice.getDate(), "Total: ",invoice.getTotalAmount());
		System.out.printf("|%-" + (((headingLength/5)*3)-1) + "s%" + (headingLength/5) + "s%-" + (headingLength/5) + "s|\n"
				,invoice.getCustomerPhoneNo(), "Paid: ",invoice.getAmountPaid());
		System.out.printf("|%-" + (((headingLength/5)*3)-1) + "s%" + (headingLength/5) + "s%-" + (headingLength/5) + "s|\n"
				,invoice.getCustomerFirstName() + " " + invoice.getCustomerLastName(), "Balance: ",invoice.getBalance());
		System.out.println("=".repeat(headingLength-1)+"\n");

	}
	
	static void saveAndSerialize() throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Serialized Data.txt"));
        out.writeObject(products);
        out.writeObject(invoices);
        out.close();
        System.out.println("\nData are serialized in a file.");
        saveDataIntoDatabase();
        
	}
	
	static void fetchData() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Serialized Data.txt"));
        LinkedList<Product> fetchedProducts = (LinkedList<Product>) in.readObject();
        ArrayList<Invoice> fetchedInvoices = (ArrayList<Invoice>) in.readObject();
        in.close();
        if (!fetchedProducts.isEmpty()||!fetchedInvoices.isEmpty()) {
        	products = fetchedProducts;
        	invoices = fetchedInvoices;
        }
        else {
        	System.out.println("Not all data is retrieved from file.");
        	fetchDataFromDB();
        }
        
	}
	
	static void fetchDataFromDB() {
		System.out.println("Fetching data from Data Base...");
		try {
        	Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);
            
            Statement st = con.createStatement();
            
/*----------------------------fetching products----------------------------------*/            
            String sql = "IF EXISTS (SELECT * FROM sys.tables WHERE name = 'products')\r\n"
            		+ "BEGIN\r\n"
            		+ "   SELECT * FROM products\r\n"
            		+ "END";
            ResultSet resultSet = st.executeQuery(sql);
            
            products.clear();
            while(resultSet.next()){
            	Product productFromDatabase = new Product();
            	productFromDatabase.setId(resultSet.getInt(1));
            	productFromDatabase.setName(resultSet.getString(2));
            	productFromDatabase.setPrice(resultSet.getFloat(3));
            	products.add(productFromDatabase);
            }
            
/*----------------------------fetching invoices----------------------------------*/            
            invoices.clear();
            sql = "IF EXISTS (SELECT * FROM sys.tables WHERE name = 'invoices')\r\n"
            		+ "BEGIN\r\n"
            		+ "   SELECT * FROM invoices\r\n"
            		+ "END";
            resultSet = st.executeQuery(sql);
            invoices.clear();
            while(resultSet.next()){
            	Invoice invoiceFromDatabase = new Invoice();
            	invoiceFromDatabase.setInvoiceNo(resultSet.getInt(1));
            	invoiceFromDatabase.setCustomerFirstName(resultSet.getString(2));
            	invoiceFromDatabase.setCustomerLastName(resultSet.getString(3));
            	invoiceFromDatabase.setCustomerPhoneNo(resultSet.getInt(4));
            	Date date = resultSet.getDate(5);
            	invoiceFromDatabase.setInitiatedDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            	invoiceFromDatabase.setPaidAmount(resultSet.getFloat(6));
            	invoices.add(invoiceFromDatabase);
            }
/*----------------------------fetching invoices items----------------------------------*/            
            sql = "IF EXISTS (SELECT * FROM sys.tables WHERE name = 'invoice_items')\r\n"
            		+ "BEGIN\r\n"
            		+ "   SELECT * FROM invoice_items\r\n"
            		+ "END";
            resultSet = st.executeQuery(sql);
            
            while(resultSet.next()){
            	InvoiceItem invoiceItemFromDatabase = new InvoiceItem();
            	
            	invoiceItemFromDatabase.setProduct(getProductById(resultSet.getInt(2)));
            	invoiceItemFromDatabase.setQuantity(resultSet.getFloat(4));
            	
            	Invoice parentInvoice = getInvoiceById(resultSet.getInt(3));
            	invoices.get(invoices.indexOf(parentInvoice)).items.add(invoiceItemFromDatabase);
            }
            
            con.close();
        }
		
		
		
        catch(Exception ex) {
        	System.out.println("Something went wrong: ");
        	System.err.println(ex);
        }
	}

	static void saveDataIntoDatabase() {
		try {
        	Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, user, pass);
            
            Statement st = con.createStatement();
            resetDatabase(st);
/*----------------------------------------adding data to databasee----------------------------------------*/
            String sql;
            
            System.out.println("\nsaving data into database...");
            for (Invoice i: invoices) {
            	System.out.println("check 1");
            	sql = "INSERT INTO invoices VALUES(\r\n"
            			+ "	" + i.getInvoiceNo() + ",\r\n"
            			+ "	'" + i.getCustomerFirstName() + "',\r\n"
            			+ "	'" + i.getCustomerLastName() + "',\r\n"
            			+ "	" + i.getCustomerPhoneNo() +  ",\r\n"
            			+ "	" + "CONVERT(DATE, '" + i.getDate() + "'),\r\n"
            			+ "	" + i.getAmountPaid() + "\r\n"
            			+ ");";
            	st.executeUpdate(sql);
            }
            System.out.println("Invoices are saved.");
            
            System.out.println("\nsaving data into database...");
            for (Product i: products) {
            	System.out.println("check 2");

            	sql = "INSERT INTO products VALUES(\r\n"
            			+ "	" + i.getId() + ",\r\n"
            			+ "	'" + i.getName()+ "',\r\n"
            			+ "	" + i.getPrice() + "\r\n"
            			+ ");";
            	st.executeUpdate(sql);
            }
            System.out.println("Products are saved.");
            
            System.out.println("\nsaving data into database...");
            for (Invoice i : invoices) {
            	for(InvoiceItem j : i.items) {
                	System.out.println("check 3");

            		sql = "INSERT INTO invoice_items VALUES(\r\n"
            				+ "	" + j.getId() + ",\r\n"
            				+ "	" + i.getInvoiceNo() + ",\r\n"
            				+ "	" + j.getQuantity() + "\r\n"
            				+ ");";
                	st.executeUpdate(sql);
            	}
            }
            System.out.println("Items inside each invoice are saved.");
/*-----------------------------------end---------------------------------------*/
            con.close();
        }
        catch(Exception ex) {
        	System.out.println("Something went wrong: ");
        	System.err.println(ex);
        }
	}

	static void resetDatabase(Statement st) throws Exception {

/*----------------------------------------Resetting all tables----------------------------------------*/
            st.executeUpdate("DROP TABLE IF EXISTS invoice_items,invoices,products;");

/*----------------------------------------creating Invoices table----------------------------------------*/
            String sql = "CREATE TABLE invoices(\r\n"
            		+ "		invoice_no INT PRIMARY KEY,\r\n"
            		+ "		customer_first_name TEXT,\r\n"
            		+ "		customer_last_name TEXT,\r\n"
            		+ "		customer_phone_no INT,\r\n"
            		+ "		initiated_date DATE,\r\n"
            		+ "		paid_amount FLOAT\r\n"
            		+ "	)\r\n";
            int returnedVal = st.executeUpdate(sql);
            if (returnedVal==0) {
            	System.out.println("\ninvoices table is created");            	
            }
            
/*----------------------------------------creating Products table----------------------------------------*/
            sql = "CREATE TABLE products(\r\n"
            		+ "		product_id INT PRIMARY KEY,\r\n"
            		+ "		product_name text,\r\n"
            		+ "		product_price FLOAT\r\n"
            		+ "	)\r\n";
            returnedVal = st.executeUpdate(sql);
            if (returnedVal==0) {
            	System.out.println("\nproducts table is created");            	
            }
            
/*----------------------------------------creating Invoice_items table----------------------------------------*/
            sql = "CREATE TABLE invoice_items(\r\n"
            		+ "		id INT PRIMARY KEY IDENTITY(1,1),\r\n"
            		+ "		product_id INT, \r\n"
            		+ "		invoice_id INT ,\r\n"
            		+ "		quanity FLOAT,\r\n"
            		+ "		FOREIGN KEY (product_id) REFERENCES products(product_id),\r\n"
            		+ "		FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_no)\r\n"
            		+ "	)\r\n";
            returnedVal = st.executeUpdate(sql);
            if (returnedVal==0) {
            	System.out.println("\ninvoice items table is created");            	
            }
	}
	
	
}
