package io.github.walayd.mower_project.controllers;

import io.github.walayd.mower_project.domain.Mower;
import io.github.walayd.mower_project.domain.Point;
import io.github.walayd.mower_project.domain.actions.Action;
import io.github.walayd.mower_project.domain.actions.ActionFactory;
import io.github.walayd.mower_project.domain.orientation.Orientation;
import io.github.walayd.mower_project.exceptions.ActionDoNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Parser controller, parse a syntactically and semantically correct file
 * outputs:
 * - lawnMaxPoint : the point that determines the size of the lawn
 * - mowers: list of mowers (containing their position and orientation)
 * - actions: list of queues of actions (as the order is important and we want to divide the workload)
 *
 * @author Walid LARABI
 */
public class ParserController {
    Logger logger = LoggerFactory.getLogger(ParserController.class);
    String inputPath;

    Point lawnMaxPoint;
    Map<Mower, List<Action>> mowersAndActions;


    Set<Mower> mowers;
    List<Action> actions;


    List<Queue<Action>> actionQueues;

    public ParserController(String inputPath) {
        this.inputPath = inputPath;
        mowersAndActions = new HashMap<>();
        mowers = new HashSet<>();
        actions = new ArrayList<>();
        actionQueues = new ArrayList<>();
    }

    public void parse() {
        try {
            File myInput = new File(inputPath);
            Scanner myReader = new Scanner(myInput);
            // process lawn size
            if (myReader.hasNextLine()) {
                String[] lawnDataTokens = myReader.nextLine().split(" ");
                logger.info("lawn data token " + lawnDataTokens.length);
                lawnMaxPoint = new Point(Integer.parseInt(lawnDataTokens[0]), Integer.parseInt(lawnDataTokens[1]));
            }

            // process mowers and their actions
            while (myReader.hasNextLine()) {

                // Process Mowers
                String[] mowerDataTokens = myReader.nextLine().split(" ");
                Mower mower = new Mower(
                        Orientation.valueOf(mowerDataTokens[2]),
                        new Point(Integer.parseInt(mowerDataTokens[0]), Integer.parseInt(mowerDataTokens[1])));
                mowers.add(mower);

                // Process Actions
                Queue<Action> queue = new LinkedList<>();
                for (String actionToken : myReader.nextLine().split("")) {
                    actions.add(ActionFactory.getAction(mower, actionToken));
                    queue.add(ActionFactory.getAction(mower, actionToken));
                }
                actionQueues.add(queue);

                // fill mowersAndActions hashmap
                mowersAndActions.put(mower, actions);
            }
        } catch (FileNotFoundException | ActionDoNotExistException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public Map<Mower, List<Action>> getMowersAndActions() {
        return mowersAndActions;
    }

    public Set<Mower> getMowers() {
        return mowers;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Point getLawnMaxPoint() {
        return lawnMaxPoint;
    }

    public List<Queue<Action>> getActionQueues() {
        return actionQueues;
    }

    public void generateOutput(Set<Mower> setMowers, String outputPath) {
        try {
            File myObj = new File(outputPath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(outputPath);
            for (Mower mower : setMowers) {
                myWriter.write(mower.getActualPosition().getX() + " "
                        + mower.getActualPosition().getY() + " "
                        + mower.getOrientation().toString() + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
