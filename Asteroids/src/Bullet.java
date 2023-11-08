/**
 * @Author: Jasper
 * @Desc:
 * @Create: 2023-06-03 22:49
 **/

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serial;

public class Bullet extends Polygon
{
    @Serial
    private static final long serialVersionUID = 1L;
    int boardWidth = Board.boardWidth, boardHeight = Board.boardHeight;

    private double centerX, centerY;
    public static int[] polyXArr = {-3, 3, 3, -3, -3};
    public static int[] polyYArr = {-3, -3, 3, 3, -3};
    private int bulletWidth = 6, bulletHeight = 6;

    public boolean onScreen;
    //private double moveAngle;
    private double xVelo = 6, yVelo = 6;

    public Bullet(double centerX, double centerY, double moveAngle) {

        super(polyXArr, polyYArr, 5);

        this.centerX = centerX;
        this.centerY = centerY;
        //this.moveAngle = moveAngle;

        this.onScreen = true;

        this.setXVelo(this.bulletXMoveAngle(moveAngle) * 10);
        this.setYVelo(this.bulletYMoveAngle(moveAngle) * 10);

    }
//    public Bullet(int[] polyXArr, int[] polyYArr, int pointsInPoly) {
//        super(polyXArr, polyYArr, pointsInPoly);
//
//    }

//    public static int[] getPolyXArr(int staXPos) {
//        int[] tmpPolyXArr = polyYArr.clone();
//
//        for (int i = 0; i < tmpPolyXArr.length; i++)
//            tmpPolyXArr[i] += staXPos;
//
//        return tmpPolyXArr;
//    }

//    public static int[] getPolyYArr(int staXPos) {
//        int[] tmpPolyYArr = polyYArr.clone();
//
//        for (int i = 0; i < tmpPolyYArr.length; i++)
//            tmpPolyYArr[i] += staXPos;
//
//        return tmpPolyYArr;
//    }

    public double getCenterX() {
        return centerX;
    }

//    public void setCenterX(double centerX) {
//        this.centerX = centerX;
//    }

    public double getCenterY() {
        return centerY;
    }

//    public void setCenterY(double centerY) {
//        this.centerY = centerY;
//    }


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

//    public void setMoveAngle(double moveAngle) {
//        this.moveAngle = moveAngle;
//    }

//    public double getMoveAngle() {
//        return moveAngle;
//    }

    public Rectangle getBounds() {
        //return new Rectangle((int) getCenterX() - 6, (int) getCenterY() - 6, getWidth(), getHeight());
        return new Rectangle(super.xpoints[0], super.ypoints[0], getWidth(), getHeight());
    }

//    public Polygon getPolygon() {
//        return (Polygon) this;
//    }

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
            else if (this.centerX > boardWidth + 500)
                this.onScreen = false;

            this.changeYPos(this.getyVelo());
            if (this.getCenterY() < 0)
                this.onScreen = false;
            else if (this.centerY > boardHeight + 400)
                this.onScreen = false;
        }
    }
}
