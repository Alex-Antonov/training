/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noveogroup.java.statistic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class Statistic {

    private static final Logger LOGGER = LoggerFactory.getLogger(Statistic.class);
    private List<Object> invalid;
    private List<Object> valid;

    public Statistic() {
        invalid = new ArrayList();
        valid = new ArrayList();
    }

    public synchronized void addInvalid(final Object obj) {
        invalid.add(obj);
    }

    public synchronized void addValid(final Object obj) {
        valid.add(obj);
    }

    public List getInvalidList() {
        return invalid;
    }

    public List getValidList() {
        return valid;
    }

    public void printStatistic() {

        LOGGER.info("\n--------------\nHave been validated " + valid.size() + ": ");

        for (final Object object : valid) {
            System.out.println(object);
        }

        LOGGER.info("\n--------------\nHave not been validated " + invalid.size() + ": ");

        for (final Object object : invalid) {
            System.out.println(object);
        }

    }
}
