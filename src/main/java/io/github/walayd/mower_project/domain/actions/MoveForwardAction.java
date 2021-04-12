package io.github.walayd.mower_project.domain.actions;

import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * MoveForwardAction, concrete Action, that moves a Mower forward
 * features:
 * - simulateAction:
 * - executeAction:
 *
 * @author Walid LARABI
 */
public class MoveForwardAction implements Action {
    Logger logger = LoggerFactory.getLogger(MoveForwardAction.class);
    final private Mower mower;

    public MoveForwardAction(Mower mower) {
        this.mower = mower;
    }

    @Override
    public void execute() {
        //logger.info(mower + " execute moveForward");
        this.mower.moveForward();
    }

    public Point simulateActualPoint() {
        return this.mower.getActualPosition();
    }

    public Point simulateNextPoint() {
        return this.mower.getNextPosition();
    }

    @Override
    public String toString() {
        return "MOVE_FORWARD_ACTION";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveForwardAction that = (MoveForwardAction) o;
        return mower.equals(that.mower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mower);
    }
}
