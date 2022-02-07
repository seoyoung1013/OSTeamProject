package monsters;

import java.awt.*;

public class Monster4 extends Monster{
    //		int x, y;
    //		int width = 50,  height = 50, HP4 = 10;

    public Monster4(int x, int y, Image image){
        super(x, y, 10, 4, 50, 50, image);
    }

    public void drawAM(Graphics g2) {
        g2.setColor(Color.red);
        g2.fillRect(this.getX()+24,this.getY()+50,2,10);
    }

    @Override
    public void move() {
        this.setX(this.getX() + Monster.MOVE_X);
        this.setY(this.getY() + Monster.MOVE_Y);
    }

    @Override
    public void draw(Graphics g) {
        // 패널에 그리는 것 연관
        g.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }
}
