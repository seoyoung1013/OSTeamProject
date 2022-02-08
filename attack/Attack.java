package attack;

import java.awt.*;

public abstract class Attack{
	int x, y;
	private int width, height;
	private Color color;
	
	public Attack(int x, int y, int width, int height, int gap, Color color) {
		this.x = x+(gap/2)-(width/2);
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	// getter
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	// setter
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	abstract public void move();
}