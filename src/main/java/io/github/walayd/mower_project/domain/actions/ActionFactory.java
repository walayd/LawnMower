package io.github.walayd.mower_project.domain.actions;

import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.exceptions.ActionDoNotExistException;

/**
 * Action Factory, creates an Action class (MovingForwardAction, TurnLeftAction, TurnRightAction) from a String
 *
 * @author Walid LARABI
 */
public class ActionFactory {
    public static Action getAction(Mower mower, String actionString) throws ActionDoNotExistException {
        if (actionString.equalsIgnoreCase("L")) {
            return new TurnLeftAction(mower);
        } else if (actionString.equalsIgnoreCase("R")) {
            return new TurnRightAction(mower);
        } else if (actionString.equalsIgnoreCase("F")) {
            return new MoveForwardAction(mower);
        } else {
            throw new ActionDoNotExistException(actionString);
        }
    }
}
