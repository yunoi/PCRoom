package model;

public class UserVO {
	private String userId;
	private String userName;
	private String userGender;
	private String userBirth;
	private String userAdult;
	private String userPhone;
	private String userHomePhone;
	private String userEmail;
	private String userSignday;
	private String userImage;
	private String userStatus;
	private String userPw;
	
	// 디폴트
	public UserVO() {
		super();
	}

	// pw, phone, homephone, email, image
	public UserVO(String userPhone, String userHomePhone, String userEmail, String userImage, String userPw) {
		super();
		this.userPhone = userPhone;
		this.userHomePhone = userHomePhone;
		this.userEmail = userEmail;
		this.userImage = userImage;
		this.userPw = userPw;
	}

// 이름 아이디 성별 폰 집전화 생일 메일 성인여부 가입일 이용상태 이미지파일
	public UserVO(String userId, String userName, String userGender, String userBirth, String userAdult,
			String userPhone, String userHomePhone, String userEmail, String userSignday, String userImage,
			String userStatus) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userGender = userGender;
		this.userBirth = userBirth;
		this.userAdult = userAdult;
		this.userPhone = userPhone;
		this.userHomePhone = userHomePhone;
		this.userEmail = userEmail;
		this.userSignday = userSignday;
		this.userImage = userImage;
		this.userStatus = userStatus;
	}

	// 이름 아이디 패스워드 성별 폰 집전화 생일 이메일 성인여부 가입일, 상태, 이미지(전체)
	public UserVO(String userId, String userName, String userGender, String userBirth, String userAdult,
			String userPhone, String userHomePhone, String userEmail, String userSignday, String userImage,
			String userStatus, String userPw) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userGender = userGender;
		this.userBirth = userBirth;
		this.userAdult = userAdult;
		this.userPhone = userPhone;
		this.userHomePhone = userHomePhone;
		this.userEmail = userEmail;
		this.userSignday = userSignday;
		this.userImage = userImage;
		this.userStatus = userStatus;
		this.userPw = userPw;
	}

	// 아이디 패스워드 
	public UserVO(String userId, String userPw) {
		super();
		this.userId = userId;
		this.userPw = userPw;
	}
	
	//패스워드 폰
//	public UserVO(String userPhone, String userPw) {
//		super();
//		this.userPhone = userPhone;
//		this.userPw = userPw;
//	}

	// 패스워드 폰 집전화
	public UserVO(String userPhone, String userHomePhone, String userPw) {
		super();
		this.userPhone = userPhone;
		this.userHomePhone = userHomePhone;
		this.userPw = userPw;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserAdult() {
		return userAdult;
	}

	public void setUserAdult(String userAdult) {
		this.userAdult = userAdult;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserHomePhone() {
		return userHomePhone;
	}

	public void setUserHomePhone(String userHomePhone) {
		this.userHomePhone = userHomePhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserSignday() {
		return userSignday;
	}

	public void setUserSignday(String userSignday) {
		this.userSignday = userSignday;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userName=" + userName + ", userGender=" + userGender + ", userBirth="
				+ userBirth + ", userAdult=" + userAdult + ", userPhone=" + userPhone + ", userHomePhone="
				+ userHomePhone + ", userEmail=" + userEmail + ", userSignday=" + userSignday + ", userImage="
				+ userImage + ", userStatus=" + userStatus + ", userPw=" + userPw + "]";
	}

	

}