/**
 * @Author: Jasper
 * @Desc:
 * @Create: 2023-06-08 08:35
 **/

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serial;

public abstract class Shell extends Polygon
{
    @Serial
    private static final long serialVersionUID = 1L;

    private double centerX, centerY;
    private double xVelo, yVelo;
    private int bulletWidth, bulletHeight;
    protected boolean onScreen;

    public Shell(double centerX, double centerY, double moveAngle) {
        super(new int[]{-3, 3, 3, -3, -3}, new int[]{-3, -3, 3, 3, -3}, 5);

        this.centerX = centerX;
        this.centerY = centerY;

        this.onScreen = true;

        this.setXVelo(this.bulletXMoveAngle(moveAngle) * 10);
        this.setYVelo(this.bulletYMoveAngle(moveAngle) * 10);
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void changeXPos(double incAmt) {
        this.centerX += incAmt;
    }

    public void changeYPos(double incAmt) {
        this.centerY += incAmt;
    }

    public double getxVelo() {
        return xVelo;
    }

    public double getyVelo() {
        return yVelo;
    }

    public void setXVelo(double xVel) {
        this.xVelo = xVel;
    }

    public void setYVelo(double yVel) {
        this.yVelo = yVel;
    }

    public int getWidth() {
        return bulletWidth;
    }

    public int getHeight() {
        return bulletHeight;
    }

    public Rectangle getBounds() {
        return new Rectangle(super.xpoints[0], super.ypoints[0], getWidth(), getHeight());
    }

    public double bulletXMoveAngle(double xMoveAngle) {
        return Math.cos(xMoveAngle * Math.PI / 180);
    }

    public double bulletYMoveAngle(double yMoveAngle) {
        return Math.sin(yMoveAngle * Math.PI / 180);
    }

    public void move() {
        if (this.onScreen) {
            this.changeXPos(this.getxVelo());

            if (this.getCenterX() < 0)
                this.onScreen = false;
            else if (this.centerX > Board.boardWidth + 500)
                this.onScreen = false;

            this.changeYPos(this.getyVelo());
            if (this.getCenterY() < 0)
                this.onScreen = false;
            else if (this.centerY > Board.boardHeight + 400)
                this.onScreen = false;
        }
    }
}

