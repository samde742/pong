package Pong;

import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Font;

public class PongGame {
    public static void main(String[] args) {
        new PongGame().runGame();
    }


    final static int WINW = 1000;
    final static int WINH = 800;
    GraphicsConsole gc = new GraphicsConsole(WINW, WINH);
    Paddle p1,p2;
    PongBall b;
    int points1=0, points2=0;
    Color white1 = (new Color(255,255,255));
    Color white2 = (new Color(255,255,255,200));
    boolean isDeflected = false;
    boolean serve1;
    Font points = new Font("Comic Sans MS", Font.BOLD, 50);

    PongGame() {
        gc.setLocationRelativeTo(null); 
        gc.setAntiAlias(true);
        gc.setTitle("Dollar Store Pong");
        gc.setBackgroundColor(new Color(0,0,50));
        gc.setColor(Color.WHITE);
        gc.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        gc.clear();
        
    }

    void runGame() {
        // creates ball in random pos
        b = new PongBall(200 + (int)(Math.random() *400), 100 + (int)(Math.random() * 600), 40,40);
        p1 = new Paddle(50, 300, 10, 100);
        p2 = new Paddle(WINW-50, 300, 10, 100);

        //chooses who to serve to
        if (serve1) b.vx *= -1;

        // draws graphics then waits 3 seconds
        drawGraphics();
        if (points1 < 3 && points2 < 3) gc.sleep(3000);

        // main game loop
        while(points1 < 3 && points2 < 3) {
            movePaddles();
            paddleCollide();
            updatePoints();
            moveBall();
            drawGraphics();
            gc.sleep(5);
        }
        
        // end screen
        gc.setColor(Color.RED);
        if (points1 == 3) gc.drawString("Player 1 Wins! Press space to play again.", 100, 300);
        else gc.drawString("Player 2 Wins! Press space to play again.", 100, 300);

        // play again loop
        while (true) {
            if (gc.getKeyCode() == ' ') {
                points1 = 0;
                points2 = 0;
                runGame();
            }
            else if (gc.isKeyDown(81)) gc.close();    
        }
    }

     void updatePoints(){
        if (b.x <= 0){
            points2++;
            serve1 = true; // choses who to serve to
            drawGraphics();
            runGame(); // reruns game with ball in new random pos 
        }
        else if (b.x >= WINW-b.width) {
            points1++;
            drawGraphics();
            runGame(); //reruns game
        }
    }
  
    void drawGraphics() {
        synchronized(gc) {
            gc.clear();
            
            // draw mid line
            gc.setColor(white1);
            gc.drawLine(500, 0, 500, WINH);

            // draw border
            gc.setColor(white2);
            gc.setStroke(10);
            gc.drawLine(0, 0, 1000, 0);
            gc.drawLine(1000, 0, 1000, 800);
            gc.drawLine(1000, 800, 0, 800);
            gc.drawLine(0, 0, 0, 800);

            // draw points
            
            gc.drawString("" + points1, 350, 100);
            gc.drawString("" + points2, 600, 100);

            // draw ball and paddles
            gc.drawOval(b.x, b.y, b.width, b.height);
            gc.fillRect(p1.x, p1.y, p1.width, p1.height);
            gc.fillRect(p2.x, p2.y, p2.width, p2.height);
            
        }
    }

	void movePaddles() { 

        // arrow keys
        if (gc.isKeyDown(38) && p2.y > 0) p2.y -= p2.vy;
        if (gc.isKeyDown(40) && p2.y < WINH-p2.height) p2.y += p2.vy;


        // w and s key        
        if (gc.isKeyDown('W') && p1.y > 0) p1.y -= p1.vy;
        if (gc.isKeyDown('S') && p1.y < WINH-p2.height) p1.y += p1.vy;
    }

    void moveBall() {
        b.x += b.vx;
        b.y += b.vy;

        // top and bottom borders
        if (b.y+b.height >= WINH) b.vy *= -1;
        if (b.y <= 0) b.vy *= -1; 
    }

    void paddleCollide() {

        // ball with paddle 1
        if (b.intersects(p1) && !isDeflected) {
            b.vx *= -1;
            isDeflected = true;

        }

        // ball with paddle 2
        else if (b.intersects(p2) && !isDeflected) {
            b.vx *= -1;
            isDeflected = true;
        }
        
        // ball is colliding until it no longer is colliding with the paddle, isDeflected is false
        if (!b.intersects(p1) && !b.intersects(p2)) {
            isDeflected = false;
        }
    }

}