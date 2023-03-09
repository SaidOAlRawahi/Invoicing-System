import java.util.ArrayList;

abstract public class Menu {
	String title;
	ArrayList<MenuPart> menuParts = new ArrayList<MenuPart>();
	ArrayList<MenuPart> showMenu(){
		System.out.println(title);
		for (int i = 0; i < menuParts.size(); i++){
			System.out.println((i+1)+"- " + menuParts.get(i).title);
			
		}
		return this.menuParts;
	}
	
}

class  MainMenu extends Menu{
	MainMenu(){
		this.title = "\nMain Menu.";
		this.menuParts.add(new ShopSettingsPart());
		this.menuParts.add(new ManageShopItemsPart());
		this.menuParts.add(new CreateNewInvoicePart());
		this.menuParts.add(new ReportStatsPart());
		this.menuParts.add(new ReportAllInvoicesPart());
		this.menuParts.add(new SearchInvoicePart());
		this.menuParts.add(new AppStatsPart());
		this.menuParts.add(new Insert1MillionInvoicesPart());
		this.menuParts.add(new ExitPart("Exit."));
	}
}

class  ShopSettingsMenu extends Menu{
	ShopSettingsMenu(){
		this.title = "\nShop Settings.";
		this.menuParts.add(new LoadDataPart());
		this.menuParts.add(new SetShopNamePart());
		this.menuParts.add(new SetInvoiceHeaderPart());
		this.menuParts.add(new ExitPart("Go Back."));
	}
}

class  ManageShopMenu extends Menu{
	ManageShopMenu(){
		this.title = "\nManage Shop Items.";
		this.menuParts.add(new AddItemsPart());
		this.menuParts.add(new DeleteItemsPart());
		this.menuParts.add(new ChangeItemPricePart());
		this.menuParts.add(new ReportAllItemsPart());
		this.menuParts.add(new ExitPart("Go Back."));
	}
}