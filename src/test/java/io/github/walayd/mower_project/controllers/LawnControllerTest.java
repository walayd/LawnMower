/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.controllers;

import io.github.walayd.mower_project.domain.Lawn;
import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.orientation.Orientation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class LawnControllerTest {
    LawnController lawnController;
    ParserController parserController;
    Set<Mower> expectedMowers;

    @BeforeEach
    public void init() throws IOException {
        Lawn lawn = new Lawn(new Point(5, 5));
        lawnController = new LawnController(lawn);

        // Parsing resource file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sampleMowers.txt").getFile());
        parserController = new ParserController(file.getAbsolutePath());
        parserController.parse();
        lawnController.initializeMowersPositions(parserController.getMowers());
        lawnController.setActionsQueues(parserController.getActionQueues());

        // adding expectedMowers
        expectedMowers = new HashSet<>();
        Mower mower1 = new Mower(Orientation.N, new Point(1, 3));
        Mower mower2 = new Mower(Orientation.E, new Point(5, 1));
        expectedMowers.add(mower1);
        expectedMowers.add(mower2);
    }

    @Test
    public void initializeMowersPositions() {
        Lawn lawn = new Lawn(new Point(5, 5));
        LawnController lawnController = new LawnController(lawn);
        lawnController.initializeMowersPositions(parserController.getMowers());
        assertEquals(parserController.getMowers(), lawnController.getState());
    }

    @Test
    @RepeatedTest(100)
    public void runSequentially() {
        lawnController.runSequentially();
        assertEquals(expectedMowers, lawnController.getState());
    }

    @Test
    @RepeatedTest(100)
    public void runInParallel() {
        lawnController.runInParallel(10);
        assertEquals(expectedMowers, lawnController.getState());
    }
}