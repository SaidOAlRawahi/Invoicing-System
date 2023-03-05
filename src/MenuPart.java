
abstract public class MenuPart {
	int clicks = 0;
	String title = "myTitle";
	void triggerAction() {
		clicks++;
	}
	
}
class ExitPart extends MenuPart{
	ExitPart(String title){
		this.title = title;
	}
	
	
	@Override
	void triggerAction(){
		Shop.menues.pop();
	}
	
}


/*-------------------------------Main Menu------------------------------*/
class ShopSettingsPart extends MenuPart{
	ShopSettingsPart(){
		this.title = "Shop Settings";
	}
	@Override
	void triggerAction(){
		Shop.menues.push(new ShopSettingsMenu());
	}
	
}
class ManageShopItemsPart extends MenuPart{
	ManageShopItemsPart(){
		this.title = "Manage Shop Items";
	}
	@Override
	void triggerAction(){
		Shop.menues.push(new ManageShopMenu());
	}
	
}
class CreateNewInvoicePart extends MenuPart{
	CreateNewInvoicePart(){
		this.title = "Create New Invoice";
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ReportStatsPart extends MenuPart{
	ReportStatsPart(){
		this.title = "Report: Statistics";
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ReportAllInvoicesPart extends MenuPart{
	ReportAllInvoicesPart(){
		this.title = "Report: All invoices";
	}
	@Override
	void triggerAction(){
		
	}
	
}
class SearchInvoicePart extends MenuPart{
	SearchInvoicePart(){
		this.title = "Search (1) Invoice";
	}
	
	@Override
	void triggerAction(){
		
	}
	
}
class AppStatsPart extends MenuPart{
	AppStatsPart(){
		this.title = "Program Statistics";
	}
	@Override
	void triggerAction(){
		
	}
	
}
/*---------------------------------End----------------------------------*/

/*-----------------------------Shop Settings----------------------------*/
class LoadDataPart extends MenuPart{
	LoadDataPart(){
		this.title = "Load Data";
	}
	@Override
	void triggerAction(){
		do {
			Product newProduct = new Product();
			newProduct.setId(Shop.getIntInput("Enter the ID of the new product: "));
			newProduct.setName(Shop.getStringInput("Enter the name of the new product: "));
			newProduct.setPrice(Shop.getFloatInput("Enter the price of the product: "));
		}while(Shop.repeatProcess("Do you want to add more items (Y/N)? "));
	}
	
}
class SetShopNamePart extends MenuPart{
	SetShopNamePart(){
		this.title = "Set Shop Name";
	}
	@Override
	void triggerAction(){
		Shop.shopName = Shop.getStringInput("Enter the name of the shop.");
		System.out.println("The name of the shop is changed to: " + Shop.shopName);
	}
	
}
class SetInvoiceHeaderPart extends MenuPart{
	SetInvoiceHeaderPart(){
		this.title = "Set Invoice Header";
	}
	@Override
	void triggerAction(){
		Shop.tel = Shop.getIntInput("Enter the telephone number of the shop: ");
		Shop.fax = Shop.getIntInput("Enter the fax number of the shop: ");
		Shop.email = Shop.getStringInput("Enter the email of the shop: ");
		Shop.website = Shop.getStringInput("Enter the website address of the shop: ");
	}
	
}
/*---------------------------------End----------------------------------*/

/*------------------------------Manage Shop-----------------------------*/
class AddItemsPart extends MenuPart{
	AddItemsPart(){
		this.title = "Add Items";
	}
	@Override
	void triggerAction(){
		
	}
	
}
class DeleteItemsPart extends MenuPart{
	DeleteItemsPart(){
		this.title = "Delete Items";
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ChangeItemPricePart extends MenuPart{
	ChangeItemPricePart(){
		this.title = "Change Item Price";
	}
	
	@Override
	void triggerAction(){
		
	}
	
}
class ReportAllItemsPart extends MenuPart{
	ReportAllItemsPart(){
		this.title = "Report All Items";
	}
	@Override
	void triggerAction(){
		
	}
	
}
/*---------------------------------End----------------------------------*/