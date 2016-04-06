package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mediator.Mediator;

public class Search_control {
	Mediator med;

	Search_control(Mediator med){
		this.med=med;
	}
	
	void set_room(){
		med.get_search_tab().get_room_but().setOnAction(new EventHandler<ActionEvent>() { 
			@Override
		    public void handle(ActionEvent e) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(med.database, med.user, med.password);
					Statement st=conn.createStatement();
			    	med.get_search_tab().get_display().getColumns().clear();
					
			    	
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
				
		    	
		    	
		    }});
	}
}
