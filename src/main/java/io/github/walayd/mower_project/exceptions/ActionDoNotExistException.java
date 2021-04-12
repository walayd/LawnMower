package io.github.walayd.mower_project.exceptions;

/**
 * Exception Class raised if we want to create an inexisting action
 *
 * @author Walid LARABI
 */
public class ActionDoNotExistException extends Exception {
    public ActionDoNotExistException(String action) {
        super("Action <" + action + "> does not exist");
    }
}
