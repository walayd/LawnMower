/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.domain;

import io.github.walayd.mower_project.domain.orientation.Orientation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MowerTest {

    @Test
    public void testGetOrientation() {
        Mower inputMower = new Mower(Orientation.N, new Point(0,0));
        assertEquals(inputMower.getOrientation(), Orientation.N);
    }

    @Test
    public void testGetActualPosition() {
        Mower inputMower = new Mower(Orientation.N, new Point(0,0));
        assertEquals(inputMower.getActualPosition(), new Point(0,0));
    }

    @Test
    public void testGetNextPosition() {
        // since the mower Orientation is N, and we move forward, result should be (x, y+1)
        Mower inputMower = new Mower(Orientation.N, new Point(0,0));
        assertEquals(inputMower.getNextPosition(), new Point(0,1));
    }

    @Test
    public void testMoveForward() {
        Mower inputMower = new Mower(Orientation.N, new Point(0,0));
        inputMower.moveForward();
        assertEquals(inputMower.getActualPosition(), new Point(0,1));
    }

    @Test
    public void testTurnLeft() {
        Mower inputMower = new Mower(Orientation.N, new Point(0,0));
        inputMower.turnLeft();
        assertEquals(inputMower.getOrientation(), Orientation.W);
    }

    @Test
    public void testTurnRight() {
        Mower inputMower = new Mower(Orientation.N, new Point(0,0));
        inputMower.turnRight();
        assertEquals(inputMower.getOrientation(), Orientation.E);
    }
}