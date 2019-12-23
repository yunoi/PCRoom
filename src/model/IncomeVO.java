package model;

public class IncomeVO {
	private String incomeYear;
	private String incomeMonth;
	private String incomeDay;
	private int dayPCIncome;
	private int dayItemIncome;
	private int dayTotalIncome;

	public IncomeVO() {
	}
	
	public IncomeVO(String incomeYear, String incomeMonth, String incomeDay, int dayPCIncome, int dayItemIncome,
			int dayTotalIncome) {
		super();
		this.incomeYear = incomeYear;
		this.incomeMonth = incomeMonth;
		this.incomeDay = incomeDay;
		this.dayPCIncome = dayPCIncome;
		this.dayItemIncome = dayItemIncome;
		this.dayTotalIncome = dayTotalIncome;
	}

	public String getIncomeYear() {
		return incomeYear;
	}
	public void setIncomeYear(String incomeYear) {
		this.incomeYear = incomeYear;
	}
	public String getIncomeMonth() {
		return incomeMonth;
	}
	public void setIncomeMonth(String incomeMonth) {
		this.incomeMonth = incomeMonth;
	}
	public String getIncomeDay() {
		return incomeDay;
	}
	public void setIncomeDay(String incomeDay) {
		this.incomeDay = incomeDay;
	}
	public int getDayPCIncome() {
		return dayPCIncome;
	}
	public void setDayPCIncome(int dayPCIncome) {
		this.dayPCIncome = dayPCIncome;
	}
	public int getDayItemIncome() {
		return dayItemIncome;
	}
	public void setDayItemIncome(int dayItemIncome) {
		this.dayItemIncome = dayItemIncome;
	}
	public int getDayTotalIncome() {
		return dayTotalIncome;
	}
	public void setDayTotalIncome(int dayTotalIncome) {
		this.dayTotalIncome = dayTotalIncome;
	}
	
	
}
