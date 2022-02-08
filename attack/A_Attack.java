package attack;

import java.awt.Color;

public class A_Attack extends Attack {

	public A_Attack(int x, int y, int width, int height, int gap, Color color) {
		super(x, y, width, height, gap, color);
	}

	@Override
	public void move() {
		this.setY(this.getY() - 15);
	}
}
