import javax.swing.*;
import java.awt.*;

//생명력을 나타내는 하트
class Heart {
    int index;
    Image heart1 = new ImageIcon(getClass().getClassLoader().getResource("res/HEART.png")).getImage(); //하트
    Heart(int i) {
        index = i;
    }

    public void drawH(Graphics g) {
        g.drawImage(heart1, 30 + index * 30, 950, 27, 21, null);
    }
}
