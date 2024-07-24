package entities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock {
	
	
	private int stockId;
	private String stockName;
	private String stockSector;
	private double startValue;
	private Date startDate;
	private double accumulatedValue;
	private Date recordDate;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
	
	public Stock() {
		
	}
	
	
	
	public Stock(int stockId, String stockName, String stockSector, double startValue, Date startDate, double accumulatedValue, Date recordDate) {
		super(); 
		this.stockId = stockId;
		this.stockName = stockName;
		this.stockSector = stockSector;
		this.startValue = startValue;
		this.startDate = startDate;
		this.accumulatedValue = accumulatedValue;
		this.recordDate = recordDate;
	}
	
	

	public Stock(String stockName, String stockSector, double startValue, Date startDate, double accumulatedValue, Date recordDate) {
		super();
		this.stockName = stockName;
		this.stockSector = stockSector;
		this.startValue = startValue;
		this.startDate = startDate;
		this.accumulatedValue = accumulatedValue;
		this.recordDate = recordDate;
	}
	
	
	
	public String getStockName() {
		return stockName;
	}
	
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	
	
	public double getStartValue() {
		return startValue;
	}
	
	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	
	public double getAccumulatedValue() {
		return accumulatedValue;
	}
	
	public void setAccumulatedValue(double accumulatedValue) {
		this.accumulatedValue = accumulatedValue;
	}
	
	
	
	public Date getRecordDate() {
		return recordDate;
	}
	
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	
	
	public String getStockSector() {
		return stockSector;
	}

	public void setStockSector(String stockSector) {
		this.stockSector = stockSector;
	}
	
	

	public int getStockId() {
		return stockId;
	}
	
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	
	
	@Override
	public String toString() {
		return "[" + stockId + "] Stock: " + stockName + "; Sector: " + stockSector + "; Start value: R$" + df.format(startValue) + "; Start date: " + sdf.format(startDate)
				+ "; Accumulated value: R$" + df.format(accumulatedValue) + "; Record date: " + sdf.format(recordDate) + "";
	}	
}
