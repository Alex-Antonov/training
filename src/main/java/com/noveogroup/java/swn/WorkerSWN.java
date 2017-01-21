package com.noveogroup.java.swn;

import java.util.List;

import com.noveogroup.java.statistic.Statistic;
import com.noveogroup.java.validation.exception.ValidationException;
import com.noveogroup.java.validationFactory.ValidationProcess;
import com.noveogroup.java.worker.AWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander
 */
public class WorkerSWN extends AWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerSWN.class);
    private static final int DELAY = 1000;
    private final List<Object> sharedList;

    // CR3 Seems it can be removed? Never leave a legacy code commented. If you use Version Control system like
    // git the changes history is always acceptable in it       

    public WorkerSWN(List<Object> sharedList, Statistic statistic) {
        this.sharedList = sharedList;
        terminated = false;
        this.statistic = statistic;
    }

    @Override
    public void run() {
        LOGGER.info(this + " [STARTED]\n");
        while (!terminated) {
            try {
                while (!terminated) {
                    // CR3 Please compare your implementation and the method consume()
//                    // Perform this only if we got an object from the queue. If there is not any object - just wait
//                    synchronized (sharedList) {
//                        if (!sharedList.isEmpty()) {
//                            LOGGER.warn(this + ": Consuming object from list");
//                            final Object obj = sharedList.remove(0);
//                            consumeObjects(obj);
//                            sharedList.wait();
//                        } else {
//                            LOGGER.warn(this + ": List is empty");
//                            sharedList.notifyAll();
//                        }
//                    }

                    // CR3 Please check the following implementation
                    consume();
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException ex) {
                //Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        LOGGER.info(this + " [FINISHED]\n");
    }

    private void consume() throws InterruptedException {
        Object readObject;
        // CR3 Block the shared resource
        synchronized (sharedList) {
            // CR3 Wait until shared list is empty
            while(sharedList.isEmpty()) {
                LOGGER.warn("{}: List is empty, wait...", this);
                sharedList.wait();
            }

            // CR3 Here we have locked not empty shared resource, log its size
            LOGGER.warn("{}: List is not empty, size: {}, consume...", this, sharedList.size());

            // CR3 Get object from the resource and release the resource notifying all others awaiting threads
            readObject = sharedList.remove(0);
            sharedList.notifyAll();
        }
        // CR3 here validate the read object
        // +
        consumeObjects(readObject);
    }

    private void consumeObjects(final Object obj) throws InterruptedException {

        // CR2 the same, please check WorkerBQ   
        try {
            ValidationProcess.Validate(obj);
            // CR2 wait, notify, notifyAll?
            statistic.addValid(obj);
        } catch (ValidationException e) {
            // CR2 wait, notify, notifyAll?
            statistic.addInvalid(obj);
        }

    }   
}
