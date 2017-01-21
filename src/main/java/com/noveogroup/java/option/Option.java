package com.noveogroup.java.option;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author зс
 */
public class Option {

    private static final String PROPERTIES_FILE = "./properties.txt";

    // CR3 this value should be moved into property file because the amount of classes to serialize is limitted
    // by your imlementation and if the user change the value to, for eg, 10 the wrong readers will be created
    
    //If you mean value COUNT_OF_CLASSES, it has been included in property file.
    //all this values are there
    public static int COUNT_OF_CLASSES;
    public static String[] CLASS_NAME;
    public static int[] COUNT_OF_OBJ;
    public static boolean BLOCKING_QUEUE;
    public static int COUNT_OF_WORKERS;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);
            //Here it is readed
            COUNT_OF_CLASSES = Integer.parseInt(properties.getProperty("COUNT_OF_CLASSES"));
            CLASS_NAME = properties.getProperty("CLASS_NAME").split(";");
            String[] objCnt = properties.getProperty("COUNT_OF_OBJ").split(";");

            COUNT_OF_OBJ = new int[objCnt.length];

            for (int i = 0; i < objCnt.length; i++) {
                COUNT_OF_OBJ[i] = Integer.parseInt(objCnt[i]);
            }

            BLOCKING_QUEUE = Boolean.parseBoolean(properties.getProperty("BLOCKING_QUEUE"));
            System.out.println("BLOCKING_QUEUE = " + BLOCKING_QUEUE);
            COUNT_OF_WORKERS = Integer.parseInt(properties.getProperty("COUNT_OF_WORKERS"));

        } catch (FileNotFoundException ex) {
            System.err.println("Properties config file not found");
        } catch (IOException ex) {
            System.err.println("Error while reading file");
        } finally {
            try {
                propertiesFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
