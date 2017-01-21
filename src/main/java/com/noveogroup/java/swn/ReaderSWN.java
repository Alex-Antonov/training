package com.noveogroup.java.swn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.noveogroup.java.reader.AReader;
import com.noveogroup.java.threadcallback.ThreadCallBack;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander
 */
public class ReaderSWN extends AReader {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ReaderSWN.class);
    private final List<Object> sharedList;
    private final int SIZE = 3;//Queue length
    private boolean running = false;
    private ThreadCallBack callBack;

    /**
     *
     * @param sharedList
     * @param fileName
     */
    public ReaderSWN(List<Object> sharedList, String fileName, ThreadCallBack callBack) {

        this.sharedList = sharedList;
        this.fileName = fileName;
        running = true;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        LOGGER.info(this + " [STARTED]\n");
        try {

            LOGGER.info(this + " open file: " + fileName);
            file = new File(fileName);
            inputStream = new ObjectInputStream(new FileInputStream(file));
            int cntObj = (Integer) inputStream.readObject();
            for (int j = 0; j < cntObj; j++) {
                produceFile(inputStream.readObject());
                Thread.sleep(DELAY);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ReaderSWN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReaderSWN.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            LOGGER.info(this + " [FINISHED]\n");
            try {
                inputStream.close();
            } catch (IOException ex) {
                //Logger.getLogger(com.noveogroup.java.blockingQueue.ReaderBQ.class.getName()).log(Level.SEVERE, null, ex);
            }
            callBack.deleteReader(this);
        }
    }

    private void produceFile(Object obj) throws InterruptedException {

        //wait if queue is full
        // CR3 better fist block the shared resource, because on wait() this resource is released
        // +
        synchronized (sharedList) {
            while (sharedList.size() >= SIZE) {
                // CR3 it is convenient to use brackets if you use slf4j logger:
                LOGGER.warn("{}: Queue is full, size {}. Wait...", this, sharedList.size());
                sharedList.wait();
            }

            // CR3
            LOGGER.warn("{}: Queue is not full, size {}. Add read object {} into it", this, sharedList.size(), obj);
            sharedList.add(obj);
            sharedList.notifyAll();
        }

//        //producing element and notify consumers
//        synchronized (sharedList) {
//            LOGGER.warn("Adding into shared list: " + obj);
//            sharedList.add(obj);
//            sharedList.notifyAll();
//        }
    }
}
