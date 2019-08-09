package oxq.action.waitingroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WaitChatServer {
	private ServerSocket ss;
	private ArrayList<WaitChatHandler> list;

	public WaitChatServer() {
		try {
			ss = new ServerSocket(9500);
			System.out.println("서버준비완료");

			list = new ArrayList<WaitChatHandler>();

			while (true) { // accept 될때마다 하기위해서
				Socket socket = ss.accept(); // 낚아채서 소켓을 만듬
				WaitChatHandler handler = new WaitChatHandler(socket, list); // 스레드 생성
				handler.start(); // 스레드 시작

				list.add(handler);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new WaitChatServer();
	}
}

