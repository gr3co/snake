package co.sgre.snake.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import co.sgre.snake.entities.*;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800, HEIGHT = 800, TILE = 20;
	private int appleX, appleY;
	private Thread thread;
	private Random r;
	private ArrayList<BodyPart> snake;
	private Apple apple;
	
	/*
	 * initial condition: snake starts at (5,5) with 3 segments facing right
	 */
	private boolean running = false, paused = true;
	private int headX = 5, headY = 5, size = 3, hiscore = 0, direction = 1;

	public Screen(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		Listener l  = new Listener();
		addKeyListener(l);
		snake = new ArrayList<BodyPart>();
		BodyPart b = new BodyPart(headX,headY,TILE);
		snake.add(b);
		r = new Random();
		appleX = r.nextInt(WIDTH / TILE);
		appleY = r.nextInt(HEIGHT / TILE);
		apple = new Apple(appleX, appleY, TILE);
		start();
	}
	
	public void tick(){
		
		//update location
		switch(direction){
		case 0: 
			//up
			headY--;
			break;
		case 1: 
			//right
			headX++;
			break;
		case 2: 
			//down
			headY++;
			break;
		case 3:
			//left
			headX--;
		default:
			break;
		}

		//update snake
		BodyPart b = new BodyPart(headX, headY, TILE);
		while (snake.size() >= size){
			snake.remove(0);
		}
		snake.add(b);

		//check apple collision
		if (headX == appleX && headY == appleY){
			size++;
			hiscore = hiscore > size - 3 ? hiscore : size - 3;
			appleX = r.nextInt(WIDTH / TILE);
			appleY = r.nextInt(HEIGHT / TILE);
			apple = new Apple(appleX, appleY, TILE);
		}

		//out of bounds
		if (headX < 0 || headX >= WIDTH / TILE ||
				headY < 0 || headY >= HEIGHT / TILE){
			stop();
		}

		//collision detection
		for (int i = 0; i < snake.size() - 1; i++){
			if (snake.get(i).getxCoor() == headX 
					&& snake.get(i).getyCoor() == headY){
				stop();
			}
		}

	}

	public void paint(Graphics g){

		g.setColor(Color.green);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		if (paused){
			g.setColor(Color.white);
			g.setFont(new Font("Arial",Font.BOLD,48));
			g.drawString("press space to continue", 100, HEIGHT / 2);
			g.setFont(new Font("Arial",Font.BOLD,24));
			g.drawString("controls: WASD or arrow keys", 100, HEIGHT / 2 + 50);
		}

		else{
			apple.draw(g);
			for (int i = 0; i < snake.size(); i++){
				snake.get(i).draw(g);
			}
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,16));
		g.drawString("Score: "+(size - 3), WIDTH - 80, 20);
		g.drawString("High Score: "+hiscore, WIDTH - 120, 40);
	}

	public void start(){
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void stop(){
		running = false;
	}

	public void restart(){
		size = 3;
		headX = 5;
		headY = 5;
		direction = 1;
		appleX = r.nextInt(WIDTH / TILE);
		appleY = r.nextInt(HEIGHT / TILE);
		apple = new Apple(appleX, appleY, TILE);
		paused = true;
		start();
	}

	public void run(){
		while(running){
			repaint();
			if (!paused){
				tick();
			}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				continue;
			}
		}

		//when game is over, restart
		restart();
	}

	private class Listener implements KeyListener{

		public void keyPressed(KeyEvent e) {
			int d = e.getKeyCode();
			if (!paused){
				if ((d == KeyEvent.VK_UP || d == KeyEvent.VK_W) && direction != 2){
					direction = 0;
				}
				if ((d == KeyEvent.VK_RIGHT || d == KeyEvent.VK_D) && direction != 3){
					direction = 1;
				}
				if ((d == KeyEvent.VK_DOWN || d == KeyEvent.VK_S) && direction != 0){
					direction = 2;
				}
				if ((d == KeyEvent.VK_LEFT || d == KeyEvent.VK_A) && direction != 1){
					direction = 3;
				}
			}
			if (d == KeyEvent.VK_SPACE || d == KeyEvent.VK_P){
				paused = !paused;
			}
		}

		public void keyReleased(KeyEvent e) {
			return;
		}

		public void keyTyped(KeyEvent e) {
			return;
		}

	}
}
