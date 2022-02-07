import java.awt.*;

public class Item {
    private int x, y;
    private int width = 30, height =30, type;
    private Image image;

    public Item(int x, int y, int type, Image image){
        this.x = x;
        this.y = y;
        this.type = type;
        this.image = image;
    }

    // getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getType() {
        return type;
    }
    public Image getImage() {
        return image;
    }
    public int getXI() {
        return this.x+10;
    }
    public int getYI() {
        return this.y+10;
    }


    // setter
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    
    public void draw(Graphics g){
        g.drawImage(image, this.x, this.y, this.width, this.height, null);
    }
    public void move(){ this.y++; }
    public double distance(int dx, int dy) {
        double nx = Math.pow( (this.x + this.width / 2) - dx, 2);
        double ny = Math.pow( (this.y + this.height / 2) - dy, 2);
        return Math.sqrt(nx + ny);
    }
}
