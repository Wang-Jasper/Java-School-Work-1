/**
 * @Author: Jasper
 * @Desc:
 * @Create: 2023-06-01 20:14
 **/

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
//for the sound
import javax.sound.sampled.*;
//import javax.swing.border.Border;
import java.io.IOException;
import java.net.*;

public class Board extends JFrame
{
    public static int boardWidth = 1000;
    public static int boardHeight = 800;
    public static boolean muted = false;
    public static boolean keyHeld = false;      //if the key is being held
    public static int keyHCode;     //store the key that is being held

    public static ArrayList<Bullet> bullets = new ArrayList<>();

    String thrustFile = "file:./src/sound/thrust.wav";
    String fireFile = "file:./src/sound/fire.wav";

    public static void main(String[] args) {
        Board theBoard = new Board();
        new Menu(theBoard).setVisible(true);
    }

    public Board() {
        this.setTitle("Asteroids");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(boardWidth, boardHeight);
        this.setLocationRelativeTo(null);

        //deal with the key event
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 87) {
                    //System.out.println("Forward");
                    keyHCode = e.getKeyCode();
                    keyHeld = true;
                    if (!Board.muted)
                        Board.playSound(thrustFile);
                } else if (e.getKeyCode() == 68) {
                    //System.out.println("RotateRight");
                    keyHCode = e.getKeyCode();
                    keyHeld = true;
                } else if (e.getKeyCode() == 65) {
                    //System.out.println("RotateLeft");
                    keyHCode = e.getKeyCode();
                    keyHeld = true;
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //System.out.println("shoot");
                    bullets.add(new Bullet(GameDrawingPanel.theShip.getShootXPoint(), GameDrawingPanel.theShip.getShootYPoint(),
                            GameDrawingPanel.theShip.getRotateAngle()));
                    if (!Board.muted)
                        Board.playSound(fireFile);
                }

            }

            //using wasd to move
            // w:87, a:65, d:68
            @Override
            public void keyReleased(KeyEvent e) {
                keyHeld = false;
            }
        });

        GameDrawingPanel gamePanel = new GameDrawingPanel();
        this.add(gamePanel, BorderLayout.CENTER);

        //creates a fixed-sized Thread Pool, means it can run 5 task at the same time
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

        //redraw the gameBoard every 20ms
        executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0L, 20L, TimeUnit.MILLISECONDS);

        this.setVisible(true);
    }

    @SuppressWarnings("deprecation")

    public static void playSound(String soundFile) {

        URL soundLocation;

        try {
            soundLocation = new URL(soundFile);

            Clip clip;
            clip = AudioSystem.getClip();

            AudioInputStream inputStream;

            inputStream = AudioSystem.getAudioInputStream(soundLocation);

            clip.open(inputStream);
            //how many times to loop
            clip.loop(0);
            clip.start();
        } catch (MalformedURLException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setMuted(boolean muted) {
        Board.muted = muted;
    }

    public static boolean getMuted() {
        return Board.muted;
    }
}

//related to Thread
class RepaintTheBoard implements Runnable
{
    Board theBoard;

    public RepaintTheBoard(Board theBoard) {
        this.theBoard = theBoard;
    }

    @Override
    public void run() {
        theBoard.repaint();
    }
}

//using the swing to paint
class GameDrawingPanel extends JComponent
{
    public ArrayList<Rock> rocks = new ArrayList<>();

    public ArrayList<MiddleRock> middleRocks = new ArrayList<>();

    static SpaceShip theShip = new SpaceShip();
    private int score = 0;
    private int wave = 1;

    public GameDrawingPanel() {
        //15 here is the number of the rocks going to draw
        for (int i = 0; i < 15; i++) {
            int rndStaXPos = (int) (Math.random() * (Board.boardWidth - 40) + 1);
            int rndStaYPos = (int) (Math.random() * (Board.boardHeight - 40) + 1);

            rocks.add(new Rock(Rock.getPolyXArr(rndStaXPos), Rock.getPolyYArr(rndStaYPos), 13, rndStaXPos, rndStaYPos));
            Rock.rocks = rocks;
        }
    }

    public void paint(Graphics g) {
        Graphics2D gSetting = (Graphics2D) g;
        AffineTransform identity = new AffineTransform();

        gSetting.setColor(Color.BLACK);
        gSetting.fillRect(0, 0, getWidth(), getHeight());

        //rendering rule
        gSetting.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gSetting.setPaint(Color.WHITE);     //the color of the rock

        g.drawRect(0, 0, 1000, 800);
        g.drawRect(1, 1, 998, 798);
        g.drawRect(2, 2, 996, 796);

        gSetting.setPaint(Color.WHITE);
        gSetting.setFont(new Font("Arial", Font.BOLD, 40));
        gSetting.drawString("Score: " + score + "          " + "Wave: " + wave, 300, 50);


        int numStars = 50;
        int MaxStarSize = 4;

        for (int i = 0; i < numStars; i++) {
            // the random position
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            int size = (int) (Math.random() * MaxStarSize) + 1;

            // draw the star(dot)
            g.fillOval(x, y, size, size);
        }

        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if (rock.onScreen) {
                rock.move(theShip, Board.bullets);
                //gSetting.translate(-rock.(), -rock.getY())
                gSetting.draw(rock);
                //g.drawRect((int) rock.getSuperX(), (int) rock.getSuperY(), rock.rockWidth, rock.rockHeight);
            } else {
                middleRocks.add(new MiddleRock(MiddleRock.getPolyXArr(), MiddleRock.getPolyYArr(), 13, rock.uLeftXPos, rock.uLeftYPos));
                score += 5;
                rocks.remove(rock);
            }
        }

        for (MiddleRock middleRock : middleRocks) {
            if (middleRock.onScreen) {
                middleRock.move(theShip, Board.bullets);
                gSetting.draw(middleRock);
            }
        }

        //new wave adding rocks
        if (score == wave * 30) {
            wave++;
            for (int i = 0; i < wave * 2; i++) {
                int rndStaXPos = (int) (Math.random() * (Board.boardWidth - 40) + 1);
                int rndStaYPos = (int) (Math.random() * (Board.boardHeight - 40) + 1);

                rocks.add(new Rock(Rock.getPolyXArr(rndStaXPos), Rock.getPolyYArr(rndStaYPos), 13, rndStaXPos, rndStaYPos));
                Rock.rocks = rocks;
            }
        }

        //System.out.println(Board.muted);

        if (Board.keyHeld && Board.keyHCode == 68) {       //D
            theShip.increaseRotateAngle();
        } else if (Board.keyHeld && Board.keyHCode == 65) {         //A
            theShip.decreaseRotateAngle();
        } else if (Board.keyHeld && Board.keyHCode == 87) {         //W
            theShip.setMovingAngle(theShip.getRotateAngle());
            theShip.increaseXVelo(theShip.shipXMoveAngle(theShip.getMovingAngle()) * 0.2);
            theShip.increaseYVelo(theShip.shipYMoveAngle(theShip.getMovingAngle()) * 0.2);
        }

        theShip.move();

        gSetting.setTransform(identity);

        gSetting.translate(theShip.getXCenter(), theShip.getYCenter());

        gSetting.rotate(Math.toRadians(theShip.getRotateAngle()));

        gSetting.draw(theShip);
        //gSetting.draw(theShip.getBounds());

        for (Bullet bullet : Board.bullets) {
            bullet.move();

            if (bullet.onScreen) {
                gSetting.setTransform(identity);

                gSetting.translate(bullet.getCenterX(), bullet.getCenterY());

                gSetting.draw(bullet);

                //gSetting.draw(bullet.getBounds());
            }
        }
    }


}