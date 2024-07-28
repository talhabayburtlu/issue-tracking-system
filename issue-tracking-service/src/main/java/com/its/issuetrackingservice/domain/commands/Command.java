package com.its.issuetrackingservice.domain.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@SuperBuilder
public abstract class Command<R> {
    private Boolean returnResultAfterExecution = Boolean.TRUE;

    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract R execute();

    public abstract Optional<R> getResult();
}
