package entities;

import java.util.Date;

public class Stock {
	
	
	
	private String stockName;
	private String stockSector;
	private double startValue;
	private Date startDate;
	private double accumulatedValue;
	private Date recordDate;
	
	
	public Stock() {
		
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
	public void setStartValue(float startValue) {
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
	public void setAccumulatedValue(float accumulatedValue) {
		this.accumulatedValue = accumulatedValue;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	
	
	@Override
	public String toString() {
		return "Stock [stockName=" + stockName + ", startValue=" + startValue + ", startDate=" + startDate
				+ ", accumulatedValue=" + accumulatedValue + ", recordDate=" + recordDate + "]";
	}

	public String getStockSector() {
		return stockSector;
	}

	public void setStockSector(String stockSector) {
		this.stockSector = stockSector;
	}
	
	
	

	
	
	
}
