package com.site.construction.constants;

import java.util.HashMap;
import java.util.Map;

//Each square in a site map would be one of these block types
public enum Block {
    PLAIN_LAND(1, 'o'), CLEARED_LAND(1, 'c'), ROCKY_LAND(2,'r'), TREE_LAND(2,'t'), PRESERVED_LAND(0,'T');
    private static final Map<Character, Block> LOOKUP_BY_VALUE;

    static {
        LOOKUP_BY_VALUE = new HashMap();
        for (Block block : Block.values()) {
            LOOKUP_BY_VALUE.put(block.getValue(), block);
        }
    }

    private final int fuelUnits;
    private final char value;
    Block(int fuelUnits, char value){
        this.fuelUnits = fuelUnits;
        this.value = value;
    }

    public int getFuelUnits() {
        return this.fuelUnits;
    }

    public char getValue() {
        return this.value;
    }

    public static Block retrieveByValue(char value) {
        return LOOKUP_BY_VALUE.get(value);
    }
}
