package model;

public class ItemVO {
	private String itemCode; 
	private String itemCategory;
	private String itemName;
	private int itemPrice;
	private int itemStockGarage;
	private int itemStockDisplay;
	private int itemTotalStock;
	private String itemImg;
	
	public ItemVO() {
	}

	public ItemVO(String itemCode, String itemCategory, String itemName, int itemPrice, int itemStockGarage,
			int itemStockDisplay, int itemTotalStock) {
		super();
		this.itemCode = itemCode;
		this.itemCategory = itemCategory;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemStockGarage = itemStockGarage;
		this.itemStockDisplay = itemStockDisplay;
		this.itemTotalStock = itemTotalStock;
	}

	public ItemVO(String itemCode, String itemCategory, String itemName, int itemPrice, int itemStockGarage,
			int itemStockDisplay, int itemTotalStock, String itemImg) {
		super();
		this.itemCode = itemCode;
		this.itemCategory = itemCategory;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemStockGarage = itemStockGarage;
		this.itemStockDisplay = itemStockDisplay;
		this.itemTotalStock = itemTotalStock;
		this.itemImg = itemImg;
	}

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getItemStockGarage() {
		return itemStockGarage;
	}

	public void setItemStockGarage(int itemStockGarage) {
		this.itemStockGarage = itemStockGarage;
	}

	public int getItemStockDisplay() {
		return itemStockDisplay;
	}

	public void setItemStockDisplay(int itemStockDisplay) {
		this.itemStockDisplay = itemStockDisplay;
	}

	public int getItemTotalStock() {
		return itemTotalStock;
	}

	public void setItemTotalStock(int itemTotalStock) {
		this.itemTotalStock = itemTotalStock;
	}
	
	
}
