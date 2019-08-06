package oxq.dto;

public class MemberDTO {
	private String id;	// 회원 아이디
	private String pwd;	// 회원 비밀번호
	private String nickName;	// 회원 닉네임
	private String email;	// 회원 이메일
	private int on_off;		// 로그인 여부(로그인 하면 1 아니면 0)
	private int score;		// 게임에서 점수
	private int getScore;	// total
	
	// getters
	public String getId() {
		return id;
	}
	public String getPwd() {
		return pwd;
	}
	public String getNickName() {
		return nickName;
	}
	public String getEmail() {
		return email;
	}
	public int getOn_off() {
		return on_off;
	}
	public int getScore() {
		return score;
	}
	public int getGetScore() {
		return getScore;
	}
	
	// setters
	public void setId(String id) {
		this.id = id;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setOn_off(int on_off) {
		this.on_off = on_off;
	}	
	public void setScore(int score) {
		this.score = score;
	}
	public void setGetScore(int getScore) {
		this.getScore = getScore;
	}
	
	@Override
	public String toString() {
		return nickName;
	}
}
