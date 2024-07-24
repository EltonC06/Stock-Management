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
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat df = new DecimalFormat("#,###,####,##0.00");
	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	
	CsvLink csvLink = new CsvLink();
	
	
	
	
	protected void saveStockRecordData(Stock stock) {

		// vou salvar apenas o Nome, Valor acumulado, Data de registro
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(csvLink.csvPathLink(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // esse true não vai deletar o ultimo dado registrado
		
		
		BufferedWriter bw = new BufferedWriter(fw);
		
		
		try {
			bw.write(stock.getStockName() + "|" + df.format(stock.getAccumulatedValue()) + "|" + stock.getRecordDate());
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	protected void dateAndDataFormat() {
		Date today = new Date();
		
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(csvLink.csvPathLink(), true);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		BufferedWriter bw = new BufferedWriter(fw);
		
		try {
			bw.newLine();
			bw.write(sdf.format(today));
			bw.newLine();
			bw.newLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void readAndSeparate() {
		
		
		
		
	}
	
	
	
	
}
