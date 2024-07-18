package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import entities.Stock;
import entities.StockManager;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		StockManager manager = new StockManager();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Stock stock = new Stock("TESTE4F", "tomada1", 100.00, sdf.parse("10/10/1000"), 120.00, sdf.parse("10/10/2000"));
		
		manager.insertStock(stock);
		

	}

}
