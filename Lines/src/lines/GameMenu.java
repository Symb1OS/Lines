package lines;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bolls.Boll;

public class GameMenu extends JFrame {

	private static final long serialVersionUID = -2772739529685466396L;
	
	private static final String LOGO_TYPE  = Boll.PATH + "lines.PNG";
	
	private JPanel contentPane;

	public static void main(String[] args) {
		GameMenu menu = new GameMenu();
	}

	public GameMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 350, 450, 300);
		setSize(200, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel iconGame = new JLabel("Иконка игры");
		iconGame.setIcon(new ImageIcon(LOGO_TYPE));
		iconGame.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(iconGame, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JButton newGameButton = new JButton("Начать игру");
		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Lines Game = new Lines();
				dispose();
				
			}
		});
		panel.add(newGameButton);
		
		JButton settingsButton = new JButton("Настройки");
		settingsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameSettings settings = new GameSettings();
				dispose();
				
			}
		});
		panel.add(settingsButton);
		
		JButton endGame = new JButton("Выход");
		endGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(endGame);
		
		setResizable(false);
		setVisible(true);
	}
}