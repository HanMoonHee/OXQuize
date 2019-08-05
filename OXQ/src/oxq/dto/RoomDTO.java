package oxq.dto;

import java.io.Serializable;

public class RoomDTO implements Serializable {
	private int room_no;		// 게임방 번호
	private String room_name;	// 게임방 이름
	private String room_pwd;	// 게임방 비밀번호
	private String room_leader;	// 게임방 방장
	private int room_limit;		// 게임방 최대 인원수
	
	// getters
	public int getRoom_no() {return room_no;}
	public String getRoom_name() {return room_name;}
	public String getRoom_pwd() {return room_pwd;}
	public String getRoom_leader() {return room_leader;}
	public int getRoom_limit() {return room_limit;}

	// setters
	public void setRoom_no(int room_no) {this.room_no = room_no;}
	public void setRoom_name(String room_name) {this.room_name = room_name;}
	public void setRoom_pwd(String room_pwd) {this.room_pwd = room_pwd;}
	public void setRoom_leader(String room_leader) {this.room_leader = room_leader;}
	public void setRoom_limit(int room_limit) {this.room_limit = room_limit;}
}
