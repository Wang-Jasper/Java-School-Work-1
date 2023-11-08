/**
 * @Author: Jasper
 * @Desc:
 * @Create: 2023-06-02 21:55
 **/

import java.awt.*;

public class SpaceShip extends Polygon
{
    private double xVelo = 0, yVelo = 0;
    int boardWidth = Board.boardWidth, boardHeight = Board.boardHeight;
    private double XCenter = (boardWidth) / 2.0, YCenter = (boardHeight) / 2.0;

    public static int[] polyXArr = {-13, 23, -13, -5, -13};
    //500, 527, 500, 508, 500
    //400, 415, 430, 415, 400
    public static int[] polyYArr = {-15, 0, 15, 0, -15};
    private final int shipWidth = 36, shipHeight = 30;
    //private double uLeftXPos = getXCenter() + polyXArr[0];
    //private double uLeftYPos = getYCenter() + polyYArr[0];
    private double rotationAngle = 0, movingAngle = 0;

    public SpaceShip() {
        super(polyXArr, polyYArr, 5);
    }

    public Rectangle getBounds() {
        return new Rectangle(super.xpoints[0], super.ypoints[0], getShipWidth(), getShipHeight());
    }

    public double getXCenter() {
        return XCenter;
    }

    public double getYCenter() {
        return YCenter;
    }

    public void setXCenter(double xCent) {
        this.XCenter = xCent;
    }

    public void setYCenter(double yCent) {
        this.YCenter = yCent;
    }

    public void increaseXPos(double incAmt) {
        this.XCenter += incAmt;
    }

    public void increaseYPos(double incAmt) {
        this.YCenter += incAmt;
    }

    public int getShipWidth() {
        return shipWidth;
    }

    public int getShipHeight() {
        return shipHeight;
    }


    public double getxVelo() {
        return xVelo;
    }

    public double getyVelo() {
        return yVelo;
    }

    public void setxVelo(double xVel) {
        this.xVelo = xVel;
    }

    public void setyVelo(double yVel) {
        this.yVelo = yVel;
    }

    public void increaseXVelo(double xVelInc) {
        if (this.xVelo < 9)
            this.xVelo += xVelInc;
    }

    public void increaseYVelo(double yVelInc) {
        if (this.yVelo < 9)
            this.yVelo += yVelInc;
    }

    public void setMovingAngle(double moveAngle) {
        this.movingAngle = moveAngle;
    }

    public double getMovingAngle() {
        return movingAngle;
    }

    public double shipXMoveAngle(double xMoveAngle) {
        return Math.cos(xMoveAngle * Math.PI / 180);
    }

    public double shipYMoveAngle(double yMoveAngle) {
        return Math.sin(yMoveAngle * Math.PI / 180);
    }


    public double getRotateAngle() {
        return rotationAngle;
    }

    public void increaseRotateAngle() {

        if (getRotateAngle() >= 355) {
            rotationAngle = 0;
        } else {
            rotationAngle += 5;
        }
    }

    public void decreaseRotateAngle() {
        if (getRotateAngle() < 0) {
            rotationAngle = 355;
        } else {
            rotationAngle -= 5;
        }
    }

    //get the shooting point of the ship
    public double getShootXPoint() {
        return this.getXCenter() + Math.cos(rotationAngle) * 14;
    }

    public double getShootYPoint() {
        return this.getYCenter() + Math.sin(rotationAngle) * 14;
    }

    public void move() {

        this.increaseXPos(this.getxVelo());

        if (this.getXCenter() < 0) {
            this.setXCenter(boardWidth + 500);
        } else if (this.getXCenter() > boardWidth + 500) {
            this.setXCenter(0);
        }

        this.increaseYPos(this.getyVelo());
        if (this.getYCenter() < 0) {
            this.setYCenter(boardHeight + 400);
        } else if (this.getYCenter() > boardHeight + 400) {
            this.setYCenter(0);
        }
    }
}