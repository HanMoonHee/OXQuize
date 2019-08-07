package oxq.action.login;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private String email;
	private String id;
	private String password;
	private boolean idC, emailC;
	
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

		idT.addKeyListener(this);
		idT.setFocusTraversalKeysEnabled(false);
		emailT.addKeyListener(this);
		emailT.setFocusTraversalKeysEnabled(false);
		JPanel idP = new JPanel();
		JPanel emailP = new JPanel();

		idP.add(idL);
		idP.add(idT);
		emailP.add(emailL);
		emailP.add(emailT);
		idP.setBounds(0, 10, 250, 35);
		emailP.setBounds(0, 50, 250, 35);
		findB.setBounds(255, 15, 70, 60);

		con.add(idP);
		con.add(emailP);
		con.add(findB);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		
		setBounds(480, 150, 400, 490);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	      
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
