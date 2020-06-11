package com.tanglei.springbootstudydemo.global.myannotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
    String [] params ();
}
