package oxq.action.login;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import oxq.dao.MemberDAO;
import oxq.dto.MemberDTO;

public class IdFind extends JFrame implements ActionListener {
	private MemberDAO dao = MemberDAO.getInstance();
	private ArrayList<MemberDTO> list;
	private JLabel emailL;
	private JTextField emailT;
	private JButton findB;
	private JList<String> idList;
	private DefaultListModel<String> model;
	private JScrollPane scroll;
	private Container con;
	private String email;

	public IdFind() {
		super("아이디찾기");

		con = getContentPane();
		con.setLayout(null);
		emailL = new JLabel("이메일 입력:");
		emailT = new JTextField(15);
		findB = new JButton("찾기");
		emailT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findB.doClick();
				}
			}
		});
		emailT.setFocusTraversalKeysEnabled(false);
		model = new DefaultListModel<String>();
		idList = new JList<String>(model);
		scroll = new JScrollPane(idList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(30, 60, 335, 300);

		JPanel p = new JPanel();
		p.add(emailL);
		p.add(emailT);
		p.add(findB);
		p.setBounds(30, 10, 330, 35);

		con.add(p);
		con.add(scroll);
		findB.addActionListener(this);

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
		email = emailT.getText();
		list = dao.getMemberList();
		if (email.length() <= 0) {
			model.addElement("찾는 아이디가없습니다.");
		}
		else {
			model.clear();
			for (MemberDTO dto : list) {
				if (email.equals(dto.getEmail())) {
					model.addElement(dto.getId());
				}
				else if(!email.equals(dto.getEmail())) {
					model.addElement("찾는 아이디가없습니다.");
				}
			}
		}

	}
}
