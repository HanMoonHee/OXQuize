package oxq.action.login;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;

public class MemberEdit2 extends JFrame implements ActionListener {
	private JLabel idL, pwdL, nickNameL, telL, emailL, hyphenL1, hyphenL2, golL;
	private JTextField idT, nickNameT, tel2T, tel3T, emailT;
	private JPasswordField pwdT;
	private JComboBox<String> tel1C, emailC;
	private JButton updateB, cancelB, waitB;
	private ArrayList<MemberDTO> list;
	private MemberDTO dto;
	private String id;
	private String mail; // 이메일

	// private MemberDTO dto;

	public MemberEdit2() {
		super("회원정보 수정");

		idL = new JLabel("    아이디  :    ");
		pwdL = new JLabel("    비밀번호 : ");
		nickNameL = new JLabel("    닉네임 : ");
		telL = new JLabel("    전화번호 : ");
		emailL = new JLabel("    이메일 : ");
		hyphenL1 = new JLabel("-");
		hyphenL2 = new JLabel("-");
		golL = new JLabel("@");

		idT = new JTextField(10);
		idT.setEnabled(false);
		pwdT = new JPasswordField(10);
		nickNameT = new JTextField(10);
		tel2T = new JTextField(5);
		tel3T = new JTextField(5);
		emailT = new JTextField(7);

		String[] tel = { "010", "011", "016", "019" };
		tel1C = new JComboBox<String>(tel);

		String[] email = { "naver.com", "gmail.com", "hanmail.net" };
		emailC = new JComboBox<String>(email);

		updateB = new JButton("개인정보 수정");
		cancelB = new JButton("취소");
		waitB = new JButton("대기실");

		JPanel idP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 아이디
		idP.add(idL);
		idP.add(idT);
		idP.add(waitB);

		JPanel pwdP = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 비밀번호
		pwdP.add(pwdL);
		pwdP.add(pwdT);

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

		JPanel buttonP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel buttonP2 = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonP2.add(updateB);
		buttonP2.add(cancelB);
		buttonP.add(buttonP2);

		JPanel centerP = new JPanel(new GridLayout(5, 1, 0, 0));
		centerP.add(idP);
		centerP.add(pwdP);
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
		updateB.addActionListener(this);
		cancelB.addActionListener(this);
		waitB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateB) {// 회원정보 수정
			String tel = tel1C.getSelectedItem().toString() + hyphenL1.getText() + tel2T.getText() + hyphenL2.getText()
					+ tel3T.getText();
			String email = emailT.getText() + golL.getText() + emailC.getSelectedItem().toString();
			MemberDTO dto = new MemberDTO();
			dto.setPwd(pwdT.getText());
			dto.setNickName(nickNameT.getText());
			dto.setTel(tel);
			dto.setEmail(email);
			dto.setId(idT.getText());
			if(pwdT.getPassword().length == 0) {
				JOptionPane.showConfirmDialog(this, "비밀번호를 입력해주세요.", "비밀번호 공백", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if(nickNameT.getText().length() == 0) {
				JOptionPane.showConfirmDialog(this, "닉네임을 입력해주세요.", "닉네임 공백", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if(tel2T.getText().length() == 0) {
				JOptionPane.showConfirmDialog(this, "전화번호를 입력해주세요.", "전화번호 공백", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if(tel3T.getText().length() == 0) {
				JOptionPane.showConfirmDialog(this, "전화번호를 뒷 자리를 입력해주세요.", "전화번호 공백", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if(emailT.getText().length() == 0) {
				JOptionPane.showConfirmDialog(this, "이메일을 입력해주세요.", "이메일 공백", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			int result = JOptionPane.showConfirmDialog(this, "프로필을 수정하시겠습니까?", "프로필 수정", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION) {
				JOptionPane.showConfirmDialog(this, "프로필 변경이 취소되었습니다..", "프로필 변경 취소", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			} else if (result == JOptionPane.OK_OPTION) {
				MemberDAO dao = MemberDAO.getInstance();
				int su = dao.updateMember(dto);
				JOptionPane.showConfirmDialog(this, "프로필 변경 되었습니다.", "프로필 변경 완료", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
			} else {
				return;
			}

			

		} else if (e.getSource() == cancelB) {
			setVisible(false);
		} else if (e.getSource() == waitB) {
			MemberDTO dto;

			MemberDAO dao = MemberDAO.getInstance();
			dto = dao.loginDTO("test1");

			// 이메일 값 가져오기
			String mail0 = dto.getEmail();
			// @ 인덱스 찾기
			int idx = mail0.indexOf("@");
			// 이메일 앞부분 자르기
			String mail1 = mail0.substring(0, idx);
			// 이메일 뒷부분 자르기
			String mail2 = mail0.substring(idx + 1);

			// 전화번호 값 가져오기
			String tel0 = dto.getTel();
			// - 인덱스
			String[] tel = tel0.split("-");

			String[] tel1 = new String[3];

			for (int i = 0; i < tel.length; i++) {
				tel1[i] = tel[i];
			}

			idT.setText(dto.getId());
			nickNameT.setText(dto.getNickName());
			tel1C.setSelectedItem(tel[0]);
			tel2T.setText(tel[1]);
			tel3T.setText(tel[2]);
			emailT.setText(mail1);
			emailC.setSelectedItem(mail2);

		}

	}

	public static void main(String[] args) {
		new MemberEdit2().event();
	}
}
