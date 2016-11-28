package org.keega.idea.util.dao.bduti_utill;

import org.keega.idea.util.dao.annotation.SqlColumn;
import org.keega.idea.util.dao.annotation.SqlPrimaryKey;
import org.keega.idea.util.dao.annotation.SqlTableName;
import org.keega.idea.util.dao.debug.DebugController;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component("sqlAddUtil")
public class SqlAddUtil<T> {


    @Inject
    private JdbcTemplate jdbcTemplate;//spring上下文必须配置依赖注入jdbcTemplate
    private static final String Values = " values ";
    private static final String Insert_Into = "insert into ";

    public List<Field> changeArrayFields2List(Field[] fields) {
        List<Field> fieldsList = new ArrayList<Field>();
        for (int i = 0; i < fields.length; i++) {
            fieldsList.add(fields[i]);
        }
        return fieldsList;
    }

    public List<Field> RemoveExcludeField(String[] excludeField, List<Field> fieldsList) {
        for (int i = 0; i < excludeField.length; i++) {
            for (int j = 0; j < fieldsList.size(); j++) {
                if (fieldsList.get(j).getName().equals(excludeField[i].trim())) {
                    fieldsList.remove(j);
                    break;
                }
            }
        }
        return fieldsList;
    }

    public void handerSqlExcludeField(String[] excludeField, Method[] methods, Field[] fields, StringBuffer fieldSet,
                                      StringBuffer valueSet, T t) {
        List<Field> fieldsList = changeArrayFields2List(fields);
        fieldsList = RemoveExcludeField(excludeField, fieldsList);
        for (int i = 0; i < fieldsList.size(); i++) {
            Field ff = fieldsList.get(i);
            if (isSqlColumnAnnotation(ff)) {
                for (int j = 0; j < methods.length; j++) {
                    if (isFieldMatchMethodName(t, ff, methods[j])) {// 对应字段不能为空
                        // Annotaion的值
                        String fieldName = ff.getAnnotation(SqlColumn.class).value();
                        setFieldAndValue(fieldName, fieldSet, valueSet, ff, methods[j], t);
                        break;// 只要找到了一个对应的值，就跳出内循环
                    }
                }
            }

        }
    }

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

    public boolean isSqlColumnAnnotation(Field f) {
        if (f.isAnnotationPresent(SqlColumn.class)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFieldMatchMethodName(T t, Field f, Method m) {
        // get开头的方法，并且转换字段和方法名小写进行比较
        try {
            if (m.getName().startsWith("get")
                    && m.getName().substring(3).toLowerCase().equals(f.getName().toLowerCase())
                    && m.invoke(t) != null) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setFieldAndValue(String fieldName, StringBuffer fieldSet, StringBuffer valueSet, Field f, Method m,
                                 T t) {// 检查annotation里面的value是否为原始""字符串
        try {
            if ("".equals(fieldName.trim())) {// 如果为默认的空字符串
                fieldSet.append(f.getName()).append(",");
                setInsertValue(valueSet, m, t);
            } else {// 如果设置初始值
                fieldSet.append(fieldName).append(",");
                setInsertValue(valueSet, m, t);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 这是插入参数的值的类型判断
    public void setInsertValue(StringBuffer valueSet, Method m, T t)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String valueType = m.invoke(t).getClass().getSimpleName();// 插入的值类型
        if (valueType.equals("String")) {
            valueSet.append("'").append(m.invoke(t)).append("',");
        } else if (valueType.equals("Integer")) {
            valueSet.append(m.invoke(t)).append(",");
        } else if (valueType.equals("Long")) {
            valueSet.append(m.invoke(t)).append(",");
        } else if (valueType.equals("Date")) {
            valueSet.append("'").append(m.invoke(t)).append("',");
        }

    }

    public void handerSql(Method[] methods, Field[] fields, StringBuffer fieldSet, StringBuffer valueSet, T t) {
        for (int i = 0; i < fields.length; i++) {
            if (isSqlColumnAnnotation(fields[i])) {
                for (int j = 0; j < methods.length; j++) {
                    if (isFieldMatchMethodName(t, fields[i], methods[j])) {// 对应字段不能为空
                        // Annotaion的值
                        String fieldName = fields[i].getAnnotation(SqlColumn.class).value();
                        setFieldAndValue(fieldName, fieldSet, valueSet, fields[i], methods[j], t);
                        break;// 只要找到了一个对应的值，就跳出内循环
                    }
                }
            }
        }
    }

    public void handerPrimaryKey(StringBuffer fieldSet, StringBuffer valueSet, Field[] fields, String tableName) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(SqlPrimaryKey.class)) {
                String primaryKey = fields[i].getAnnotation(SqlPrimaryKey.class).value();//默认值为id
                boolean isAutoIncrement = fields[i].getAnnotation(SqlPrimaryKey.class).Auto_Increment();
                if (!isAutoIncrement) {//如果主键不是自增
                    //int rowCount = this.jdbcTemplate.queryForObject("select count(*) from t_actor", Integer.class);
                    int count = jdbcTemplate.queryForObject("select count(*) from " + tableName + "", Integer.class);
                    if (count < 1) {
                        fieldSet.append(primaryKey).append(",");
                        valueSet.append((1) + ",");
                    } else {
                        Integer in = jdbcTemplate.queryForObject("select max(" + primaryKey + ") from " + tableName, Integer.class);
                        fieldSet.append(primaryKey).append(",");
                        valueSet.append(in + 1).append(",");
                    }
                }//如果是自增的那就不用插入主键字段
            }
        }
    }

    public String getInsertSql(T t) {
        Method[] methods = t.getClass().getDeclaredMethods();// 把方法名全部转成小写，再去get，用来属性字段比较
        Field[] fields = t.getClass().getDeclaredFields();
        StringBuffer fieldSet = new StringBuffer(" (");
        StringBuffer valueSet = new StringBuffer(" (");
        String tableName = this.getTableName(t);
        this.handerPrimaryKey(fieldSet, valueSet, fields, tableName);
        this.handerSql(methods, fields, fieldSet, valueSet, t);//非主键部分
        String f = fieldSet.substring(0, fieldSet.length() - 1) + ")";
        String v = valueSet.substring(0, valueSet.length() - 1) + ")";
        if (DebugController.isDebugOpent) System.out.println(Insert_Into + tableName + f + Values + v);
        return Insert_Into + tableName + f + Values + v;
    }

    public String getInsertSql(T t, String... excludeField) {
        Method[] methods = t.getClass().getDeclaredMethods();// 把方法名全部转成小写，再去get，用来属性字段比较
        Field[] fields = t.getClass().getDeclaredFields();
        StringBuffer fieldSet = new StringBuffer(" (");
        StringBuffer valueSet = new StringBuffer(" (");
        String tableName = this.getTableName(t);
        this.handerPrimaryKey(fieldSet, valueSet, fields, tableName);
        this.handerSqlExcludeField(excludeField, methods, fields, fieldSet, valueSet, t);
        String f = fieldSet.substring(0, fieldSet.length() - 1) + ")";
        String v = valueSet.substring(0, valueSet.length() - 1) + ")";
        if (DebugController.isDebugOpent) System.out.println(Insert_Into + tableName + f + Values + v);
        return Insert_Into + tableName + f + Values + v;
    }

}
