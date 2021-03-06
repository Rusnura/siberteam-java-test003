package org.tumasov.siberteam.test;

import org.apache.log4j.Logger;
import org.tumasov.siberteam.test.services.ParsingService;
import org.tumasov.siberteam.test.workers.FileWriter;

import java.util.*;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println("Usage: test.jar FileWithURLs OutputFileName AnagramOutputFileName");
            return;
        } else if (args.length == 1) {
            System.err.println("Please, define an output file!\nUsage: test.jar FileWithURLs OutputFileName AnagramOutputFileName");
            return;
        }

        try {
            ParsingService parser = new ParsingService(args[0]);
            List<String> sortedListOfWords = parser.start();
            System.out.println("Данные получены...");
            FileWriter.writeDictionaryToFile(args[1], sortedListOfWords);
            System.out.println("Отсортированы и записаны в файл\n");
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}