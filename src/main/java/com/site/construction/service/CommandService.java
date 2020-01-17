package com.site.construction.service;

import com.site.construction.data.CommandHistory;
import com.site.construction.validation.ValidationErrors;

import java.util.List;

public interface CommandService {
    CommandHistory generateCommandFromInput(String input, ValidationErrors validationErrors);

    String printHistory(List<CommandHistory> history);
}
