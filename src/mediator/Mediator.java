package mediator;






import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class Mediator {
	
	public String database="jdbc:postgresql://localhost/dbs_proj";
	public String user = "Fiare";
    public String password = "fiare";
	
	
	Tab res_tab;
	Tab search_tab;
	Stage main_stage;
	gui.Pop_win pop_win;
	
	public Mediator(gui.Tabs tabs,Stage main_stage,gui.Pop_win pop_win){
		res_tab=tabs.get_res_tab();
		search_tab=tabs.get_search_tab();
		this.main_stage=main_stage;
		this.pop_win=pop_win;
	}
	
	
	public gui.Res_tab get_res_tab(){
		return (gui.Res_tab) res_tab;
	}
	public gui.Search_tab get_search_tab(){
		return (gui.Search_tab) search_tab;
	}
	public Stage get_main_stage(){
		return main_stage;
	}
	public gui.Pop_win get_pop_win(){
		return pop_win;
	}
	
	
}
