package oxq.action.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class PwFind extends JFrame implements ActionListener {

	public PwFind() {
		super("비밀번호 찾기");
		
		
		
		setBounds(480, 150, 400, 490);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	      
	}
}
