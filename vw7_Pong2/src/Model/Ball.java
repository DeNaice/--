package Model;

public class Ball {
    public int x;
    public int y;
    public int size;
   public int vx;
    public int vy;

    public Ball(int x, int y, int size, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.vx = vx;
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
