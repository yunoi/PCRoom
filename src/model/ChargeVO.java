package model;

public class ChargeVO {
	private int enterNo; 
	private String userStartTime;
	private int prepaidMoney;
	private String userId;
	private String userStartDate; 
	private String seatNo;
	private int userAvailableTime;
	
	public ChargeVO() {
	}

	// 처음입장 최초충전용
	public ChargeVO(int prepaidMoney, String userId, int userAvailableTime) {
		super();
		this.prepaidMoney = prepaidMoney;
		this.userId = userId;
		this.userAvailableTime = userAvailableTime;
	}

	// 추가충전용
	public ChargeVO(int prepaidMoney, int userAvailableTime) {
		super();
		this.prepaidMoney = prepaidMoney;
		this.userAvailableTime = userAvailableTime;
	}
	
	public ChargeVO(int enterNo, String userStartTime, int prepaidMoney, String userId, String userStartDate, String seatNo, int userAvailableTime) {
		super();
		this.enterNo = enterNo;
		this.userStartTime = userStartTime;
		this.prepaidMoney = prepaidMoney;
		this.userId = userId;
		this.userStartDate = userStartDate;
		this.seatNo = seatNo;
		this.userAvailableTime = userAvailableTime;
	}

	// enterNo 없음
	public ChargeVO(String userStartTime,
			int prepaidMoney, String userId, String userStartDate, String seatNo, int userAvailableTime) {
		super();
		this.userStartTime = userStartTime;
		this.prepaidMoney = prepaidMoney;
		this.userId = userId;
		this.userStartDate = userStartDate;
		this.seatNo = seatNo;
		this.userAvailableTime = userAvailableTime;
	}
	
	// 시작시간, 사용가능시간, 지불 요금, 아이디, 시작 날짜 
	public ChargeVO(String userStartTime, int prepaidMoney, String userId,
			String userStartDate, int userAvailableTime) {
		super();
		this.userStartTime = userStartTime;
		this.prepaidMoney = prepaidMoney;
		this.userId = userId;
		this.userStartDate = userStartDate;
		this.userAvailableTime = userAvailableTime;
	}
	
	public int getEnterNo() {
		return enterNo;
	}

	public void setEnterNo(int enterNo) {
		this.enterNo = enterNo;
	}

	public String getUserStartTime() {
		return userStartTime;
	}

	public void setUserStartTime(String userStartTime) {
		this.userStartTime = userStartTime;
	}

	public int getUserAvailableTime() {
		return userAvailableTime;
	}

	public void setUserAvailableTime(int userAvailableTime) {
		this.userAvailableTime = userAvailableTime;
	}

	public int getPrepaidMoney() {
		return prepaidMoney;
	}

	public void setPrepaidMoney(int prepaidMoney) {
		this.prepaidMoney = prepaidMoney;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserStartDate() {
		return userStartDate;
	}

	public void setUserStartDate(String userStartDate) {
		this.userStartDate = userStartDate;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	} 
 
}
