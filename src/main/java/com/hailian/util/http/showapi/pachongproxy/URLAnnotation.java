package com.hailian.util.http.showapi.pachongproxy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface URLAnnotation {
    String value();
}
