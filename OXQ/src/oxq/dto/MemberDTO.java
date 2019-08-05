package oxq.dto;

public class MemberDTO {
	private	String member_id;		// 회원 아이디
	private String member_pwd;		// 회원 패스워드
	private String member_name;		// 회원 이름
	private String member_nickName; // 회원 닉네임
	private String member_tel;		// 회원 전화번호
	private String member_email;	// 회원 이메일	
	private int member_login;		// 회원 로그인 여부 (로그인이면 1, 아니면 0)
	
	// getters
	public String getId() {return member_id;}
	public String getPwd() {return member_pwd;}
	public String getName() {return member_name;}
	public String getNickName() {return member_nickName;}
	public String getTel() {return member_tel;}
	public String getEmail() {return member_email;}
	public int getLogin() {return member_login;}
	
	// setters
	public void setMember_id(String member_id) {this.member_id = member_id;}
	public void setMember_pwd(String member_pwd) {this.member_pwd = member_pwd;}
	public void setMember_name(String member_name) {this.member_name = member_name;}
	public void setMember_nickName(String member_nickName) {this.member_nickName = member_nickName;}
	public void setMember_tel(String member_tel) {this.member_tel = member_tel;}
	public void setMember_email(String member_email) {this.member_email = member_email;}
	public void setMember_login(int member_login) {this.member_login = member_login;}
}
