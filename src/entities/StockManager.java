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
	
	public StockManager() {
		createTable(); // esse comando so é executado 1 vez e vai criar uma tabela se o usuário não tiver uma
	}
	
	public void insertStock(Stock stock) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			// (default, 'PSSA3F', 'saúde', '1000.00', '2024-07-18', '1200.00', '2024-09-18'); 
			String query = "Insert into stocks values (default, '" + stock.getStockName() + "', '" + stock.getStockSector() + "', '" + stock.getStartValue() + "', '" + sqlsdf.format(stock.getStartDate()).replace("/", "-") + "', '" + stock.getAccumulatedValue() + "', '" + sqlsdf.format(stock.getRecordDate()).replace("/", "-") + "');";
			// o format transforma a data em string no formato desejado. O parse transforma o string em data no formato desejado
			st.execute(query);
		} catch (SQLException msg) {
			//throw new DbException(msg.getMessage());
			System.out.println("\nAlgum valor não foi digitado no formato desejado e a ação registrada não foi salva\ntente novamente!");
		}
		// o id vai ser gerado la, e eu o-trago pra cá
	}
	
	private void loadStocks() {
			try {
				conn = DB.getConnection();
				st = conn.createStatement();
				rs = st.executeQuery("select * from stocks");
				listOfStocks.clear(); // evitar adicionar ação toda vez que carregar a função
				
				while (rs.next()) { // isso é booleano, quando não tiver proxima linha ele vai dar false e vai parar
					Stock tempStock = new Stock();
					tempStock.setStockId(rs.getInt("id"));
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
			System.out.println(listOfStocks.get(i).toString()); // ideal seria tirar esse indicado I e me basear pelo id das ações
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
			break;
		case date:
			try {
				conn = DB.getConnection();
				st = conn.createStatement();
				
				String query = "update stocks set " + columnName.toString() + " = '" + sqlsdf.format(sdf.parse(newData)) + "' where id = '" + stockNum + "';";
				// transformei string em data de acordo com o padrão digitado pelo usuario e depois formatei para ficar com o padrão do sql
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
	
	public double[] getSpecificGain(int stockNum) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "select start_value, accumulated_value from stocks where id = " + stockNum + ";";
			
			rs = st.executeQuery(query);
			
			double[] returnDoubleList = new double[4];
			double startInvestment = 0;
			double actualInvestment = 0;
			
			if (rs.next()) { // o result set começa na linha 0, então tem que executar um comando antes para que ele passe para a linha 1 para ler os dados
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
	

	public ArrayList<Stock> getListOfStocks() { // essa lista vai armazenar as ações aqui localmente
		return listOfStocks;
	}
	
	public void resetStockId() { // descobrir jeito mais seguro de resetar ids
		loadStocks();
		clearDataBase();
		
		for (int i = 0; i<listOfStocks.size(); i++) {
			Stock tempStock = new Stock(listOfStocks.get(i).getStockName(), listOfStocks.get(i).getStockSector(), listOfStocks.get(i).getStartValue(), listOfStocks.get(i).getStartDate(), listOfStocks.get(i).getAccumulatedValue(), listOfStocks.get(i).getRecordDate());
			insertStock(tempStock);
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
	
	private void createTable() { // aqui é pra usuarios novos, ele vai criar a tabela por ele (não da pra criar banco de dados por ele)
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			String query = "CREATE TABLE if not exists `stocks` (\r\n"
					+ "  `id` int NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `stock` varchar(10) NOT NULL,\r\n"
					+ "  `sector` varchar(20) NOT NULL,\r\n"
					+ "  `start_value` decimal(10,2) NOT NULL,\r\n"
					+ "  `start_date` date NOT NULL,\r\n"
					+ "  `accumulated_value` decimal(10,2) NOT NULL,\r\n"
					+ "  `record_date` date NOT NULL,\r\n"
					+ "  PRIMARY KEY (`id`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
			
			
			st.execute(query);
			
		} catch (SQLException msg) {
			throw new DbException(msg.getMessage());
		}
		
		
	}
	
	
}
