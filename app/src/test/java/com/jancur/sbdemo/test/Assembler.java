/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.test;

/**
 * Base Assembler class.
 * 
 * @param <T>
 */
public abstract class Assembler<T extends Object> {

    private T internalObject;

    /**
     * 
     * @return T
     */
    public final T assemble() {
        if (null == internalObject) {
            internalObject = internalAssemble();
        }
        return internalObject;
    }

    /**
     * 
     * @return T
     */
    protected final T getInternalObject() {
        return internalObject;
    }

    /**
     * 
     * @return T
     */
    protected abstract T internalAssemble();
}
