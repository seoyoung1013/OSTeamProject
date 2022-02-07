package monsters;

import java.awt.*;

public class Monster1 extends Monster{
    //  int pX, pY;
    //	int width = 30, height = 30, HP1 = 5;
    // int attackWidth = 2, attackHeight = 20;

    public Monster1(int x, int y, Image image){
        super(x, y, 5, 1, 30, 30, image);
    }

    @Override
    public void move() {
        this.setY(this.getY() + Monster.MOVE_Y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }
}
