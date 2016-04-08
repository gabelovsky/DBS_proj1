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

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import mediator.Mediator;

public class Search_control {
	Mediator med;
	Boolean is_re=false;
	Search_control(Mediator med){
		this.med=med;
	}
	
	void set_room(){
		med.get_search_tab().get_room_but().setOnAction(new EventHandler<ActionEvent>() { 
		
			@Override
		    public void handle(ActionEvent e) {
				Connection conn;
				is_re=false;
				try {
					conn = DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					Statement st=conn.createStatement();
			    	//med.get_search_tab().get_display().getColumns().clear();
			    	String []columns={"Number:","Floor:","Type:","Alig.:","Price:","Reservation count:"};
			    	String room_str="";
			    	String name_str="";
			    	String id_str="";
			    	if(!med.get_search_tab().get_room_field().getText().equals("")){
						room_str=" AND r.number="+med.get_search_tab().get_room_field().getText();
					}
			    	if(!med.get_search_tab().get_name_field().getText().equals("")){
						name_str=" AND b.customer in (select id from customers where name like '%"+med.get_search_tab().get_name_field().getText()+"%')";
					}
			    	if(!med.get_search_tab().get_id_field().getText().equals("")){
						id_str=" AND b.customer in (select id from customers where perid="+Integer.parseInt(med.get_search_tab().get_id_field().getText())+")";
					}
			    	
			    	
			    	ResultSet rs=st.executeQuery("SELECT r.number,r.floor,r.type,r.alig,r.price,count(r.number) FROM rooms AS r LEFT JOIN bookings AS b ON b.room=r.id "
			    	+"WHERE TRUE"+room_str+name_str+id_str
			    	+" GROUP BY r.number,r.floor,r.type,r.alig,r.price ");
			    	
			    	
			    	
		
			    	Table_fill.set_table(rs,columns, med.get_search_tab().get_display());
			    	rs.close();
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
				
		    	
		    	
		    }});
	}
	
	void set_person(){
		med.get_search_tab().get_per_but().setOnAction(event ->{
			//@SuppressWarnings({ "unchecked", "rawtypes" })
			
				Connection conn;
				is_re=false;
				try {
					conn = DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					Statement st=conn.createStatement();
			    	med.get_search_tab().get_display().getColumns().clear();
			    	
			    	
			    	String[] columns={"Name:","Per. id:","Res. count:","Avg. sum:"};
			    	ResultSet rs=st.executeQuery("select c.name,c.perid,COUNT(b.id),COALESCE(ROUND(AVG(bi.sum)),0) from customers as c full join bookings as b on b.customer=c.id "
			    			+ "full join billings as bi on b.billing=bi.id group by c.perid,c.name order by c.name");

					    	
					    	
					Table_fill.set_table(rs, columns, med.get_search_tab().get_display());
			    	
			    	
			    	rs.close();
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
				
		    	
		    	
		    });
	}

	void set_reservation(){
		med.get_search_tab().get_res_but().setOnAction(new EventHandler<ActionEvent>() { 
			
			@Override
		    public void handle(ActionEvent e) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					Statement st=conn.createStatement();
			    	med.get_search_tab().get_display().getColumns().clear();
			    	
			    	String room_str="";
			    	String name_str="";
			    	String id_str="";
			    	if(!med.get_search_tab().get_room_field().getText().equals("")){
						room_str=" AND r.number="+med.get_search_tab().get_room_field().getText();
					}
			    	if(!med.get_search_tab().get_name_field().getText().equals("")){
						name_str=" AND (c.name LIKE '%"+med.get_search_tab().get_name_field().getText()+"%'"+
			    	"OR bi.name LIKE '%"+med.get_search_tab().get_name_field().getText()+"%')";
					}
			    	if(!med.get_search_tab().get_id_field().getText().equals("")){
						id_str=" AND (c.perid="+Integer.parseInt(med.get_search_tab().get_id_field().getText())+" OR bi.perid="+
					Integer.parseInt(med.get_search_tab().get_id_field().getText())+")";
					}
			    	
			    	String []columns={"Number:","From:","To:","Price:","Floor:","Type:","Alig.:","Res. name:","Res. id:","Bill. name:","Bill. id:","Sum:","Bill. type:","Card id:","Paid?"};
			    	ResultSet rs=st.executeQuery("SELECT r.number,b.from_date,b.to_date,r.price,r.floor,r.type,r.alig,c.name,c.perid,bi.name,bi.perid,bi.sum,bi.type,bi.cardid,bi.paid"
			    			+" FROM bookings AS b JOIN rooms AS r ON b.room=r.id JOIN customers AS c on b.customer=c.id JOIN billings AS bi ON b.billing=bi.id WHERE TRUE"
			    			+room_str+name_str+id_str+" ORDER BY r.number");
					    	
					    	
					    	
					    	
			    	Table_fill.set_table(rs,columns, med.get_search_tab().get_display());
			    	is_re=true;
			    	rs.close();
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
			
		    }});
	}

	void set_remove(){
		med.get_search_tab().get_rem_but().setOnAction(new EventHandler<ActionEvent>() { 
			
			@SuppressWarnings("unchecked")
			@Override
		    public void handle(ActionEvent e) {

		    	if(med.get_search_tab().get_display().getSelectionModel().isEmpty() || !is_re){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("No reservation selected");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}		
				Connection conn;
				try {
					conn = DriverManager.getConnection(med.get_database(), med.get_user(), med.get_password());
					Statement st=conn.createStatement();
			   
					ObservableList<String> row=(ObservableList<String>) med.get_search_tab().get_display().getSelectionModel().getSelectedItem();
					
				
					
					
					 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					 Date parsed =  format.parse(row.get(1));
				     java.sql.Date from = new java.sql.Date(parsed.getTime());
				     parsed= format.parse(row.get(2));
				     java.sql.Date to=new java.sql.Date(parsed.getTime());
				     
					st.executeUpdate("DELETE FROM bookings WHERE room=(SELECT id FROM rooms WHERE number="+row.get(0)+") AND from_date='"+from+"' AND to_date='"+to+"'");
					med.get_search_tab().get_display().getItems().remove(med.get_search_tab().get_display().getSelectionModel().getSelectedItem());
			    	st.close();
					conn.close();	
				} catch (Exception e1) {	
					
				
					e1.printStackTrace();
				}
				
		    	
		    	
		    }});
	}

	void set_edit(){
		med.get_search_tab().get_edit_but().setOnAction(new EventHandler<ActionEvent>() { 
			
			@SuppressWarnings("unchecked")
			@Override
		    public void handle(ActionEvent e) {

		    	if(med.get_search_tab().get_display().getSelectionModel().isEmpty() || !is_re){
		    		((Label)med.get_pop_win().get_err_stage().getScene().getRoot()).setText("No reservation selected");
		    		med.get_pop_win().get_err_stage().show();
		    		return;
		    	}		
		    	ObservableList<String> row=(ObservableList<String>) med.get_search_tab().get_display().getSelectionModel().getSelectedItem();
		    	
		    	
		    	med.get_edit_tab().get_room_field().setText(row.get(0));
		    	med.get_edit_tab().get_res_name().setText(row.get(7));
		    	med.get_edit_tab().get_res_id().setText(row.get(8));
		    	med.get_edit_tab().get_pay_name().setText(row.get(9));
		    	med.get_edit_tab().get_pay_id().setText(row.get(10));
		    	med.get_edit_tab().get_pay_box().getSelectionModel().select(row.get(12));
		    	med.get_edit_tab().get_card_field().setText(row.get(13));
		    	
		    	
		    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				Calendar cal = Calendar.getInstance();
				 
				 
				try {
					date = format.parse(row.get(1));
					cal.setTime(date);
					med.get_edit_tab().get_from_y().getSelectionModel().select(cal.get(Calendar.YEAR));
					med.get_edit_tab().get_from_m().getSelectionModel().select(cal.get(Calendar.MONTH));
					med.get_edit_tab().get_from_d().setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
					date = format.parse(row.get(2));
					cal.setTime(date);
					med.get_edit_tab().get_to_y().getSelectionModel().select(cal.get(Calendar.YEAR));
					med.get_edit_tab().get_to_m().getSelectionModel().select(cal.get(Calendar.MONTH));
					med.get_edit_tab().get_to_d().setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				 
				
				
				
				
		    	med.get_edit_tab().setDisable(false);
		    	med.get_edit_tab().getTabPane().getSelectionModel().select(med.get_edit_tab());
		    	med.get_res_tab().setDisable(true);
		    	med.get_search_tab().setDisable(true);
		    	
		    	
		    	
		   
				
		    	
		    	
		    }});
		
	}
}
