package oxq.dto;

public class RoomDTO {
	private int room_no;
	private String room_name;
	private String room_pwd;
	private String room_leader;
	private int room_limit;
	
	// getters
	public int getRoom_no() {
		return room_no;
	}
	public String getRoom_name() {
		return room_name;
	}
	public String getRoom_pwd() {
		return room_pwd;
	}
	public String getRoom_leader() {
		return room_leader;
	}
	public int getRoom_limit() {
		return room_limit;
	}
	
	// setters
	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public void setRoom_pwd(String room_pwd) {
		this.room_pwd = room_pwd;
	}
	public void setRoom_leader(String room_leader) {
		this.room_leader = room_leader;
	}
	public void setRoom_limit(int room_limit) {
		this.room_limit = room_limit;
	}
}
