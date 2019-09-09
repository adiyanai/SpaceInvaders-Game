package graphic;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;
import geometry.Point;
import geometry.Rectangle;

/**
 * A class that represent a group of aliens(enemies).
 * And responsible of moving them on the screen.
 * @author Adi Yanai
 *
 */
public class Aliens implements Sprite {
    private List<Block> aliens;
    private double speed;
    private double startSpeed;
    private GameLevel game;
    private double firstX;
    private double firstY;
    private boolean checkLose;

    /**
     * Constructor.
     * @param a - a blocks list
     * @param game - a game level
     * @param speed - the speed of the aliens move
     */
    public Aliens(List<Block> a, GameLevel game, double speed) {
        this.aliens = a;
        this.speed = speed;
        this.startSpeed = speed;
        this.game = game;
        this.firstX = a.get(0).getCollisionRectangle().getSaveStartPoint().getX();
        this.firstY = a.get(0).getCollisionRectangle().getSaveStartPoint().getY();
        this.checkLose = false;
    }

    /**
     * return the checkLose.
     * @return the checkLose
     */
    public boolean getCheckLose() {
        return this.checkLose;
    }
    /**
     * set the list of the aliens.
     * @param a - a blocks list of aliens
     */
    public void setAliens(List<Block> a) {
        this.aliens = a;
    }
    /**
     * return the list of the aliens.
     * @return the blocks list of aliens
     */
    public List<Block> getAliens() {
        return this.aliens;
    }

    /**
     * draw the aliens on the screen.
     * @param d - a draw surface
     */
    public void drawOn(DrawSurface d) {
        for (int i = 0; i < this.aliens.size(); i++) {
            this.aliens.get(i).drawOn(d);
        }
    }

    /**
     * find the alien with the smallest x. and return this x.
     * @return the smallest x.
     */
    public double checkLeft() {
        double x, temp;
        x = 1000;
        for (int i = 0; i < this.aliens.size(); i++) {
            temp = this.aliens.get(i).getCollisionRectangle().getUpperLeft().getX();
            if (temp < x) {
                x = temp;
            }
        }
        return x;
    }

    /**
     * find the alien with the biggest x. and return this x.
     * @return the biggest
     */
    public double checkRight() {
        double x, temp;
        x = -100;
        for (int i = 0; i < this.aliens.size(); i++) {
            temp = this.aliens.get(i).getCollisionRectangle().getUpperLeft().getX();
            if (temp > x) {
                x = temp;
            }
        }
        return x;
    }

    /**
     * find the alien with the smallest y. and return this y.
     * @return the smallest y.
     */
    public double checkUp() {
        double y, temp;
        y = 1000;
        for (int i = 0; i < this.aliens.size(); i++) {
            temp = this.aliens.get(i).getCollisionRectangle().getUpperLeft().getY();
            if (temp < y) {
                y = temp;
            }
        }
        return y;
    }

    /**
     * find the alien with the biggest y. and return this y.
     * @return the biggest y.
     */
    public double checkDown() {
        double y, temp;
        y = -100;
        for (int i = 0; i < this.aliens.size(); i++) {
            temp = this.aliens.get(i).getCollisionRectangle().getUpperLeft().getY();
            if (temp > y) {
                y = temp;
            }
        }
        return y;
    }

    /**
     * move the aliens one step to the side.
     */
    public void moveToSide() {
        Rectangle r;
        double x, y;
        for (int i = 0; i < this.aliens.size(); i++) {
            r = this.aliens.get(i).getCollisionRectangle();
            x = r.getUpperLeft().getX();
            y = r.getUpperLeft().getY();
            r.setUpperLeft(new Point(x + this.speed, y));
        }
    }

    /**
     * move the aliens one step down.
     */
    public void moveDown() {
        Rectangle r;
        double x, y;
        for (int i = 0; i < this.aliens.size(); i++) {
            r = this.aliens.get(i).getCollisionRectangle();
            x = r.getUpperLeft().getX();
            y = r.getUpperLeft().getY();
            r.setUpperLeft(new Point(x, y + 15));
        }
    }

    /**
     * move the aliens to the start of the screen.
     */
    public void moveToTheStart() {
        double minX = checkLeft();
        double minY = checkUp();
        double distanceX = minX - this.firstX;
        double distanceY = minY - this.firstY;
        aliens.get(0).getCollisionRectangle().setUpperLeft(new Point(this.firstX, this.firstY));
        for (int i = 1; i < aliens.size(); i++) {
            aliens.get(i).getCollisionRectangle().setUpperLeft(new Point(
                    aliens.get(i).getCollisionRectangle().getUpperLeft().getX() - distanceX,
                    aliens.get(i).getCollisionRectangle().getUpperLeft().getY() - distanceY));
        }
        this.speed = this.startSpeed;
    }

    /**
     * move the aliens on the screen.
     * @param dt - frames per seconds
     */
    public void moveAliens(double dt) {
        this.checkLose = false;
        // check if the aliens pass the boundary on the left
        if (this.checkLeft() < 0) {
            // move the aliens one step down
            this.moveDown();
            // increase the speed by 10% and change the aliens direction
            this.speed *= -1.1;
        }
        // check if the aliens pass the boundary on the right
        if (this.checkRight() > 760) {
            // move the aliens one step down
            this.moveDown();
            // increase the speed by 10% and change the aliens direction
            this.speed *= -1.1;
        }
        // check if the aliens arrived to the blocks shields
        if (this.checkDown() > 475) {
            // move the aliens to the start point
            this.moveToTheStart();
            // indicate that the player lose one live cause the aliens arrived to the blocks shields
            this.checkLose = true;
        }
        // shoot ball from a random alien
        this.shotBalls();
        // move the aliens one step to side
        this.moveToSide();
    }

    /**
     * Every 0.5 seconds a random column of aliens is chosen to shoot,
     * the lowest alien on that column will release a shot.
     */
    public void shotBalls() {
        List<Block> l = new ArrayList<Block>();
        double x, y, tempX, tempY;
        Block blockTemp, randBlock;
        // create a list of the lowest alien on that column
        for (int i = 0; i < this.aliens.size(); i++) {
            tempX = this.aliens.get(i).getCollisionRectangle().getUpperLeft().getX();
            tempY = this.aliens.get(i).getCollisionRectangle().getUpperLeft().getY();
            blockTemp = this.aliens.get(i);
            for (int j = i; j < this.aliens.size(); j++) {
                x = this.aliens.get(j).getCollisionRectangle().getUpperLeft().getX();
                y = this.aliens.get(j).getCollisionRectangle().getUpperLeft().getY();
                if (x == tempX && y > tempY) {
                    blockTemp = this.aliens.get(j);
                    tempY = y;
                }
            }
            l.add(blockTemp);
        }

        Random rand = new Random();
        // choose a random alien from the the lowest alien on that column list
        randBlock = l.get(rand.nextInt(l.size()));
        // send the alien that needs to shoot to a game level function that make ball
        this.game.createShootBall(randBlock);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt - frames per second
     */
    public void timePassed(double dt) {
        this.moveAliens(dt);
    }

    /**
     * add the aliens to the game.
     * @param g - a game level
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}