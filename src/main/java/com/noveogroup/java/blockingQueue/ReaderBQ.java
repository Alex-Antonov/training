package com.noveogroup.java.blockingQueue;

import com.noveogroup.java.reader.AReader;
import com.noveogroup.java.swn.ReaderSWN;
import com.noveogroup.java.threadcallback.ThreadCallBack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Math.*;
import static java.lang.Thread.sleep;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexander
 */
public class ReaderBQ extends AReader {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ReaderBQ.class);
    private ThreadCallBack callBack;
    private final BlockingQueue sharedQueue;

    public ReaderBQ(BlockingQueue sharedQueue, String fileName, final ThreadCallBack callBack) {
        this.sharedQueue = sharedQueue;
        this.fileName = fileName;
        this.callBack = callBack;
    }

    public void run() {
        LOGGER.info(this + " [STARTED]\n");
        try {

            file = new File(fileName);
            inputStream = new ObjectInputStream(new FileInputStream(file));
            final int cntObj = (Integer) inputStream.readObject();

            for (int j = 0; j < cntObj; j++) {
                sharedQueue.put(inputStream.readObject());
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                }
            }
            // CR1 It is not a correct way to close a stream. Please check how streams should be closed
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderBQ.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderBQ.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReaderBQ.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReaderBQ.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                LOGGER.info(this + " [FINISHED]\n");
                callBack.deleteReader(this);
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ReaderBQ.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
