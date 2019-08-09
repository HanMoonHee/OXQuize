package oxq.action.waitingroom;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import oxq.dao.MemberDAO;
import oxq.dao.RoomDAO;
import oxq.dto.MemberDTO;
import oxq.dto.RoomDTO;

public class WaitingRoom extends JFrame implements ActionListener, Runnable {
	// 방만들기, 전송 버튼
	private JButton roomB, sendB, myInfoB;
	// 채팅창
	private JTextArea output;
	private JTextField input;
	// 라벨들
	private JLabel mainL, idL, idL2, nicknameL, nicknameL2, rankL, userL;
	// 방 목록
	private JList<RoomDTO> roomList;
	private DefaultListModel<RoomDTO> roomModel;
	// 랭킹 목록
	private JList<String> rankList;
	private DefaultListModel<String> rankModel;
	// 유저 목록
	private JList<MemberDTO> userList;
	private DefaultListModel<MemberDTO> userModel;
	// 채팅 관련
	private Socket socket;
	private String id;
	private String nickName;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	// 방만들기 다이알로그
	private RoomDialog roomDialog;
	// 스레드 풀
	private ExecutorService executorService;
	
	public WaitingRoom(MemberDTO mydto) {
		super("게임대기실");
		id = mydto.getId();	// 로그인 한 사람 아이디
		nickName = mydto.getNickName();	// 로그인 한 사람 닉네임
		
		// 스레드풀
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());	// 스레드풀 생성
		
		// 버튼
		roomB = new JButton("방만들기");
		sendB = new JButton("보내기");
		myInfoB = new JButton("정보 수정");

		// 채팅 출력창
		output = new JTextArea(15, 70); // 채팅 출력
		output.setEditable(false); // 채팅 내용 수정 X
		JScrollPane chatScroll = new JScrollPane(output);
		chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// 채팅 입력창
		input = new JTextField(); // 채팅 입력

		// 라벨
		mainL = new JLabel("*** 내정보 *** ");
		idL = new JLabel("  아이디 : ");
		idL2 = new JLabel(id); // 접속 한 유저 id 출력
		nicknameL = new JLabel("  닉네임 : ");
		nicknameL2 = new JLabel(nickName); // 접속한 유저 닉네임 출력
		rankL = new JLabel("< 랭킹 >");
		userL = new JLabel("< 유저목록 >");

		mainL.setFont(new Font("고딕", Font.BOLD, 18));
		idL.setFont(new Font("고딕", Font.BOLD, 18));
		idL2.setFont(new Font("고딕", Font.BOLD, 18));
		nicknameL.setFont(new Font("고딕", Font.BOLD, 18));
		nicknameL2.setFont(new Font("고딕", Font.BOLD, 18));
		rankL.setFont(new Font("고딕", Font.BOLD, 15));
		userL.setFont(new Font("고딕", Font.BOLD, 15));
		
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
		main.add(myInfoB);

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
		
		// 랭킹 리스트
		RoomDAO rankdao = RoomDAO.getInstance();
		ArrayList<MemberDTO> rankArrayList = rankdao.getRankList();
		
		for (MemberDTO dto : rankArrayList) {
			rankModel.addElement(dto.getNickName() + " 1등 횟수 : \t" + dto.getWin_cnt());
		}
				
		// 디비에 저장된 룸 불러와서 roomModel에 저장
		RoomDAO roomdao = RoomDAO.getInstance();
		ArrayList<RoomDTO> roomArrayList = roomdao.getRoomList();
						
		for (RoomDTO roomdto : roomArrayList) {
			roomModel.addElement(roomdto);
		}
	
		//종료 버튼 눌렀을때
		addWindowListener(new WindowAdapter() {	
			MemberDAO exitdao = MemberDAO.getInstance();
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (oos == null || ois == null)
					System.exit(0);
				exitdao.LogoutFlag(id);
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
				JList list = (JList)e.getSource();
				if(e.getClickCount() == 2) { // 더블클릭
					int index = list.locationToIndex(e.getPoint());
					System.out.println(index+"번 리스트방 입장");
					enterGame(index);
				}
			}
		});
	}

	public void service() {
		try {
			// 소켓 생성
			socket = new Socket("localhost", 9500); // "loaclhost"
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
		System.out.println("방 인덱스 번호" +room.get(index));
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.loginDTO(id);
		
		if (room.get(index).getRoomPwd() == null) {	// 비밀번호가 없는 방
			int ans = JOptionPane.showConfirmDialog(this, "입장 하시겠습니까?", "입장", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (ans == 0) {	// 입장 버튼 눌렀을때
//				roomdao.updatePlayer2(dto.getNickName(), roomdao.getRoomNum());	// 디비에 player2 update
				//new GameWindow(dto.getNickName(), 2, roomdao.getRoomNum());	//palycnt 2로 넣어주기
			}
		} else { // 비밀번호 있는 방
			String pwd = JOptionPane.showInputDialog(this, "비밀번호를 입력하세요");
			System.out.println(pwd);
			if(pwd != null) {
				if(room.get(index).getRoomPwd().equals(pwd)) { // 비밀번호 일치
//					roomdao.updatePlayer2(dto.getNickName(), roomdao.getRoomNum());	// 디비에 player2 update
					//new GameWindow(dto.getNickName(), 2, roomdao.getRoomNum());	//palycnt 2로 넣어주기
				} else if(!room.get(index).getRoomPwd().equals(pwd)) { // 비밀번호 틀림
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
					
					if((dto.getMessage().indexOf("입장하였습니다") != -1) || (dto.getMessage().indexOf("퇴장하였습니다") != -1)) {
						// 유저목록 스레드
						executorService.submit(new Runnable() {

							@Override
							public void run() {
								RoomDAO memberdao = RoomDAO.getInstance();
								ArrayList<MemberDTO> memberArrayList = memberdao.getMemberList();
								
								userModel.clear();	// 리스트 비우고 다시 써주려고 clear()
								
								for (MemberDTO dto : memberArrayList) {
									userModel.addElement(dto);
								}
							}
							
						});
					}
					
					int position = output.getText().length(); // 스크롤 자동
					output.setCaretPosition(position);
				} else if (dto.getCommand() == WaitInfo.ROOM) {	//방생성 받는곳
					RoomDAO roomdao = RoomDAO.getInstance();
					ArrayList<RoomDTO> roomArrayList = roomdao.getRoomList();
					
					roomModel.clear();		
					
					for (RoomDTO roomdto : roomArrayList) {
						roomModel.addElement(roomdto);
					}
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
		if (e.getSource() == sendB || e.getSource() == input) {	// 보내기 버튼
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
		} else if (e.getSource() == roomB) {	// 방만들기 버튼
			MemberDAO dao = MemberDAO.getInstance();
			MemberDTO dto = dao.loginDTO(id);
			roomDialog = new RoomDialog(this, dto);
			
			roomDialog.setVisible(true);
			RoomDAO roomdao = RoomDAO.getInstance();
			String roomName = roomdao.getRoomName(dto.getNickName());
			System.out.println("방 이름: " + roomName);
			
			if(roomDialog.getRoom_ok() == 1) { // 방만들어졌을때 대기방 닫고 게임방 키기
				setVisible(false);
				
				new GameWindow(dto.getNickName(), roomName).service();	// 닉네임, 1, 방 시퀀스번호 넘겨줌
			} else if(roomDialog.getRoom_ok() == 0) { // 방안만들어졌을때
				
			}
		}
	}

//	public static void main(String[] args) {
//		new WaitingRoom().service();
//	}

}
