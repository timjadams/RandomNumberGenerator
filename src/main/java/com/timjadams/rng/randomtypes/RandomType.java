/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timjadams.rng.randomtypes;

import java.util.Random;

/**
 *
 * @author me
 */
public abstract class RandomType {
    private static final Random RANDOM_GENERATOR = new Random();

    protected final Random getRandomGenerator() {
        return RANDOM_GENERATOR;
    }

    public abstract String getRandomValue();
}
