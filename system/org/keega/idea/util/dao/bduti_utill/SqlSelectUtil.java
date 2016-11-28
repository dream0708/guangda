package org.keega.idea.util.dao.bduti_utill;

import org.keega.idea.util.dao.annotation.SqlColumn;
import org.keega.idea.util.dao.interf.StaticResourceProvide;
import org.keega.idea.util.dao.annotation.SqlPrimaryKey;
import org.keega.idea.util.dao.annotation.SqlTableName;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by asus_n56 on 2016/9/16.
 */
@Component("sqlSelectUtil")
public class SqlSelectUtil<T> implements StaticResourceProvide {
    /**
     * 获取要插入的表名
     */
    public String getTableName(Class<T> clz) {
       /* Field[] fields = clz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent())
        }*/
        String tableName = clz.getAnnotation(SqlTableName.class).value();
        if ("".equals(tableName.trim())) {
            tableName = clz.getSimpleName();
        }
        return tableName;
    }



    public String setSqlPrimaryKey(Field field, Method[] methods,Object primaryKey) {
        if (field.isAnnotationPresent(SqlPrimaryKey.class)) {
            String whereCondition = "";
            for (int j = 0; j < methods.length; j++) {
                if (methods[j].getName().startsWith("get")) {
                    String beanField = field.getName().toLowerCase();
                    String sqlGetMehtod = methods[j].getName().substring(3).toLowerCase();
                    if (beanField.equals(sqlGetMehtod)) {
                        String sqlField = field.getAnnotation(SqlPrimaryKey.class).value();
                        whereCondition = Where + sqlField + Equals +  primaryKey ;
                        break;
                    }
                }
            }
            return whereCondition;
        } else {
            return null;
        }
    }

    /**
     * 用来设置实体类的查询字段，运用别名的方式查询
     * @param clz 实体类的class
     * @return 返回要查询的字段集
     */
    public String getQueryFields(Class<T> clz) {
        Field[] fields = clz.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(SqlPrimaryKey.class)) {
                String AnnoPrimaryKeyValue = fields[i].getAnnotation(SqlPrimaryKey.class).value();//因为默认主键名为id所以不用判断空
                if (!"id".equals(AnnoPrimaryKeyValue)) {
                    sb.append(AnnoPrimaryKeyValue).append(" as ").append(fields[i].getName()).append(" ,");
                } else {
                    sb.append(AnnoPrimaryKeyValue).append(" ,");
                }
            }
            if (fields[i].isAnnotationPresent(SqlColumn.class)) {
                String AnnoValue = fields[i].getAnnotation(SqlColumn.class).value();//如果为""，则使用实体类字段名称作为查询字段
                if (!"".equals(AnnoValue.trim())) {
                    sb.append(AnnoValue).append(" as ").append(fields[i].getName()).append(" ,");
                } else {
                    sb.append(fields[i].getName()).append(" ,");
                }
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

}
