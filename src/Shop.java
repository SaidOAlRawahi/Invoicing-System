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
	
	public static void main(String[] args) {
		MainMenu mainMenu = new MainMenu();
		menues.push(mainMenu);
		System.out.print("\n " + "=".repeat(50) + Shop.shopName + "=".repeat(50) + "\n");
		int headingLength = Shop.shopName.length()+102;
		System.out.printf("|%-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s||%-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s|\n", "Telephone:", Shop.tel, "Fax:", Shop.fax);
		System.out.printf("|%-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s||%-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s|\n", "Email:", Shop.email, "Website:", Shop.website);
		System.out.println("=".repeat(headingLength-1));
		System.out.printf("|%-" + ((headingLength/5)) + "s%-" + (headingLength/5) + "s%-" + ((headingLength/5)) + "s%-" + ((headingLength/5)-1) + "s%-" + (headingLength/5) + "s|\n", "Product Id","Product Name", "Unit Price", "Quantity", "Price");
	
		
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
		for (Product i: Shop.products) {
			if(i.getId() == productId) {
				return i;
			}
		}
		return null;
	}
	
	static void printInvoice(Invoice invoice) {
		System.out.print("\n " + "=".repeat(50) + Shop.shopName + "=".repeat(50) + "\n");
		int headingLength = Shop.shopName.length()+102;
		System.out.printf("|%-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s| %-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s|\n", "Telephone:", Shop.tel, "Fax:", Shop.fax);
		System.out.printf("|%-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s| %-" + ((headingLength/4)-1) + "s%" + (headingLength/4) + "s|\n", "Email:", Shop.email, "Website:", Shop.website);
		System.out.println("=".repeat(headingLength-1));
		System.out.printf("|%-" + ((headingLength/5)) + "s%-" + (headingLength/5) + "s%-" + ((headingLength/5)) + "s%-" + ((headingLength/5)-1) + "s%-" + (headingLength/5) + "s|\n", "Product Id","Product Name", "Unit Price", "Quantity", "Price");
		for (InvoiceItem i: invoice.items) {
			System.out.printf("|%-" + ((headingLength/5)) + "s%-" + (headingLength/5) + "s%-" + ((headingLength/5)) + "s%-" 
					+ ((headingLength/5)-1) + "s%-" + (headingLength/5) + "s|\n", 
					i.getId(),i.getName(),i.getPricePerUnit(),i.getQuantity(),i.getTotalAmount());
		}
		System.out.println("-".repeat(headingLength-1));
		System.out.printf("|%-" + (((headingLength/5)*3)-1) + "s%" + (headingLength/5) + "s%-" + (headingLength/5) + "s|\n"
				,invoice.getDate(), "Total: ",invoice.getTotalAmount());
		System.out.printf("|%-" + (((headingLength/5)*3)-1) + "s%" + (headingLength/5) + "s%-" + (headingLength/5) + "s|\n"
				," ", "Paid: ",invoice.getAmountPaid());
		System.out.printf("|%-" + (((headingLength/5)*3)-1) + "s%" + (headingLength/5) + "s%-" + (headingLength/5) + "s|\n"
				," ", "Balance: ",invoice.getBalance());
	}
	
	
}
