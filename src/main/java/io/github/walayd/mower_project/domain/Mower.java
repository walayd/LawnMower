package io.github.walayd.mower_project.domain;

import io.github.walayd.mower_project.domain.orientation.Orientation;
import io.github.walayd.mower_project.domain.orientation.Turn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Class that represents a mower.
 * <p>
 * features:
 * - has 2D point position
 * - has an orientation (N, E, W, S)
 * - Gives actual and potential new position
 * - Execute concrete actions on mowers (moveForward, turnLeft, turnRight)
 *
 * @author Walid LARABI
 */
public class Mower {
    Logger logger = LoggerFactory.getLogger(Mower.class);

    public Orientation getOrientation() {
        return orientation;
    }

    private Orientation orientation;
    private Point point;

    // Constructor
    public Mower(Orientation orientation, Point Point) {
        this.orientation = orientation;
        this.point = Point;
    }

    // Positions dashboard
    public Point getActualPosition() {
        return point;
    }

    public Point getNextPosition() {
        Point nextMovePoint = null;
        switch (this.orientation) {
            case N:
                nextMovePoint = new Point(point.getX(), point.getY() + 1);
                break;
            case E:
                nextMovePoint = new Point(point.getX() + 1, point.getY());
                break;
            case W:
                nextMovePoint = new Point(point.getX() - 1, point.getY());
                break;
            case S:
                nextMovePoint = new Point(point.getX(), point.getY() - 1);
        }
        return nextMovePoint;
    }

    // Actions
    public void moveForward() {
        this.point = getNextPosition();
    }

    public void turnLeft() {
        this.orientation = this.orientation.turnTo(Turn.L);
    }

    public void turnRight() {
        this.orientation = this.orientation.turnTo(Turn.R);
    }

    @Override
    public String toString() {
        return "Mower{" +
                "orientation=" + orientation +
                ", point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mower mower = (Mower) o;
        return orientation == mower.orientation && point.equals(mower.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orientation, point);
    }
}
