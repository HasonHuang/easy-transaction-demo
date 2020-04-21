package com.ihason.dtp.easytrans.demos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class BigObjectNoSerializable implements Serializable {

    private Class<?> clazz;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;

    public static BigObjectNoSerializable ofAll() throws NoSuchMethodException {
        return new BigObjectNoSerializable(
                BigObjectNoSerializable.class,
                "setClazz",
                new Class[] {Class.class},
                new Object[]{ new ValueObject()});
    }

    public static BigObjectNoSerializable ofOne() throws NoSuchMethodException {
        return new BigObjectNoSerializable(
                BigObjectNoSerializable.class,
                null,
                null,
                null);
    }

    public static class ValueObject implements Serializable {

    }

}
