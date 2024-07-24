package application;

import java.text.DecimalFormat;
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
	static Scanner sc = new Scanner(System.in).useLocale(Locale.US);
	
	static StockManager manager = new StockManager();
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	
	static private DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
	

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
	
		System.out.println("--- Bem vindo ao Stock Management ---");
		
		while (true) {
			
			System.out.println("\nO que você deseja fazer? \n\n1) Adicionar nova ação \n2) Ver lista completa de ações \n3) Adicionar novo registro \n4) Deletar ação específica \n5) Alterar algum dado da ação \n6) Gerar lucro total \n7) Gerar lucro específico\n8) Sair do programa ");
			System.out.print("\nDigite aqui:");
			
			try {
				
				int decision = sc.nextInt();
				
				switch (decision) {
				
				case 1:
					addNewStock();
					break;
					
				case 2:
					showAllStocks();
					break;
					
				case 3:
					addNewRecord();
					break;
					
				case 4:
					deleteSpecificStock();
					break;
					
				case 5:
					changeStockData();
					break;
					
				case 6:
					totalProfit();
					break;
					
				case 7:
					specificProfit();
					break;
					
				case 8:
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
	}
	
	
	
	private static void saveAndQuit() {
		manager.resetStockId();
		sc.close();
		manager.closeSystem();
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
					System.out.print("\nDigite o seu código:");
					stockName = sc.next();
					if (stockName.length() > 10) {
						System.out.println("\nLimite máximo de caracteres atingido (>10), tente novamente!");
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
					System.out.print("\nDigite seu setor:");
					
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
					double maxValue = 9999999999.99;
					System.out.print("\nValor inicial de investimento: R$");
					initialValue = sc.nextDouble();

					if ( initialValue > maxValue ) {
						System.out.println("\nLimite máximo de dígitos atingido (>12), tente novamente!");
					} else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPor favor, digite um valor válido. [Ex: 100.00]!");
					sc.next();
				}
			}
			
			while (true) {
				try {
					System.out.print("\nData de quando o investimento foi realizado [dd/mm/yyyy]:");
					initialDate = sc.next();
					sdf.parse(initialDate);
					break;
				} catch (ParseException e) {
					System.out.println("\nFormato de data digitado de maneira incorreta, tente novamente!");
				}
			}
	
				
			
			try {
				
				tempStock = new Stock(stockName, stockSector, initialValue, sdf.parse(initialDate), initialValue, sdf.parse(initialDate));
				manager.insertStock(tempStock, true);
				break;
			} catch (ParseException e) { 
				System.out.println("\nFormato de data digitado de maneira incorreta, tente novamente!");
			} catch (InputMismatchException e) {
				System.out.println("\nAlgum valor não foi digitado no formato desejado, tente novamente!");
			} catch (DbException e) {
				System.out.println("\nAlgum valor não foi digitado no formato desejado, tente novamente!");
			}
		}
		
		
	}
	
	
	
	public static void showAllStocks() {
		System.out.println();
		manager.showAllStocks();
		
	}
	
	
	
	public static void addNewRecord() {
		int idDecision = 0;
		double actualValue = 0;
		String dateDecision = null;
		Date today = new Date();
		System.out.println();
		manager.showAllStocks();
		
		while (true) {
			try {
				System.out.print("\nSelecione um id de ação para adicionar um novo registro:");
				idDecision = sc.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
			}
		}
		
		while (true) {
			try {
				System.out.print("\nDigite o valor atual do investimento:");
				actualValue = sc.nextDouble();
				break;
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido! [Ex: 100.00]");
				sc.next();
			}
		}
		
		while (true) {
			try {
				System.out.print("\nPreço registrado hoje [Y/N]?");
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
			System.out.println("\nNovo registro salvo no banco de dados!");
		}
		else if (dateDecision.equalsIgnoreCase("N")){
			String recordDate;
			while (true) {
				try {
					System.out.print("\nDigite a data de registro [dd/MM/yyyy]:");
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
			
			System.out.println("\nNovo registro salvo no banco de dados!");
		}
	}
	

	
	public static void changeStockData() {
		int columnOption;
		
		manager.showAllStocks();
		
		System.out.print("\nSelecione o id da ação que você deseja alterar algum tipo de dado:");
		int idDecision = sc.nextInt();
		
		System.out.println("Qual coluna você deseja alterar?");
		
		while (true) {
			System.out.println("[1] Nome da ação\n[2] Setor\n[3] Valor inicial\n[4] Data inicial\n");
			System.out.print("Selecione uma opção: ");
			try {
				columnOption = sc.nextInt();
				if (columnOption > 4 || columnOption < 1) {
					System.out.println("\nPor favor, digite uma opção válida!");
				}
				else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
				sc.next();
			}
		}
		
		switch(columnOption) {
		case 1:
			String stockName;
			while (true) {
				System.out.print("Digite o novo código da ação: ");
				try {
					stockName = sc.next();
					if (stockName.length() > 10) {
						System.out.println("\nLimite máximo de caracteres atingido (>10), tente novamente!");
					}
					else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("\nPor favor, digite um valor válido!");
					sc.next();
				}
			}
			
			manager.changeStockData(idDecision, ColumnName.stock, stockName, DataType.stringandvalue);
			break;
		case 2:
			String stockSector;
			while (true) {
				try {
					System.out.print("Digite o novo nome do setor: ");
					
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
			
			manager.changeStockData(idDecision, ColumnName.sector, stockSector, DataType.stringandvalue);
			break;
		case 3:
			double initialValue;
			while (true) {
				try {
					System.out.print("Digite o novo valor inicial de investimento: R$");
					initialValue = sc.nextDouble();
					
					String[] separate = Double.toString(initialValue).split(",");
					
					
					if ( Double.toString(initialValue).length() > 12 || separate[0].length() > 10) {
						System.out.println("\nLimite máximo de dígitos atingido (>12), tente novamente!");
					} else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPor favor, digite um valor válido. [Ex: 100.00]!");
					sc.next();
				}
			}
			manager.changeStockData(idDecision, ColumnName.start_value, initialValue+"", DataType.stringandvalue);
			break;
		case 4:
			String newDate;
			while (true) {
				try {
					System.out.print("Digite uma nova data [dd/mm/yyyy]: ");
					newDate = sc.next();
					sdf.parse(newDate);
					break;
				} catch (ParseException e) {
					System.out.println("\nFormato de data digitado de maneira incorreta, tente novamente!");
				}
			}
			manager.changeStockData(idDecision, ColumnName.start_date, newDate, DataType.date);
			break;
		}
	} 
	
	
	
	public static void totalProfit() { 
		double profitList[] = manager.getGeneralGain();
		
		System.out.println("\nValor inicial investido = R$" + df.format(profitList[0]));
		System.out.println("Valor final do investimento = R$" + df.format(profitList[1]));
		System.out.println("Lucro em R$ = R$" + df.format(profitList[2]));
		System.out.printf("Lucro em porcentagem: %.2f%% \n", profitList[3]);
	}
	
	
	
	public static void deleteSpecificStock() {
		
		System.out.println();
		manager.showAllStocks();
		while (true) {
			try {
				System.out.print("\nSelecione um id de ação para deletar:");
	
				int idDecision = sc.nextInt();
				manager.deleteStock(idDecision);
				break;
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor válido!");
				sc.next();
			}
		}
		
		System.out.println("\nAção deletada com sucesso!");		
	}
	
	
	
	private static void specificProfit() {
		System.out.println();
		manager.showAllStocks();
		int specificId;
		while (true) {
			try {
				System.out.print("\nDigite o id da ação que deseja ver o lucro específico:");
				specificId = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Por favor, digite um valor válido!");
				sc.next();
			}
		}

		double specificProfit[] = manager.getSpecificGain(specificId);
		
		System.out.println("\nValor inicial investido = " + df.format(specificProfit[0]));
		System.out.println("Valor final do investimento = " + df.format(specificProfit[1]));
		System.out.println("Lucro em R$ = " + df.format(specificProfit[2]));
		System.out.printf("Lucro em porcentagem = %.2f%% \n", specificProfit[3]);
	}
	
	
	
}
//TODOF erro em novo registro na parte de colocar dinheiro, bug de inserir letras VVV
//TODOF try/catch VVV
//TODO Traduzir
//TODOF limpar notas (falta em outras classes)
//TODOF Fechar resultset, statement, connection VVV
//TODOF formatar dinheiro 
//TODOF descobrir como bota porcentagem no lucro VVV
//TODO ajeitar codigo (metade feito, falta dentro dos metodos)
//TODO opção de salvar backup do atual estado da ação? (so o nome, acumulo, data)
//TODOF ajustar limite de dinheiro VVV