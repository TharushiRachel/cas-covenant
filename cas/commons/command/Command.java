package com.itechro.cas.commons.command;

import com.itechro.cas.exception.impl.AppsException;

/**
 * Command Interface
 *
 * @author : chamara
 */
public interface Command<T extends ExecutionContext> {

    public T execute(T context) throws AppsException;
}
