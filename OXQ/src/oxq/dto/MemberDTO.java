package oxq.dto;

public class MemberDTO {
	private String id; // 회원 아이디
	private String pwd; // 회원 비밀번호
	private String nickName; // 컨펌아이디
	private String email; // 회원 이메일
	private String tel;
	private int login; // 로그인 여부(로그인 하면 1 아니면 0)
	private int o_cnt; // 맞춘 개수
	private int x_cnt; // 틀린 개수
	private int win_cnt; // 1등 횟수

	// getters
	public String getId() {
		return id;
	}

	public String getPwd() {
		return pwd;
	}

	public String getTel() {
		return tel;
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public int getLogin() {
		return login;
	}

	public int getO_cnt() {
		return o_cnt;
	}

	public int getX_cnt() {
		return x_cnt;
	}

	public int getWin_cnt() {
		return win_cnt;
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

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public void setLogin(int login) {
		this.login = login;
	}

	public void setO_cnt(int o_cnt) {
		this.o_cnt = o_cnt;
	}

	public void setX_cnt(int x_cnt) {
		this.x_cnt = x_cnt;
	}

	public void setWin_cnt(int win_cnt) {
		this.win_cnt = win_cnt;
	}

	@Override
	public String toString() {
		return nickName;
	}
}
