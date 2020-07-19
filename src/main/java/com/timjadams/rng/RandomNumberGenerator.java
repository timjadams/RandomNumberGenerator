/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timjadams.rng;

import java.awt.EventQueue;

/**
 * @author Tim J. Adams
 */
public class RandomNumberGenerator {
    public static void main(final String... args) {
        EventQueue.invokeLater(() -> {
            final RandomNumberGeneratorUI rngui = new RandomNumberGeneratorUI();
            rngui.run();
        });
    }
}
