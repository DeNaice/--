package Controller;
//import IView.View;

import Model.Model;
import Model.Ball;
import Model.Rectangle;
import View.IView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static processing.core.PApplet.arrayCopy;

public class Controller {
    int width, height;
    IView view;
    Model model;
    GameState state;
    Ball ball;
    Rectangle rec;
    Rectangle rec2;
    public int move = 0;

    public Controller(IView view, int width, int height) {
        this.state = GameState.TITLE_SCREEN;
        this.height = height;
        this.width = width;
        this.view = view;
        model = new Model(width, height);
        ball = new Ball(this.width / 2, this.height / 2, 15, ((int) (Math.random() * 9 + 1) - 5), ((int) (Math.random() * 9 + 1) - 5));
        rec = new Rectangle(15, this.height / 2, 15, 100, 10);
        rec2 = new Rectangle(this.width - 30, this.height / 2, 15, 100, 10);
        while (ball.vx == 0) {
            ball.vx = (int) (Math.random() * 9 + 1) - 5;
        }
        while (ball.vy == 0) {
            ball.vy = (int) (Math.random() * 9 + 1) - 5;
        }
    }

    public void nextFrame(PrintWriter writer, BufferedReader reader, boolean server) throws IOException {
        if (server) { // SERVER
            String line = "";
            line = reader.readLine(); // Input von Client empfangen
            // 0 wenn kein Input erfolgt ist, wird jeden Frame gesendet
            switch (line) {
                case "1" ->{
                    System.out.println("UP");
                    userInput("UP");
                }

                case "2" -> {
                    System.out.println("DOWN");
                    userInput("DOWN");
                }

            }
            writer.println( // Zustand an Client schicken
                "" + rec.y +
                "," + rec2.y +
                "," + ball.x +
                "," + ball.y +
                "," + model.score[0] +
                "," + model.score[1] +
                "," + state
            );
        } else { // CLIENT
            writer.println(move); // Eingabe oder 0 an Server schicken
            move = 0;
            String line = "";
            line = reader.readLine(); // Spiel empfangen
            String[] game = line.split(","); // Spiel entschlüsseln
            rec.y =Integer.parseInt(game[0]);
            rec2.y =Integer.parseInt(game[1]);
            ball.x =Integer.parseInt(game[2]);
            ball.y =Integer.parseInt(game[3]);
            model.score[0] =Integer.parseInt(game[4]);
            model.score[1] =Integer.parseInt(game[5]);
            state = GameState.valueOf(game[6]);
        }


        switch (state) {
            case TITLE_SCREEN -> {
                view.drawTitleScreen();
            }
            case GAME -> {
                boolean scored = model.moveBall(ball, rec, rec2);
                if (scored) {
                    ball.x = width / 2;
                    ball.y = height / 2;
                    ball.vx = 0;
                    ball.vy = 0;
                    while (ball.vx == 0) {
                        ball.vx = (int) (Math.random() * 9 + 1) - 5;
                    }
                    while (ball.vy == 0) {
                        ball.vy = (int) (Math.random() * 9 + 1) - 5;
                    }
                }
                view.drawGame(rec, rec2, ball, model.score);
            }
            case GAME_SCORE -> {
                view.drawScore(model.score);
            }
        }
    }

    public void userInput(String direction) {
        switch (state) {
            case TITLE_SCREEN -> {
                state = GameState.GAME;
            }
            case GAME -> {

                switch (direction) {
                    case "W" -> {
                        rec.y = rec.y - rec.vy;
                        rec.y = Math.max(rec.y, 0);
                    }
                    case "S" -> {
                        rec.y = rec.y + rec.vy;
                        rec.y = Math.min(rec.y, height - rec.sizey);
                    }
                    case "UP" -> {
                        rec2.y = rec2.y - rec2.vy;
                        rec2.y = Math.max(rec2.y, 0);
                    }
                    case "DOWN" -> {
                        rec2.y = rec2.y + rec2.vy;
                        rec2.y = Math.min(rec2.y, height - rec2.sizey);
                    }
                }

            }
        }
    }
}

