package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbException;

public class StockManager {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sqlsdf = new SimpleDateFormat("yyyy/MM/dd");
	
	
	public void insertStock(Stock stock) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			// (default, 'PSSA3F', 'saúde', '1000.00', '2024-07-18', '1200.00', '2024-09-18'); 
			String query = "Insert into stocks values (default, '" + stock.getStockName() + "', '" + stock.getStockSector() + "', '" + stock.getStartValue() + "', '" + "2000-10-10" + "', '" + stock.getAccumulatedValue() + "', '" + "2010-10-10" + "');";
			
			st.execute(query);
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
		
	}
	
	public void loadStocks() {
		
	}
	
	public void changeStock() {
		
	}
	
	public void getGeneralGain() {
		
	}
	
	public void getSpecificGain() {
		
	}
	
	public void deleteStock() {
		
	}
	
	public void addNewRecords() {
		
	}
	
	
}
