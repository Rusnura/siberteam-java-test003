package org.tumasov.siberteam.test.workers;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter {
    private static final Logger log = Logger.getLogger(FileWriter.class);
    public static void write(String filename, List<String> words) {
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(new java.io.FileWriter(file, true))) {
            for (String word: words) {
                writer.println(word);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
