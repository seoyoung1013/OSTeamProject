import java.awt.*;

public class Attack {
    private int x, y;
    private int width, height, speed;
    private Color color;

    public Attack(int x, int y, int width, int height, int speed, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    //getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //setter
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void moveA(){
        this.y += this.speed;
    }
    public void drawMA(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}