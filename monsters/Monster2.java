package monsters;

import java.awt.*;

// 오른쪽에서 왼쪽 대각선 움직임
public class Monster2 extends Monster{
    // int pX, pY;
    // int width = 36, height = 36, HP2 = 5;
    public Monster2(int x, int y, Image image){
        super(x, y, 5, 2, 36, 36, image);
    }

    @Override
    public void move() {
        this.setX( this.getX() - Monster.MOVE_X);
        this.setY( this.getY() - Monster.MOVE_Y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }
}
