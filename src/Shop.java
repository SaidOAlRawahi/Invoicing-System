import java.util.*;


public class Shop {
	
	static Stack<Menu> menues = new Stack<Menu>();
	static ArrayList<Product> products = new ArrayList<Product>();
	static Scanner sc = new Scanner(System.in);
	static String shopName;
	static int tel;
	static int fax;
	static String email;
	static String website;
	
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
		scanner.close();
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
	
	
}
