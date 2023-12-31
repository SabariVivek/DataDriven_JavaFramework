package com.project.datadriventesting.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonPath {

    /**
     * Only filename should be given since the default location of the file will be considered as "src/test/resource/testdata/"...
     */
    String fileName();
}