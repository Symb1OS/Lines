package lines;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Test extends JFrame{

	Test(){
		super("Test");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new ScoreGame());
		setSize(200, 200);
		setResizable(false);
		setVisible(true);
	}
	
	synchronized public static void stringOut(){
		System.out.println("");
	}
	
	public static void main(String[] args) {
		//Test test = new Test(); 
		
		OutString outThread = new OutString();
		outThread.start();
		
		while(true){
			System.out.println("Main Thread");
		}

	}

}

class  OutString extends Thread{

	
	public void run() {
		while (true) {
			System.out.println("OutThread");
		}
	}
	
}