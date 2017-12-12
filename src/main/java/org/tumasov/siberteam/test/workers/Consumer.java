package org.tumasov.siberteam.test.workers;

import org.apache.log4j.Logger;
import org.tumasov.siberteam.test.services.IDone;
import org.tumasov.siberteam.test.services.StringUtil;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private static final Logger log = Logger.getLogger(Consumer.class);
    private final BlockingQueue<String> queueOfLines;
    private final ConcurrentMap<String, Object> wordsHashMap;
    private final IDone indicator;
    private final Object emptyObjectForMap = new Object();
    private final ArrayList<String> lines = new ArrayList<>();

    public Consumer(BlockingQueue<String> queueOfLines, ConcurrentMap<String, Object> wordsHashMap, IDone indicator) {
        this.queueOfLines = queueOfLines;
        this.wordsHashMap = wordsHashMap;
        this.indicator = indicator;
    }

    private void analyze() throws InterruptedException {
        String line;
        if ((line = queueOfLines.poll(10, TimeUnit.MILLISECONDS)) != null) {
            lines.add(line);
        }
    }

    @Override
    public void run() {
        try {
            while (!indicator.getIsDone()) {
                analyze();
            }

            // Checking queue for empty again
            while (!queueOfLines.isEmpty()) {
                analyze();
            }

            // Work with received lines
            for (String line: lines) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                while (tokenizer.hasMoreTokens()) {
                    String word = StringUtil.clearPunctuation(tokenizer.nextToken());
                    if (word.trim().length() >= 3) {
                        this.wordsHashMap.put(word, emptyObjectForMap);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error(e);
        }
    }
}
