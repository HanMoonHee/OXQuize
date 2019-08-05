package oxq.action.waitingroom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class WaitingRoom extends JFrame{
	private Color b;
	private JButton roomAdd, send;
	private JTextArea chat, chat2;
	private JTextField all;
	private JLabel user, rank;
	private JList userList, rankList, roomList;
	
	public WaitingRoom() {
		
		
		//버튼
		roomAdd = new JButton("방만들기");
		send = new JButton("보내기");
		
		//텍스트 에리어
		chat = new JTextArea();
		chat2 = new JTextArea();
		
		//chat scroll
		JScrollPane scroll = new JScrollPane(chat);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		chat.setEditable(false);
		
		//라벨
		user = new JLabel("<유저 목록>");
		rank = new JLabel("<랭킹>");
		//JList
		userList = new JList();
		rankList = new JList();
		roomList = new JList();
		
		JScrollPane scroll2 = new JScrollPane(userList);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scroll3 = new JScrollPane(rankList);
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scroll4 = new JScrollPane(roomList);
		scroll4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel chatp = new JPanel();
		chatp.add(scroll);
		
		JPanel chat2p = new JPanel(new BorderLayout());
		chat2p.add("Center", chat2);
		chat2p.add("East", send);
		
		JPanel userListp = new JPanel();
		userListp.add(user);
		
		JPanel userp = new JPanel(new BorderLayout());
		userp.add("North", userListp);
		userp.add("East", scroll2);
		
		JPanel roomp = new JPanel(new BorderLayout());
		roomp.add("South",scroll4);
		scroll4.setPreferredSize(new Dimension(700, 400));
		JPanel rankp = new JPanel();
		rankp.add( rank);
		
		JPanel rankListp = new JPanel(new BorderLayout());
		rankListp.add("North", rankp);
		rankListp.add("East", scroll3);
		
		JPanel gp = new JPanel(new BorderLayout());
		gp.add("North", roomAdd);
		gp.add("Center", scroll);
		gp.add("South", chat2p);
		
		JPanel gp2 = new JPanel(new BorderLayout());
		gp2.add("Center", gp);
		gp2.add("East", userp);
		scroll.setPreferredSize(new Dimension(100, 200));
		
		JPanel gp3 = new JPanel(new BorderLayout());
		gp3.add("Center", roomp);
		gp3.add("East", rankListp);
		
		Container c = this.getContentPane();
		add("South", gp2);
		add("Center", gp3);
		
		setBounds(480, 150, 1000, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
	}
	public static void main(String[] args) {
		new WaitingRoom();
	}

}
