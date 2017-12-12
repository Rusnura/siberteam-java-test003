package org.tumasov.siberteam.test;

import org.apache.log4j.Logger;
import org.tumasov.siberteam.test.services.ParsingService;
import org.tumasov.siberteam.test.workers.FileWriter;

import java.util.List;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println("Usage: test.jar FileWithURLs OutputFileName");
            return;
        } else if (args.length == 1) {
            System.err.println("Please, define an output file!\nUsage: test.jar FileWithURLs OutputFileName");
            return;
        }

        try {
            ParsingService service = new ParsingService(args[0]);
            List<String> sortedListOfWords = service.start();
            FileWriter.write(args[1], sortedListOfWords);

            System.out.println(sortedListOfWords);
        } catch (Exception e) {
            log.error(e);
        }
    }
}