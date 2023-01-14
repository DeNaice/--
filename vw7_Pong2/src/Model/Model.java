package Model;
import Model.Ball;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class Model {
    int rec1y;
    int rec2y;
    int ballx;
    int bally;
    Ball ball;
    int width;
    int height;
    public int[] score= new int[2];
    public Model(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public boolean moveBall(Ball ball, Rectangle rec1, Rectangle rec2){
        int distx, disty, newx, newy;

        if ((ball.y + ball.vy) > (height-ball.size/2) || (ball.y + ball.vy) < 0+ball.size/2) {
            ball.vy = ball.vy - 2*ball.vy;
        }

        if ((ball.x + ball.vx) > (width-ball.size/2)) { // rechts
//            System.out.println("Rechts");
            distx = (ball.x + ball.vx) - (width-ball.size/2); // Distanz außerhalb
            score[0] = score[0]+1;
            return true;
        } else if ((ball.x + ball.vx) < (0 + ball.size/2)) { // links
//            System.out.println("Links");
            distx = (0 + ball.size/2) - (ball.x + ball.vx); // Distanz außerhalb
            score[1] = score[1]+1;
            return true;
        } else { // Keine Kollision
//            System.out.println("Else");

//            System.out.println((ball.x + ball.vx));
//            System.out.println((rec2.x-ball.size/2));
//            System.out.println((rec2.y > ball.y));
//            System.out.println((rec2.y+rec2.sizey < ball.y));
//            System.out.println(rec2.y);
//            System.out.println(ball.y);
//            System.out.println((ball.y+(ball.size/2) > rec2.y && ball.y-(ball.size/2)< rec2.y+ rec2.sizey));
//            if ((ball.x + ball.vx) > (rec2.x-ball.size/2) && (rec2.y > ball.y) && (rec2.y+rec2.sizey < ball.y)) { // rechts
                if( (ball.y+(ball.size/2) > rec2.y && ball.y-(ball.size/2)< rec2.y+ rec2.sizey) && (ball.x+ball.size/2+ ball.vx > rec2.x)) {
//                    System.out.println("Rechts rect");
//                    System.out.println((ball.x + ball.vx));
//                    System.out.println((rec2.x - ball.size / 2));
//                    System.out.println(rec2.x);
                    distx = (ball.x + ball.vx) - (rec2.x - ball.size / 2); // Distanz außerhalb
                    newx = min(ball.x + ball.vx - (2 * distx), rec2.x-ball.size/2); // Neue X Position mit Distanz außerhalb als Entfernung zum Rand
                    ball.vx = ball.vx - (2 * ball.vx); // Geschwindigkeit umkehren
//            } else if ((ball.x + ball.vx) < (rec1.x + rec1.sizex + ball.size/2) && (rec1.y > ball.y) && (rec1.y+rec1.sizey < ball.y)) { // links
                }else if ((ball.y+(ball.size/2) > rec1.y && ball.y-(ball.size/2)< rec1.y+ rec1.sizey) && (ball.x-ball.size/2+ ball.vx < rec1.x + rec1.sizex)){
//                System.out.println("Links rect");
                distx = (rec1.x+rec1.sizex + ball.size/2) - (ball.x + ball.vx); // Distanz außerhalb
                newx = max(ball.x + ball.vx + (2 * distx), rec1.x+rec1.sizex+ball.size/2); // Neue X Position mit Distanz außerhalb als Entfernung zum Rand
                ball.vx = ball.vx - (2 * ball.vx); // Geschwindigkeit umkehren
            } else { // Keine Kollision
//                System.out.println("Else X");
                newx = ball.x + ball.vx; // Neue Position
            }
        }

        if ((ball.y + ball.vy) > (height-ball.size/2)) { // unten
//            System.out.println("Unten");
            disty = (ball.y + ball.vy) - (height-ball.size/2); // Distanz außerhalb
            newy = ball.y + ball.vy - (2 * disty); // Neue Y Position mit Distanz außerhalb als Entfernung zum Rand
            ball.vy = ball.vy - (2 * ball.vy); // Geschwindigkeit umkehren
        } else if ((ball.y + ball.vy) < (0 + ball.size/2)) { // oben
//            System.out.println("Oben");
            disty = (0 + ball.size/2) - (ball.y + ball.vy); // Distanz außerhalb
            newy = ball.y + ball.vy + (2 * disty); // Neue Y Position mit Distanz außerhalb als Entfernung zum Rand
            ball.vy = ball.vy - (2 * ball.vy); // Geschwindigkeit umkehren
        } else { // Keine Kollision
//            System.out.println("Else y");
            newy = ball.y + ball.vy; // Neue Position
        }

        ball.move(newx, newy);
        return false;
    }
}

