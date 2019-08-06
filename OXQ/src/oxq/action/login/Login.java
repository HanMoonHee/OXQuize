package oxq.action.login;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener, Runnable{
	
	private JLabel idL, pwdL;
	private JTextField idT, pwdT;
	private JButton signIN, signUP, idFindB, pwFindB;
	
	public Login() {
		setTitle("LOGIN");
		JPanel pn1 = new JPanel();
		idL = new JLabel("ID : ");
		idT = new JTextField(15);
		pn1.add(idL);
		pn1.add(idT);
		
		JPanel pn2 = new JPanel();
		pwdL = new JLabel("PASSWORD : ");
		pwdT = new JTextField(15);
		pn2.add(pwdL);
		pn2.add(pwdT);
		
		JPanel pn3 = new JPanel();
		signIN = new JButton("SIGN IN");
		signUP = new JButton("SIGN UP");
		pn3.add(signIN);
		pn3.add(signUP);
		
		JPanel pn4 = new JPanel();
		idFindB = new JButton("ID SEARCH");
		pwFindB = new JButton("PASSWORD SEARCH");
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
		contentPane.add("Center", alignP);
		
		setBounds(50, 50, 800, 600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		signIN.addActionListener(this);
		signUP.addActionListener(this);
		idFindB.addActionListener(this);
		pwFindB.addActionListener(this);
	}
	
	@Override
	public void run() {
		
	}

	private MemberDAO dao = MemberDAO.getInstance();
	private String nickName;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("SIGN IN")) {
			if(idT.getText().length()<=0) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "로그인 에러", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if (pwdT.getText().length() <= 0 && idT.getText().length() > 0) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "private", JOptionPane.INFORMATION_MESSAGE);
			} else {
				ArrayList<MemberDTO> list = dao.check(); //memberDTO를 list변수에 남아 dao와 check이벤트를 실행?
				
				for (MemberDTO dto : list) {
					if(dto.getId().equals(idT.getText())) {
						nickName = idT.getText();
						
					}
				}
			}
			
			
		}//로그인
		
		else if (e.getActionCommand().equals("회원가입")) {
			
		}
		
		else if (e.getActionCommand().equals("아이디 찾기")) {
			
		}
		
		else if (e.getActionCommand().equals("비밀번호 찾기")) {
			
		}
		
		
	}
	
	public static void main(String[] args) {
		new Login();
	}
	
	
}
