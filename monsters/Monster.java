package monsters;

import java.awt.*;

public abstract class Monster {
    final static int MOVE_X = 2; //몬스터 x축 움직임
    final static int MOVE_Y = 3; //몬스터 y축 움직임

    private int x, y;
    private int width, height, hp, type;
    private Image image;

    public Monster(int x, int y, int hp, int type, int width, int height, Image image){
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.type = type;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    //getter
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getHp() { return hp; }
    public int getType() {
        return type;
    }
    public Image getImage() {
        return image;
    }

    //setter
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setHp(int hp) { this.hp = hp; }
    public void setType(int type) {
        this.type = type;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    // 몬스터들이 구현해야하는 메소드
    abstract public void move();
    abstract public void draw(Graphics g);

    public void reduceHp(){ this.hp--; }
    public double distance(int dx, int dy){
        double nx = Math.pow( (this.x + this.width/ 2) - dx, 2);
        double ny = Math.pow( (this.y + this.height / 2) - dy, 2);
        return Math.sqrt(nx + ny);
    }
}
