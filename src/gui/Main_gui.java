package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mediator.Mediator;


public class Main_gui extends Application{

	public static void main(String[] args) {
		launch();
		}

	
	@Override
	public void start(Stage main_stage) throws Exception {
		Connect_win conn_win=new Connect_win();
		conn_win.new_window();
		conn_win.show();
		
		Pop_win pop_win=new Pop_win();
		pop_win.new_window();
		
		pop_win.initModality(Modality.WINDOW_MODAL);
		pop_win.initOwner(main_stage);
		
		Serv_win serv_win=new Serv_win();
		serv_win.new_window();
		serv_win.initModality(Modality.WINDOW_MODAL);
		serv_win.initOwner(main_stage);
		
		
		TabPane tp=new TabPane();
		Tabs tabs=new Tabs();
		tp.getTabs().addAll(tabs.get_res_tab(),tabs.get_search_tab(),tabs.get_edit_tab());
		Scene scene = new Scene(tp, 750, 800);
	
		
		Mediator med=new Mediator(tabs,main_stage,pop_win,serv_win,conn_win);
		
		@SuppressWarnings("unused")
		control.Main_control mc=new control.Main_control(med);
		
		
		
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);	
		main_stage.setTitle("Hotel manager 3000");
		main_stage.setScene(scene);
		//main_stage.show();
	}

}
