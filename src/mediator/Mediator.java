
package mediator;







import javafx.scene.control.Tab;
import javafx.stage.Stage;


public class Mediator {
	
	
	public final int lock_time =50;
	private String database="jdbc:postgresql://localhost/dbs_proj";
	private String user = "postgres";
    private String password = "postgres";
	private String client_num = "";
	
	Tab res_tab;
	Tab search_tab;
	Tab edit_tab;
	Stage main_stage;
	gui.Pop_win pop_win;
	gui.Serv_win serv_win;
	gui.Connect_win conn_win;
	
	public Mediator(gui.Tabs tabs,Stage main_stage,Stage pop_win,Stage serv_win,Stage conn_win){
		
		
		
		
		
		res_tab=tabs.get_res_tab();
		search_tab=tabs.get_search_tab();
		edit_tab=tabs.get_edit_tab();
		this.main_stage=main_stage;
		this.pop_win=(gui.Pop_win) pop_win;
		this.serv_win=(gui.Serv_win) serv_win;
		this.conn_win=(gui.Connect_win) conn_win;
	}
	public void set_database(String database){
		this.database=database;
	}
	public void set_user(String user){
		this.user=user;
	}
	public void set_password(String password){
		this.password=password;
	}
	public void set_client_num(String client_num){
		this.client_num=client_num;
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
	public gui.Serv_win get_serv_win(){
		return serv_win;
	}
	public gui.Connect_win get_conn_win(){
		return conn_win;
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
	public String get_client_num(){
		return client_num;
	}
	
}
