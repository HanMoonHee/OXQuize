package oxq.dto;

public class RoomDTO {
	public static final int ROOMLIMIT = 2;	// 인원수 최대 2명 고정

	private int roomNumer; // 방번호
	private String roomName; // 방이름
	private String roomPwd;  // 방비밀번호
	private int playerCnt;	// 방에 들어간 사람수
	private int player1;	// 1번 플레이어 닉네임(방장)
	private int player2;	// 2번 플레이어 닉네임
	
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
	public int getPlayer1() {
		return player1;
	}
	public int getPlayer2() {
		return player2;
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
	public void setPlayer1(int player1) {
		this.player1 = player1;
	}
	public void setPlayer2(int player2) {
		this.player2 = player2;
	}
	
	@Override
	public String toString() {
		return roomName;
	}
}
