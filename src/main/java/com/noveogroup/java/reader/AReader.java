package com.noveogroup.java.reader;

import java.io.File;
import java.io.ObjectInputStream;

/**
 *
 * @author зс
 */
public abstract class AReader extends Thread {

    protected String fileName;
    protected ObjectInputStream inputStream;
    protected File file;
    protected static final int DELAY = 500;
}
