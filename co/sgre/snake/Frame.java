package co.sgre.snake;

import java.awt.GridLayout;
import javax.swing.JFrame;
import co.sgre.snake.graphics.Screen;

public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;

	public Frame(){
		super("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridLayout(1,1,0,0));
		Screen s = new Screen();
		add(s);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Frame();
	}

}
