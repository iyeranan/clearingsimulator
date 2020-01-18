package com.site.construction.data;

import com.site.construction.constants.Command;

//Class storing command history
public class CommandHistory {
    private final Command command;
    private final int steps;

    public CommandHistory(Command command, int steps) {
        this.command = command;
        this.steps = steps;
    }

    public int getSteps() {
        return this.steps;
    }

    public Command getCommand() {
        return this.command;
    }
}
