package org.tumasov.siberteam.test.workers;

import org.apache.log4j.Logger;
import org.tumasov.siberteam.test.services.IDone;
import org.tumasov.siberteam.test.services.StringUtil;

import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ParsingConsumer implements Runnable {
    private static final Logger LOG = Logger.getLogger(ParsingConsumer.class);
    private static final Boolean PRESENT = Boolean.TRUE;
    private final BlockingQueue<String> queueOfLines;
    private final ConcurrentMap<String, Object> wordsHashMap;
    private final IDone indicator;

    public ParsingConsumer(BlockingQueue<String> queueOfLines, ConcurrentMap<String, Object> wordsHashMap, IDone indicator) {
        this.queueOfLines = queueOfLines;
        this.wordsHashMap = wordsHashMap;
        this.indicator = indicator;
    }

    private void analyze() throws InterruptedException {
        String line;
        if ((line = queueOfLines.poll(100, TimeUnit.MILLISECONDS)) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line.trim(), " \t\n\r\f ");
            while (tokenizer.hasMoreTokens()) {
                String word = StringUtil.clearPunctuation(tokenizer.nextToken().trim());
                if (word.trim().length() >= 3) {
                    this.wordsHashMap.putIfAbsent(word, ParsingConsumer.PRESENT);
                }
            }
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
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }
}
