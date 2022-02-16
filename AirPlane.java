import javax.swing.*;
import java.awt.*;

class AirPlane extends ImageIcon {
    int x;
    int y;
    int w;
    int h;

    int moveX = 10;
    int moveY = 10;
    Image player = new ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage(); //플레이어
    AirPlane(int posX, int posY,int PLANE_WIDTH,int PLANE_HEIGHT) {
        x = posX;
        y = posY;
        w = PLANE_WIDTH;
        h = PLANE_HEIGHT;
    }

    public void draw(Graphics g) {
        g.drawImage(player, x, y, w, h, null);
    }

    public void moveUP() {
        y -= moveY;
    }

    public void moveDOWN() {
        y += moveY;
    }

    public void moveRIGHT() {
        x += moveX;
    }

    public void moveLEFT() {
        x -= moveX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distance(int x, int y) { //중점과의 거리
        return Math.sqrt(Math.pow((this.x + w / 2) - x, 2) + Math.pow((this.y + h / 2) - y, 2));
    }

}