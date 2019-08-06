package oxq.dto;

public class RoomDTO {
	private int roomNumer; // 방번호
	private String roomName; // 방이름

	// getters
	public int getRoomNumer() {
		return roomNumer;
	}
	public String getRoomName() {
		return roomName;
	}

	// setters
	public void setRoomNumer(int roomNumer) {
		this.roomNumer = roomNumer;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public String toString() {
		return roomName;
	}
}
