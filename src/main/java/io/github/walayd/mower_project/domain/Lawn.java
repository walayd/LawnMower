package io.github.walayd.mower_project.domain;

import io.github.walayd.mower_project.exceptions.PointAlreadyOccupiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Lawn {
    boolean isLocked;
    Point maxPoint;
    Logger logger = LoggerFactory.getLogger(Lawn.class);

    final Map<Point, Boolean> map;

    public Lawn(Point maxPoint) {
        this.maxPoint = maxPoint;
        map = Collections.synchronizedMap(new HashMap<>());
    }

    public synchronized void occupy(Point point) throws PointAlreadyOccupiedException {
        try {
            lock();
            if (isFree(point)) {
                map.put(point, false);
            } else {
                unlock();
                throw new PointAlreadyOccupiedException(point.toString());
            }
            unlock();
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public void free(Point point) throws InterruptedException {
        lock();
        if (!isFree(point)) {
            map.put(point, true);
        }
        unlock();
    }

    public Boolean isFree(Point point) {
        synchronized (map) {
            Boolean result = map.get(point);
            if (result != null) {
                return result;
            } else {
                map.put(point, true);
                return true;
            }
        }
    }

    public Boolean isInsideLawn(Point point) {
        return point.getX() >= 0 &&
                point.getY() >= 0 &&
                point.getX() <= maxPoint.getX() &&
                point.getY() <= maxPoint.getY();
    }

    private synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    private synchronized void unlock() {
        isLocked = false;
        notify();
    }
}
