package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entities.Stock;
import entities.StockManager;
import entities.enums.ColumnName;
import entities.enums.DataType;

public class Program {
	static Scanner sc = new Scanner(System.in);
	
	static StockManager manager = new StockManager();
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // tenho que deixar essas declarações fora para pegar em todos os metodos
	// tem que deixar static para pegar no codigo inteiro
	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		
		// parse transforma string em data no formato. Format formata uma data e transforma em string de acordo com o padrão estabelecido no sdf
		
		
		System.out.println("--- Bem vindo ao Stock Management ---");
		
		while (true) {
			System.out.println("O que você deseja fazer? \n1)Adicionar nova ação \n2)Ver lista completa de ações \n3)Adicionar novo registro \n4)Deletar ação específica \n5)Alterar algum dado da ação \n6) Gerar lucro total \n7) Sair do programa ");
			int decision = sc.nextInt();
			
			switch (decision) {
			case 1:
				// o valor inicial de investimento deve mudar de nome para valor investido. E o outro para valor acumulado
				addNewStock();
				
				// tenho que pegar essas declarações de variaveis e declarar no inicio do programa
				break;
			case 2:
				showAllStocks();
				
				break;
			case 3:
				// novo registro
				addNewRecord();

				break;
			case 4:
				// deletar ação especifica
				deleteSpecificStock();
				
				break;
			case 5:
				// alterar dado
				changeStockData();

				break;
			case 6:
				// lucro
				totalProfit();
				
				break;
			case 7:
				// terminar programa
				break;
			}
			
			if (decision == 7) {
				break;
			}

		}	

	}
	
	public static void addNewStock() {
		System.out.println("Digite o seu código:");
		String stockName = sc.next();
		
		System.out.println("Digite seu setor:");
		String stockSector = sc.next();
		
		System.out.println("Valor inicial de investimento:");
		double initialValue = sc.nextDouble();
		
		System.out.println("Data de quando o investimento foi realizado [dd/mm/yyyy]: ");
		String initialDate = sc.next();
		
		// se a data for diferente da de hoje, não perguntar as proximas perguntas
		
		System.out.println("Valor acumulado do investimento");
		double actualValue = sc.nextDouble();
		
		
		System.out.println("Data de registro de seu último valor acumulado [dd/mm/yyyy]:");
		String actualDate = sc.next();
		
		Stock tempStock = null; // aq inicializei
		try { // aq instanciei o objeto
			tempStock = new Stock(stockName, stockSector, initialValue, sdf.parse(initialDate), actualValue, sdf.parse(actualDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		manager.insertStock(tempStock);
		System.out.println("new stock saved");
		
		
		
		
	}
	
	public static void showAllStocks() {
		manager.showAllStocks();
		
	}
	
	public static void addNewRecord() {
		int idDecision;
		double actualValue;
		String dateDecision;
		Date today = new Date();
		manager.showAllStocks();
		
		System.out.println("\nSelecione um id de ação para adicionar um novo registro:");
		idDecision = sc.nextInt();
		
		System.out.println("Digite seu valor atual:");
		actualValue = sc.nextDouble();
		
		System.out.println("Preço registrado hoje [Y/N]?");
		
		dateDecision = sc.next();
		
		if (dateDecision.equalsIgnoreCase("Y")) {
			manager.addNewStockRecord(idDecision, actualValue, today);
			System.out.println("Salvo");
		}
		else {
			System.out.println("Digite a data de registro [dd/MM/yyyy]: ");
			String recordDate = sc.next();
			try {
				manager.addNewStockRecord(idDecision, actualValue, sdf.parse(recordDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("Salvo");
		}
		
		
	}
	
	public static void deleteSpecificStock() { // não ta funcionando direito (tenho que usar o id original da tabela SQL)
		
		manager.showAllStocks();
		
		System.out.println("\nSelecione um id de ação para deletar:");
		int idDecision = sc.nextInt();
		
		manager.deleteStock(idDecision);
		System.out.println("Ação deletada com sucesso!");
		
		
	}
	
	public static void changeStockData() {
		int columnOption;
		
		manager.showAllStocks();
		
		System.out.println("\n Selecione o id da ação que você deseja alterar algum tipo de dado:");
		int idDecision = sc.nextInt();
		
		System.out.println("Qual coluna você deseja alterar?");
		
		System.out.println("[1] Nome da ação\n[2] Setor\n[3] Valor inicial\n[4]Data inicial\n");
		System.out.print("Selecione uma opção: ");
		columnOption = sc.nextInt();
		
		switch(columnOption) {
		case 1: // TODO ta dando erro com data aqui
			System.out.println("Digite o novo nome da ação: ");
			String stockName = sc.next();
			manager.changeStockData(idDecision, ColumnName.stock, stockName, DataType.stringandvalue);
			
			break;
			
		case 2:
			System.out.println("Digite o novo nome do setor:");
			String stockSector = sc.next();
			
			break;
		
		case 3:
			System.out.println("Digite o novo valor investido:");
			double investedValue = sc.nextDouble();
			
			break;
		
		case 4:
			System.out.println("Digite uma nova data [dd/mm/yyyy]:");
			String newDate = sc.next();
			break;
		}
			
		
		
		
		
	} // da o mesmo resultado independente do valor do objeto
	// metodo estatico: independente da instanciação do objeto o seu resultado não deve variar. (não precisa instanciar objeto)
	public static void totalProfit() { // metodos estaticos não precisa de objetos para ser criados
		StockManager manager = new StockManager(); // o comportamento desse metodo não varia, deiferente de um metodo não estatico
		double profitList[] = manager.getGeneralGain();
		
		System.out.println("Valor inicial investido = " + profitList[0]);
		System.out.println("Valor final do investimento = " + profitList[1]);
		System.out.println("Lucro em R$ = " + profitList[2]);
		System.out.printf("Lucro em porcentagem: %.2f \n\n", profitList[3]);
		
	}
	
	

}
