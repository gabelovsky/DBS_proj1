package control;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import mediator.Mediator;
import redis.clients.jedis.Jedis;

public class Conn_control {

	Mediator med;
	Conn_control(Mediator med){
		this.med=med;
	}
	
	void set_conn(){
		med.get_conn_win().get_conn_but().setOnAction(event ->{
			med.set_database(med.get_conn_win().get_dbs_field().getText());
			med.set_user(med.get_conn_win().get_name_field().getText());
			med.set_password(med.get_conn_win().get_pass_field().getText());
			
			try {
				Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
				
				med.get_conn_win().close();
				med.get_main_stage().show();
				
				conn.close();
			} catch (Exception e1) {
				((Label)med.get_pop_win().getScene().getRoot()).setText("Can't connect to database");
	    		med.get_pop_win().show();
	    		return;}
		});
	}
	
	void set_client(){
		//set client name and add to redis
		Jedis jed=new Jedis("localhost");;
		for (int i=0;i<=jed.scard("cs");i++){
			if(!jed.sismember("cs","c"+i)){
				jed.sadd("cs", "c"+i);
				med.set_client_num("c"+i);
				break;
	
			}
			
		}	
		jed.close();
	
	}
	
	
	
	void set_closes(){
		
		med.get_pop_win().setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	 
	        	  med.get_pop_win().setTitle("Error:");
	          }
	    });
		
		
		med.get_conn_win().setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	 
	           med.get_main_stage().fireEvent(new WindowEvent(
	        		   med.get_main_stage(),
	        	        WindowEvent.WINDOW_CLOSE_REQUEST
	        	    ));
	          
	          }
	    });
	}

}
