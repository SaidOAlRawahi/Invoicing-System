import java.util.ArrayList;

abstract public class Menu {
	String title;
	ArrayList<MenuPart> menuItems = new ArrayList<MenuPart>();
	void showMenu(){
		System.out.println(title);
		for (int i = 0; i < menuItems.size(); i++){
			System.out.println("["+(i+1)+"]" + menuItems.get(i).title);
		}
	}
}

/*-------------------------------Main Menu------------------------------*/

