import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

abstract public class MenuPart {
	int clicks = 0;
	String title = "myTitle";

	void triggerAction() {
		clicks++;
	}

}

class ExitPart extends MenuPart {
	ExitPart(String title) {
		this.title = title;
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		Shop.menues.pop();
	}

}

/*-------------------------------Main Menu------------------------------*/
class ShopSettingsPart extends MenuPart {
	Menu returnedMenu;

	ShopSettingsPart() {
		this.title = "Shop Settings";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		returnedMenu = new ShopSettingsMenu();
		Shop.menues.push(returnedMenu);
	}

}

class ManageShopItemsPart extends MenuPart {
	Menu returnedMenu;

	ManageShopItemsPart() {
		this.title = "Manage Shop Items";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		returnedMenu = new ManageShopMenu();
		Shop.menues.push(returnedMenu);
	}

}

class CreateNewInvoicePart extends MenuPart {
	Invoice resultedInvoice;

	CreateNewInvoicePart() {
		this.title = "Create New Invoice";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		do {
			int invoiceNo = Shop.getIntInput("Enter the bill number: ");
			Invoice newInvoice = Shop.getInvoiceById(invoiceNo);
			if (newInvoice != null) {
				System.out.println("Invoice already exists.");
			} else {
				newInvoice = new Invoice();
				newInvoice.setInvoiceNo(invoiceNo);
				newInvoice.setCustomerFirstName(Shop.getStringInput("Enter the customer first name: "));
				newInvoice.setCustomerLastName(Shop.getStringInput("Enter the customer last name: "));
				newInvoice.setCustomerPhoneNo(Shop.getIntInput("Enter the customer phone Number: "));
				newInvoice.setInitiatedDate(LocalDate.now());

				do {
					int productId = Shop.getIntInput("Enter the id of the product: ");
					Product product = Shop.getProductById(productId);
					if (product != null) {
						float quantity = Shop.getFloatInput("Enter the quantity: ");
						InvoiceItem item = new InvoiceItem();
						item.setProduct(product);
						item.setQuantity(quantity);
						newInvoice.items.add(item);
					} else {
						System.out.println("Product with an id of " + productId + " does not Exist...");
					}
				} while (Shop.repeatProcess("Do you want to enter another item (Y/N)? "));

				while (true) {
					if (!newInvoice.setPaidAmount(Shop.getFloatInput("Enter the paid amount: "))) {
						System.out.println("Insufficient payment.");
					} else {
						break;
					}
				}
				Shop.invoices.add(newInvoice);
				System.out.println("New invoice is added");
			}
			Shop.printInvoice(newInvoice);
			resultedInvoice = newInvoice;
		} while (Shop.repeatProcess("Do you want to add another invoice (Y/N)? "));
		try {
			Shop.saveAndSerialize();
		} catch (Throwable t) {
			System.out.println("Could save data due to: " + t);
		}

	}

}

class ReportStatsPart extends MenuPart {
	int noOfProducts;
	int noOfInvoices;
	float totalSales;

	ReportStatsPart() {
		this.title = "Report: Statistics";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		float totalSales = 0;
		for (Invoice i : Shop.invoices) {
			totalSales += i.getTotalAmount();
		}
		noOfProducts = Shop.products.size();
		noOfInvoices = Shop.invoices.size();
		this.totalSales = totalSales;
		System.out.println("| No Of Items | No of Invoices |   Total Sales   |");
		System.out.println("|-------------|----------------|-----------------|");
		System.out.printf("| %-11s | %-14s | %-15s |\n", noOfProducts, noOfInvoices, totalSales);
	}

}

class ReportAllInvoicesPart extends MenuPart {
	ArrayList<Invoice> returnedInvoices;

	ReportAllInvoicesPart() {
		this.title = "Report: All invoices";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		System.out
				.println("| Invoice No. | Invoice Date |        Customer Name        | No. Items | Total | Balance |");
		System.out
				.println("|-------------|--------------|-----------------------------|-----------|-------|---------|");
		for (Invoice i : Shop.invoices) {
			System.out.printf("|%-13s|%-14s|%-29s|%-11s|%-7s|%-9s|\n", i.getInvoiceNo(), i.getDate(),
					i.getCustomerFirstName() + " " + i.getCustomerLastName(), i.items.size(), i.getTotalAmount(),
					i.getBalance());
		}
		this.returnedInvoices = Shop.invoices;
	}

}

class SearchInvoicePart extends MenuPart {
	Invoice resultedInvoice;

	SearchInvoicePart() {
		this.title = "Search (1) Invoice";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		int input = Shop.getIntInput("Enter the No. of the invoice: ");
		Invoice invoice = Shop.getInvoiceById(input);
		if (invoice != null) {
			this.resultedInvoice = invoice;
			Shop.printInvoice(invoice);
		} else {
			System.out.println("Invoice with ID of " + input + " does not exists.");
		}
	}

}

class AppStatsPart extends MenuPart {
	AppStatsPart() {
		this.title = "Program Statistics";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		for (MenuPart i : Shop.menues.peek().menuParts) {
			System.out.println(i.title + ": " + i.clicks);
		}
	}

}
class Insert1MillionInvoicesPart extends MenuPart {
	Insert1MillionInvoicesPart() {
		this.title = "Insert 1 Million Invoices";
	}

	@Override
	void triggerAction() {
		super.triggerAction();

		long endTime = 0;
		long startTime = 0;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			Shop.con = DriverManager.getConnection(Shop.url, Shop.user, Shop.pass);
			
			Statement st = Shop.con.createStatement();
			Shop.resetDatabase(st);
			System.out.println("Inserting the rows to db....");

//-----------------------------average time is 5 minutes for this to store 1 million users.---------------------------------
			/*startTime = System.nanoTime();
			for (int i = 0; i < 1_000_000; i++) {
				String sql = "INSERT INTO invoices VALUES(\r\n"
            			+ "	" + i + ",\r\n"
            			+ "	'Said" + i + "',\r\n"
            			+ "	'Alrawahi" + i + "',\r\n"
            			+ "	99" + i + ",\r\n"
            			+ "	" + "getdate(),\r\n"
            			+ "	" + i + ".22\r\n"
            			+ ");";
				st.executeUpdate(sql);
			}
			endTime   = System.nanoTime();*/
			
//-----------------------------This one is taking over 2 hours so No.---------------------------------
			/*startTime = System.nanoTime();
			String sql = "";
			for (int i = 0; i < 1_000_000; i++) {
				sql += "INSERT INTO invoices VALUES(\r\n"
            			+ "	" + i + ",\r\n"
            			+ "	'Said" + i + "',\r\n"
            			+ "	'Alrawahi" + i + "',\r\n"
            			+ "	99" + i + ",\r\n"
            			+ "	" + "getdate(),\r\n"
            			+ "	" + i + ".22\r\n"
            			+ ");\r\n";
			}
			st.executeUpdate(sql);
			endTime   = System.nanoTime();*/
			
//-----------------------------average time is 2:33---------------------------------
			PreparedStatement statement = Shop.con.prepareStatement("INSERT INTO invoices VALUES (?, ?, ?, ?, getdate(), ?)");
			startTime = System.nanoTime();
			for (int i = 0; i < 1_000_000; i++) {
				statement.setInt(1,i);
				statement.setString(2,"Said"+i);
				statement.setString(3,"Alrawahi"+i);
				statement.setInt(4,99+i);
				statement.setFloat(5,(float) (i+0.22));
				statement.addBatch();
			}
			statement.executeBatch();
			endTime   = System.nanoTime();
			
			Shop.con.close();
		}
		catch(Exception ex) {
			System.out.println("Something went wrong: ");
			System.err.println(ex);
		}
		long totalTime = endTime - startTime;
		int minutes = (int) (totalTime / 1_000_000_000) / 60;
		int seconds = (int) (totalTime / 1_000_000_000) % 60;
		System.out.println("time taken to insert 1M records took " + minutes + ":" + seconds);
		
	}

}
/*---------------------------------End----------------------------------*/

/*-----------------------------Shop Settings----------------------------*/
class LoadDataPart extends MenuPart {
	LoadDataPart() {
		this.title = "Load Data";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		try {
			Shop.fetchData();
		} catch (Throwable t) {
			System.out.println("Could save fetch data due to: " + t);
		}
	}

}

class SetShopNamePart extends MenuPart {
	SetShopNamePart() {
		this.title = "Set Shop Name";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		Shop.shopName = Shop.getStringInput("Enter the name of the shop: ");
		System.out.println("The name of the shop is changed to: " + Shop.shopName);
	}

}

class SetInvoiceHeaderPart extends MenuPart {
	SetInvoiceHeaderPart() {
		this.title = "Set Invoice Header";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		Shop.tel = Shop.getIntInput("Enter the telephone number of the shop: ");
		Shop.fax = Shop.getIntInput("Enter the fax number of the shop: ");
		Shop.email = Shop.getStringInput("Enter the email of the shop: ");
		Shop.website = Shop.getStringInput("Enter the website address of the shop: ");
	}

}
/*---------------------------------End----------------------------------*/

/*------------------------------Manage Shop-----------------------------*/
class AddItemsPart extends MenuPart {
	AddItemsPart() {
		this.title = "Add Items";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		do {
			Product newProduct = new Product();
			int productId = Shop.getIntInput("Enter the ID of the new product: ");
			if (Shop.getProductById(productId) == null) {
				newProduct.setId(productId);
				newProduct.setName(Shop.getStringInput("Enter the name of the new product: "));
				newProduct.setPrice(Shop.getFloatInput("Enter the price of the product: "));
				Shop.products.add(newProduct);
				System.out.println("New Product is added.");
			} else {
				System.out.println("Product with an id of " + productId + " already exists...");
			}
		} while (Shop.repeatProcess("Do you want to add more items (Y/N)? "));
		try {
			Shop.saveAndSerialize();
		} catch (Throwable t) {
			System.out.println("Could save data due to: " + t);
		}
	}

}

class DeleteItemsPart extends MenuPart {
	DeleteItemsPart() {
		this.title = "Delete Items";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		do {
			int productId = Shop.getIntInput("Enter the ID of the new product: ");
			Product product = Shop.getProductById(productId);
			if (product != null) {
				Shop.products.remove(product);
				System.out.println(productId + " product is deleted.");
			} else {
				System.out.println("Product with an id of " + productId + " does not Exist...");
			}
		} while (Shop.repeatProcess("Do you want to delete more items (Y/N)? "));
		try {
			Shop.saveAndSerialize();
		} catch (Throwable t) {
			System.out.println("Could save data due to: " + t);
		}
	}

}

class ChangeItemPricePart extends MenuPart {
	ChangeItemPricePart() {
		this.title = "Change Item Price";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		do {
			int productId = Shop.getIntInput("Enter the ID of the product you want to change its price: ");
			Product product = Shop.getProductById(productId);
			if (product != null) {
				float newPrice = Shop.getFloatInput("Enter the new price of " + productId + ": ");
				Shop.products.get(Shop.products.indexOf(product)).setPrice(newPrice);
			} else {
				System.out.println("Product with an id of " + productId + " does not Exist...");
			}
		} while (Shop.repeatProcess("Do you want to change the price another item (Y/N)? "));
		try {
			Shop.saveAndSerialize();
		} catch (Throwable t) {
			System.out.println("Could save data due to: " + t);
		}
	}

}

class ReportAllItemsPart extends MenuPart {
	LinkedList<Product> returnedProducts;

	ReportAllItemsPart() {
		this.title = "Report All Items";
	}

	@Override
	void triggerAction() {
		super.triggerAction();
		System.out.println("| Product ID | Product Name |    Price    |");
		System.out.println("|------------|--------------|-------------|");
		for (Product i : Shop.products) {
			System.out.printf("| %-10s | %-12s | %-11s |\n", i.getId(), i.getName(), i.getPrice());
		}
		this.returnedProducts = Shop.products;
	}

}
/*---------------------------------End----------------------------------*/