package oxq.action.game;

import java.io.Serializable;

enum PlayInfo {
	JOIN, SEND, EXIT, TIMER // 게임방 참가,게임방 채팅, 게임방 나가기
}

public class PlayInfoDTO implements Serializable {
	public static final long serialVersionUID = 1L; // 직렬화 버전 맞추기

	private String nickname; // 게임방 참가자의 닉네임
	private String message; // 채팅 내용
	private PlayInfo command; // 게임방 참가, 채팅, 나가기
	
	// getters
	public String getNickname() {
		return nickname;
	}
	public String getMessage() {
		return message;
	}
	public PlayInfo getCommand() {
		return command;
	}
	
	// setters 
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCommand(PlayInfo command) {
		this.command = command;
	}
}
