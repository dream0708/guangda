package org.keega.idea.util.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the sql table name ;if don't set the value ,
 * and use the default "", then the sql table name use by the class name;
 * @author asus_n56
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SqlTableName {
	public String value();
}
