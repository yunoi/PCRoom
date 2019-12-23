package model;

public class AdminVO {
	private String adminId;
	private String adminName;
	private String adminGender;
	private String adminBirth;
	private String adminPhone;
	private String adminAddress;
	private String adminEmail;
	private String adminWorkingTime;
	private String adminLevel;
	private String adminCleanArea;
	private String adminPw;
	
	public AdminVO() {
		
	}

	public AdminVO(String adminId, String adminName, String adminGender, String adminBirth, String adminPhone,
			String adminAddress, String adminEmail, String adminWorkingTime, String adminLevel, String adminCleanArea,
			String adminPw) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminGender = adminGender;
		this.adminBirth = adminBirth;
		this.adminPhone = adminPhone;
		this.adminAddress = adminAddress;
		this.adminEmail = adminEmail;
		this.adminWorkingTime = adminWorkingTime;
		this.adminLevel = adminLevel;
		this.adminCleanArea = adminCleanArea;
		this.adminPw = adminPw;
	}

	public AdminVO(String adminId, String adminName, String adminGender, String adminBirth, String adminPhone,
			String adminAddress, String adminEmail, String adminWorkingTime, String adminLevel, String adminCleanArea) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminGender = adminGender;
		this.adminBirth = adminBirth;
		this.adminPhone = adminPhone;
		this.adminAddress = adminAddress;
		this.adminEmail = adminEmail;
		this.adminWorkingTime = adminWorkingTime;
		this.adminLevel = adminLevel;
		this.adminCleanArea = adminCleanArea;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminGender() {
		return adminGender;
	}

	public void setAdminGender(String adminGender) {
		this.adminGender = adminGender;
	}

	public String getAdminBirth() {
		return adminBirth;
	}

	public void setAdminBirth(String adminBirth) {
		this.adminBirth = adminBirth;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminAddress() {
		return adminAddress;
	}

	public void setAdminAddress(String adminAddress) {
		this.adminAddress = adminAddress;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminWorkingTime() {
		return adminWorkingTime;
	}

	public void setAdminWorkingTime(String adminWorkingTime) {
		this.adminWorkingTime = adminWorkingTime;
	}

	public String getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}

	public String getAdminCleanArea() {
		return adminCleanArea;
	}

	public void setAdminCleanArea(String adminCleanArea) {
		this.adminCleanArea = adminCleanArea;
	}

	public String getAdminPw() {
		return adminPw;
	}

	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}

	@Override
	public String toString() {
		return "AdminVO [adminId=" + adminId + ", adminName=" + adminName + ", adminGender=" + adminGender
				+ ", adminBirth=" + adminBirth + ", adminPhone=" + adminPhone + ", adminAddress=" + adminAddress
				+ ", adminEmail=" + adminEmail + ", adminWorkingTime=" + adminWorkingTime + ", adminLevel=" + adminLevel
				+ ", adminCleanArea=" + adminCleanArea + ", adminPw=" + adminPw + "]";
	}
	
}