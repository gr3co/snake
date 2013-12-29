package co.sgre.snake.entities;

import java.awt.Color;
import java.awt.Graphics;

public class Apple {

	private int xCoor, yCoor, width, height;
	
	public Apple(){
		this(10,10,10);
	}
	
	public Apple(int xCor, int yCor, int size){
		this.xCoor = xCor;
		this.yCoor = yCor;
		this.width = size;
		this.height = size;
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect(xCoor * width, yCoor * width, width, height );
		g.setColor(Color.red);
		g.fillRect(xCoor * width + 2, yCoor * width + 2, width - 4, height - 4);
	}
	
}
