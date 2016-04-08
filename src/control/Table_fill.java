package control;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class Table_fill {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void set_table(ResultSet rs, String [] columns,TableView table){
		try {
			table.getColumns().clear();
			for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){    
			    final int j = i;                
			    TableColumn col = new TableColumn(columns[i]);
			    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
			        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
			            return new SimpleStringProperty(param.getValue().get(j).toString());                        
			        }                    
			    });
			    table.getColumns().addAll(col);  
			}
    	ObservableList<ObservableList> data=FXCollections.observableArrayList();
    	while(rs.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                row.add(rs.getString(i));
            }
            data.add(row);
    	}
    	table.setItems(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
}