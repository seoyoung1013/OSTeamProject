import javax.swing.*;
import java.awt.*;

class SubPlane extends ImageIcon {
    int x;
    int y;
    int w;
    int h;
    Image sub = new ImageIcon(getClass().getClassLoader().getResource("res/sub.png")).getImage(); //서브 비행기
    SubPlane(int posX, int posY) {
        x = posX;
        y = posY;
        w = 30;
        h = 30;
    }

    public void draw(Graphics g) { //서브 비행기 위치 설정
        g.drawImage(sub, x - 35, y + 10, w, h, null);
        g.drawImage(sub, x + 55, y + 10, w, h, null);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}