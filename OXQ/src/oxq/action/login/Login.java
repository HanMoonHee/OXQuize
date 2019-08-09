package oxq.action.login;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import oxq.action.waitingroom.WaitingRoom;
import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;

public class Login extends JFrame implements ActionListener, KeyListener {
	private JLabel idL, pwdL;
	private JTextField idT, pwdT;
	private JButton signIN, signUP, idFindB, pwFindB;

	private MemberDTO dto;
	private String id;
	private String pwd;
	private BufferedImage img;

	public Login() {
		setTitle("LOGIN");
		
//		try {
//			img = ImageIO.read(new File("C:\\Users\\bitcamp\\Documents\\git\\OXQuize\\OXQ\\imgs\\bg.jpg"));
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
//			System.exit(0);
//			e.printStackTrace();
//		}
		
		myPanel panel = new myPanel();
		panel.setSize(800, 600);
		
		JPanel pn1 = new JPanel();
		idL = new JLabel("ID : ");
		idT = new JTextField(15);
		pn1.add(idL);
		pn1.add(idT);

		JPanel pn2 = new JPanel();
		pwdL = new JLabel("PASSWORD : ");
		pwdT = new JPasswordField(15);
		pn2.add(pwdL);
		pn2.add(pwdT);

		JPanel pn3 = new JPanel();
		signIN = new JButton("로그인");
		signUP = new JButton("회원가입");
		pn3.add(signIN);
		pn3.add(signUP);

		JPanel pn4 = new JPanel();
		idFindB = new JButton("아이디 찾기");
		pwFindB = new JButton("비밀번호 찾기");
		pn4.add(idFindB);
		pn4.add(pwFindB);
		
		JPanel centerP = new JPanel(new GridLayout(4, 1));
		centerP.add(pn1);
		centerP.add(pn2);
		centerP.add(pn3);
		centerP.add(pn4);

		JPanel alignP = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 200));
		alignP.add(centerP);
		
		Container contentPane = this.getContentPane();
		//contentPane.add(panel);
		contentPane.add("Center", alignP);

		setBounds(480, 150, 800, 600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public void event() {
		signIN.addActionListener(this);
		signUP.addActionListener(this);
		idFindB.addActionListener(this);
		pwFindB.addActionListener(this);
		
		pwdT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signIN.doClick();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		MemberDAO dao = MemberDAO.getInstance();
		
		if (e.getSource() == signIN) {
			id = idT.getText();
			pwd = pwdT.getText();
			System.out.println(id +": " +pwd);
			if(idT.getText() == null || idT.getText() == "") {	// 아이디 입력 안했을때
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
			} else if (pwdT.getText() == null || pwdT.getText() == "") { // 비번 입력 안했을때
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "private", JOptionPane.INFORMATION_MESSAGE);				
			} else { // 아이디 비번 입력 되어있을때
				if(dao.flag(id) == 1) {
					JOptionPane.showMessageDialog(this, "이미 로그인 중입니다!!", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);	
				}
				else if(dao.flag(id) == 0) {
					int flag = dao.login(id, pwd);
					if(flag == 1) { //로그인 성공
						JOptionPane.showMessageDialog(this, "로그인 성공!!", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
						dao.LoginFlag(id);	// 로그인 1로 바꿔주기
						dto = dao.loginDTO(id);
						new WaitingRoom(dto).service();
						
						setVisible(false);
						
					} else if(flag == 0) { //비밀번호 틀림
						JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
					} else if(flag == -1) {	//아이디 없음
						JOptionPane.showMessageDialog(this, "아이디가 존재하지 않습니다", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

		} // 로그인
		
		else if (e.getSource() == signUP) {	new SignUp().event(); }
		else if (e.getActionCommand().equals("아이디 찾기")) {	new IdFind(); }
		else if (e.getActionCommand().equals("비밀번호 찾기")) { new PwFind(); }
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}

	public static void main(String[] args) {
		new Login().event();
	}
	
}