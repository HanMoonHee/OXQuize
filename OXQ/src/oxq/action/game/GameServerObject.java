package oxq.action.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServerObject {
	private ServerSocket ss; 
	public ArrayList<GameHandlerObject> list;

	public GameServerObject() {

		try {
			ss = new ServerSocket(9600);
			System.out.println("게임방 서버 준비 완료");

			list = new ArrayList<GameHandlerObject>();

			while (true) {
				Socket socket = ss.accept();

				GameHandlerObject handler = new GameHandlerObject(socket, list);
				handler.start();
				list.add(handler);
				System.out.println(list.size());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new GameServerObject();
	}
}
