package com.noveogroup.java.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.noveogroup.java.option.Option;
import com.noveogroup.java.pojo.Client;
import com.noveogroup.java.pojo.Nakl;
import com.noveogroup.java.pojo.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class ObjectGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectGenerator.class);

    private ObjectOutputStream outputStream;
    public File file;
    private String[] ClassName = Option.CLASS_NAME;
    // CR1 Bad practice. Never use file path delimiters like this. This is not crossplatform solution
    // Check File.separator
    // Also I propose to use a property file with all settings and try to use relative paths
    private final String fileType = ".txt";
    private boolean toInit = true;

    // CR1 this 10 is better to define as a constant, like:
    // public static final int MAX_AMOUNT = 10;
    // private int cntOfOnjects = MAX_AMOUNT;
    // Also this value can be put into settings property file
    private int[] cntOfObjects = Option.COUNT_OF_OBJ;
    private static List<Object> list = new ArrayList();

    public ObjectGenerator() {

    }

    public void generateClient() {
        for (int i = 0; i < cntOfObjects[0]; i++) {
            Client client = new Client();
            if (toInit) {
                if (Math.random() > 0.5) {
                    ValueGenerator valGen = new ValueGenerator();
                    client.setBalance(valGen.nextInt(4));
                    client.setCity(valGen.nextWord(10));
                    client.setID(valGen.nextInt());
                    client.setName(valGen.nextWord(5));
                    list.add(client);
                } else {
                    generateValidClient();
                }

            }

        }

        serialize(ClassName[0]);
    }

    public void generateNakl() {
        for (int i = 0; i < cntOfObjects[1]; i++) {
            Nakl nakl = new Nakl();
            if (toInit) {
                if (Math.random() > 0.5) {
                    ValueGenerator gen = new ValueGenerator();
                    nakl.setDate(gen.nextDate());
                    nakl.setGood(gen.nextWord(7));
                    nakl.setID(gen.nextInt());
                    nakl.setName(gen.nextWord(5));
                    list.add(nakl);
                } else {
                    generateValidNakl();
                }

            }

        }

        serialize(ClassName[1]);
    }

    public void generateProducer() {
        for (int i = 0; i < cntOfObjects[2]; i++) {
            Producer producer = new Producer();
            if (toInit) {
                if (Math.random() > 0.5) {
                    ValueGenerator gen = new ValueGenerator();
                    producer.setCity(gen.nextWord(10));
                    producer.setID(gen.nextInt());
                    producer.setName(gen.nextWord(5));
                    list.add(producer);
                } else {
                    generateValidProducer();
                }
            }

        }

        serialize(ClassName[2]);
    }

    public void setInit(boolean toInit) {
        this.toInit = toInit;
    }

    private void serialize(final String fileName) {
        try {
            file = new File((fileName + fileType));

            outputStream = new ObjectOutputStream(
                    new FileOutputStream(file));

            outputStream.writeObject(list.size());

            for (final Object object : list) {
                // Lets log what we are going to serialize
                LOGGER.warn("Serialize object: {}", object);
                outputStream.writeObject(object);
            }

            outputStream.flush();
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
            list.clear();
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
               LOGGER.error("Cannot serialize object", ObjectGenerator.class.getName(), ex);
            }

        }

    }

    private static void generateValidProducer() {
        Producer producer = new Producer();
        producer.setName("abcdef");
        producer.setCity("abcdef");
        producer.setID(1);
        list.add(producer);
    }

    private static void generateValidNakl() {
        Nakl nakl = new Nakl();
        Date date = (Date) java.util.Calendar.getInstance().getTime();
        nakl.setDate(date);
        nakl.setGood("abcd");
        nakl.setID(1);
        nakl.setName("abcd012");
        list.add(nakl);
    }

    private static void generateValidClient() {
        Client client = new Client();
        Date date = (Date) java.util.Calendar.getInstance().getTime();

        client.setBalance(11);
        client.setID(1);
        client.setCity("abcd");
        client.setName("abcd");
        list.add(client);
    }
}
