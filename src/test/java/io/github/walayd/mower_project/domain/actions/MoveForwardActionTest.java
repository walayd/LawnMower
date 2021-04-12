/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.domain.actions;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.orientation.Orientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveForwardActionTest {
    @Test
    public void execute() {
        Mower mower = new Mower(Orientation.N, new Point(0,0));
        Action moveForwardAction = new MoveForwardAction(mower);
        assertEquals(mower.getActualPosition(), new Point(0,0));
        moveForwardAction.execute();
        assertEquals(mower.getActualPosition(), new Point(0,1));
    }

    @Test
    public void simulateActualPoint() {
        Mower mower = new Mower(Orientation.N, new Point(0,0));
        MoveForwardAction moveForwardAction = new MoveForwardAction(mower);
        assertEquals(mower.getActualPosition(), moveForwardAction.simulateActualPoint());
    }

    @Test
    public void simulateNextPoint() {
        Mower mower = new Mower(Orientation.N, new Point(0,0));
        MoveForwardAction moveForwardAction = new MoveForwardAction(mower);
        assertEquals(mower.getNextPosition(),new Point(0,1));
    }
}