package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import mediator.Mediator;

public class Edit_control {

	Mediator med;
	Edit_control(Mediator med){
		this.med=med;
		
	}
	
	
	void set_confirm(){
		
		med.get_edit_tab().get_confirm_but().setOnAction(new EventHandler<ActionEvent>() { 
			
			@SuppressWarnings("unchecked")
			@Override
		    public void handle(ActionEvent e) {
		
				if(med.get_edit_tab().get_room_field().equals("")){
					((Label)med.get_pop_win().getScene().getRoot()).setText("No room set");
		    		med.get_pop_win().show();
		    		return;
				}
				if(med.get_edit_tab().get_from_d().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("From date empty");
		    		med.get_pop_win().show();
		    		return;
		    	}		
				if(med.get_edit_tab().get_to_d().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("To date empty");
		    		med.get_pop_win().show();
		    		return;
		    	}		
				if(Integer.parseInt(med.get_edit_tab().get_from_d().getText())>31||Integer.parseInt(med.get_edit_tab().get_from_d().getText())==0)	
		    	{
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Wrong date set");
		    		med.get_pop_win().show();
		    		return;
		    	}
				if(Integer.parseInt(med.get_edit_tab().get_to_d().getText())>31||Integer.parseInt(med.get_edit_tab().get_to_d().getText())==0)	
		    	{
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Wrong date set");
		    		med.get_pop_win().show();
		    		return;
		    	}
				
				int days = get_days();
				if (days<=0){
					((Label)med.get_pop_win().getScene().getRoot()).setText("Wrong date set");
		    		med.get_pop_win().show();
		    		return;
				}
				if(med.get_edit_tab().get_res_name().equals("")){
					((Label)med.get_pop_win().getScene().getRoot()).setText("Reservation name missing");
		    		med.get_pop_win().show();
		    		return;
				}
				if(med.get_edit_tab().get_res_id().equals("")){
					((Label)med.get_pop_win().getScene().getRoot()).setText("Reservation id missing");
		    		med.get_pop_win().show();
		    		return;
				}
				if(med.get_edit_tab().get_pay_name().equals("")){
					((Label)med.get_pop_win().getScene().getRoot()).setText("Billing name missing");
		    		med.get_pop_win().show();
		    		return;
				}
				if(med.get_edit_tab().get_pay_id().equals("")){
					((Label)med.get_pop_win().getScene().getRoot()).setText("Billing id missing");
		    		med.get_pop_win().show();
		    		return;
				}
				if(!med.get_edit_tab().get_pay_box().getValue().equals("Cash") && med.get_edit_tab().get_card_field().getText().equals("")){
		    		((Label)med.get_pop_win().getScene().getRoot()).setText("Card number missing");
		    		med.get_pop_win().show();
		    		return;
		    	}
				
			
				try {
					Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					
					Statement st=conn.createStatement();
					Statement sec_st=conn.createStatement();
					ObservableList<String> row=(ObservableList<String>) med.get_search_tab().get_display().getSelectionModel().getSelectedItem();
					
					
					
					
					
					String from_str="'"+med.get_edit_tab().get_from_y().getValue()+"-"+med.get_edit_tab().get_from_m().getValue()+"-"+med.get_edit_tab().get_from_d().getText()+"'";
					String to_str="'"+med.get_edit_tab().get_to_y().getValue()+"-"+med.get_edit_tab().get_to_m().getValue()+"-"+med.get_edit_tab().get_to_d().getText()+"'";
					String cardnum="0";
					if(!med.get_edit_tab().get_pay_box().getValue().equals("Cash"))
						cardnum=med.get_edit_tab().get_card_field().getText();
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parsed =  format.parse(row.get(1));
				    java.sql.Date from = new java.sql.Date(parsed.getTime());
				    parsed= format.parse(row.get(2));
				    java.sql.Date to=new java.sql.Date(parsed.getTime());
				     
				 
				    

					if(!row.get(7).equals(med.get_edit_tab().get_res_name().getText()) || !row.get(8).equals(med.get_edit_tab().get_res_id().getText())){
						st.executeUpdate("UPDATE customers SET name='"+med.get_edit_tab().get_res_name().getText()+"',perid="+med.get_edit_tab().get_res_id().getText()+
						" WHERE id=(SELECT id FROM customers WHERE name='"+row.get(7)+"' AND perid="+row.get(8)+")");
					}
				    
					
					ResultSet rs=st.executeQuery("SELECT price FROM rooms WHERE number="+med.get_edit_tab().get_room_field().getText());
					rs.next();
					int price=rs.getInt(1)*days;
					
					rs=st.executeQuery("SELECT b.id FROM bookings AS b JOIN rooms AS r ON b.room=r.id WHERE r.number="+row.get(0)+" AND from_date='"+from+"' AND to_date='"+to+"'");
					rs.next();
					int bookid=rs.getInt(1);
					
					rs=st.executeQuery("SELECT s.type,COUNT(s.type) FROM services AS s JOIN serlink AS l ON l.service=s.id WHERE l.booking="+bookid+" GROUP BY s.type ORDER BY s.type");
					
					while(rs.next()){
						
						ResultSet price_rs=sec_st.executeQuery("SELECT price FROM services WHERE type='"+rs.getString(1)+"'");
						price_rs.next();
						price=price+(rs.getInt(2)*price_rs.getInt(1));
						price_rs.close();
					}
					
					if(!med.get_edit_tab().get_serv_box().getValue().equals("-")){
						rs=st.executeQuery("SELECT price FROM services WHERE type='"+med.get_edit_tab().get_serv_box().getValue()+"'");
						rs.next();
						price=price+rs.getInt(1);
						
						rs=st.executeQuery("SELECT id FROM services WHERE type='"+med.get_edit_tab().get_serv_box().getValue()+"'");
						rs.next();
						st.executeUpdate("INSERT INTO serlink(booking,service) VALUES ("+bookid+","+rs.getInt(1)+");");
						
					}
					if(!med.get_edit_tab().get_rserv_box().getValue().equals("-")){
						
						rs=st.executeQuery("SELECT id FROM services WHERE type='"+med.get_edit_tab().get_rserv_box().getValue()+"'");
						rs.next();
						int rowc=sec_st.executeUpdate("DELETE FROM serlink WHERE booking="+bookid+" AND service="+rs.getInt(1));
						
						
						while(rowc>1){
							st.executeUpdate("INSERT INTO serlink(booking,service) VALUES ("+bookid+","+rs.getInt(1)+");");
							rowc--;
						}

						
						
						rs=st.executeQuery("SELECT price FROM services WHERE type='"+med.get_edit_tab().get_rserv_box().getValue()+"'");
						rs.next();
						if (!(rowc==0)){
							price=price-rs.getInt(1);
						}
						
					}
					st.executeUpdate("UPDATE billings SET name='"+med.get_edit_tab().get_pay_name().getText()+"',perid="+med.get_edit_tab().get_pay_id().getText()+",type='"
							+med.get_edit_tab().get_pay_box().getValue()+"',cardid="+cardnum+",sum="+price+" WHERE id=(SELECT billing FROM bookings WHERE room="
							+"(SELECT id FROM rooms WHERE number="+row.get(0)+") AND from_date='"+from+"' AND to_date='"+to+"')");
					
					
				    String room_str="";
				    if(!row.get(0).equals(med.get_edit_tab().get_room_field().getText()))
				    	room_str=",room=(SELECT id FROM rooms WHERE number="+med.get_edit_tab().get_room_field().getText()+")";
				    
				    
					st.executeUpdate("UPDATE bookings SET id=id"+room_str+",from_date="+from_str+
					",to_date="+to_str+" WHERE room=(SELECT id FROM rooms WHERE number="+row.get(0)+") AND from_date='"+from+"' AND to_date='"+to+"'");
				
			
					
					med.get_res_tab().setDisable(false);
					med.get_search_tab().setDisable(false);
					med.get_search_tab().getTabPane().getSelectionModel().select(med.get_search_tab());
					med.get_edit_tab().setDisable(true);
					med.get_search_tab().get_res_but().fire();
					rs.close();
					sec_st.close();
					st.close();
					conn.close();
				} catch (Exception e1) {
				
					((Label)med.get_pop_win().getScene().getRoot()).setText("Database error at edit");
		    		med.get_pop_win().show();
		    		return;}
				
				
				
				
			 }});
		
	}
	
	void set_cancel(){
		med.get_edit_tab().get_cancel_but().setOnAction(event ->{

			med.get_res_tab().setDisable(false);
			med.get_search_tab().setDisable(false);
			med.get_search_tab().getTabPane().getSelectionModel().select(med.get_search_tab());
			med.get_edit_tab().setDisable(true);
		});
		
		
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void set_show(){
		med.get_edit_tab().get_show_but().setOnAction(event ->{
			try {
				Connection conn=DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
				Statement st=conn.createStatement();
				ObservableList<String> row=(ObservableList<String>) med.get_search_tab().get_display().getSelectionModel().getSelectedItem();
				
				String[] columns={"Service:","Count:"};
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date parsed =  format.parse(row.get(1));
			    java.sql.Date from = new java.sql.Date(parsed.getTime());
			    parsed= format.parse(row.get(2));
			    java.sql.Date to=new java.sql.Date(parsed.getTime());
			    
				ResultSet rs=st.executeQuery("SELECT b.id FROM bookings AS b JOIN rooms AS r ON b.room=r.id WHERE r.number="+row.get(0)+" AND from_date='"+from+"' AND to_date='"+to+"'");
				rs.next();
				int bookid=rs.getInt(1);
				rs=st.executeQuery("SELECT s.type,COUNT(s.type) FROM services AS s JOIN serlink AS l ON l.service=s.id WHERE l.booking="+bookid+" GROUP BY s.type ORDER BY s.type");
				Table_fill.set_table(rs,columns,(TableView)med.get_serv_win().getScene().getRoot());
				
				med.get_serv_win().show();	
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e1) {
				((Label)med.get_pop_win().getScene().getRoot()).setText("Database error at service display");
	    		med.get_pop_win().show();
	    		return;}
			
		});
	}
	
	int get_days(){
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(med.get_edit_tab().get_from_y().getValue()),
				Integer.parseInt(med.get_edit_tab().get_from_m().getValue()),
				Integer.parseInt(med.get_edit_tab().get_from_d().getText()));
		Date from_date = cal.getTime();
		cal.set(Integer.parseInt(med.get_edit_tab().get_to_y().getValue()),
				Integer.parseInt(med.get_edit_tab().get_to_m().getValue()),
				Integer.parseInt(med.get_edit_tab().get_to_d().getText()));
		Date to_date = cal.getTime();
		
		return (int) ((to_date.getTime() - from_date.getTime()) / (1000 * 60 * 60 * 24));
	}
	
}
