package mediator;





import javafx.scene.control.Tab;

public class Mediator {
	
	public String database="jdbc:postgresql://localhost/dbs_proj";
	public String user = "Fiare";
    public String password = "fiare";
	
	
	Tab res_tab;
	
	public Mediator(Tab res_tab){
		this.res_tab=res_tab;
	}
	
	
	public gui.Res_tab get_res_tab(){
		return (gui.Res_tab) res_tab;
	}
	
	
}
