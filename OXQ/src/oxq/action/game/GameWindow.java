package oxq.action.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class GameWindow extends JFrame implements Runnable {
	private JButton startB;
	private JButton exitB;
	private JTextField input;
	private JTextArea output;
	private JTextArea questionArea;
	private ImageIcon icon;
	private JScrollPane scrollPane;

	public GameWindow() {
		icon = new ImageIcon("resources/bonobono1.png");
		JPanel background = new JPanel(new BorderLayout()) {
			public void paintComponent(Graphics g) {
				Dimension dd = getSize();
				g.drawImage(icon.getImage(), 0, 0, dd.width, dd.height, null);

				setOpaque(false);
				super.paintComponent(g);
			}
		};

		startB = new JButton("Game Start");
		exitB = new JButton("Exit");

		input = new JTextField();
		Dimension d1 = new Dimension();
		d1.setSize(700, 30);
		input.setPreferredSize(d1);

		output = new JTextArea();
		output.setEditable(false);
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Dimension d2 = new Dimension();
		d2.setSize(700, 100);
		scroll.setPreferredSize(d2);

		Dimension d3 = new Dimension();
		d3.setSize(700, 140);
		questionArea = new JTextArea();
		questionArea.setEditable(false);
		questionArea.setPreferredSize(d3);
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		questionArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JPanel chatPan = new JPanel();
		chatPan.setLayout(new BoxLayout(chatPan, BoxLayout.Y_AXIS));
		chatPan.add(scroll);
		chatPan.add(input);

		JPanel btnPan = new JPanel(new GridLayout(2, 1, 0, 0));
		btnPan.add(startB);
		btnPan.add(exitB);

		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		south.add(chatPan);
		south.add(btnPan);

		JPanel north = new JPanel();
		north.setOpaque(false);
		north.add(questionArea);

		JPanel center = new JPanel();
		center.setBackground(Color.DARK_GRAY);
		Image img_o = new ImageIcon("resources/letter_oo.png").getImage().getScaledInstance(350, 350,
				Image.SCALE_SMOOTH);
		Image img_x = new ImageIcon("resources/letter_xx.png").getImage().getScaledInstance(350, 350,
				Image.SCALE_SMOOTH);

		ImageIcon icon_o = new ImageIcon(img_o);
		ImageIcon icon_x = new ImageIcon(img_x);

		JPanel playpan = new JPanel(new FlowLayout()) {
			public void paintComponent(Graphics g) {
				g.drawImage(img_o, 75, 80, null);
				g.drawImage(img_x, 575, 80, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		Container con = getContentPane();

		background.add(playpan);
		background.add("North", north);
		background.add("South", south);

		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);

		setResizable(false);
		setBounds(480, 150, 1000, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new GameWindow();
	}
}
