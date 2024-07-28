package com.its.issuetrackingservice.domain.commands;

import java.util.Optional;

public interface Command {
    void execute();

    String getName();
    Optional<?> getResult();
}
