package com.fh.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation {

    String headName();
    int order();
    String datePattern() default "yyyyMMdd";
    enum DataType {
        String,Number,Date,
    }
    /**
     * 数据类型，可以是String，Number(数字型)，Date等类型
     * @return
     */
    DataType type() default DataType.String;

}
