package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import mediator.Mediator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class Res_control {
	Mediator med;
	String ser1_str="";
	String ser2_str="";
	String ser3_str="";
	Thread jed_th;
	boolean running=false;
	
	Res_control(Mediator med){
		this.med=med;
	}
	
	void set_search(){
		
		
		med.get_res_tab().get_search_button().setOnAction(new EventHandler<ActionEvent>() { 
		   
			@SuppressWarnings("unchecked")
			@Override
		    public void handle(ActionEvent e) {
		    	try {
		    		
		    		if(jed_th.getState()==Thread.State.NEW){
		    			jed_th.start();
		    		}
		    		
		    		running=false;
		    		
		    		
		    		int sel_ind=-1;
		    		int sel_room = -1;
		    		if(med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem()!=null){
		    			sel_ind=med.get_res_tab().get_mid_display().getSelectionModel().getSelectedIndex();		
		    			ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
		    			sel_room=Integer.parseInt(row.get(0));
		    		}
		    	  
		    	
		    	
		    		
		    		
					Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					Statement st=conn.createStatement();
					
					
					String []columns={"Number:","Floor:","Type:","Alig.:","Price:"};
					String from_str;
					String to_str;
					String date_str;
					String num_str="";
					String floor_str="";
					String type_str="";
					String ali_str="";
					String price_str="";
					
					
					
					
					if(med.get_res_tab().get_from_d().getText().equals("")){
			    		((Label)med.get_pop_win().getScene().getRoot()).setText("From date empty");
			    		med.get_pop_win().show();
			    		return;
			    	}		

					if(med.get_res_tab().get_to_d().getText().equals("")){
			    		((Label)med.get_pop_win().getScene().getRoot()).setText("To date empty");
			    		med.get_pop_win().show();
			    		return;
			    	}		
					if(Integer.parseInt(med.get_res_tab().get_from_d().getText())>31||Integer.parseInt(med.get_res_tab().get_from_d().getText())==0)	
			    	{
			    		((Label)med.get_pop_win().getScene().getRoot()).setText("Wrong date set");
			    		med.get_res_tab().get_mid_display().getColumns().clear();					
			    		med.get_pop_win().show();
			    		return;
			    	}
					if(Integer.parseInt(med.get_res_tab().get_to_d().getText())>31||Integer.parseInt(med.get_res_tab().get_to_d().getText())==0)	
			    	{
			    		((Label)med.get_pop_win().getScene().getRoot()).setText("Wrong date set");
			    		med.get_res_tab().get_mid_display().getColumns().clear();
			    		med.get_pop_win().show();
			    		return;
			    	}
					
					
					
					int days = get_days();
				
					if (days<=0){
						((Label)med.get_pop_win().getScene().getRoot()).setText("Wrong date set");
						med.get_res_tab().get_mid_display().getColumns().clear();
			    		med.get_pop_win().show();
			    		return;
					}
					
					from_str="'"+med.get_res_tab().get_from_y().getValue()+"-"+med.get_res_tab().get_from_m().getValue()+"-"+med.get_res_tab().get_from_d().getText()+"'";
					to_str="'"+med.get_res_tab().get_to_y().getValue()+"-"+med.get_res_tab().get_to_m().getValue()+"-"+med.get_res_tab().get_to_d().getText()+"'";
					date_str=" AND(r.id NOT IN (SELECT room FROM bookings AS bo WHERE("+from_str+" BETWEEN bo.from_date AND bo.to_date) OR ("+to_str
					+"BETWEEN bo.from_date AND bo.to_date) OR ("+from_str+"<=bo.from_date AND"+to_str+">=bo.to_date)))"; 
					
					if(!med.get_res_tab().get_room_field().getText().equals("")){
						num_str=" AND r.number="+med.get_res_tab().get_room_field().getText();
					}
					if(!med.get_res_tab().get_floor_box().getValue().equals("-")){
						floor_str=" AND r.floor="+med.get_res_tab().get_floor_box().getValue();
					}
					if(!med.get_res_tab().get_type_box().getValue().equals("-")){
						type_str=" AND r.type='"+med.get_res_tab().get_type_box().getValue()+"'";
					}
		
					if(!med.get_res_tab().get_ali_box().getValue().equals("-")){
						ali_str=" AND r.alig='"+med.get_res_tab().get_ali_box().getValue()+"'";
					}
					
					if(!med.get_res_tab().get_price_box().getValue().equals("-")){	
						switch(med.get_res_tab().get_price_box().getValue()){
							case "50-":
								price_str=" AND r.price<=50";
								break;
							case "50-80":
								price_str=" AND r.price>=50 AND price<=80";
								break;
							case "80-150":
								price_str=" AND r.price>=80 AND price<=150";
								break;
							case "150+":
								price_str=" AND r.price>=150";
								break;	
						}	
					}
					Jedis jed=new Jedis("localhost");
					Set<String> clientz=jed.smembers("cs");
					
					
					if(med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem()!=null){
						jed.del(med.get_client_num());
						jed.del(med.get_client_num()+"h");
					}
					
					
					Calendar cal = Calendar.getInstance();
					cal.set(Integer.parseInt(med.get_res_tab().get_from_y().getValue()),
							Integer.parseInt(med.get_res_tab().get_from_m().getValue())-1,
							Integer.parseInt(med.get_res_tab().get_from_d().getText()));
					Date from_date = cal.getTime();
					cal.set(Integer.parseInt(med.get_res_tab().get_to_y().getValue()),
							Integer.parseInt(med.get_res_tab().get_to_m().getValue())-1,
							Integer.parseInt(med.get_res_tab().get_to_d().getText()));
					Date to_date = cal.getTime();
					
					SimpleDateFormat format = new SimpleDateFormat("''yyyy-MM-dd''");
					Date from_new=null;
					Date to_new=null;
					
					String locked_str="";
					
					
					for(String s:clientz){
						if(jed.get(s)!=null){
							
							try {
								to_new=format.parse(jed.hget(s+"h", "to"));
								from_new=format.parse(jed.hget(s+"h", "from"));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							
							
							
							
							
							
							
							if((from_date.after(from_new)&&from_date.before(to_new))
								||(to_date.after(from_new)&&to_date.before(to_new))
								||(from_date.before(from_new)&&to_date.after(to_new))||
								from_date.equals(from_new)||from_date.equals(to_new)||
								to_date.equals(from_new)||from_date.equals(to_new)
							){
								locked_str=locked_str+" AND r.number!="+jed.hget(s+"h", "room");
								
							}
						}
					}
					
					
					ResultSet rs=st.executeQuery("SELECT DISTINCT r.number,r.floor,COALESCE(r.type,'-'),COALESCE(r.alig,'-'),r.price FROM rooms AS r LEFT JOIN bookings AS b ON b.room=r.id WHERE TRUE"
					+num_str+floor_str+type_str+ali_str+price_str+date_str+locked_str+" ORDER BY r.number;");
					
					
					
					Table_fill.set_table(rs,columns, med.get_res_tab().get_mid_display());
					int new_room=-1;
					if(sel_ind!=-1){
						int index=0;
						while(new_room!=sel_room){
							med.get_res_tab().get_mid_display().getSelectionModel().select(index);
							jed.del(med.get_client_num()+"c");
							ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
			    			new_room=Integer.parseInt(row.get(0));
							index++;
							
						}
						
						
						
						
					}
		    		
					
					running=true;
			        jed.close();
					rs.close();
					st.close();
					conn.close();
					
					
					
					
					
				} catch (SQLException e1) {	
					e1.printStackTrace();
					((Label)med.get_pop_win().getScene().getRoot()).setText("Database error at search");
		    		med.get_pop_win().show();
		    		return;}
		    	catch(JedisException e2){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Redis error at search");
		    		med.get_pop_win().show();
		    		return;
		    	}
		    	}});
		    	
	}
	
	void set_confirm(){
		med.get_res_tab().get_confirm_button().setOnAction(new EventHandler<ActionEvent>() { 
		    @SuppressWarnings("unchecked")
			public void handle(ActionEvent e) {
		    	
		    	
		    	
		    	
		    	if(med.get_res_tab().get_mid_display().getSelectionModel().isEmpty()){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("No room selected");
		    		med.get_pop_win().show();
		    		return;
		    	}		    	
		    	if(med.get_res_tab().get_res_name().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Reservation name missing");
		    		med.get_pop_win().show();
		    		return;
		    	}
		    	if(med.get_res_tab().get_res_id().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Reservation id missing");
		    		med.get_pop_win().show();
		    		return;
		    	}
		    	if(med.get_res_tab().get_pay_name().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Billing name missing");
		    		med.get_pop_win().show();
		    		return;
		    	}
		    	if(med.get_res_tab().get_pay_id().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Billing id missing");
		    		med.get_pop_win().show();
		    		return;
		    	}
		    	if(!med.get_res_tab().get_pay_box().getValue().equals("Cash") && med.get_res_tab().get_card_field().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Card number missing");
		    		med.get_pop_win().show();
		    		return;
		    	}
		    	
		    
		    	
				try {
					Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					Statement st=conn.createStatement();
					
					String from_str="'"+med.get_res_tab().get_from_y().getValue()+"-"+med.get_res_tab().get_from_m().getValue()+"-"+med.get_res_tab().get_from_d().getText()+"'";
					String to_str="'"+med.get_res_tab().get_to_y().getValue()+"-"+med.get_res_tab().get_to_m().getValue()+"-"+med.get_res_tab().get_to_d().getText()+"'";
					
					ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
					
					
					Calendar cal = Calendar.getInstance();
					cal.set(Integer.parseInt(med.get_res_tab().get_from_y().getValue()),
							Integer.parseInt(med.get_res_tab().get_from_m().getValue()),
							Integer.parseInt(med.get_res_tab().get_from_d().getText()));
					Date from_date = cal.getTime();
					cal.set(Integer.parseInt(med.get_res_tab().get_to_y().getValue()),
							Integer.parseInt(med.get_res_tab().get_to_m().getValue()),
							Integer.parseInt(med.get_res_tab().get_to_d().getText()));
					Date to_date = cal.getTime();
					
					int days = (int) ((to_date.getTime() - from_date.getTime()) / (1000 * 60 * 60 * 24));
					int price= days*Integer.parseInt(row.get(4));
					
					if(!med.get_res_tab().get_ser1_box().getValue().equals("-")){
						price=price+get_price_of(med.get_res_tab().get_ser1_box().getValue());}
					if(!med.get_res_tab().get_ser2_box().getValue().equals("-")){
						price=price+get_price_of(med.get_res_tab().get_ser2_box().getValue());}
					if(!med.get_res_tab().get_ser3_box().getValue().equals("-")){
						price=price+get_price_of(med.get_res_tab().get_ser3_box().getValue());}
					
					String cardnum="0";
					if(!med.get_res_tab().get_pay_box().getValue().equals("Cash"))
						cardnum=med.get_res_tab().get_card_field().getText();
					
					
					ResultSet rs=st.executeQuery("SELECT perid FROM customers WHERE perid="+Integer.parseInt(med.get_res_tab().get_res_id().getText())+" AND name='"
					+med.get_res_tab().get_res_name().getText()+"'");
					
					
					if(!rs.next()){
						st.executeUpdate("INSERT INTO customers(name,perid) VALUES ('"+med.get_res_tab().get_res_name().getText()+"',"+med.get_res_tab().get_res_id().getText()+");");
					}
					
					st.executeUpdate("INSERT INTO billings(name,perid,type,cardid,sum) VALUES ('"
					+med.get_res_tab().get_pay_name().getText()+"',"+med.get_res_tab().get_pay_id().getText()
					+",'"+med.get_res_tab().get_pay_box().getValue()+"',"+cardnum
					+","+price+");"			
					,Statement.RETURN_GENERATED_KEYS);
					rs = st.getGeneratedKeys();
					int key=0;
					if ( rs.next() ) {
						key = rs.getInt(1);
					}
					
					st.executeUpdate("INSERT INTO bookings(from_date,to_date,room,customer,billing) VALUES "
							+"("+from_str+","+to_str+",(SELECT id FROM rooms WHERE number="+row.get(0)+"),(SELECT id FROM customers WHERE perid="+med.get_res_tab().get_res_id().getText()
							+" AND name='"+med.get_res_tab().get_res_name().getText()+"'),"+Integer.toString(key)+");"
							,Statement.RETURN_GENERATED_KEYS);
					
					rs = st.getGeneratedKeys();
					if ( rs.next() ) {
						key = rs.getInt(1);
					}
					if(!med.get_res_tab().get_ser1_box().getValue().equals("-"))
						st.executeUpdate("INSERT INTO serlink(booking,service) VALUES ("+key+",(SELECT id FROM services WHERE type='"+med.get_res_tab().get_ser1_box().getValue()+"'))");
					if(!med.get_res_tab().get_ser2_box().getValue().equals("-"))
						st.executeUpdate("INSERT INTO serlink(booking,service) VALUES ("+key+",(SELECT id FROM services WHERE type='"+med.get_res_tab().get_ser2_box().getValue()+"'))");
					if(!med.get_res_tab().get_ser3_box().getValue().equals("-"))
						st.executeUpdate("INSERT INTO serlink(booking,service) VALUES ("+key+",(SELECT id FROM services WHERE type='"+med.get_res_tab().get_ser3_box().getValue()+"'))");
					
					med.get_res_tab().get_mid_display().getItems().remove(med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem());
					
					
					med.get_pop_win().setTitle("Res conf");
					((Label)med.get_pop_win().getScene().getRoot()).setText("Successful reservation");					
		    		med.get_pop_win().show();
		    		
					
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e1) {
					((Label)med.get_pop_win().getScene().getRoot()).setText("Database error at reservation");
		    		med.get_pop_win().show();
		    		return;}}});
		
		
	}
	
	void set_total_thread(){
		Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
            	
            	
                while(true){
                	
                	Platform.runLater(new Runnable(){
						@SuppressWarnings("unchecked")
						@Override
						public void run() {
							if(!med.get_res_tab().get_mid_display().getSelectionModel().isEmpty()
							&& !med.get_res_tab().get_from_d().getText().equals("")
							&& !med.get_res_tab().get_to_d().getText().equals("")){
								
								ObservableList<String> row=(ObservableList<String>) med.get_res_tab().get_mid_display().getSelectionModel().getSelectedItem();
							
								int days=get_days();
								int price= days*Integer.parseInt(row.get(4));
								
								
								
								med.get_res_tab().get_total_area().setText("Days: "+Integer.toString(days)+"\nPrice total: "+Integer.toString(price)+ser1_str
										+ser2_str+ser3_str+"€");
							}else{
								med.get_res_tab().get_total_area().setText("No room selected\n or bad date");
							}
					}
                		
                	});
                	try{Thread.sleep(100);}
                	
                	catch (InterruptedException ex) {
                		
        				break;
        			}
                } 
            }
        });
		
		
		med.get_main_stage().setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		    	jed_th.interrupt();
		        th.interrupt();
		        
		        Jedis jed=new Jedis("localhost");
		        
		        jed.del(med.get_client_num()+"c");
		        jed.srem("cs", med.get_client_num());
		        
		        jed.del(med.get_client_num()+"h");
		        jed.del(med.get_client_num());
		        jed.close(); 
		        
		    }
		});
		th.start();
		
	}
	
	void set_jedis_thread(){
		
		jed_th=new Thread(new Runnable() {
			
            @Override
            public void run() {
            	
                JedisPoolConfig pool_con=new JedisPoolConfig();
            	pool_con.setMaxTotal(64);
            	pool_con.setTestOnBorrow(true);
            	pool_con.setTestOnReturn(true);
            	pool_con.setTestOnReturn(true);
            	JedisPool pool = new JedisPool(pool_con,"localhost");
            	
                while(running){
                   
                	Platform.runLater(new Runnable(){
						@SuppressWarnings("unchecked")
						@Override
						public void run() {
							
							if(jed_th.isInterrupted()){
								pool.close();
								return;
							}
							
							
							Jedis jed=pool.getResource();
							
							//timeout
							if(jed.get(med.get_client_num())==null&&jed.exists(med.get_client_num()+"h")){
								jed.del(med.get_client_num()+"h");
								jed.set(med.get_client_num()+"c","changed");
								med.get_res_tab().get_mid_display().getSelectionModel().select(null);	
							}
							
							
							
							Set<String> clientz=jed.smembers("cs");
						//"listener"
					    for(String s:clientz){			
								if(!s.equals(med.get_client_num())){
									if(jed.get(s+"c")!=null){
										med.get_res_tab().get_search_button().fire();
										
										jed.del(s+"c");
									}
									
									
									
									
									
									
									
								}
							}
							
							jed.close();
							 }
						
                		
                	});
                	try{Thread.sleep(100);}
                	catch (InterruptedException ex) {
        				break;
        			}
                } 
              
            }
        });
		
		
		
	}
	
	void set_service_boxes(){
		med.get_res_tab().get_ser1_box().setOnAction((event) -> {
		   if(med.get_res_tab().get_ser1_box().getValue().equals("-"))
			   ser1_str="";
		   else 
			   ser1_str="\n+"+get_price_of(med.get_res_tab().get_ser1_box().getValue());
			   
		});
		med.get_res_tab().get_ser2_box().setOnAction((event) -> {
			   if(med.get_res_tab().get_ser2_box().getValue().equals("-"))
				   ser2_str="";
			   else 
				   ser2_str="+"+get_price_of(med.get_res_tab().get_ser2_box().getValue());
				   
			});
		med.get_res_tab().get_ser3_box().setOnAction((event) -> {
			   if(med.get_res_tab().get_ser2_box().getValue().equals("-"))
				   ser3_str="";
			   else 
				   ser3_str="+"+get_price_of(med.get_res_tab().get_ser3_box().getValue());
				   
			});
	}
	
	int get_days(){
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(med.get_res_tab().get_from_y().getValue()),
				Integer.parseInt(med.get_res_tab().get_from_m().getValue()),
				Integer.parseInt(med.get_res_tab().get_from_d().getText()));
		Date from_date = cal.getTime();
		cal.set(Integer.parseInt(med.get_res_tab().get_to_y().getValue()),
				Integer.parseInt(med.get_res_tab().get_to_m().getValue()),
				Integer.parseInt(med.get_res_tab().get_to_d().getText()));
		Date to_date = cal.getTime();
		
		
		return (int) ((to_date.getTime() - from_date.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	int get_price_of(String type){
		
		int price=0;
		try {
			Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery("SELECT price FROM services WHERE type='"+type+"'");
			rs.next();
			
			price=rs.getInt(1);
			rs.close();
			st.close();
			conn.close();
			
			
			
		} catch (SQLException e) {
			((Label)med.get_pop_win().getScene().getRoot()).setText("Database error at service search");
    		med.get_pop_win().show();
    		
		}
		return price;
		
	}
	
	@SuppressWarnings("unchecked")
	void set_listener(){
		Map<String, String> map = new HashMap<>(); 
		Jedis jed=new Jedis("localhost");
		
		
		med.get_res_tab().get_mid_display().getSelectionModel().selectedItemProperty().addListener((obs, old_sel, new_sel) -> {
		    if (new_sel != null) {
		    	
		    	 String  from_str;
				 String to_str;
		    	
		    	 ObservableList<String> row=(ObservableList<String>) new_sel;
		    	 from_str="'"+med.get_res_tab().get_from_y().getValue()+"-"+med.get_res_tab().get_from_m().getValue()+"-"+med.get_res_tab().get_from_d().getText()+"'";
		    	 to_str="'"+med.get_res_tab().get_to_y().getValue()+"-"+med.get_res_tab().get_to_m().getValue()+"-"+med.get_res_tab().get_to_d().getText()+"'";
		    	 
		    	 
		    	 
		    	 
		         map.put("room", row.get(0));  
		         map.put("from", from_str);  
		         map.put("to", to_str); 
		         try {  
		             jed.hmset(med.get_client_num()+"h", map);  
		             jed.set(med.get_client_num(), "locked");
		             jed.expire(med.get_client_num(), med.lock_time);
		             jed.set(med.get_client_num()+"c", "changed");
		             
		           
		             
		             
		             
		   
		         } catch (JedisException e) {  
		        	 ((Label)med.get_pop_win().getScene().getRoot()).setText("Redis error");
			    	 med.get_pop_win().show();
		         }  
		         
		         jed.close();
		         
		    }
		});

	}
	
	void set_date_listener(){
		ChangeListener<String> listener =(observable, oldValue, newValue) ->{
			med.get_res_tab().get_mid_display().getColumns().clear();	  
		};
		med.get_res_tab().get_from_d().textProperty().addListener(listener);
		med.get_res_tab().get_to_d().textProperty().addListener(listener);
		med.get_res_tab().get_from_m().valueProperty().addListener(listener);
		med.get_res_tab().get_from_y().valueProperty().addListener(listener);
		med.get_res_tab().get_to_m().valueProperty().addListener(listener);
		med.get_res_tab().get_to_y().valueProperty().addListener(listener);
	}
	
}
