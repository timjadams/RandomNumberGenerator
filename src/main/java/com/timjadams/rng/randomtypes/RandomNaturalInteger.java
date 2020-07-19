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
public class RandomNaturalInteger extends RandomType {
    @Override
    public String getRandomValue() {
        final int randomInt = getRandomGenerator().nextInt();
        final int randomNaturalInt = Math.abs(randomInt);
        return Integer.toString(randomNaturalInt);
    }
}
