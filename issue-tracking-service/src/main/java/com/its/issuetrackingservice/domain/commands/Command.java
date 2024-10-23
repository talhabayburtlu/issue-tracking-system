package com.its.issuetrackingservice.domain.commands;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Command<R> {
    @Builder.Default
    private Boolean returnResultAfterExecution = Boolean.TRUE;

    public String getName() {
        return getClass().getSimpleName();
    }

    protected abstract void init();
    public abstract R execute();

    public abstract Optional<R> getResult();
}
