package oxq.action.login;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;

public class PwFind extends JFrame implements ActionListener, KeyListener {

	private MemberDAO dao = MemberDAO.getInstance();
	private ArrayList<MemberDTO> list;
	private JLabel idL, emailL;
	private JTextField idT, emailT;
	private JButton findB;
	private Container con;
	private String id;
	private String email;
	private MemberDTO dto;

	public PwFind() {
		super("비밀번호 찾기");

		con = getContentPane();
		con.setLayout(null);
		idL = new JLabel("아이디 입력:");
		idT = new JTextField(15);
		emailL = new JLabel("이메일 입력:");
		emailT = new JTextField(15);
		findB = new JButton("찾기");
		findB.addActionListener(this);

		emailT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findB.doClick();
				}
			}
		});

		JPanel idP = new JPanel();
		JPanel emailP = new JPanel();

		idP.add(idL);
		idP.add(idT);
		emailP.add(emailL);
		emailP.add(emailT);
		idP.setBounds(30, 150, 250, 35);
		emailP.setBounds(30, 180, 250, 35);
		findB.setBounds(290, 150, 70, 60);

		con.add(idP);
		con.add(emailP);
		con.add(findB);

		setBounds(580, 180, 400, 450);
		setVisible(true);
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * 1. 아이디와 이메일값을 입력받는다. 2. 찾기버튼을 누른다. 3. dao에서 입력한 아이디와 이메일로 디비 검사 후 맞는것을
		 * getter하여 list변수에 넣는다. 4. 4. 입력한 이메일에 비밀번호를 보낸다.
		 */
		id = idT.getText();

		email = emailT.getText();
		list = dao.getMemberList();

		// 아이디를입력하세요
		for (MemberDTO dto : list) {
			if (id.length() <= 0) {
				JOptionPane.showMessageDialog(this, "아이디를 입력하세요", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (id.length() > 0 && email.length() <= 0) {
				JOptionPane.showMessageDialog(this, "이메일을 입력하세요", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (!id.equals(dto.getId()) || !email.equals(dto.getEmail())) {
				JOptionPane.showMessageDialog(this, "아이디와 이메일을 확인해 주세요", "아이디 이메일 불일치", JOptionPane.ERROR_MESSAGE);
			} else {
				new SMTPMailSendPassword(id, email);
				JOptionPane.showMessageDialog(this, "비밀번호를 발송하였습니다.", "Send", JOptionPane.ERROR_MESSAGE);
	
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
