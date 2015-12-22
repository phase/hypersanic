package hypersanic;

import java.awt.*;
import java.awt.image.*;

class Map {
    static Color WATER = new Color(0, 148, 255);
    Screen[][] screens;
    int screenX = 0, screenY = 0;

    public Map(Screen[][] screens) {
        this.screens = screens;
    }

    public Screen getCurrentScreen() {
        return screens[screenY][screenX];
    }

    public void checkBounds(Character c) {
        Screen s = getCurrentScreen();
        // left x
        if (c.x < 0 && screenX != 0) {
            screenX--;
            c.x = s.getWidth() - 20;
            c.facingLeft = true;
        }
        else if (c.x < 0) {
            c.x = 20;
        }
        // right x
        if (c.x > s.getWidth() && screens[screenY][screenX+1] != null) {
            screenX++;
            c.x = 20;
            c.facingLeft = false;
        }
        else if (c.x > s.getWidth()) {
            c.x = s.getWidth() - 20;
        }
        // bottom y
        if (c.y < 0 && screenY != 0) {
            screenY--;
            c.y = s.getHeight() - 20;
        }
        else if (c.y < 0) {
            c.y = 20;
        }
        // top y
        if (c.y > s.getHeight() && screens[screenY+1][screenX] != null) {
            screenY++;
            c.y = 20;
        }
        else if (c.y > s.getHeight()) {
            c.y = s.getHeight() - 20;
        }
    }

    public Color getColor(int x, int y) {
        Color c = new Color(0, 0, 0);
        try {
            c = new Color(getCurrentScreen().img.getRGB(x, y));
        }
        catch (Exception e) {}
        return c;
    }

    static class Screen {
        boolean selected = false;
        BufferedImage img;

        public Screen(BufferedImage img) {
            this.img = img;
        }

        public int getWidth() {
            return img.getWidth() > Hypersanic.WIDTH ? Hypersanic.WIDTH : img.getWidth();
        }

        public int getHeight() {
            return img.getHeight() > Hypersanic.HEIGHT ? Hypersanic.HEIGHT : img.getHeight();
        }

        public void select() {
            this.selected = true;
        }

        public void draw(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }
}