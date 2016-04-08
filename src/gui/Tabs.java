package gui;



import javafx.scene.control.Tab;

public class Tabs {

	Res_tab res_tab;
	Search_tab search_tab;
	Edit_tab edit_tab;
	
	public Tab get_res_tab(){
		return res_tab;
	}
	public Tab get_search_tab(){
		return search_tab;
	}
	public Tab get_edit_tab(){
		return edit_tab;
	}
	
	
	
	Tabs(){
		create_tabs();
	}
	void create_tabs(){
		res_tab=new Res_tab();
		search_tab=new Search_tab();
		edit_tab=new Edit_tab();
	}
}
