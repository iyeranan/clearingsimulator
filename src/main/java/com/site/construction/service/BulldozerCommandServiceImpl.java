package com.site.construction.service;

import com.site.construction.constants.Command;
import com.site.construction.data.CommandHistory;
import com.site.construction.validation.ValidationErrors;

import java.util.List;
import java.util.stream.Collectors;

//Service acts as a generate and print command util for the clearing simulator
public class BulldozerCommandServiceImpl implements CommandService {
    public CommandHistory generateCommandFromInput(String input, ValidationErrors validationErrors) {
        Command command = null;
        String stepsInput = null;
        if (input != null && input.trim().length() > 0) {
            if (input.trim().contains(" ")) {
                String[] split = input.trim().split(" ");
                input = split[0];
                stepsInput = split[1];
            }
            for (Command enums : Command.values()) {
                if (enums.toString().equalsIgnoreCase(input.trim()) || enums.getAbbreviation().equalsIgnoreCase(input.trim())) {
                    command = enums;
                    break;
                }
            }
        }
        int steps = 0;
        if (command == null) {
            validationErrors.getErrors().add("ERROR: Invalid command entered");
            return null;
        } else if (Command.ADVANCE.equals(command)) {
            if (stepsInput == null || stepsInput.trim().length() == 0) {
                validationErrors.getErrors().add("ERROR: For Advance commands, please enter steps to advance");
                return null;
            }
            try {
                steps = Integer.parseInt(stepsInput.trim());
            } catch (Exception e) {
                validationErrors.getErrors().add("ERROR: For Advance commands, please enter numeric steps to advance");
                return null;
            }
        }
        return new CommandHistory(command, steps);
    }

    public String printHistory(List<CommandHistory> history) {
        if (history == null || history.size() == 0) {
            return "";
        }
        return history.stream().map(commandHistory -> {
            if (Command.LEFT.equals(commandHistory.getCommand()) || Command.RIGHT.equals(commandHistory.getCommand())) {
                return "turn " + commandHistory.getCommand().toString().toLowerCase();
            }
            if (Command.ADVANCE.equals(commandHistory.getCommand())) {
                return Command.ADVANCE.toString().toLowerCase() + " " + commandHistory.getSteps();
            }
            return commandHistory.getCommand().toString().toLowerCase();
        }).collect(Collectors.joining(", "));
    }
}
