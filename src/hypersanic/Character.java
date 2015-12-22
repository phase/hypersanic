package hypersanic;

import java.awt.*;
import java.awt.image.*;
import java.util.concurrent.*;

public class Character {
    int x, y;
    int health;
    BufferedImage img;
    boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false, facingLeft = false;

    public Character(BufferedImage img) {
        this.img = img;
        // middle of screen
        x = Hypersanic.WIDTH / 2;
        y = Hypersanic.HEIGHT / 2;
        health = 100;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void kill() {
        health = 0;
    }

    public void draw(Graphics g) {
        int dx = x - (img.getWidth() / 2);
        int dy = y - (img.getHeight() / 2);
        dx += ThreadLocalRandom.current().nextInt(12);
        dy += ThreadLocalRandom.current().nextInt(12);
        if (facingLeft) g.drawImage(img, dx + img.getWidth(), dy, -img.getWidth(), img.getHeight(), null);
        else g.drawImage(img, dx, dy, img.getWidth(), img.getHeight(), null);
    }

    public void move(int x, int y) {
        if (!Hypersanic.map.getColor(this.x + x, this.y).equals(Map.WATER)) this.x += x;
        if (!Hypersanic.map.getColor(this.x, this.y + y).equals(Map.WATER)) this.y += y;
        Hypersanic.map.checkBounds(this);
    }
}
