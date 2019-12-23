package model;

public class EquipVO {
	private String equipNo;
	private String equipCategory;
	private String equipName;
	private String asInfo;
	private String equipStatus;
	private String seatNo;
	
	public EquipVO() {
	}

	public EquipVO(String equipNo, String equipCategory, String equipName, String asInfo, String equipStatus,
			String seatNo) {
		super();
		this.equipNo = equipNo;
		this.equipCategory = equipCategory;
		this.equipName = equipName;
		this.asInfo = asInfo;
		this.equipStatus = equipStatus;
		this.seatNo = seatNo;
	}

	public String getEquipNo() {
		return equipNo;
	}

	public void setEquipNo(String equipNo) {
		this.equipNo = equipNo;
	}

	public String getEquipCategory() {
		return equipCategory;
	}

	public void setEquipCategory(String equipCategory) {
		this.equipCategory = equipCategory;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getAsInfo() {
		return asInfo;
	}

	public void setAsInfo(String asInfo) {
		this.asInfo = asInfo;
	}

	public String getEquipStatus() {
		return equipStatus;
	}

	public void setEquipStatus(String equipStatus) {
		this.equipStatus = equipStatus;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
}
