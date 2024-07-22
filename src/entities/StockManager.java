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
				conn = DB.getConnection();
				st = conn.createStatement();
				rs = st.executeQuery("select * from stocks");
				listOfStocks.clear(); // evitar adicionar ação toda vez que carregar a função
				
				while (rs.next()) { // isso é booleano, quando não tiver proxima linha ele vai dar false e vai parar
					Stock tempStock = new Stock();
					tempStock.setStockName(rs.getString("stock"));
					tempStock.setStockSector(rs.getString("sector"));
					tempStock.setStartValue(rs.getDouble("start_value"));
					tempStock.setStartDate(rs.getDate("start_date"));
					tempStock.setAccumulatedValue(rs.getDouble("accumulated_value"));
					tempStock.setRecordDate(rs.getDate("record_date"));
					

					listOfStocks.add(tempStock); // adicionando ação na lista local
				}
				
			} catch (SQLException msg) {
				throw new DbException(msg.getMessage());
			}
	}
	
	public void showAllStocks() {
		loadStocks();
		for (int i = 0; i < listOfStocks.size(); i++) {
			System.out.println("[" + (i+1) + "] " + listOfStocks.get(i).toString()); // ideal seria tirar esse indicado I e me basear pelo id das ações
		}
	}
	
	public void addNewStockRecord(int stockNum, double accumulatedValue, Date recordDate) { // essa aqui vai ser um comando usual de o usuario mudar o ultimo registro dele
	
		try {
			conn = DB.getConnection();
			st  = conn.createStatement();
			
			String query = "update stocks set accumulated_value = '" + accumulatedValue + "', record_date = '" + sqlsdf.format(recordDate) + "' where id = '" + stockNum + "';";
			
			st.execute(query);
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
		
		
		// 1º selecionar stock especifica para adicionar seu novo valor acumulado 
		// 2º adiciconar valor acumulado (sobrescrever o outro)
		// 3º gerar data automaticamente
		// 4º atualizar lista 
		
		
		
	}
	
	public void changeStockData(int stockNum, ColumnName columnName, String newData, DataType dataType) {
		// posso interligar o newRecord com esse aq
		switch (dataType) {
		case stringandvalue: // stock tem limite de 10 e sector tem limite de 30
			try {
				conn = DB.getConnection();
				st = conn.createStatement();
				
				String query = "update stocks set " + columnName.toString() + " = '" + newData.toString() + "' where id = '" + stockNum + "';";
				
				st.execute(query);
				
			} catch (SQLException msg) {
				throw new DbException(msg.getMessage());
			}
			
		case date:
			try {
				conn = DB.getConnection();
				st = conn.createStatement();
				
				String query = "update stocks set " + columnName.toString() + " = '" + sqlsdf.parse(newData) + "' where id = '" + stockNum + "';";
				
				st.execute(query);
				
			} catch (SQLException msg) {
				throw new DbException(msg.getMessage());
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}

	}
	
	public double[] getGeneralGain() {
		
		try {
			
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "select start_value, accumulated_value from stocks;";
			
			rs = st.executeQuery(query);
			
			Double actualFullInvestment = 0.0;
			Double startFullInvestment = 0.0;
			
			while (rs.next()) {
				actualFullInvestment += rs.getDouble("accumulated_value");
				startFullInvestment += rs.getDouble("start_value");
			}
			
			
			double profit = actualFullInvestment - startFullInvestment;
			
			double percentageProfit = ( (actualFullInvestment - startFullInvestment) / startFullInvestment ) * 100;
			
			double[] returnDoubleList = new double[4]; // retornando multiplos valores (é so usar uma lista)
			
			returnDoubleList[0] = startFullInvestment;
			returnDoubleList[1] = actualFullInvestment;
			returnDoubleList[2] = profit;
			returnDoubleList[3] = percentageProfit;
			
			return returnDoubleList;

			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
		
	}
	
	public void getSpecificGain() {
		
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
	

	public ArrayList<Stock> getListOfStocks() { // essa lista vai armazenar as ações aqui localmente
		return listOfStocks;
	}
	
	
}
