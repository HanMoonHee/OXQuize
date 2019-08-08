package oxq.action.waitingroom;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import oxq.action.game.GameWindow;
import oxq.dao.RoomDAO;
import oxq.dto.MemberDTO;
import oxq.dto.RoomDTO;

public class WaitingRoom extends JFrame implements ActionListener, Runnable {
	private JButton roomB, sendB;
	private JTextArea output;
	private JTextField input;
	private JLabel mainL, idL, idL2, nicknameL, nicknameL2, rankL, userL;
	private JList<RoomDTO> roomList;
	private JList<String> rankList;
	private JList<MemberDTO> userList;
	private Socket socket;
	private String id;
	private String nickName;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private DefaultListModel<RoomDTO> roomModel;
	private DefaultListModel<MemberDTO> userModel;
	private DefaultListModel<String> rankModel;
	private RoomDialog roomDialog; // 방만들기 다이알로그

	public WaitingRoom(MemberDTO mydto) {
		super("게임대기실");
		id = mydto.getId(); // 로그인 한 사람 아이디
		nickName = mydto.getNickName(); // 로그인 한 사람 닉네임
		// 버튼
		roomB = new JButton("방만들기");
		sendB = new JButton("보내기");

		// 채팅 출력창
		output = new JTextArea(15, 70); // 채팅 출력
		output.setEditable(false); // 채팅 내용 수정 X
		JScrollPane chatScroll = new JScrollPane(output);
		chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// 채팅 입력창
		input = new JTextField(); // 채팅 입력

		// 라벨
		mainL = new JLabel("내정보");
		idL = new JLabel("아이디");
		idL2 = new JLabel(id); // 접속 한 유저 id 출력
		nicknameL = new JLabel("닉네임");
		nicknameL2 = new JLabel(nickName); // 접속한 유저 닉네임 출력
		rankL = new JLabel("<랭킹>");
		userL = new JLabel("<유저목록>");

		// 방 리스트
		roomList = new JList<RoomDTO>(new DefaultListModel<RoomDTO>());
		roomModel = (DefaultListModel<RoomDTO>) roomList.getModel();

		JScrollPane roomScroll = new JScrollPane(roomList);
		roomScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roomScroll.setPreferredSize(new Dimension(790, 400));

		// 랭킹 리스트
		rankList = new JList<String>(new DefaultListModel<String>());
		rankModel = (DefaultListModel<String>) rankList.getModel();

		JScrollPane rankScroll = new JScrollPane(rankList);
		rankScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rankScroll.setPreferredSize(new Dimension(170, 400));

		// 유저 리스트
		userList = new JList<MemberDTO>(new DefaultListModel<MemberDTO>());
		userModel = (DefaultListModel<MemberDTO>) userList.getModel();

		JScrollPane userScroll = new JScrollPane(userList);
		userScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		userScroll.setPreferredSize(new Dimension(170, 240));

		// 채팅 textfield + 보내기 버튼
		JPanel chatBottom = new JPanel(new BorderLayout());
		chatBottom.add("Center", input);
		chatBottom.add("East", sendB);

		// 채팅 input+output
		JPanel chat = new JPanel(new BorderLayout());
		chat.add("Center", chatScroll);
		chat.add("South", chatBottom);

		// 상단에 내정보 아이디 , 닉네임 출력
		JPanel main = new JPanel(new FlowLayout(FlowLayout.LEFT));
		main.add(mainL);
		main.add(idL);
		main.add(idL2);
		main.add(nicknameL);
		main.add(nicknameL2);

		// 방리스트 + 방만들기
		JPanel room = new JPanel(new BorderLayout());
		room.add("North", roomScroll);
		room.add("South", roomB);

		// 내정보 + 방
		JPanel up = new JPanel(new BorderLayout());
		up.add("North", main);
		up.add("South", room);

		// 랭킹 + 리스트
		JPanel rankLP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rankLP.add(rankL);
		JPanel rank = new JPanel(new BorderLayout());
		rank.add("North", rankLP);
		rank.add("South", rankScroll);

		// 랭킹 + 내정보 + 방
		JPanel up2 = new JPanel(new FlowLayout());
		up2.add(up);
		up2.add(rank);

		// 유저목록 + 유저
		JPanel userLP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		userLP.add(userL);
		JPanel user = new JPanel(new BorderLayout());
		user.add("North", userLP);
		user.add("South", userScroll);

		// 유저 + 채팅
		JPanel bottom = new JPanel(new FlowLayout());
		bottom.add(chat);
		bottom.add(user);

		// 전체 합치기
		JPanel center = new JPanel(new BorderLayout());
		center.add("North", up2);
		center.add("South", bottom);

		Container con = this.getContentPane();
		con.add(center);

		setBounds(480, 150, 1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		// DB에서 모든 레코드 꺼내서 JList들에 뿌리기
		// 방목록
		RoomDAO roomdao = RoomDAO.getInstance();
		ArrayList<RoomDTO> roomArrayList = roomdao.getRoomList();

		for (RoomDTO dto : roomArrayList) {
			roomModel.addElement(dto);
		}
		// 유저목록
		RoomDAO memberdao = RoomDAO.getInstance();
		ArrayList<MemberDTO> memberArrayList = memberdao.getMemberList();

		for (MemberDTO dto : memberArrayList) {
			userModel.addElement(dto);
		}

		// 랭킹 리스트
		RoomDAO rnakdao = RoomDAO.getInstance();
		ArrayList<MemberDTO> rankArrayList = rnakdao.getRankList();

		for (MemberDTO dto : rankArrayList) {
			rankModel.addElement(dto.getNickName() + " 1등 횟수 : \t" + dto.getWin_cnt());
		}

		// 종료 버튼 눌렀을때
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (oos == null || ois == null)
					System.exit(0);

				WaitInfoDTO dto = new WaitInfoDTO();
				dto.setCommand(WaitInfo.EXIT);
				try {
					oos.writeObject(dto);
					oos.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		});

		// 방목록 눌렀을때 이벤트
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JList list = (JList) e.getSource();
				if (e.getClickCount() == 2) { // 더블클릭
					int index = list.locationToIndex(e.getPoint());
					System.out.println(index + "번 리스트방 입장");
					enterGame(index);
				}
			}
		});
	}

	public void service() {
		try {
			// 소켓 생성172.20.10.2 192.168.0.53 192.168.0.49
			socket = new Socket("192.168.0.49", 9500); // "loaclhost"
			// oos, ois 생성
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			// Server에 NickName 보내주기
			WaitInfoDTO dto = new WaitInfoDTO(); // dto 객체 생성
			dto.setCommand(WaitInfo.JOIN);
			dto.setNickName(nickName); // nickName은 로그인 창에서 가지고 오기

			oos.writeObject(dto); // 닉네임, 연결상태 서버로 전송
			oos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("서버를 찾을 수 없습니다");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("서버와 연결이 안되었습니다");
		}
		// 이벤트
		sendB.addActionListener(this);
		input.addActionListener(this);
		roomB.addActionListener(this);
		// 쓰레드
		Thread thread = new Thread(this);
		thread.start();
	}

	// 방 리스트 더블 클릭시 들어가는 액션
	public void enterGame(int index) {
		RoomDAO roomdao = RoomDAO.getInstance();
		ArrayList<RoomDTO> room = roomdao.getRoomList();
		System.out.println(room.get(index));

		if (room.get(index).getRoomPwd() == null) { // 비밀번호가 없는 방
			int ans = JOptionPane.showConfirmDialog(this, "입장 하시겠습니까?", "입장", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (ans == 0) { // 입장 버튼 눌렀을때
				new GameWindow();
			}
		} else { // 비밀번호 있는 방
			String pwd = JOptionPane.showInputDialog(this, "비밀번호를 입력하세요");
			System.out.println(pwd);
			if (pwd != null) {
				if (room.get(index).getRoomPwd().equals(pwd)) { // 비밀번호 일치
					new GameWindow();
				} else if (!room.get(index).getRoomPwd().equals(pwd)) { // 비밀번호 틀림
					JOptionPane.showMessageDialog(this, "비밀번호가 다릅니다", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void run() {
		WaitInfoDTO dto = null;
		try {
			while (true) {
				// 서버에게 받음
				dto = (WaitInfoDTO) ois.readObject();
				if (dto.getCommand() == WaitInfo.EXIT) {
					ois.close();
					oos.close();
					socket.close();

					System.exit(0);
				} else if (dto.getCommand() == WaitInfo.SEND) {
					output.append(dto.getMessage() + "\n");

					int position = output.getText().length(); // 스크롤 자동
					output.setCaretPosition(position);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendB || e.getSource() == input) { // 보내기 버튼
			// TextField의 내용 서버에 보내기
			WaitInfoDTO dto = new WaitInfoDTO();
			String message = input.getText(); // 텍스트필드 내용 가지고오기
			if (message.toLowerCase().equals("exit")) {
				dto.setCommand(WaitInfo.EXIT); // 종료
			} else {
				dto.setCommand(WaitInfo.SEND); // 전송 상태
				dto.setMessage(message); // 메세지 보내기
			}
			try {
				oos.writeObject(dto); // 서버로 전송상태, 메시지 객체 보내기
				oos.flush(); // 버퍼 비워주기
			} catch (IOException io) {
				io.printStackTrace();
			}
			input.setText(""); // 서버에 보내주고 텍스트필드 비우기
		} else if (e.getSource() == roomB) { // 방만들기 버튼
			roomDialog = new RoomDialog(this);
			roomDialog.setVisible(true);
			roomModel.addElement(roomDialog.getDto());
		}
	}

//	public static void main(String[] args) {
//		new WaitingRoom().service();
//	}

}
