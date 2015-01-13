package lines;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;

public class ScoreGame extends JPanel {

	/**
	 * Create the panel.
	 */
	public ScoreGame() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton newGameButton = new JButton("Начать заного");
		panel.add(newGameButton);
		
		JButton endGameButton = new JButton("Закрыть игру");
		panel.add(endGameButton);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Ваш счет");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		panel_1.add(lblNewLabel);
		setSize(200, 200);
		setVisible(true);

	}
	
	public static void main(String [] args){
		
		ScoreGame scoreGame = new ScoreGame();
		
		
	}
}
