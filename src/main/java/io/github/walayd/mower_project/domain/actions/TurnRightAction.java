package io.github.walayd.mower_project.domain.actions;

import io.github.walayd.mower_project.domain.Mower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class TurnRightAction implements Action {
    Logger logger = LoggerFactory.getLogger(TurnRightAction.class);
    final private Mower mower;

    public TurnRightAction(Mower mower) {
        this.mower = mower;
    }

    @Override
    public void execute() {
        //logger.info("mower " + mower + " execute turnRight");
        mower.turnRight();
    }

    @Override
    public String toString() {
        return "TURN_RIGHT_ACTION";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnRightAction that = (TurnRightAction) o;
        return mower.equals(that.mower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mower);
    }
}
