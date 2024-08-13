package com.its.issuetrackingservice.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Command<R> {
    private Boolean returnResultAfterExecution = Boolean.TRUE;

    public String getName() {
        return getClass().getSimpleName();
    }

    protected abstract void init();

    protected abstract R execute();

    public abstract Optional<R> getResult();
}
