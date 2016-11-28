package org.keega.idea.util.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this Annotation value is use to match 
 * database field , the default value equals "",
 * if use default value , then the database table field 
 * would use this bean field match .
 * @author asus_n56
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlColumn {
	
	//public String columnName()  default "";
	public String value() default "";
}
