package org.tumasov.siberteam.test.workers;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter {
    private static final Logger LOG = Logger.getLogger(FileWriter.class);
    public static void writeDictionaryToFile(String fileName, List<String> words) {
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(new java.io.FileWriter(file, true))) {
            for (String word: words) {
                writer.println(word);
            }
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
