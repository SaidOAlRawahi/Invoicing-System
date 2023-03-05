
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
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ManageShopItemsPart extends MenuPart{
	ManageShopItemsPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class CreateNewInvoicePart extends MenuPart{
	CreateNewInvoicePart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ReportStatsPart extends MenuPart{
	ReportStatsPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ReportAllInvoicesPart extends MenuPart{
	ReportAllInvoicesPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class SearchInvoicePart extends MenuPart{
	SearchInvoicePart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class AppStatsPart extends MenuPart{
	AppStatsPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
/*---------------------------------End----------------------------------*/

/*-----------------------------Shop Settings----------------------------*/
class LoadDataPart extends MenuPart{
	LoadDataPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class SetShopNamePart extends MenuPart{
	SetShopNamePart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class SetInvoiceHeaderPart extends MenuPart{
	SetInvoiceHeaderPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
/*---------------------------------End----------------------------------*/

/*------------------------------Manage Shop-----------------------------*/
class AddItemsPart extends MenuPart{
	AddItemsPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class DeleteItemsPart extends MenuPart{
	DeleteItemsPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
class ChangeItemPricePart extends MenuPart{
	ChangeItemPricePart(){
		
	}
	
	@Override
	void triggerAction(){
		
	}
	
}
class ReportAllItemsPart extends MenuPart{
	ReportAllItemsPart(){
		
	}
	@Override
	void triggerAction(){
		
	}
	
}
/*---------------------------------End----------------------------------*/