/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timjadams.rng.randomtypes;

/**
 *
 * @author me
 */
public class RandomInteger extends RandomType {
    @Override
    public String getRandomValue() {
        final int randomInt = getRandomGenerator().nextInt();
        return Integer.toString(randomInt);
    }
}
