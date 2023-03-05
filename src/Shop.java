import java.util.*;


public class Shop {
	
	static Stack<Menu> menues = new Stack<Menu>();
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		MainMenu mainMenu = new MainMenu();
		menues.push(mainMenu);
		
		while(!menues.isEmpty()) {
			menues.peek().showMenu();
			int input = getMenuInput(menues.peek());
			menues.peek().menuParts.get(input).triggerAction();
			
			if (menues.isEmpty()) {
				if(repeatProcess("Are you sure you want to exit (Y/N)?")) {
					menues.push(mainMenu);
				}
			}
		}
		
		System.out.println("\n Goodbye");
		System.gc();
        System.runFinalization();
	}
	
	static int getMenuInput(Menu menu) {
		while(true) {
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
				System.out.println("Invalid Input");
			}
			menu.showMenu();
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
