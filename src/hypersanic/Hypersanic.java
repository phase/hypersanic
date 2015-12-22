package hypersanic;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Hypersanic extends JFrame {
    public static int WIDTH = 1080, HEIGHT = 720;
    public static Character player;
    public static Map map;
    public static Graphics graphics;
    public static Hypersanic instance;
    //talking
    public static boolean talking = true;
    public static Character talker = null;
    public static BufferedImage TALK_BACKGROUND;
    public static BufferedImage UNKNOWN;

    public static void main(String[] args) {
        try {
            instance = new Hypersanic();
            graphics = instance.getGraphics();
            while (true) {
                instance.update(graphics);
                Thread.sleep(100);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hypersanic() throws Exception {
        UNKNOWN = ImageIO.read(new File("res/unknown.png"));
        TALK_BACKGROUND = ImageIO.read(new File("res/talk.png"));
        player = new Character(ImageIO.read(new File("res/sanic.png")));
        Map.Screen[][] screens = new Map.Screen[16][16];
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                try {
                    screens[y][x] = new Map.Screen(ImageIO
                            .read(new File("res/map" + Integer.toString(y, 16) + Integer.toString(x, 16) + ".png")));
                }
                catch (Exception e) {}
            }
        }
        map = new Map(screens);
        // frame
        setTitle("hypre sanic 20xx edison");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setFocusable(true);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (talking) return;
                if (e.getKeyCode() == KeyEvent.VK_UP) player.moveUp = true;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) player.moveDown = true;
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.moveLeft = true;
                    player.facingLeft = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.moveRight = true;
                    player.facingLeft = false;
                }
            }

            @Override public void keyReleased(KeyEvent e) {
                if (talking) return;
                if (e.getKeyCode() == KeyEvent.VK_UP) player.moveUp = false;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) player.moveDown = false;
                if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveLeft = false;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveRight = false;
            }
        });
    }

    @Override public void update(Graphics g) {
        paint(g);
        if (!talking) {
            if (player.moveUp) player.move(0, -60);
            if (player.moveDown) player.move(0, 60);
            if (player.moveLeft) player.move(-60, 0);
            if (player.moveRight) player.move(60, 0);
        }
    }

    @Override public void paint(Graphics g) {
        // System.out.println("drawing");
        map.getCurrentScreen().draw(g);
        player.draw(g);
        if (talking) {
            BufferedImage t = talker == null ? UNKNOWN : talker.img;
            g.drawImage(TALK_BACKGROUND, 0, 0, null);
            g.drawImage(t, 0, 0, null);
        }
    }
}
