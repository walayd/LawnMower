/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class PointTest {
    Point inputPoint = new Point(1, 2);

    public void testPointGetX() {
        assertEquals(1, inputPoint.getX().intValue());
    }

    public void testPointGetY() {
        assertEquals(2, inputPoint.getY().intValue());
    }

    public void testPointEquals() {
        assertEquals(inputPoint, new Point(1,2));
    }

    public void testPointNotEquals() {
        assertNotSame(inputPoint, new Point(2,2));
    }
}