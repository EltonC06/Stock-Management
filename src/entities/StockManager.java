package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.DB;
import db.DbException;

public class StockManager {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	private ArrayList<Stock> listOfStocks = new ArrayList<Stock>();
	

	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sqlsdf = new SimpleDateFormat("yyyy/MM/dd");
	
	
	public void insertStock(Stock stock) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			// (default, 'PSSA3F', 'saúde', '1000.00', '2024-07-18', '1200.00', '2024-09-18'); 
			String query = "Insert into stocks values (default, '" + stock.getStockName() + "', '" + stock.getStockSector() + "', '" + stock.getStartValue() + "', '" + sqlsdf.format(stock.getStartDate()).replace("/", "-") + "', '" + stock.getAccumulatedValue() + "', '" + sqlsdf.format(stock.getRecordDate()).replace("/", "-") + "');";
			// o format transforma a data em string no formato desejado. O parse transforma o string em data no formato desejado
			st.execute(query);
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
		
	}
	
	private void loadStocks() {
			try {
				Stock tempStock = new Stock();
				conn = DB.getConnection();
				st = conn.createStatement();
				rs = st.executeQuery("select * from stocks");
				
				while (rs.next()) { // isso é booleano, quando não tiver proxima linha ele vai dar false e vai parar
					tempStock.setStockName(rs.getString("stock"));
					tempStock.setStockSector(rs.getString("sector"));
					tempStock.setStartValue(rs.getDouble("start_value"));
					tempStock.setStartDate(rs.getDate("start_date"));
					tempStock.setAccumulatedValue(rs.getDouble("accumulated_value"));
					tempStock.setRecordDate(rs.getDate("record_date"));
					
					System.out.println("Tarefa adicionada na temp list: " + tempStock.toString());
					
					listOfStocks.add(tempStock); // adicionando ação na lista temporaria
				}
				
			} catch (SQLException msg) {
				throw new DbException(msg.getMessage());
			}
	}
	
	public void showAllStocks() {
		loadStocks();
		for (int i = 0; i < listOfStocks.size(); i++) {
			System.out.println(listOfStocks.get(1).toString());
		}
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

	public ArrayList<Stock> getListOfStocks() { // essa lista vai armazenar as ações aqui localmente
		return listOfStocks;
	}
	
	
}
