package lines;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TimerLabel extends JLabel {
	
	private static final long serialVersionUID = -8722490200131120913L;

	public TimerLabel(Timer timer) {
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	private TimerTask timerTask = new TimerTask() {
		private volatile int time = -1;

		private Runnable refresher = new Runnable() {
			@Override
			public void run() {
				int t = time;
				TimerLabel.this.setText(String.format("%02d:%02d", t / 60,
						t % 60));
			}
		};

		@Override
		public void run() {
			time++;
			SwingUtilities.invokeLater(refresher);
		}
	};

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel timerLabel = new TimerLabel(new Timer());
		timerLabel.setFont(new Font(timerLabel.getFont().getFontName(),
				timerLabel.getFont().getStyle(), 36));
		frame.add(timerLabel);
		frame.pack();
		frame.setVisible(true);
	}
}