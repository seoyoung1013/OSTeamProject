package attack;

import java.awt.Color;

public class Attack1 extends Attack{

	public Attack1(int x, int y, int width, int height, int gap, Color color) {
		super(x, y, width, height, gap, color);
	}

	@Override
	public void move() {
		this.setY(this.getY() + 15);
	}

}
