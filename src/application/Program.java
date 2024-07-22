package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entities.Stock;
import entities.StockManager;
import entities.enums.ColumnName;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		StockManager manager = new StockManager();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date today = new Date();
		
		
		//Stock stock = new Stock("TESTE4F", "tomada1", 100.00, sdf.parse("10/10/1000"), 120.00, sdf.parse("10/10/2000"));
		// quem vai gerenciar a data, se é hoje ou não, vai ser o eclipse e não o MySQL (Devido a minha afinidade maior
		// parse transforma string em data no formato. Format formata uma data e transforma em string de acordo com o padrão estabelecido no sdf
		
		
		System.out.println("--- Bem vindo ao Stock Management ---");
		
		while (true) {
			System.out.println("O que você deseja fazer? \n1)Adicionar nova ação \n2)Ver lista completa de ações \n3)Adicionar novo registro \n4)Deletar ação específica \n5)Alterar algum dado da ação \n6) Gerar lucro total \n7) Sair do programa ");
			int decision = sc.nextInt();
			
			switch (decision) {
			case 1:
				// o valor inicial de investimento deve mudar de nome para valor investido. E o outro para valor acumulado
				
				System.out.println("Digite o seu código:");
				String stockName = sc.next();
				
				System.out.println("Digite seu setor:");
				String stockSector = sc.next();
				
				System.out.println("Valor inicial de investimento:");
				Double initialValue = sc.nextDouble();
				
				System.out.println("Data de quando o investimento foi realizado [dd/mm/yyyy]: ");
				String initialDate = sc.next();
				
				// se a data for diferente da de hoje, não perguntar as proximas perguntas
				
				System.out.println("Valor acumulado do investimento");
				Double actualValue = sc.nextDouble();
				
				
				System.out.println("Data de registro de seu último valor acumulado [dd/mm/yyyy]:");
				String actualDate = sc.next();
				
				Stock tempStock = new Stock(stockName, stockSector, initialValue, sdf.parse(initialDate), actualValue, sdf.parse(actualDate));
				
				manager.insertStock(tempStock);
				System.out.println("new stock saved");
				// tenho que pegar essas declarações de variaveis e declarar no inicio do programa
				break;
			case 2:
				manager.showAllStocks();
				
				break;
			case 3:
				manager.showAllStocks();
				
				System.out.println("\nSelecione um id de ação para adicionar um novo registro:");
				int recordId = sc.nextInt();
				
				System.out.println("Digite seu valor atual:");
				actualValue = sc.nextDouble();
				
				System.out.println("Preço registrado hoje [Y/N]?");
				
				String dateDecision = sc.next();
				
				if (dateDecision.equalsIgnoreCase("Y")) {
					manager.addNewStockRecord(recordId, actualValue, today);
					System.out.println("Salvo");
				}
				else {
					System.out.println("Digite a data de registro [dd/MM/yyyy]: ");
					String recordDate = sc.next();
					manager.addNewStockRecord(recordId, actualValue, sdf.parse(recordDate));
					System.out.println("Salvo");
				}

				break;
			case 4:
				// deletar ação especifica
				manager.showAllStocks();
				
				System.out.println("\nSelecione um id de ação para deletar:");
				int deletionId = sc.nextInt();
				
				manager.deleteStock(deletionId);
				System.out.println("Ação deletada com sucesso!");
				
				
				break;
			case 5:
				manager.showAllStocks();
				
				System.out.println("\n Selecione o id da ação que você deseja alterar algum tipo de dado:");
				int changeId = sc.nextInt();
				
				System.out.println("Qual coluna você deseja alterar?");
				
				System.out.println("[1] Nome da ação\n[2] Setor\n[3] Valor inicial\n[4]Data inicial\n[5]Valor acumulado\n[6]Data de regitro");
				System.out.print("Selecione uma opção: ");
				int columnOption = sc.nextInt();
				
				break;
			case 6:
				System.out.println("Ta");
				double profitList[] = manager.getGeneralGain();
				
				System.out.println("Valor inicial investido = " + profitList[0]);
				System.out.println("Valor final do investimento = " + profitList[1]);
				System.out.println("Lucro em R$ = " + profitList[2]);
				System.out.printf("Lucro em porcentagem: %.2f \n\n", profitList[3]);
				
				break;
				
			case 7:

				
				
				
				
				
				break;
			}
			
			if (decision == 7) {
				break;
			}
			
			

		}
		
		
		
		
		
		
		
		//manager.addNewStockRecord(id, av, today);
		
		
		
		
		// o programa vai pedir ao usuario para registrar a ação dele.
		// essa ação vai ser adicionada na classe stock
		// depois disso a stock vai direto para o stock manager 
		// esse stockmanager é o responsavel por transformar a classe stock em dado para o mysql
		// tambem vice e versa. Ele é responsavel por trazer os dados do MySQL, transformá-los em classe stock e botá-los em uma lista
		

	}

}
