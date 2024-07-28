package com.its.issuetrackingservice.domain.commands;

import org.springframework.stereotype.Service;

@Service
public class Invoker {
    private static final String START_COMMAND_FORMAT = "Starting to running the command: %s";
    private static final String FINISH_COMMAND_FORMAT = "Finishing running the command: %s";
    public void run(Command command) {
        logCommand(command, START_COMMAND_FORMAT);
        command.execute();
        logCommand(command, FINISH_COMMAND_FORMAT);
    }

    private void logCommand(Command command, String format) {
        System.out.printf((format) + "%n", command.getName());
    }

}
