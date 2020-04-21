package com.ihason.dtp.easytrans.demos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class BigObject implements Serializable {

    private Long id;

    private String name;

    private BigObjectNoSerializable another;


}
