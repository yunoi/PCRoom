package model;

public class OrderVO {
	private int orderNo;
	private String orderDate;
	private int seatNo;
	private String orderId;
	private String orderUser;
	private String orderList;
	private int orderAmount;
	private int orderPrice;
	private String orderStatus;
	// orderTableÀÇ º¯¼ö
		private int orderSum;

		public int getOrderSum() {
			return orderSum;
		}

		public void setOrderSum(int orderSum) {
			this.orderSum = orderSum;
		}
	public OrderVO() {
	}

	public OrderVO(int orderNo, String orderDate, int seatNo, String orderId, String orderUser, String orderList,
			int orderAmount, int orderPrice, String orderStatus) {
		super();
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.seatNo = seatNo;
		this.orderId = orderId;
		this.orderUser = orderUser;
		this.orderList = orderList;
		this.orderAmount = orderAmount;
		this.orderPrice = orderPrice;
		this.orderStatus = orderStatus;
	}

	public OrderVO(String orderStatus) {
		super();
		this.orderStatus = orderStatus;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
