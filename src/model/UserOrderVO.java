package model;

public class UserOrderVO {
	// orderDB의 7개 변수
	private int orderNo;
	private int enterNo;
	private String itemCode;
	private int itemOrderAmount;
	private int orderPrice;
	private String orderDate;
	private String orderStatus;
	// itemTable의 변수
	private String orderList;
	// orderTable의 변수
	private int orderSum;
	private String seatNo;
	
	
	
	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public int getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}

	// 주문번호, 주문개수, 주문가격, 상품이름
	public UserOrderVO(int orderNo, String orderList, int itemOrderAmount, int orderPrice) {
		super();
		this.orderNo = orderNo;
		this.orderList = orderList;
		this.itemOrderAmount = itemOrderAmount;
		this.orderPrice = orderPrice;
	}

	// 디폴트생성자
	public UserOrderVO() {
	}

	public UserOrderVO(int orderNo) {
		super();
		this.orderNo = orderNo;
	}

	public UserOrderVO(int orderNo, int enterNo, String itemCode, int itemOrderAmount, int orderPrice, String orderDate,
			String orderStatus, String orderList) {
		super();
		this.orderNo = orderNo;
		this.enterNo = enterNo;
		this.itemCode = itemCode;
		this.itemOrderAmount = itemOrderAmount;
		this.orderPrice = orderPrice;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderList = orderList;
	}

	// ordate 없는걸로 만듬
	public UserOrderVO(int orderNo, int enterNo, String itemCode, int itemOrderAmount, int orderPrice, String orderStatus) {
		super();
		this.orderNo = orderNo;
		this.enterNo = enterNo;
		this.itemCode = itemCode;
		this.itemOrderAmount = itemOrderAmount;
		this.orderPrice = orderPrice;
		this.orderStatus = orderStatus;
	}

	// 전체
	public UserOrderVO(int orderNo, int enterNo, String itemCode, int itemOrderAmount, int orderPrice, String orderDate,
			String orderStatus) {
		super();
		this.orderNo = orderNo;
		this.enterNo = enterNo;
		this.itemCode = itemCode;
		this.itemOrderAmount = itemOrderAmount;
		this.orderPrice = orderPrice;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
	}

	// getters/setters

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getEnterNo() {
		return enterNo;
	}

	public void setEnterNo(int enterNo) {
		this.enterNo = enterNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getItemOrderAmount() {
		return itemOrderAmount;
	}

	public void setItemOrderAmount(int itemOrderAmount) {
		this.itemOrderAmount = itemOrderAmount;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}