/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.domain.actions;

import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.orientation.Orientation;
import io.github.walayd.mower_project.exceptions.ActionDoNotExistException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ActionFactoryTest {

    @Test
    public void getAction() throws ActionDoNotExistException {
        Mower mower = new Mower(Orientation.N, new Point(0, 0));
        assertTrue(ActionFactory.getAction(mower, "F") instanceof MoveForwardAction);
        assertTrue(ActionFactory.getAction(mower, "L") instanceof TurnLeftAction);
        assertTrue(ActionFactory.getAction(mower, "R") instanceof TurnRightAction);
    }

}