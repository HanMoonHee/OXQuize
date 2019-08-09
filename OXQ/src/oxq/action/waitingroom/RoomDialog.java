package oxq.action.waitingroom;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oxq.dao.RoomDAO;
import oxq.dto.MemberDTO;
import oxq.dto.RoomDTO;

public class RoomDialog extends JDialog implements ActionListener {
	private JLabel makeRoomL, roomTitleL, roomPwdL;
	private JTextField roomNameT, roomPwdT;
	private JCheckBox secretRoom;
	private JButton createB, cancelB;
	private RoomDTO dto;
	private String nickName;
	private int room_ok;	// 방만들어지면 1값됨
	private Socket socket;
	private ObjectOutputStream oos;

	public RoomDialog(JFrame frame, MemberDTO mydto) {
		super(frame, "방만들기", true);
		nickName = mydto.getNickName();	// 접속한 사람 닉네임
		System.out.println("방만들기 닉네임 "+nickName);
		// JLabel 생성
		// JLabel 생성
		makeRoomL = new JLabel("방 만들기");
		roomTitleL = new JLabel("방 제목");
		roomPwdL = new JLabel("방 비밀번호");

		// JTextField 생성
		roomNameT = new JTextField(11);
		roomPwdT = new JTextField(5);
		roomPwdT.setEnabled(false);
		// JCheckBox 생성
		secretRoom = new JCheckBox("비밀방");

		// JButton 생성
		createB = new JButton("생성");
		cancelB = new JButton("취소");

		dto = new RoomDTO();
		
		JPanel rmp = new JPanel();
		rmp.add(makeRoomL);

		JPanel rtp = new JPanel();
		rtp.add(roomTitleL);
		rtp.add(roomNameT);
		roomTitleL.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));// 상 하 좌 우

		JPanel rpp = new JPanel();
		rpp.add(roomPwdL);
		rpp.add(secretRoom);
		rpp.add(roomPwdT);

		JPanel rbp = new JPanel();
		rbp.add(createB);
		rbp.add(cancelB);

		JPanel gp = new JPanel(new GridLayout(4, 1));
		gp.add(rmp);
		gp.add(rtp);
		gp.add(rpp);
		gp.add(rbp);

		getContentPane().add("Center", gp);
		setBounds(830, 400, 300, 190);

		// GUI 종료
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		secretRoom.addItemListener(new ItemListener() {
			// 비밀방 체크박스 컨트롤
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					roomPwdT.setEnabled(true);
				} else {
					roomPwdT.setEnabled(false);
					roomPwdT.setText("");
				}
			}
		});
		
		createB.addActionListener(this);
		cancelB.addActionListener(this);
	}
	//roomName, roomPwd
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createB) {
			// 소켓 생성
			try {
				socket = new Socket("localhost", 9500); // "loaclhost"
				// oos 생성
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException io) {
				io.printStackTrace();
			}
			
			// 데이터
			String roomName = roomNameT.getText();
			String roomPwd = roomPwdT.getText();
			int count = 1;	// 방만들면 1명 있으니까 1
			
			dto.setRoomName(roomName);
			dto.setRoomPwd(roomPwd);
			dto.setPlayerCnt(count);
			
			// DB
			RoomDAO dao = RoomDAO.getInstance();	// 싱글톤
			int seq = dao.getRoomNum();
			dto.setRoomNumer(seq);
			
			room_ok = dao.insertRoom(dto, nickName);
			System.out.println(room_ok + "개 방생성 완료");
			
			// 서버에게 방만들어짐 보내기
			WaitInfoDTO infodto = new WaitInfoDTO(); //서버에 필요한 dto
			infodto.setCommand(WaitInfo.ROOM);	//방만든 상태라고 서버에 알려주기
			try {
				oos.writeObject(infodto);
				oos.flush();
			} catch (IOException io) {
				io.printStackTrace();
			}
			// 다이알로그 닫기
			setVisible(false);
			
		} else if (e.getSource() == cancelB) {
			setVisible(false);
		}
	}
	
	public RoomDTO getDto() {
		return dto;
	}
	public int getRoom_ok() {
		return room_ok;
	}
	
}