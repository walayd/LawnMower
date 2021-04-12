/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.domain;

import io.github.walayd.mower_project.exceptions.PointAlreadyOccupiedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LawnTest {

    @Test
    public void testIsFree() {
        Lawn lawn = new Lawn(new Point(5, 5));
        try {
            lawn.occupy(new Point(0, 0));
            assertFalse(lawn.isFree(new Point(0, 0)));
        } catch (PointAlreadyOccupiedException e) {
            // pass
        }
    }

    @Test
    public void testOccupy() {
        Lawn lawn = new Lawn(new Point(5, 5));
        boolean resultCorrect = false;

        try {
            lawn.occupy(new Point(0, 0));
            lawn.occupy(new Point(0, 0));
        } catch (PointAlreadyOccupiedException e) {
            resultCorrect = true;
        }

        assertTrue(resultCorrect);
    }

    @Test
    public void testFree() {
        Lawn lawn = new Lawn(new Point(5, 5));
        try {
            lawn.occupy(new Point(0, 0));
            assertFalse(lawn.isFree(new Point(0, 0)));
            lawn.free(new Point(0, 0));
            assertTrue(lawn.isFree(new Point(0, 0)));
        } catch (PointAlreadyOccupiedException | InterruptedException e) {
            // pass
        }
    }

    @Test
    public void testIsInsideLawn() {
        Lawn lawn = new Lawn(new Point(5, 5));
        assertTrue(lawn.isInsideLawn(new Point(1, 1)));
        assertFalse(lawn.isInsideLawn(new Point(2, 6)));
        assertFalse(lawn.isInsideLawn(new Point(8, 2)));
        assertFalse(lawn.isInsideLawn(new Point(8, 8)));
        assertFalse(lawn.isInsideLawn(new Point(-1, 2)));
        assertFalse(lawn.isInsideLawn(new Point(-1, -5)));
    }
}