package mediator;





import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class Mediator {
	
	public String database="jdbc:postgresql://localhost/dbs_proj";
	public String user = "Fiare";
    public String password = "fiare";
	
	
	Tab res_tab;
	Stage main_stage;
	public Mediator(Tab res_tab,Stage main_stage){
		this.res_tab=res_tab;
		this.main_stage=main_stage;
	}
	
	
	public gui.Res_tab get_res_tab(){
		return (gui.Res_tab) res_tab;
	}
	public Stage get_main_stage(){
		return main_stage;
	}
	
}
