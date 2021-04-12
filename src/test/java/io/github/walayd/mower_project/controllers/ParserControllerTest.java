/*
 * Copyright (c) Walid LARABI
 */

package io.github.walayd.mower_project.controllers;

import io.github.walayd.mower_project.domain.Lawn;
import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.actions.Action;
import io.github.walayd.mower_project.domain.actions.MoveForwardAction;
import io.github.walayd.mower_project.domain.actions.TurnLeftAction;
import io.github.walayd.mower_project.domain.actions.TurnRightAction;
import io.github.walayd.mower_project.domain.orientation.Orientation;
import io.github.walayd.mower_project.domain.orientation.Turn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ParserControllerTest {
    Point expectedMaxPoint;
    Set<Mower> expectedInitialPositionMowers;
    List<Queue<Action>> expectedActionsQueues;
    LawnController lawnController;
    ParserController parserController;

    @BeforeEach
    void init(){
        Lawn lawn = new Lawn(new Point(5, 5));
        lawnController = new LawnController(lawn);

        // adding expectedMaxPoint (5 5)
        expectedMaxPoint = new Point(5,5);

        // adding expectedInitialPositionMowers
        // 1 2 N
        // 3 3 E
        expectedInitialPositionMowers = new HashSet<>();
        Mower mower1 = new Mower(Orientation.N, new Point(1, 2));
        Mower mower2 = new Mower(Orientation.E, new Point(3, 3));
        expectedInitialPositionMowers.add(mower1);
        expectedInitialPositionMowers.add(mower2);


        // adding expected ActionsQueues

        // mower1 LFLFLFLFF
        MoveForwardAction moveForwardActionMower1 = new MoveForwardAction(mower1);
        TurnLeftAction turnLeftActionMower1 = new TurnLeftAction(mower1);
        Queue<Action> queueMower1 = new LinkedList<>();
        queueMower1.add(turnLeftActionMower1);
        queueMower1.add(moveForwardActionMower1);
        queueMower1.add(turnLeftActionMower1);
        queueMower1.add(moveForwardActionMower1);
        queueMower1.add(turnLeftActionMower1);
        queueMower1.add(moveForwardActionMower1);
        queueMower1.add(turnLeftActionMower1);
        queueMower1.add(moveForwardActionMower1);
        queueMower1.add(moveForwardActionMower1);


        // mower2 (FFR FFR FRRF)
        MoveForwardAction moveForwardActionMower2 = new MoveForwardAction(mower1);
        TurnRightAction turnRightActionMower2 = new TurnRightAction(mower1);
        Queue<Action> queueMower2 = new LinkedList<>();
        queueMower2.add(moveForwardActionMower2);
        queueMower2.add(moveForwardActionMower2);
        queueMower2.add(turnRightActionMower2);

        queueMower2.add(moveForwardActionMower2);
        queueMower2.add(moveForwardActionMower2);
        queueMower2.add(turnRightActionMower2);

        queueMower2.add(moveForwardActionMower2);
        queueMower2.add(turnRightActionMower2);
        queueMower2.add(turnRightActionMower2);
        queueMower2.add(moveForwardActionMower2);


        expectedActionsQueues = new LinkedList<>();
        expectedActionsQueues.add(queueMower1);
        expectedActionsQueues.add(queueMower2);

        // Parsing resource file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sampleMowers.txt").getFile());
        parserController = new ParserController(file.getAbsolutePath());
        parserController.parse();
        lawnController.initializeMowersPositions(parserController.getMowers());
        lawnController.setActionsQueues(parserController.getActionQueues());
    }


    @Test
    void getMowers() {
        assertEquals(expectedInitialPositionMowers, parserController.getMowers());
    }

    @Test
    void getLawnMaxPoint() {
        assertEquals(expectedMaxPoint, parserController.getLawnMaxPoint());
    }

    @Test
    void getActionQueues() {
        assertEquals(expectedActionsQueues.toString(), parserController.getActionQueues().toString());
    }
}