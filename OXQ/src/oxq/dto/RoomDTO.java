package oxq.dto;

public class RoomDTO {
	public static final int ROOMLIMIT = 2;	// 인원수 최대 2명 고정

	private int roomNumer; // 방번호
	private String roomName; // 방이름
	private String roomPwd;  // 방비밀번호
	private int playerCnt;	// 방에 들어간 사람수
	
	// getters
	public int getRoomNumer() {
		return roomNumer;
	}
	public String getRoomName() {
		return roomName;
	}
	public String getRoomPwd() {
		return roomPwd;
	}
	public int getPlayerCnt() {
		return playerCnt;
	}
	
	// setters
	public void setRoomNumer(int roomNumer) {
		this.roomNumer = roomNumer;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public void setRoomPwd(String roomPwd) {
		this.roomPwd = roomPwd;
	}
	public void setPlayerCnt(int playerCnt) {
		this.playerCnt = playerCnt;
	}
	
	@Override
	public String toString() {
		return roomName;
	}
}
