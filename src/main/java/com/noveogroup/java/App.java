package com.noveogroup.java;

import com.noveogroup.java.util.ObjectGenerator;
import com.noveogroup.java.util.ThreadActivator;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world.
 *
 * @author username
 */
public final class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static ThreadActivator activator = new ThreadActivator();

    private App() {

    }

    /**
     * Enter point in application.
     *
     * @param args application parameters
     */
    public static void main(final String[] args) {
        final String greeting = "Hello World!";
        System.out.println(greeting);
        LOG.info(greeting);
        // CR2 Please remove old code
        ObjectGenerator objGen = new ObjectGenerator();
        objGen.generateClient();
        objGen.generateNakl();
        objGen.generateProducer();

        activator.start();
    }
}
