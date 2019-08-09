package oxq.action.waitingroom;

import java.io.Serializable;
import java.util.ArrayList;

import oxq.dto.RoomDTO;

enum WaitInfo {
	JOIN, SEND, EXIT, ROOM
}

public class WaitInfoDTO implements Serializable {
	public static final long serialVersionUID = 1L; // 직렬화 버전 맞추기

	private String nickName;
	private String message;
	private WaitInfo command;

	private RoomDTO dto;
	private ArrayList<RoomDTO> roomList; // 방목록 저장 List

	// getters
	public String getNickName() {
		return nickName;
	}

	public String getMessage() {
		return message;
	}

	public WaitInfo getCommand() {
		return command;
	}

	public RoomDTO getDto() {
		return dto;
	}

	public ArrayList<RoomDTO> getRoomList() {
		return roomList;
	}

	// setters
	public void setMessage(String message) {
		this.message = message;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setCommand(WaitInfo command) {
		this.command = command;
	}

	public void addRoomList() {
		roomList = new ArrayList<RoomDTO>();
		roomList.add(dto);
	}

	public void setDto(RoomDTO dto) {
		this.dto = dto;
	}

}
