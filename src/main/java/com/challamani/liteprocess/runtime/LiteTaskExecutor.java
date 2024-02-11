package com.challamani.liteprocess.runtime;


public interface LiteTaskExecutor<T> {

    void execute(T task);
}
