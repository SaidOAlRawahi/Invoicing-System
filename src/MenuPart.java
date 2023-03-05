
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
		Main.menues.pop();
	}
}


/*-------------------------------Main Menu------------------------------*/
class ShopSettingsPart extends MenuPart{
	
}
class ManageShopItemsPart extends MenuPart{
	
}
/*---------------------------------End----------------------------------*/


/*-----------------------------Shop Settings----------------------------*/
class LoadDataPart extends MenuPart{
	
}
class SetShopNamePart extends MenuPart{
	
}
class SetInvoiceHeaderPart extends MenuPart{
	
}
/*---------------------------------End----------------------------------*/

/*------------------------------Manage Shop-----------------------------*/
class AddItemsPart extends MenuPart{
	
}
class DeleteItemsPart extends MenuPart{
	
}
class ChangeItemPricePart extends MenuPart{
	
}
class ReportAllItemsPart extends MenuPart{
	
}
/*---------------------------------End----------------------------------*/