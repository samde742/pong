package Pong;
import java.awt.Rectangle;
import java.awt.Color;

public class PongBall extends Rectangle {
    Color color;
    int  vx= 5, vy=5;

    PongBall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = height;
        this.height = height;
        color = Color.WHITE;
    }
}  