package org.tumasov.siberteam.test.workers;

import org.apache.log4j.Logger;
import org.tumasov.siberteam.test.services.IDone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BlockingQueue;

public class ParsingProducer implements Runnable, IDone {
    private static final Logger LOG = Logger.getLogger(ParsingProducer.class);
    private final BlockingQueue<String> queueOfLines;
    private boolean isDone = false;
    private final URL url;

    public ParsingProducer(BlockingQueue<String> queueOfLines, URL url) {
        this.queueOfLines = queueOfLines;
        this.url = url;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                queueOfLines.put(line);
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            isDone = true;
            try {
                bufferedReader.close();
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }
}
