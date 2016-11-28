package org.keega.idea.util.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the default value equals id;
 * and the default Auto_increment is true; 
 * @author asus_n56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlPrimaryKey {
	
	public String value() default "id";
	public boolean Auto_Increment() default true;
	
}
