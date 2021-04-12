package io.github.walayd.mower_project.exceptions;

/**
 * Exception Class raised if we want to occupy a point already occupied
 *
 * @author Walid LARABI
 */
public class PointAlreadyOccupiedException extends Exception {
    public PointAlreadyOccupiedException(String string) {
        super("the point " + string + " is already occupied");
    }
}
