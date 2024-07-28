package com.its.issuetrackingservice.domain.commands;

import org.springframework.stereotype.Service;

@Service
public class Invoker {
    private static final String START_COMMAND_FORMAT = "Starting to running the command: %s";
    private static final String FINISH_COMMAND_FORMAT = "Finishing running the command: %s";
    public <R> R run(Command<R> command) {
        logCommand(command, START_COMMAND_FORMAT);
        R result = command.execute();
        logCommand(command, FINISH_COMMAND_FORMAT);
        return result;
    }

    private void logCommand(Command command, String format) {
        System.out.printf((format) + "%n", command.getName());
    }

}
