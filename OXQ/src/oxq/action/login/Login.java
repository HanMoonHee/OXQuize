package oxq.action.login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
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
import javax.swing.JLayeredPane;
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
		setLayout(null);

		// 이미지불러오기
		try {
			img = ImageIO.read(new File("imgs/bg.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
			e.printStackTrace();
		}

		// 레이아웃설정
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 800, 600);
		layeredPane.setLayout(null);
		
		// 이미지패널
		myPanel panel = new myPanel();
		panel.setBounds(100, 20, 600, 423);

		// 로그인패널
		JPanel pn1 = new JPanel();
		idL = new JLabel("ID : ");
		idT = new JTextField(15);
		pn1.add(idL);
		pn1.add(idT);
		pn1.setBounds(0, 400, 800, 50);
		pn1.setBackground(new Color(0, 255, 0, 0)); //투명도설정

		JPanel pn2 = new JPanel();
		pwdL = new JLabel("PASSWORD : ");
		pwdT = new JPasswordField(15);
		pn2.add(pwdL);
		pn2.add(pwdT);
		pn2.setBounds(0, 440, 800, 50);
		pn2.setBackground(new Color(0, 255, 0, 0)); //투명도설정

		JPanel pn3 = new JPanel();
		signIN = new JButton("로그인");
		signUP = new JButton("회원가입");
		idFindB = new JButton("아이디 찾기");
		pwFindB = new JButton("비밀번호 찾기");
		pn3.add(signIN);
		pn3.add(signUP);
		pn3.add(idFindB);
		pn3.add(pwFindB);
		pn3.setBounds(0, 480, 800, 50);
		pn3.setBackground(new Color(0, 255, 0, 0)); //투명도설정

		// 마지막추가
		layeredPane.add(panel, new Integer(0));
		layeredPane.add(pn1, new Integer(1));
		layeredPane.add(pn2, new Integer(2));
		layeredPane.add(pn3, new Integer(3));

		Container contentPane = this.getContentPane();
		contentPane.add("Center", layeredPane);
		contentPane.setBackground(new Color(255, 219, 200)); //투명도설정

		setBounds(480, 150, 800, 600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class myPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
			setOpaque(false);
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
		// 로그인
		if (e.getSource() == signIN) {
			id = idT.getText();
			pwd = pwdT.getText();
			//System.out.println(id + ": " + pwd);
			if (idT.getText() == null || idT.getText() == "") { // 아이디 입력 안했을때
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
			} else if (pwdT.getText() == null || pwdT.getText() == "") { // 비번 입력 안했을때
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "private", JOptionPane.INFORMATION_MESSAGE);
			} else { // 아이디 비번 입력 되어있을때
				if (dao.flag(id) == 1) {
					JOptionPane.showMessageDialog(this, "이미 로그인 중입니다!!", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
				} else if (dao.flag(id) == 0) {
					int flag = dao.login(id, pwd);
					if (flag == 1) { // 로그인 성공
						JOptionPane.showMessageDialog(this, "로그인 성공!!", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
						dao.loginFlag(id); // 로그인 1로 바꿔주기
						dto = dao.loginDTO(id);
						new WaitingRoom(dto).service();

						setVisible(false);

					} else if (flag == 0) { // 비밀번호 틀림
						JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
					} else if (flag == -1) { // 아이디 없음
						JOptionPane.showMessageDialog(this, "아이디가 존재하지 않습니다", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
		// 회원가입
		else if (e.getSource() == signUP) {new SignUp().event();} 
		// 아이디 찾기
		else if (e.getSource() == idFindB) {new IdFind();} 
		// 비밀번호 찾기
		else if (e.getSource() == pwFindB) {new PwFind();}
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