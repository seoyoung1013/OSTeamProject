package monster;

import java.awt.*;

public class Monster3 extends Monster{
    // int pX,  pY;
    // int width = 45, height = 45, HP3 = 10;

    public Monster3(int x, int y, Image image){
        super(x, y, 10, 45, 45, image);
    }

    @Override
    public void move() {
        this.setX( this.getX() + Monster.MOVE_X );
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }
}
