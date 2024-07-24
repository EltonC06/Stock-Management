package entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PastStockSaver {
	
	CsvLink csvLink = new CsvLink();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat df = new DecimalFormat("#,###,####,##0.00");
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	
	
	protected void saveStockRecordData(Stock stock) {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(csvLink.csvPathLink(), true);
		} catch (IOException e) {
			System.out.println("Algum erro aconteceu enquanto o sistema estava salvando os dados no arquivo csv.");
		} 
		
		
		BufferedWriter bw = new BufferedWriter(fw);
		
		try {
			bw.write(stock.getStockName() + "|" + df.format(stock.getAccumulatedValue()) + "|" + stock.getRecordDate());
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Algum erro aconteceu enquanto o sistema estava salvando os dados no arquivo csv.");
		}
		
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("Algum erro aconteceu enquanto o sistema estava salvando os dados no arquivo csv.");
		}
	}
	
	
	
	protected void dateAndDataFormat() {
		Date today = new Date();
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(csvLink.csvPathLink(), true);
		} catch(IOException e) {
			System.out.println("Algum erro aconteceu enquanto o sistema estava salvando os dados no arquivo csv.");
		}
		
		BufferedWriter bw = new BufferedWriter(fw);
		
		try {
			bw.newLine();
			bw.write(sdf.format(today));
			bw.newLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("Algum erro aconteceu enquanto o sistema estava salvando os dados no arquivo csv.");
		}
	}
}
