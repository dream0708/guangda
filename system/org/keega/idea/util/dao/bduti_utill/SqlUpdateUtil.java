package org.keega.idea.util.dao.bduti_utill;

import org.keega.idea.util.dao.interf.StaticResourceProvide;
import org.keega.idea.util.dao.annotation.SqlColumn;
import org.keega.idea.util.dao.annotation.SqlPrimaryKey;
import org.keega.idea.util.dao.annotation.SqlTableName;
import org.keega.idea.util.dao.debug.DebugController;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by asus_n56 on 2016/9/15.
 */
@Component("sqlUpdateUtil")
public class SqlUpdateUtil<T> implements StaticResourceProvide{

    /**
     * 获取要插入的表名
     */
    public String getTableName(T t) {
        String tableName = t.getClass().getAnnotation(SqlTableName.class).value();
        if ("".equals(tableName.trim())) {
            tableName = t.getClass().getSimpleName();
        }
        return tableName;
    }

    public String getUpdateSql(T t,String tableName) {
        Field[] fields = t.getClass().getDeclaredFields();
        Method[] methods = t.getClass().getDeclaredMethods();
        StringBuffer stringBuffer = new StringBuffer(Update);
        stringBuffer.append(tableName).append(Set);
        String whereCondition = "";
        for (int i = 0; i < fields.length; i++) {
            setSqlFieldAndValue(fields[i], stringBuffer, methods, t);
        }
        for (int i = 0; i < fields.length; i++) {
            whereCondition = setSqlPrimaryKey(fields[i],methods,t);
            break;
        }
        String sql = stringBuffer.substring(0, stringBuffer.length() - 1) + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return sql;
    }


    public String getWhereUpdateSql(T t,String tableName,String whereCondition) {
        Field[] fields = t.getClass().getDeclaredFields();
        Method[] methods = t.getClass().getDeclaredMethods();
        StringBuffer stringBuffer = new StringBuffer(Update);
        stringBuffer.append(tableName).append(Set);
        for (int i = 0; i < fields.length; i++) {
            setSqlFieldAndValue(fields[i], stringBuffer, methods, t);
        }
        String sql = stringBuffer.substring(0, stringBuffer.length() - 1) + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return sql;
    }


    private void setSqlFieldAndValue(Field field,StringBuffer stringBuffer,Method[] methods,T t) {
        if (field.isAnnotationPresent(SqlColumn.class)) {
            for (int j = 0; j < methods.length; j++) {
                if (methods[j].getName().startsWith("get")) {//get方法开头的方法
                    String beanField = field.getName().toLowerCase();//实体类字段全部转成小写
                    String sqlGetMehtod = methods[j].getName().substring(3).toLowerCase();//get开头并且去掉get之后全部转成小写
                    if (beanField.equals(sqlGetMehtod)) {//如果实体类字段与方法字段一致
                        try {
                            String sqlField = field.getAnnotation(SqlColumn.class).value();//数据库字段的值
                            Object o = methods[j].invoke(t);//get出实体类属性的值
                            if (!"".equals(sqlField.trim())) {//判断是否设置数据库字段的值
                                if (o != null) {//如果值不为空
                                    String valueType = o.getClass().getSimpleName();//获取这个从实体类get出来的值的类型的string名
                                    if (valueType.equals("String")) {
                                        stringBuffer.append(sqlField).append(Equals).append(_o).append(o).append(_o).append(_J);
                                    } else if (valueType.equals("Integer")) {
                                        stringBuffer.append(sqlField).append(Equals).append(o).append(_J);
                                    } else if (valueType.equals("Long")) {
                                        stringBuffer.append(sqlField).append(Equals).append(o).append(_J);
                                    } else if (valueType.equals("Date")) {
                                        stringBuffer.append(sqlField).append(Equals).append(_o).append(o).append(_o).append(_J);
                                    } else {
                                        //如果都不是以上类型，这指定这个类型
                                        stringBuffer.append(sqlField).append(Equals).append(_o).append(o).append(_o).append(_J);
                                    }
                                }
                            } else {
                                if (o != null) {//如果值不为空
                                    String valueType = o.getClass().getSimpleName();//获取这个从实体类get出来的值的类型的string名
                                    if (valueType.equals("String")) {
                                        stringBuffer.append(field.getName()).append(Equals).append(_o).append(o).append(_o).append(_J);
                                    } else if (valueType.equals("Integer")) {
                                        stringBuffer.append(field.getName()).append(Equals).append(o).append(_J);
                                    } else if (valueType.equals("Long")) {
                                        stringBuffer.append(field.getName()).append(Equals).append(o).append(_J);
                                    } else if (valueType.equals("Date")) {
                                        stringBuffer.append(field.getName()).append(Equals).append(_o).append(o).append(_o).append(_J);
                                    } else {
                                        //如果都不是以上类型，这指定这个类型
                                        stringBuffer.append(field.getName()).append(Equals).append(_o).append(o).append(_o).append(_J);
                                    }
                                }
                            }
                            break;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    private String setSqlPrimaryKey(Field field,Method[] methods,T t) {
        String whereCondition = "";
        if (field.isAnnotationPresent(SqlPrimaryKey.class)) {
            for (int j = 0; j < methods.length; j++) {
                if (methods[j].getName().startsWith("get")) {
                    String beanField = field.getName().toLowerCase();
                    String sqlGetMehtod = methods[j].getName().substring(3).toLowerCase();
                    if (beanField.equals(sqlGetMehtod)) {
                        try {
                            String sqlField = field.getAnnotation(SqlPrimaryKey.class).value();
                            Object o = methods[j].invoke(t);
                            whereCondition = Where + sqlField + Equals + _o+o+_o;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        return whereCondition;
    }
}
