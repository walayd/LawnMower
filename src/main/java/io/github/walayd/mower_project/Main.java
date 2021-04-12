package io.github.walayd.mower_project;

import io.github.walayd.mower_project.controllers.LawnController;
import io.github.walayd.mower_project.controllers.ParserController;
import io.github.walayd.mower_project.domain.Lawn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = args[0]; // C:/Users/wli7/Documents/test.txt
        String outputFilePath = args[1]; // C:/Users/wli7/Documents/result2.txt
        int numberOfThreads = Integer.parseInt(args[2]); //"5"

        Logger logger = LoggerFactory.getLogger(Main.class);

        // get number of logical cores (#ofCPU * #ofCores * #ofThreads) in case of hyper-threading technology
        int processors = Runtime.getRuntime().availableProcessors();
        logger.debug("Number of cores available for the Java application: " + processors);

        ParserController parserController = new ParserController(inputFilePath);
        parserController.parse();
        Lawn lawn = new Lawn(parserController.getLawnMaxPoint());
        LawnController lawnController = new LawnController(lawn);
        lawnController
                .initializeMowersPositions(parserController.getMowers())
                .setActionsQueues(parserController.getActionQueues())
                .runInParallel(numberOfThreads);

        parserController.generateOutput(lawnController.getState(), outputFilePath);
    }
}
