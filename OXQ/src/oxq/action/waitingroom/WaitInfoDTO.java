package oxq.action.waitingroom;

import java.io.Serializable;

enum WaitInfo {
	JOIN, SEND, EXIT, ROOM
}

public class WaitInfoDTO implements Serializable {
	public static final long serialVersionUID = 1L; // 직렬화 버전 맞추기
	
	private String nickName;
	private String message;
	private WaitInfo command;
	
	// getters
	public String getNickName() {return nickName;}
	public String getMessage() {return message;}
	public WaitInfo getCommand() {return command;}
	
	// setters 
	public void setMessage(String message) {this.message = message;}
	public void setNickName(String nickName) {this.nickName = nickName;}
	public void setCommand(WaitInfo command) {this.command = command;}
	
}
