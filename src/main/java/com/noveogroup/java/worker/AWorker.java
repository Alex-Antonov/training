package com.noveogroup.java.worker;

import com.noveogroup.java.statistic.Statistic;

/**
 *
 * @author зс
 */
public abstract class AWorker extends Thread {

    // CR2 Please fix this unchecked assignment (ArrayList should be generalized)
    protected Statistic statistic;
    protected boolean terminated;

    public synchronized void terminate() {
        terminated = true;
    }
}
