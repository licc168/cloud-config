package com.licc.dove.dao.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * @author yexinzhou
 * 表示字段的值由数据库序列的方式
 * 需要和@Id注解一起使用才有效
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sequence {
    /**
     * 产生id的函数名称，只对isSharding()==false有效
     */
	String name() default "MYCAT_SEQ_NEXTVAL";
	
	/**
	 * 是否使用分库分表
	 */
	boolean isSharding() default false;
}
