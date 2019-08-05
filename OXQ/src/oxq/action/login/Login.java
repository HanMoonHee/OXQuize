package oxq.action.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener, Runnable {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("LOGIN :");
		lblLogin.setBounds(391, 271, 45, 15);
		contentPane.add(lblLogin);
		
		textField = new JTextField();
		textField.setText("???");
		textField.setBounds(483, 268, 116, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD :");
		lblPassword.setBounds(361, 294, 75, 15);
		contentPane.add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setText("???");
		textField_1.setBounds(483, 291, 116, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("SIGN IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(391, 359, 207, 21);
		contentPane.add(btnNewButton);
		
		JButton btnExit = new JButton("SIGN UP");
		btnExit.setBounds(391, 385, 208, 23);
		contentPane.add(btnExit);
		
		JButton button = new JButton("EXIT");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(391, 413, 208, 23);
		contentPane.add(button);
		
		
		setBounds(50, 50, 1000, 800);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new Login();
	}


}
