package lines;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GameSettings extends JFrame {
	
	public static final String PATH = "resources/";
	
	public static final String FILE_NAME = "confix.txt";

	private static final long serialVersionUID = 3838741218318378650L;
	
	private JPanel contentPane;
	
	private JSpinner speedBoll;
	
	private JCheckBox musicBox;
	
	private JCheckBox soundBox;
	
	public static void main(String[] args) {
		GameSettings settings = new GameSettings();
	}

	public GameSettings() {
		
		Settings settings = SettingsFile.read();
		
		Thread soundSetting = new Thread(new sound.Settings());
		soundSetting.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 300, 450, 300);
		setSize(370,200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblSpeedBoll = new JLabel("Скорость перемещения шара");
		lblSpeedBoll.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblSpeedBoll);
		
		speedBoll = new JSpinner();
		speedBoll.setModel(new SpinnerNumberModel(settings.getDelay(), 150, 250, 10));
		panel.add(speedBoll);
		
		JLabel lblNewLabel_1 = new JLabel("Музыка");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		musicBox = new JCheckBox("Вкл/Выкл");
		musicBox.setSelected(settings.isMusic());
		panel.add(musicBox);
		
		JLabel lblNewLabel_2 = new JLabel("Звуки");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		soundBox = new JCheckBox("Вкл/Выкл");
		soundBox.setSelected(settings.isSound());
		panel.add(soundBox);
		
		JLabel lblNewLabel_3 = new JLabel("");
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("");
		panel.add(lblNewLabel_5);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton saveButton = new JButton("Сохранить");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int delay = (int) speedBoll.getValue();
				boolean isMusicEnabled =  musicBox.isSelected();
				boolean isSoundEnabled = soundBox.isSelected();
				
				Settings settings = new Settings();
				settings.setDelay(delay);
				settings.setMusic(isMusicEnabled);
				settings.setSound(isSoundEnabled);
				
				SettingsFile.write(settings);
				
				StartGame menu = new StartGame();
				dispose();
				
			}
		});
		panel_1.add(saveButton);
		
		JButton button = new JButton("Назад в меню");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartGame menu = new StartGame();
				dispose();
			}
		});
		panel_1.add(button);
		
		setResizable(false);
		setVisible(true);
	}

}