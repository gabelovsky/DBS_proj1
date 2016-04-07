package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import mediator.Mediator;

public class Search_control {
	Mediator med;

	Search_control(Mediator med){
		this.med=med;
	}
	
	void set_room(){
		med.get_search_tab().get_room_but().setOnAction(new EventHandler<ActionEvent>() { 
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public void handle(ActionEvent e) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(med.database, med.user, med.password);
					Statement st=conn.createStatement();
			    	med.get_search_tab().get_display().getColumns().clear();
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
			    	
			    	//b.customer in (select id from customers where true and name like '%P%')
			    	
			    	
			    	
			    	for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){    
		                final int j = i;                
		                TableColumn col = new TableColumn(columns[i]);
		                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
		                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
		                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
		                    }                    
		                });
		                med.get_search_tab().get_display().getColumns().addAll(col);  
		            }

			    	
			    	ObservableList<ObservableList> data=FXCollections.observableArrayList();
			    	while(rs.next()){
		                ObservableList<String> row = FXCollections.observableArrayList();
		                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
		                    row.add(rs.getString(i));
		                }
		                data.add(row);
			    	}
			    	med.get_search_tab().get_display().setItems(data);
			    	rs.close();
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
				
		    	
		    	
		    }});
	}
	
	void set_person(){
		med.get_search_tab().get_per_but().setOnAction(new EventHandler<ActionEvent>() { 
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public void handle(ActionEvent e) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(med.database, med.user, med.password);
					Statement st=conn.createStatement();
			    	med.get_search_tab().get_display().getColumns().clear();
			    	
			    	
			    	
			    	/*ResultSet rs=st.executeQuery("");
					    	
					    	
					    	
					    	
					    	
					    	for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){    
				                final int j = i;                
				                TableColumn col = new TableColumn(columns[i]);
				                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
				                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
				                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
				                    }                    
				                });
				                med.get_search_tab().get_display().getColumns().addAll(col);  
				            }

					    	
					    	ObservableList<ObservableList> data=FXCollections.observableArrayList();
					    	while(rs.next()){
				                ObservableList<String> row = FXCollections.observableArrayList();
				                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
				                    row.add(rs.getString(i));
				                }
				                data.add(row);
					    	}
					    	med.get_search_tab().get_display().setItems(data);*/
			    	
			    	
			    	
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
				
		    	
		    	
		    }});
	}

	void set_reservation(){
		med.get_search_tab().get_res_but().setOnAction(new EventHandler<ActionEvent>() { 
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public void handle(ActionEvent e) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(med.database, med.user, med.password);
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
			    	
			    	String []columns={"Number:","From:","To:","Price:","Floor:","Type:","Alig.:","Cust. name:","Cust. id:","Bill. name:","Bill. id:","Sum:","Bill. type:","Card id:","Paid?"};
			    	ResultSet rs=st.executeQuery("SELECT r.number,b.from_date,b.to_date,r.price,r.floor,r.type,r.alig,c.name,c.perid,bi.name,bi.perid,bi.sum,bi.type,bi.cardid,bi.paid"
			    			+" FROM bookings AS b JOIN rooms AS r ON b.room=r.id JOIN customers AS c on b.customer=c.id JOIN billings AS bi ON b.billing=bi.id WHERE TRUE"
			    			+room_str+name_str+id_str+" ORDER BY r.number");
					    	
					    	
					    	
					    	
					    	
					    	for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){    
				                final int j = i;                
				                TableColumn col = new TableColumn(columns[i]);
				                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
				                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
				                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
				                    }                    
				                });
				                med.get_search_tab().get_display().getColumns().addAll(col);  
				            }

					    	
					    	ObservableList<ObservableList> data=FXCollections.observableArrayList();
					    	while(rs.next()){
				                ObservableList<String> row = FXCollections.observableArrayList();
				                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
				                    row.add(rs.getString(i));
				                }
				                data.add(row);
					    	}
					    	med.get_search_tab().get_display().setItems(data);
			    	
			    	
			    	rs.close();
			    	st.close();
					conn.close();	
				} catch (SQLException e1) {	
					e1.printStackTrace();}
			
		    }});
	}
}
