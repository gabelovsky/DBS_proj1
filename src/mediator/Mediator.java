package mediator;






import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class Mediator {
	
	String database="jdbc:postgresql://localhost/dbs_proj";
	String user = "admin";
    String password = "admin";
	
	
	Tab res_tab;
	Tab search_tab;
	Tab edit_tab;
	Stage main_stage;
	gui.Pop_win pop_win;
	
	public Mediator(gui.Tabs tabs,Stage main_stage,gui.Pop_win pop_win){
		res_tab=tabs.get_res_tab();
		search_tab=tabs.get_search_tab();
		edit_tab=tabs.get_edit_tab();
		this.main_stage=main_stage;
		this.pop_win=pop_win;
	}
	
	
	public gui.Res_tab get_res_tab(){
		return (gui.Res_tab) res_tab;
	}
	public gui.Search_tab get_search_tab(){
		return (gui.Search_tab) search_tab;
	}
	public gui.Edit_tab get_edit_tab(){
		return (gui.Edit_tab) edit_tab;
	}
	
	public Stage get_main_stage(){
		return main_stage;
	}
	public gui.Pop_win get_pop_win(){
		return pop_win;
	}
	public String get_database(){
		return database;
	}
	
	public String get_user(){
		return user;
	}
	public String get_password(){
		return password;
	}
	
}
