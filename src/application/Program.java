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
		
	
		System.out.println("--- Welcome to Stock Management ---");
		
		while (true) {
			
			System.out.println("\nWhat do you want to do? \n\n1) Add a new stock \n2) Show full stock list \n3) Add a new record \n4) Delete specific stock \n5) Change some stock data \n6) Generate total profit \n7) Generate specific profit \n8) Left the program ");
			System.out.print("\nType here:");
			
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
					String csvDecision = null;
					
					while (true) {
						
						try {
							
							System.out.print("\nSave Stock, accumulated value and record date on CSV archive [Y/N]?");
							csvDecision = sc.next();
							
							if (!csvDecision.equalsIgnoreCase("y") && !csvDecision.equalsIgnoreCase("n")) {
								System.out.println("Please, type a valid value!");
							} else {
								
								if (csvDecision.equalsIgnoreCase("y")) {
									saveAndQuit(true);
								} else {
									
								}
								
								break;
							
							}		
						} catch (InputMismatchException e) {
							System.out.println("Please, type a valid value!");
						}
					}
					
					System.out.println("\nSaving and quiting program...");
					System.out.println("Database saved and program shutted down!");
					
					break;
				}
				
				if (decision < 1 || decision > 8) {					
					System.out.println("\nPlease, type a valid option!");
					
				}
	
			} catch (InputMismatchException e) {
				System.out.println("\nPlease, type a valid value!");
				sc.next();
				
			}
		}
	}
	
	
	
	private static void saveAndQuit(boolean csvSave) {
		manager.resetStockId();
		sc.close();
		manager.closeSystem(csvSave);
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
					System.out.print("\nType the stock code:");
					stockName = sc.next();
					
					if (stockName.length() > 10) {
						System.out.println("\nMax character limit reached (>10), please try again!");
					}
					else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("\nPlease, type a valid value!");
				}
			}
			
			while (true) {
				
				try {
					System.out.print("\nType the stock sector:");
					
					stockSector = sc.next();
	
					if (stockSector.length() > 20) {
						System.out.println("\nMax character limit reached (>20), please try again!");
					}
					else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPlease, type a valid value!");
				}
			}
			
			while (true) {
				
				try {
					double maxValue = 9999999999.99;
					
					System.out.print("\nInitial investment value: $");
					initialValue = sc.nextDouble();

					if ( initialValue > maxValue ) {
						System.out.println("\nMax digit limit reached (>12), please try again!");
					} else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPlease, type a valid value. [Ex: 100.00]!");
					sc.next();
				}
			}
			
			while (true) {
				
				try {
					System.out.print("\nInitial date of investment [dd/mm/yyyy]:");
					initialDate = sc.next();
					
					sdf.parse(initialDate);
					
					break;
				} catch (ParseException e) {
					System.out.println("\nDate format typed incorrectly, please try again!");
				}
			}
	
				
			
			try {
				tempStock = new Stock(stockName, stockSector, initialValue, sdf.parse(initialDate), initialValue, sdf.parse(initialDate));
				manager.insertStock(tempStock, true);
				
				break;
			} catch (ParseException e) { 
				System.out.println("\nDate format typed incorrectly, please try again!");
			} catch (InputMismatchException e) {
				System.out.println("\nSome values weren't typed correctly, please try again!");
			} catch (DbException e) {
				System.out.println("\nSome values weren't typed correctly, please try again!");
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
				System.out.print("\nSelect a stock id to add a new record:");
				idDecision = sc.nextInt();
				
				break;
			} catch(InputMismatchException e) {
				System.out.println("\nPlease, type a valid value!");
			}
		}
		
		while (true) {
			
			try {
				System.out.print("\nType the actual investment value:");
				actualValue = sc.nextDouble();
				
				break;
			} catch (InputMismatchException e) {
				System.out.println("\nPlease, type a valid value! [Ex: 100.00]");
				sc.next();
			}
		}
		
		while (true) {
			
			try {
				System.out.print("\nPrice recorded today [Y/N]?");
				dateDecision = sc.next();
				
				if (!dateDecision.equalsIgnoreCase("Y") && !dateDecision.equalsIgnoreCase("N")) {
					System.out.println("\nPlease, type a valid value!");
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nPlease, type a valid value!");
			}

		}
		
		if (dateDecision.equalsIgnoreCase("Y")) {
			manager.addNewStockRecord(idDecision, actualValue, today);
			System.out.println("\nNew record saved in the database!");
		}
		else if (dateDecision.equalsIgnoreCase("N")){
			String recordDate;
			
			while (true) {
				try {
					System.out.print("\nType the record date [dd/MM/yyyy]:");
					recordDate = sc.next();
					
					sdf.parse(recordDate);
					
					break;
				} catch (ParseException e) {
					System.out.println("Date format typed incorrectly, please try again!");
				}
			}
				
			try {
				manager.addNewStockRecord(idDecision, actualValue, sdf.parse(recordDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("\nNew record saved in the database!");
		}
	}
	

	
	public static void changeStockData() {
		int columnOption;
		
		manager.showAllStocks();
		
		System.out.print("\nSelect the Stock Id to change some data:");
		int idDecision = sc.nextInt();
		
		System.out.println("which column do you want to change?");
		
		while (true) {
			System.out.println("[1] Stock name\n[2] Stock sector\n[3] Initial value\n[4] Initial date\n");
			System.out.print("Select an option:");
			try {
				columnOption = sc.nextInt();
				
				if (columnOption > 4 || columnOption < 1) {
					System.out.println("\nPlease, type a valid option!");
				}
				else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nPlease, type a valid value!");
				sc.next();
			}
		}
		
		switch(columnOption) {
		case 1:
			String stockName;
			
			while (true) {
				System.out.print("Type the new stock code:");
				try {
					stockName = sc.next();
					if (stockName.length() > 10) {
						System.out.println("\nMax character limit reached (>10), please try again!");
					}
					else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("\nPlease, type a valid value!");
					sc.next();
				}
			}
			
			manager.changeStockData(idDecision, ColumnName.stock, stockName, DataType.stringandvalue);
			break;
		
		case 2:
			String stockSector;
			
			while (true) {
				try {
					System.out.print("Type the new sector name:");
					
					stockSector = sc.next();
	
					if (stockSector.length() > 20) {
						System.out.println("\nMax character limit reached (>20), please try again!");
					}
					else {
						break;
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\nPlease, type a valid value!");
				}
			}
			
			manager.changeStockData(idDecision, ColumnName.sector, stockSector, DataType.stringandvalue);
			break;
		
		case 3:
			double initialValue;
			
			while (true) {
			
				try {
					System.out.print("Type the new initial investment value: $");
					initialValue = sc.nextDouble();
					
					String[] separate = Double.toString(initialValue).split(",");
					
					if ( Double.toString(initialValue).length() > 12 || separate[0].length() > 10) {
						System.out.println("\nMax digit limit reached (>12), please try again!");
					} else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("\nPlease, type a valid value. [Ex: 100.00]!");
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
					System.out.println("\nDate format typed incorrectly, please try again!");
				}
			}
			manager.changeStockData(idDecision, ColumnName.start_date, newDate, DataType.date);
			break;
			
		}
	} 
	
	
	
	public static void totalProfit() { 
		double profitList[] = manager.getGeneralGain();
		
		System.out.println("\nInitial invested value = $" + df.format(profitList[0]));
		System.out.println("Accumulated invested value = $" + df.format(profitList[1]));
		System.out.println("Profit = $" + df.format(profitList[2]));
		System.out.printf("Profit in percentage = %.2f%% \n", profitList[3]);
	}
	
	
	
	public static void deleteSpecificStock() {
		System.out.println();
		manager.showAllStocks();
		
		while (true) {
			try {
				System.out.print("\nSelect a Stock Id to delete it:");
				int idDecision = sc.nextInt();
				manager.deleteStock(idDecision);
				
				break;
			} catch (InputMismatchException e) {
				System.out.println("\nPlease, type a valid value!");
				sc.next();
			}
		}
		
		System.out.println("\nStock successfully deleted!");		
	}
	
	
	
	private static void specificProfit() {
		int specificId;
		
		System.out.println();
		manager.showAllStocks();
		
		while (true) {
			try {
				System.out.print("\nType the Stock Id to see your specific profit:");
				specificId = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please, type a valid value!");
				sc.next();
			}
		}

		double specificProfit[] = manager.getSpecificGain(specificId);
		
		System.out.println("\nInitial invested value = $" + df.format(specificProfit[0]));
		System.out.println("Accumulated invested value = $" + df.format(specificProfit[1]));
		System.out.println("Profit = $" + df.format(specificProfit[2]));
		System.out.printf("Profit in percentage = %.2f%% \n", specificProfit[3]);
	}
}
