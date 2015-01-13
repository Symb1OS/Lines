package lines;

import java.awt.Color;
import java.awt.Font;
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

public class EndGame extends JFrame {

	private static final long serialVersionUID = -3321700711010248266L;
	
	private static final String ICON_GAME_OVER = Boll.PATH + "gameover.PNG";
	
	private JPanel contentPane;

	public static void main(String[] args) {
		EndGame endGame = new EndGame(45);	
	}

	public EndGame(int score) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 350, 450, 300);
		setSize(300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblGameOver = new JLabel("");
		lblGameOver.setIcon(new ImageIcon(ICON_GAME_OVER));
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setForeground(Color.RED);
		lblGameOver.setFont(new Font("Tahoma", Font.BOLD, 40));
		contentPane.add(lblGameOver);
		
		JLabel scorelbl = new JLabel("Ваш счет: " + score);
		scorelbl.setHorizontalAlignment(SwingConstants.CENTER);
		scorelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(scorelbl);
		
		JButton newGameButton = new JButton("Начать заного");
		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Lines lines = new Lines();
				dispose();
			}
		});
		contentPane.add(newGameButton);
		
		JButton closeButton = new JButton("Закрыть");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton backMenu = new JButton("Назад в меню");
		backMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameMenu menu = new GameMenu();
				dispose();
			}
		});
		contentPane.add(backMenu);
		contentPane.add(closeButton);
		setVisible(true);
	}
}