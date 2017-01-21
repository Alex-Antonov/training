package com.noveogroup.java.blockingQueue;

import com.noveogroup.java.statistic.Statistic;
import java.util.concurrent.BlockingQueue;

import com.noveogroup.java.validation.exception.ValidationException;
import com.noveogroup.java.validationFactory.ValidationProcess;
import com.noveogroup.java.worker.AWorker;
import static java.lang.Thread.State.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander
 */
public class WorkerBQ extends AWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBQ.class);
    private static final int DELAY = 1000;

    private final BlockingQueue sharedQueue;

    public WorkerBQ(BlockingQueue sharedQueue, Statistic statistic) {
        this.sharedQueue = sharedQueue;
        this.statistic = statistic;
    }

    // CR2 Do not forget to use @Override annotation
    @Override
    public void run() {
        LOGGER.info(this + " [STARTED]\n");
        try {

            while (!terminated) {

                // Perform this only if we got an object from the queue. If there is not any object - just wait
                if (!sharedQueue.isEmpty()) {
                    final Object obj = sharedQueue.take();
                    consumeObjects(obj);
                }

                Thread.sleep(DELAY);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sharedQueue.clear();
            LOGGER.info(this + " [FINISHED]");
        }

    }   

    private synchronized void consumeObjects(final Object obj) throws InterruptedException {
        // CR2 field access is not a good approach in Java        
        // CR2 What is it???
        // You are already in thread why so you need anther thread (timer) inside? You can just use Thread.sleep(1000);
        // Please refactor this and git rid of timer in superclass
        // Please check the com.noveogroup.java.blockingQueue.WorkerExample

        try {
            ValidationProcess.Validate(obj);
            statistic.addValid(obj);
        } catch (ValidationException e) {
            statistic.addInvalid(obj);
        }

    }
}
