package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import db.DB;
import db.DbException;
import entities.enums.ColumnName;
import entities.enums.DataType;

public class StockManager {
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	private  ArrayList<Stock> listOfStocks = new ArrayList<Stock>();
	
	private PastStockSaver pss = new PastStockSaver();
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sqlsdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public StockManager() {
		createTable();
	}
	
	
	
	public void insertStock(Stock stock, boolean showMessage) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			String query = "Insert into stocks values (default, '" + stock.getStockName() + "', '" + stock.getStockSector() + "', '" + stock.getStartValue() + "', '" + sqlsdf.format(stock.getStartDate()).replace("/", "-") + "', '" + stock.getAccumulatedValue() + "', '" + sqlsdf.format(stock.getRecordDate()).replace("/", "-") + "');";
			st.execute(query);
			if (showMessage) {
				System.out.println("\nNew investment added successfully in the database!");
			} else {
			}
		} catch (SQLException msg) {
			System.out.println("\nSome values weren't typed correctly, please try again!");
		}
	}
	
	
	
	private void loadStocks() {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from stocks");
			listOfStocks.clear(); 
			
			while (rs.next()) { 
				Stock tempStock = new Stock();
				tempStock.setStockId(rs.getInt("id"));
				tempStock.setStockName(rs.getString("stock"));
				tempStock.setStockSector(rs.getString("sector"));
				tempStock.setStartValue(rs.getDouble("start_value"));
				tempStock.setStartDate(rs.getDate("start_date"));
				tempStock.setAccumulatedValue(rs.getDouble("accumulated_value"));
				tempStock.setRecordDate(rs.getDate("record_date"));
				
				listOfStocks.add(tempStock);
			}
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
	}
	
	
	
	public void showAllStocks() {
		loadStocks();
		
		for (int i = 0; i < listOfStocks.size(); i++) {
			System.out.println(listOfStocks.get(i).toString()); 
		}
	}
	
	
	
	public void addNewStockRecord(int stockNum, double accumulatedValue, Date recordDate) {
		try {
			conn = DB.getConnection();
			st  = conn.createStatement();
			
			String query = "update stocks set accumulated_value = '" + accumulatedValue + "', record_date = '" + sqlsdf.format(recordDate) + "' where id = '" + stockNum + "';";
			
			st.execute(query);
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}		
	}
	
	
	
	public void changeStockData(int stockNum, ColumnName columnName, String newData, DataType dataType) {
		switch (dataType) {
		
		case stringandvalue: 
			try {
				conn = DB.getConnection();
				st = conn.createStatement();
				
				String query = "update stocks set " + columnName.toString() + " = '" + newData.toString() + "' where id = '" + stockNum + "';";
				
				st.execute(query);
				
			} catch (SQLException msg) {
				throw new DbException(msg.getMessage());
			}
			break;
		
		case date:
			try {
				conn = DB.getConnection();
				st = conn.createStatement();
				
				String query = "update stocks set " + columnName.toString() + " = '" + sqlsdf.format(sdf.parse(newData)) + "' where id = '" + stockNum + "';";
				
				st.execute(query);
				
			} catch (SQLException msg) {
				throw new DbException(msg.getMessage());
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			break;
		}
	}
	
	
	
	public double[] getGeneralGain() {
		
		try {
			Double actualFullInvestment = 0.0;
			Double startFullInvestment = 0.0;
			
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "select start_value, accumulated_value from stocks;";
			
			rs = st.executeQuery(query);
			
			while (rs.next()) {
				actualFullInvestment += rs.getDouble("accumulated_value");
				startFullInvestment += rs.getDouble("start_value");
			}
			
			double profit = actualFullInvestment - startFullInvestment;
			
			double percentageProfit = ( (actualFullInvestment - startFullInvestment) / startFullInvestment ) * 100;
			
			double[] returnDoubleList = new double[4];
			
			returnDoubleList[0] = startFullInvestment;
			returnDoubleList[1] = actualFullInvestment;
			returnDoubleList[2] = profit;
			returnDoubleList[3] = percentageProfit;
			
			return returnDoubleList;

		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
	}
	
	
	
	public double[] getSpecificGain(int stockNum) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "select start_value, accumulated_value from stocks where id = " + stockNum + ";";
			
			rs = st.executeQuery(query);
			
			double[] returnDoubleList = new double[4];
			double startInvestment = 0;
			double actualInvestment = 0;
			
			if (rs.next()) {
				startInvestment = rs.getDouble("start_value");
				actualInvestment = rs.getDouble("accumulated_value");
			}

			double profit = actualInvestment - startInvestment;
			double percentageProfit = ( (actualInvestment - startInvestment) / startInvestment ) * 100;
			
			returnDoubleList[0] = startInvestment;
			returnDoubleList[1] = actualInvestment;
			returnDoubleList[2] = profit;
			returnDoubleList[3] = percentageProfit;
			
			return returnDoubleList;
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
	}
	
	
	
	public void deleteStock(int stockNum) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "delete from stocks where id = '" + stockNum + "';";
			
			st.execute(query);
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
 	}
	

	
	public ArrayList<Stock> getListOfStocks() {
		return listOfStocks;
	}
	
	
	
	public void resetStockId() {
		loadStocks();
		clearDataBase();
		
		for (int i = 0; i<listOfStocks.size(); i++) {
			Stock tempStock = new Stock(listOfStocks.get(i).getStockName(), listOfStocks.get(i).getStockSector(), listOfStocks.get(i).getStartValue(), listOfStocks.get(i).getStartDate(), listOfStocks.get(i).getAccumulatedValue(), listOfStocks.get(i).getRecordDate());
			insertStock(tempStock, false);
		}
	}
	
	
	
	private void clearDataBase() {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "truncate table stocks";
			
			st.execute(query);
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
	}
	
	
	
	private void createTable() { 
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "CREATE TABLE if not exists `stocks` (\r\n"
					+ "  `id` int NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `stock` varchar(10) NOT NULL,\r\n"
					+ "  `sector` varchar(20) NOT NULL,\r\n"
					+ "  `start_value` decimal(12,2) NOT NULL,\r\n"
					+ "  `start_date` date NOT NULL,\r\n"
					+ "  `accumulated_value` decimal(12,2) NOT NULL,\r\n"
					+ "  `record_date` date NOT NULL,\r\n"
					+ "  PRIMARY KEY (`id`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
			
			st.execute(query);
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
	}
	
	
	
	public void closeSystem(boolean csvSave) {
		saveStockData(csvSave);
		
		DB.closeConnection();
		
		try {
			st.close();
			rs.close();
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
	}
	
	
	
	private void saveStockData(boolean csvSave) {
		resetStockId();
		
		if (csvSave) {
			pss.dateAndDataFormat();
		}
		else {
		}
		
		for (int i = 0; i < listOfStocks.size(); i++) {
			
			pss.saveStockRecordData(listOfStocks.get(i));	
			
		}
	}
}
