package com.site.construction.constants;

import java.util.HashMap;
import java.util.Map;

//commands given to the simulator
public enum Command {
    ADVANCE("A"), LEFT("L"), RIGHT("R"), QUIT("Q");
    private final String abbreviation;

    private static final Map<String, Command> LOOKUP_BY_ABBR;

    static {
        LOOKUP_BY_ABBR = new HashMap<String, Command>();
        for (Command command : Command.values()) {
            LOOKUP_BY_ABBR.put(command.getAbbreviation(), command);
        }
    }

    Command(String abbreviation){
        this.abbreviation = abbreviation;
    }
    public String getAbbreviation(){
        return this.abbreviation;
    }

    public static Command retrieveByAbbreviation(String abbreviation) {
        if(abbreviation==null){
            return null;
        }
        return LOOKUP_BY_ABBR.get(abbreviation.toUpperCase());
    }
}
