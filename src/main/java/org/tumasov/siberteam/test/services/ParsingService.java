package org.tumasov.siberteam.test.services;

import org.apache.log4j.Logger;
import org.tumasov.siberteam.test.workers.Consumer;
import org.tumasov.siberteam.test.workers.Producer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class ParsingService {
    private static final Logger LOG = Logger.getLogger(ParsingService.class);
    private final String fileWithUrlsPath;
    private final List<URL> urlList = new ArrayList<>();
    private final ConcurrentMap<String, Object> wordsHashMap = new ConcurrentHashMap<>(); // for performance! I can replace it on CopyOnWriteArrayList???

    public ParsingService(String fileWithUrlsPath) {
        this.fileWithUrlsPath = fileWithUrlsPath;
    }

    public List<String> start() throws Exception {
        File file = new File(fileWithUrlsPath);
        if (!file.canRead() || !file.isFile()) {
            throw new Exception("Can't opening file for reading!");
        }

        // Parse urls file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    URL url = new URL(line);
                    this.urlList.add(url);
                } catch (MalformedURLException e) {
                    LOG.warn(e);
                }
            }
        }

        final ExecutorService downloadingService = Executors.newFixedThreadPool(urlList.size());  // Right? Can I create so many threads?
        final ExecutorService readingService = Executors.newFixedThreadPool(urlList.size());
        final BlockingQueue<String> queueOfLines = new ArrayBlockingQueue<>(10 * urlList.size());
        final ArrayList<Future> workers = new ArrayList<>();

        for (int i = 0; i < urlList.size(); i++) {
            final Producer producer = new Producer(queueOfLines, this.urlList.get(i));
            final Consumer consumer = new Consumer(queueOfLines, wordsHashMap, producer);

            downloadingService.submit(producer);
            final Future worker = readingService.submit(consumer);
            workers.add(worker);
        }

        // Wait all threads
        for (Future f: workers) {
            f.get();
        }

        downloadingService.shutdown();
        readingService.shutdown();

        // Sort
        List<String> words = new ArrayList<>(wordsHashMap.keySet());
        Collections.sort(words);

        return words;
    }
}
