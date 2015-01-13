package lines;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ScoreLabel extends JLabel {
	
	private static final long serialVersionUID = -7878885898991769742L;

	public ScoreLabel(Timer timer, int score){
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
		this.score = score;
	}
	
	private int score;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	private TimerTask timerTask = new TimerTask() {
		private volatile int time = -1;

		private Runnable refresher = new Runnable() {
			@Override
			public void run() {
				int t = time;
				ScoreLabel.this.setText(String.valueOf(getScore()));
			}
		};

		@Override
		public void run() {
			time++;
			SwingUtilities.invokeLater(refresher);
		}
	};
	
}