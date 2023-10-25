package Pong;
import java.awt.Rectangle;
import java.awt.Color;

public class Paddle extends Rectangle {

    Color color;
    int vy = 5;

    Paddle(int x, int y, int width, int h) {
        this.x = x;
        this.y = y;
        this.width = width;
        height = h;
        color = Color.WHITE;
    }
}