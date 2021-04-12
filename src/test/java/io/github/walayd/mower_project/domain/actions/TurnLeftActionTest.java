/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.domain.actions;

import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.orientation.Orientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TurnLeftActionTest {

    @Test
    public void execute() {
        Mower mower = new Mower(Orientation.N, new Point(0,0));
        Action turnLeftAction = new TurnLeftAction(mower);
        assertEquals(mower.getOrientation(), Orientation.N);
        turnLeftAction.execute();
        assertEquals(mower.getOrientation(), Orientation.W);
        turnLeftAction.execute();
        assertEquals(mower.getOrientation(), Orientation.S);
    }
}