package View;
import Model.Ball;
import Model.Rectangle;
import processing.core.PApplet;
import Controller.Controller;
import processing.core.PImage;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends PApplet implements IView{
    PImage titleScreen;
    Controller controller;
    public Socket socket = null;
    public ServerSocket server = null;
    public BufferedReader reader = null;
    public PrintWriter writer = null;
    boolean isServer;

    public static void main(String args[]){
        PApplet.main(Server.class);
    }
    public Client() {
        setSize(600, 300);
    }
    public void settings(){

    }
    public void setup() {
        this.controller = new Controller(this, width, height);
        titleScreen = loadImage("TitleScreen.png");

        try {
            connectClient();
            isServer = false;
        } catch (IOException e) {
            try {
                startServer();
                isServer = true;
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
//            throw new RuntimeException(e);
        }


    }

    public void startServer () throws IOException{

        int port = 8080;

        server = new ServerSocket(port);

        socket = server.accept();
        String line = "";
        while (!line.equals("CONNECTED")) {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            line = reader.readLine();
            System.out.println("RECEIVED:" + line);
        }
        writer = new PrintWriter(socket.getOutputStream(), true);

    }
    public void connectClient() throws IOException {

        String ip = "localhost";
        int port = 8080;
        socket = new Socket(ip, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("CONNECTED");
        System.out.println("CONNECTED");
        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

    }

    public void draw(){
        try {
            controller.nextFrame(writer, reader, isServer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void drawGame(Rectangle rec, Rectangle rec2, Ball ball, int[] score){
        background(255);
        circle(ball.x, ball.y, ball.size);
        rect(15,  rec.y, 15, 100);
        rect(width-30, rec2.y, 15, 100);
        textSize(50);
        fill(0,0,0);
        text(score[0], width/4, 35);
        text(score[1], width/4*3, 35);
    }
    public void drawTitleScreen(){
        background(255);
        image(titleScreen, 0, 0,width, height);
    }

    public void drawScore(int score[]) {
        background(255);

    }
    public void keyPressed() {

        if (CODED == 65535) {
            switch (keyCode) {
                case KeyEvent.VK_W:
                    controller.userInput("W");
                    break;
                case KeyEvent.VK_S:
                    controller.userInput("S");
                    break;

                case KeyEvent.VK_UP:
                    // move im Controller setzen, wird mit dem nächsten Frame geschickt
                    controller.move = 1;
                    controller.userInput("UP");
                    break;
                case KeyEvent.VK_DOWN:
                    // move im Controller setzen, wird mit dem nächsten Frame geschickt
                    controller.move = 2;
                    controller.userInput("DOWN");
            }
        }
    }
}
