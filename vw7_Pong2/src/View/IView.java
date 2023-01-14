package View;

import Model.Ball;
import Model.Rectangle;

public interface IView {
    public void drawGame(Rectangle rec, Rectangle rec2, Ball ball, int[] score);

    public void drawScore(int score[]);

    public void drawTitleScreen();

}