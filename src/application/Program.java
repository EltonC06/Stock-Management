package application;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import db.DbException;
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
			System.out.println("\nO que você deseja fazer? \n\n1) Adicionar nova ação \n2) Ver lista completa de ações \n3) Adicionar novo registro \n4) Deletar ação específica \n5) Alterar algum dado da ação \n6) Gerar lucro total \n7) Gerar lucro específico\n8) Sair do programa ");
			System.out.print("\nDigite aqui: ");
			
			try {
				int decision = sc.nextInt();
				
				switch (decision) {
				case 1:
					// adicionar ação
					addNewStock(); // AE
					
					break;
				case 2:
					// mostrar ações
					showAllStocks(); // AE
					
					break;
				case 3:
					// novo registro
					addNewRecord(); // AE
	
					break;
				case 4:
					// deletar ação especifica
					deleteSpecificStock(); // AE
					
					break;
				case 5:
					// alterar dado
					changeStockData();
	
					break;
				case 6:
					// lucro
					totalProfit(); // AE
					
					break;
					
				case 7:
					specificProfit(); // AE
					
					break;
				case 8:
					// terminar programa // AE
					break;
				}
				
				if (decision == 8) {
					System.out.println("\nSaving and quiting program...");
					saveAndQuit();
					System.out.println("Database saved and program shutted down!");
					
					break;
				}
				
				if (decision < 1 || decision > 8) {
					System.out.println("\nPor favor, digite uma opção válida!");
				}
	
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
				sc.next();
			}
		}
		//

	}
	


	private static void saveAndQuit() {
		manager.resetStockId();
	}

	public static void addNewStock() {
		Stock tempStock = null;
		
		String stockName;
		String stockSector;
		double initialValue;
		String initialDate = null;
		
		while (true) {
			while (true) {
				try {
					System.out.print("Digite o seu código: ");
					stockName = sc.next();
					if (stockName.length() > 10) {
						System.out.println("\nLimite máximo de caracteres atingido (>10), tente novamente!");
					}
					else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("");
				}
			}
			
			while (true) {
				try {
					System.out.print("Digite seu setor: ");
					
					stockSector = sc.next();
	
					if (stockSector.length() > 20) {
						System.out.println("\nLimite máximo de caracteres atingido (>20), tente novamente!");
					}
					else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPor favor, digite um valor válido!");
				}
			}
			
			while (true) {
				try {
					System.out.print("Valor inicial de investimento: R$");
					initialValue = sc.nextDouble();
					
					String[] separate = Double.toString(initialValue).split(",");
					
					
					if ( Double.toString(initialValue).length() > 12 || separate[0].length() > 10) {
						System.out.println("\nLimite máximo de dígitos atingido (>12), tente novamente!");
					} else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPor favor, digite um valor válido. [Ex: 100,00]!");
					sc.next();
				}
			}
			
			while (true) {
				try {
					System.out.print("Data de quando o investimento foi realizado [dd/mm/yyyy]: ");
					initialDate = sc.next();
					sdf.parse(initialDate);
					break;
				} catch (ParseException e) {
					System.out.println("\nFormato de data digitado de maneira incorreta, tente novamente!");
				}
			}
	
				
			
			try {
				tempStock = new Stock(stockName, stockSector, initialValue, sdf.parse(initialDate), initialValue, sdf.parse(initialDate));
			} catch (ParseException e) { // para mandar para o sql não precisa mandar com id
				System.out.println("\nFormato de data digitado de maneira incorreta, tente novamente!");
			} catch (InputMismatchException e) {
				System.out.println("\nAlgum valor não foi digitado no formato desejado, tente novamente.");
			} catch (DbException e) {
				System.out.println("\nAlgum valor não foi digitado no formato desejado, tente novamente.");
			}
			break;
		}
		
		manager.insertStock(tempStock);
		
	}
	
	public static void showAllStocks() {
		
		manager.showAllStocks();
		
	}
	
	public static void addNewRecord() {
		int idDecision = 0;
		double actualValue = 0;
		String dateDecision = null;
		Date today = new Date();
		manager.showAllStocks();
		
		while (true) {
			try {
				System.out.println("\nSelecione um id de ação para adicionar um novo registro:");
				idDecision = sc.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
			}
		}
		
		while (true) {
			try {
				System.out.println("Digite seu valor atual:");
				actualValue = sc.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
			}
			break;
		}
		
		while (true) {
			try {
				System.out.println("Preço registrado hoje [Y/N]?");
				dateDecision = sc.next();
				if (!dateDecision.equalsIgnoreCase("Y") && !dateDecision.equalsIgnoreCase("N")) {
					System.out.println("\nPor favor, digite um valor válido!");
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
			}

		}
		
		if (dateDecision.equalsIgnoreCase("Y")) {
			manager.addNewStockRecord(idDecision, actualValue, today);
			System.out.println("Salvo");
		}
		else if (dateDecision.equalsIgnoreCase("N")){
			String recordDate;
			while (true) {
				try {
					System.out.println("Digite a data de registro [dd/MM/yyyy]: ");
					recordDate = sc.next();
					sdf.parse(recordDate);
					break;
				} catch (ParseException e) {
					System.out.println("\nFormato de data digitado de maneira incorreta, tente novamente!");
				}
			}
				
			try {
				manager.addNewStockRecord(idDecision, actualValue, sdf.parse(recordDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			System.out.println("\nNovo registro salvo!");
		}
		

		
		
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
			manager.changeStockData(idDecision, ColumnName.sector, stockSector, DataType.stringandvalue);
			
			break;
		
		case 3:
			System.out.println("Digite o novo valor investido:");
			double investedValue = sc.nextDouble();
			
			manager.changeStockData(idDecision, ColumnName.start_value, investedValue+"", DataType.stringandvalue);
			
			break;
		
		case 4:
			System.out.println("Digite uma nova data [dd/mm/yyyy]:");
			String newDate = sc.next();
			manager.changeStockData(idDecision, ColumnName.start_date, newDate, DataType.date);
			break;
		}
			
		
		
		
		
	} // da o mesmo resultado independente do valor do objeto
	// metodo estatico: independente da instanciação do objeto o seu resultado não deve variar. (não precisa instanciar objeto)
	public static void totalProfit() { // metodos estaticos não precisa de objetos para ser criados
		double profitList[] = manager.getGeneralGain();
		
		System.out.println("Valor inicial investido = " + profitList[0]);
		System.out.println("Valor final do investimento = " + profitList[1]);
		System.out.println("Lucro em R$ = " + profitList[2]);
		System.out.printf("Lucro em porcentagem: %.2f \n\n", profitList[3]);
		
	}
	
	public static void deleteSpecificStock() {
		
		manager.showAllStocks();
		while (true) {
			try {
				System.out.println("\nSelecione um id de ação para deletar:");
	
				int idDecision = sc.nextInt();
				manager.deleteStock(idDecision);
				break;
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
				sc.next();
			}
		}
		// lista com todos os ids, se o id que o usuario digitar não tiver na lista, avisar
		
		System.out.println("Ação deletada com sucesso!");
		
		
	}
	
	private static void specificProfit() {
		manager.showAllStocks();
		int specificId;
		while (true) {
			try {
				System.out.println("Digite o id da ação que deseja ver o lucro específico:");
				specificId = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Por favor, digite um valor válido!");
				sc.next();
			}
		}

		
		
		double specificProfit[] = manager.getSpecificGain(specificId);
		
		System.out.println("Valor inicial investido = " + specificProfit[0]);
		System.out.println("Valor final do investimento = " + specificProfit[1]);
		System.out.println("Lucro em R$ = " + specificProfit[2]);
		System.out.printf("Lucro em porcentagem = %.2f%n", specificProfit[3]);
		
	}
	
	

}
