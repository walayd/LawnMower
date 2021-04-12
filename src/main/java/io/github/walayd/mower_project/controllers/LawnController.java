package io.github.walayd.mower_project.controllers;

import com.google.common.collect.Lists;
import io.github.walayd.mower_project.domain.Lawn;
import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.actions.Action;
import io.github.walayd.mower_project.domain.actions.MoveForwardAction;
import io.github.walayd.mower_project.exceptions.PointAlreadyOccupiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class LawnController {
    Logger logger = LoggerFactory.getLogger(LawnController.class);
    Lawn lawn;
    Set<Mower> mowers;
    List<Queue<Action>> actionQueues;

    public LawnController(Lawn lawn) {
        this.lawn = lawn;
        mowers = new HashSet<>();
    }

    public LawnController initializeMowersPositions(Set<Mower> mowers) {
        this.mowers = mowers;
        logger.info("Initialization...");
        for (Mower mower : this.mowers) {
            try {
                lawn.occupy(mower.getActualPosition());
            } catch (PointAlreadyOccupiedException e) {
                logger.debug(e.getLocalizedMessage());
            }
        }
        logger.info(mowers.toString());
        return this;
    }

    public LawnController setActionsQueues(List<Queue<Action>> actionQueues) {
        this.actionQueues = actionQueues;
        return this;
    }

    public LawnController runSequentially() {
        long startTime = System.nanoTime();
        logger.info("runSequentially");
        for (Queue<Action> queue : actionQueues) {
            processQueue(queue);
            logger.debug("---------------------------");
        }
        logger.info(lawn.toString());
        long stopTime = System.nanoTime();
        System.out.println("time when running sequentially in seconds : " + TimeUnit.MICROSECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
        return this;
    }

    public LawnController runInParallel(int nbOfThreadsToUse) {
        long startTime = System.nanoTime();
        logger.info("runInParallel");
        int sizeOfPartition;

        // divide the workload into partitions
        if (actionQueues.size() < nbOfThreadsToUse) {
            sizeOfPartition = 1;
        } else {
            sizeOfPartition = actionQueues.size() / nbOfThreadsToUse;
        }

        // share the workload
        ExecutorService executorService = Executors.newFixedThreadPool(nbOfThreadsToUse);
        for (List<Queue<Action>> queuePartition : Lists.partition(actionQueues, sizeOfPartition)) {
            Runnable runnable = () -> processQueueList(queuePartition);
            executorService.submit(runnable);
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Unable to stop thread, stacktrace is print below");
            e.printStackTrace();
            executorService.shutdownNow();
        }
        long stopTime = System.nanoTime();
        System.out.println("time when running in parallel in seconds : " + TimeUnit.MICROSECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS));
        return this;
    }

    private void processQueueList(List<Queue<Action>> queueList) {
        System.out.println("islem : " + queueList.size());
        for (Queue<Action> queue : queueList) {
            processQueue(queue);
        }
    }

    private void processQueue(Queue<Action> queue) {
        logger.debug("--------PROCESSING NEW QUEUE ---------");
        for (Action action : queue) {
            processAction(action);
        }
    }

    private void processAction(Action action) {
        if (action instanceof MoveForwardAction) {
            // if it is a move action, do verifications and execute
            try {
                Point actualPoint = ((MoveForwardAction) action).simulateActualPoint();
                Point nextPoint = ((MoveForwardAction) action).simulateNextPoint();
                if (lawn.isInsideLawn(nextPoint)) {
                    lawn.occupy(nextPoint);
                    action.execute();
                    lawn.free(actualPoint);
                }
            } catch (PointAlreadyOccupiedException | InterruptedException e) {
                logger.error(e.getLocalizedMessage());
            }
        } else {
            // if it is orientation action, execute it
            action.execute();
        }
    }

    public Set<Mower> getState() {
        return mowers;
    }
}
