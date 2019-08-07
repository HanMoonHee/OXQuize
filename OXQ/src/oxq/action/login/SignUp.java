package oxq.action.login;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;

public class SignUp extends JFrame implements ActionListener {
	private JLabel idL, pwdL, pwdCheckL, nickNameL, telL, emailL, hyphenL1, hyphenL2, golL;
	private JTextField idT, pwdT, pwdCheckT, nickNameT, tel2T, tel3T, emailT;
	private JComboBox<String> tel1C, emailC;
	private JButton idB, emailB, addB, cancelB, clearB;
	
	//private MemberDTO dto;

	public SignUp() {
		super("회원가입");
		
		idL = new JLabel("    아이디  :    ");
		pwdL = new JLabel("    비밀번호 : ");
		pwdCheckL = new JLabel("    비밀번호확인 :");
		nickNameL = new JLabel("    닉네임 : ");
		telL = new JLabel("    전화번호 : ");
		emailL = new JLabel("    이메일 : ");
		hyphenL1 = new JLabel("-");
		hyphenL2 = new JLabel("-");
		golL = new JLabel("@");

		idT = new JTextField(10);
		pwdT = new JTextField(10);
		pwdCheckT = new JTextField(10);
		nickNameT = new JTextField(10);
		tel2T = new JTextField(5);
		tel3T = new JTextField(5);
		emailT = new JTextField(7);

		String[] tel = { "010", "011", "016", "019" };
		tel1C = new JComboBox<String>(tel);

		String[] email = { "naver.com", "gmail.com", "hanmail.net" };
		emailC = new JComboBox<String>(email);

		idB = new JButton("중복확인");
		emailB = new JButton("이메일인증");
		addB = new JButton("회원가입");
		cancelB = new JButton("가입취소");
		clearB = new JButton("다시작성");

		JPanel idP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 아이디
		idP.add(idL);
		idP.add(idT);
		idP.add(idB);

		JPanel pwdP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 비밀번호
		pwdP.add(pwdL);
		pwdP.add(pwdT);

		JPanel pwdCP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 비밀번호확인
		pwdCP.add(pwdCheckL);
		pwdCP.add(pwdCheckT);


		JPanel nickNameP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 닉네임
		nickNameP.add(nickNameL);
		nickNameP.add(nickNameT);

		JPanel telP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 전화번호
		telP.add(telL);
		telP.add(tel1C);
		telP.add(hyphenL1);
		telP.add(tel2T);
		telP.add(hyphenL2);
		telP.add(tel3T);

		JPanel emailP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 이메일
		emailP.add(emailL);
		emailP.add(emailT);
		emailP.add(golL);
		emailP.add(emailC);
		emailP.add(emailB);

		JPanel buttonP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel buttonP2 = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonP2.add(addB);
		buttonP2.add(cancelB);
		buttonP2.add(clearB);
		buttonP.add(buttonP2);

		JPanel centerP = new JPanel(new GridLayout(6, 1, 0, 0));
		centerP.add(idP);
		centerP.add(pwdP);
		centerP.add(pwdCP);
		centerP.add(nickNameP);
		centerP.add(telP);
		centerP.add(emailP);
		
		Container con = this.getContentPane();
		con.add("Center", centerP);
		con.add("South", buttonP);

		setBounds(480, 150, 400, 490);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public void event() {
		idB.addActionListener(this);
		emailB.addActionListener(this);
		addB.addActionListener(this); 
		cancelB.addActionListener(this);
		clearB.addActionListener(this);
	}
	
	public void clear() {
		idT.setText("");
		pwdT.setText("");
		pwdCheckT.setText("");
		nickNameT.setText("");
		tel1C.setSelectedItem("010");
		tel2T.setText("");
		tel3T.setText("");
		emailT.setText("");
		emailC.setSelectedItem("naver.com");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == idB) {	// 중복확인
			
		} else if(e.getSource() == emailB) {	// 이메일 인증
			
		} else if(e.getSource() == addB) {	// 회원가입
			MemberDTO dto = new MemberDTO();
			//아이디
			String id = idT.getText();
			//패스워드
			String pwd = pwdT.getText();
			//tel1c + tel2 + tel3 합치기
			String tel = tel1C.getSelectedItem().toString() + hyphenL1.getText() + tel2T.getText() + hyphenL2.getText() +tel3T.getText();
			//email + emailc 합치기
			String email = emailT.getText() + golL.getText() + emailC.getSelectedItem().toString();
			//닉네임 
			String nickName = nickNameT.getText();
			
			
			dto.setId(id);
			dto.setPwd(pwd);
			dto.setTel(tel);
			dto.setEmail(email);
			dto.setNickName(nickName);
			
			
			System.out.println(dto.getId() + dto.getPwd() + dto.getNickName() + dto.getTel() + dto.getEmail());
			MemberDAO dao = MemberDAO.getInstance();
			int su = dao.insertOXQuiz(dto);
			
		} else if(e.getSource() == cancelB) {	// 가입취소
			ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
			for (MemberDTO dto : arrayList) {
				if(dto.equals(idT.getText())) {
					System.out.println("이미 존재하는 아이디 입니다.");
				}else {
					System.out.println("사용가능한 아이디 입니다.");
				}
			}
			setVisible(false);
		} else if(e.getSource() == clearB) {	// 다시작성
			clear();
		}
	}
	
}
