package oxq.dto;

public class RoomDTO {
	public static final int ROOMLIMIT = 2; // 인원수 최대 2명 고정

	private int roomNumer; // 방번호
	private String roomName; // 방이름
	private String roomPwd; // 방비밀번호
	private int playerCnt; // 방에 들어간 사람수
	private String player1; // 1번 플레이어 닉네임(방장)
	private String player2; // 2번 플레이어 닉네임
	private int player1_score;
	private int player2_score;
	private int ready_flag;

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
	public String getPlayer1() {
		return player1;
	}
	public String getPlayer2() {
		return player2;
	}
	public int getPlayer1_score() {
		return player1_score;
	}
	public int getPlayer2_score() {
		return player2_score;
	}
	public int getReadyFlag() {
		return ready_flag;
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
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	public void setPlayer1_score(int player1_score) {
		this.player1_score = player1_score;
	}
	public void setPlayer2_score(int player2_score) {
		this.player2_score = player2_score;
	}
	public void setReady_flag(int ready_flag) {
		this.ready_flag = ready_flag;
	}

	@Override
	public String toString() {
		return roomName;
	}
}
