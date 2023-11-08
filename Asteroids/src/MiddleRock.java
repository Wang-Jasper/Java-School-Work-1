import java.awt.Polygon;    //this is used to generate the polygonRock
import java.awt.Rectangle;      //this is used to deal with the bounce between the rock
//import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
//import java.awt.geom.Area;

public class MiddleRock extends Polygon
{
    int uLeftXPos, uLeftYPos;   //the left upper point of the rock
    int xDir, yDir;     //the direction the rock is going, xDir is speed in the x-axis
    int width = Board.boardWidth, height = Board.boardHeight;    //the size of the board which rock is going to exist
    int rockWidth = 26, rockHeight = 31;
    static ArrayList<MiddleRock> rocks = new ArrayList<>();

    // use several points to draw the rock
    public static int[] staPolXArr = {5, 9, 13, 17, 14, 18, 13, 7, 4, 1, 2, 1, 5};
    public static int[] staPolYArr = {0, 2, 1, 4, 7, 10, 15, 14, 15, 11, 8, 3, 0};

    public boolean onScreen = true;

    String explodeFile = "file:./src/sound/bangMedium.wav";

    //constructor to generate the rocks
    //rndStaXPos and y is the random position to generate the rock
    public MiddleRock(int[] polyXArr, int[] polyYArr, int pointsInPoly, int staXPos, int staYPos) {
        //points in poly is the total number of the points for polygon
        super(polyXArr, polyYArr, pointsInPoly);

        this.xDir = (int) (Math.random() * 4 + 1);
        this.yDir = (int) (Math.random() * 4 + 1);

        //this is the starting point on the board
        this.uLeftXPos = staXPos;
        this.uLeftYPos = staYPos;
    }

    //getting the bounce box of the rock
    public Rectangle getBound() {
        return new Rectangle(super.xpoints[0], super.ypoints[0], rockWidth, rockHeight);
    }

    public void move(SpaceShip theShip, ArrayList<Bullet> bullets) {
        //let the rock bounce with each other
        Rectangle rockBlock = this.getBound();
        for (MiddleRock rock : rocks) {
            if (rock.onScreen) {
                //get the otherRock block
                Rectangle otherRock = rock.getBound();
                //if the rock hit the other rock, then exchange the speed of two rocks
                if (rock != this && otherRock.intersects(rockBlock)) {
                    int tmp = this.xDir;
                    this.xDir = rock.xDir;
                    rock.xDir = tmp;

                    tmp = this.yDir;
                    this.yDir = rock.yDir;
                    rock.yDir = tmp;
                }
                //hit the ship
                Rectangle shipBox = theShip.getBounds();
                if (otherRock.intersects(shipBox)) {
                    Board.playSound(explodeFile);
                    //reset the Ship to the center, and reset the velocity
                    theShip.setXCenter((theShip.boardWidth) / 2.0);
                    theShip.setYCenter((theShip.boardHeight) / 2.0);
                    theShip.setxVelo(0);
                    theShip.setyVelo(0);
                }
                //hit the bullet
                for (Bullet bullet : bullets) {
                    if (bullet.onScreen) {
                        if (otherRock.contains(bullet.getCenterX(), bullet.getCenterY())) {
//                        if (!intersection.isEmpty()) {
                            rock.onScreen = false;
                            bullet.onScreen = false;
                            Board.playSound(explodeFile);
                        }
                    }

                }
            }
        }

        //getting the first point of polygon, which is the polyXArr in this class
        int uLeftXPos = super.xpoints[0];
        int uLeftYPos = super.ypoints[0];

        //uLeftPosX < 0 || (uLeftPosX + 25) > width it's just off the board
        if (uLeftXPos < 0 || (uLeftXPos + 25) > width)
            xDir = -xDir;       //then change the direction of the rock
        if (uLeftYPos < 0 || (uLeftYPos + 25) > height)     //check this i just changed width to height
            yDir = -yDir;

        //redraw the polygon rock over and over
        for (int i = 0; i < super.xpoints.length; i++) {
            super.xpoints[i] += xDir;
            super.ypoints[i] += yDir;
        }
    }

    //Two method to generate a random position for the rock
    public static int[] getPolyXArr() {

        return staPolXArr.clone();
    }

    public static int[] getPolyYArr() {

        return staPolYArr.clone();
    }
}
