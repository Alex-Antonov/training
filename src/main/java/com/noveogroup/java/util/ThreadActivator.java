package com.noveogroup.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.noveogroup.java.blockingQueue.ReaderBQ;
import com.noveogroup.java.blockingQueue.WorkerBQ;
import com.noveogroup.java.option.Option;
import com.noveogroup.java.reader.AReader;
import com.noveogroup.java.statistic.Statistic;
import com.noveogroup.java.swn.ReaderSWN;
import com.noveogroup.java.swn.WorkerSWN;
import com.noveogroup.java.threadcallback.ThreadCallBack;
import com.noveogroup.java.worker.AWorker;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class ThreadActivator implements ThreadCallBack {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ThreadActivator.class);
    private static final boolean blockingQueueType = Option.BLOCKING_QUEUE;
    private static final int workerCount = Option.COUNT_OF_WORKERS;
    private static BlockingQueue sharedQueue = new LinkedBlockingQueue();
    private static ArrayList<Object> sharedList = new ArrayList<Object>();
    private static final String format = ".txt";
    private static Statistic statistic;
    private static List<AReader> reader = new ArrayList<>();
    private static List<AWorker> worker = new ArrayList<>();
    private final long DELAY = 2000;

    public void start() {
        statistic = new Statistic();
        if (blockingQueueType) {
            LOGGER.info("WE ARE IN BLOCKING QUEUE ACTIVATOR");
            for (int i = 0; i < Option.COUNT_OF_CLASSES; i++) {
                reader.add(new ReaderBQ(sharedQueue, Option.CLASS_NAME[i] + format, this));
                reader.get(i).start();
            }
            for (int i = 0; i < workerCount; i++) {
                worker.add(new WorkerBQ(sharedQueue, statistic));
                worker.get(i).start();
            }
        } else {
            LOGGER.info("WE ARE IN SWN ACTIVATOR");
            for (int i = 0; i < Option.COUNT_OF_CLASSES; i++) {
                reader.add(new ReaderSWN(sharedList, Option.CLASS_NAME[i] + format, this));
                reader.get(i).start();
            }

            for (int i = 0; i < workerCount; i++) {
                worker.add(new WorkerSWN(sharedList, statistic));
                worker.get(i).start();
            }
        }
    }

    public synchronized void deleteReader(final Thread thread) {
        reader.remove(thread);
        if (reader.size() == 0) {
            finish();
        }
    }

    private synchronized void finish() {
        // CR3 ??? your sharedList is not blocked here!
        // Is it correct way or i should synch by specific shared resource?
        synchronized (sharedQueue) {
            while (!sharedQueue.isEmpty()) {
                LOGGER.warn("sharedQueue size: " + sharedQueue.size());
            }
        }
        synchronized (sharedList) {
            while (!sharedList.isEmpty()) {
                LOGGER.warn("sharedList size: " + sharedList.size());
            }
        }


        for (final AWorker thread : worker) {
            thread.terminate();
            thread.interrupt();
        }

        try {
            //getting time to finish validation of last obj from queue
            Thread.sleep(DELAY);
        } catch (InterruptedException ex) {
            //Logger.getLogger(ThreadActivator.class.getName()).log(Level.SEVERE, null, ex);
        }

        statistic.printStatistic();
    }
}
