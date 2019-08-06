package oxq.action.waitingroom;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oxq.action.game.GameWindow;

public class MakeRoom extends JDialog implements ActionListener {
	private JLabel makeRoom, roomTitle, roomPwd;
	private JTextField roomT, roomP;
	private JCheckBox secretRoom;
	private JButton creatB, cancelB;
	
	
	public MakeRoom() {
		
		

		
		// JLabel 생성
		makeRoom = new JLabel("방 만들기");
		roomTitle = new JLabel("방 제목");
		roomPwd = new JLabel("방 비밀번호");

		// JTextField 생성
		roomT = new JTextField(11);
		roomP = new JTextField(5);
		roomP.setEnabled(false);
		// JCheckBox 생성
		secretRoom = new JCheckBox("비밀방");

		// JButton 생성
		creatB = new JButton("생성");
		cancelB = new JButton("취소");

		JPanel rmp = new JPanel();
		rmp.add(makeRoom);

		JPanel rtp = new JPanel();
		rtp.add(roomTitle);
		rtp.add(roomT);
		roomTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));// 상 하 좌 우

		JPanel rpp = new JPanel();
		rpp.add(roomPwd);
		rpp.add(secretRoom);
		rpp.add(roomP);
		
		JPanel rbp = new JPanel();
		rbp.add(creatB);
		rbp.add(cancelB);
		// rbp.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 10));// 상 하 좌 우
		
		JPanel gp = new JPanel(new GridLayout(4, 1));
		gp.add(rmp);
		gp.add(rtp);
		gp.add(rpp);
		gp.add(rbp);
		add("Center", gp);

		setBounds(850, 450, 300, 190);
		setVisible(true);
		setResizable(false);
		
		
		//GUI 종료
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				
			}
		});
		
		secretRoom.addItemListener(new ItemListener() {
			
			//비밀방 체크박스 컨트롤
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					roomP.setEnabled(true);
				}else {
					roomP.setEnabled(false);
					roomP.setText("");
				}
				
			}
		});
		
		creatB.addActionListener(this);
		cancelB.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == creatB) {
			new GameWindow();
			setVisible(false);
			
		}else if(e.getSource() == cancelB) {
			setVisible(false);
			
		}
	}
}
