package com.juancoob.nanodegree.and.backingapp.domain.executor;

import com.juancoob.nanodegree.and.backingapp.domain.usecase.base.AbstractUseCase;

/**
 * Created by Juan Antonio Cobos Obrero on 17/04/18.
 */
public interface Executor {
    void executor(final AbstractUseCase useCase);
}
