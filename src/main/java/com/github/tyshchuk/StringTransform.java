package com.github.tyshchuk;

@FunctionalInterface
public interface StringTransform<T> {
    T apply(String value);
}
