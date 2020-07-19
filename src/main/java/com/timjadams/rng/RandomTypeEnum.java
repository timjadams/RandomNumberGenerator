/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timjadams.rng;

import com.timjadams.rng.randomtypes.RandomInteger;
import com.timjadams.rng.randomtypes.RandomNaturalInteger;
import com.timjadams.rng.randomtypes.RandomType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tim J. Adams
 */
public enum RandomTypeEnum {
    INTEGER("Integer", new RandomInteger()),
    NATURAL_INTEGER("Natural Integer", new RandomNaturalInteger());

    private static final Map<String, RandomTypeEnum> descriptionToValueMap = new HashMap<>();

    public static RandomTypeEnum getValueFromDescription(final String description) {
        return descriptionToValueMap.get(description);
    }

    private final String _description;
    private final RandomType _randomType;

    static {
        for (final RandomTypeEnum rt : RandomTypeEnum.values()) {
            RandomTypeEnum.descriptionToValueMap.put(rt.getDescription(), rt);
        }
    }

    RandomTypeEnum(final String description, final RandomType randomType) {
        _description = description;
        _randomType = randomType;
    }

    public String getDescription() {
        return _description;
    }

    public String generateRandomValue() {
        return _randomType.getRandomValue();
    }
}
