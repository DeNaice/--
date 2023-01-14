package Model;

public class Rectangle {
    public int x;
    public int y;

    public int sizex;
    public int sizey;
    public int vy;

    public Rectangle(int x, int y, int sizex, int sizey, int vy) {
        this.x = x;
        this.y = y;
        this.sizex = sizex;
        this.sizey = sizey;
        this.vy = vy;
    }
    /**void draw () {
     circle(x, y, size);
     }**/

    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
