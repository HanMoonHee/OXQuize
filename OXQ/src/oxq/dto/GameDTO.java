package oxq.dto;

public class GameDTO {
	private String member_id;	// 회원 아이디
	private int wincount;		// 1등한 횟수
	private int o_count;		// 맞춘 개수
	private int x_count;		// 틀린 개수
	// getter
	public String getMember_id() {return member_id;}
	public int getWincount() {return wincount;}
	public int getO_count() {return o_count;}	
	public int getX_count() {return x_count;}
	// setter
	public void setMember_id(String member_id) {this.member_id = member_id;}
	public void setWincount(int wincount) {this.wincount = wincount;}
	public void setO_count(int o_count) {this.o_count = o_count;}
	public void setX_count(int x_count) {this.x_count = x_count;}
}
	