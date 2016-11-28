package com.dexcoder.dal.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dexcoder.dal.BoundSql;
import com.dexcoder.dal.handler.MappingHandler;

/**
 * Created by liyd on 2015-12-4.
 */
public class CustomUpdateBuilder{
	
	protected static final String COMMAND_OPEN = "UPDATE ";
	protected static final String COMMAND_WHERE = " WHERE ";
	
	private String tableName;
	
	private Map<String, Object> fieldsMap = new HashMap<String, Object>();
	private Map<String, Object> wheresMap = new HashMap<String, Object>();
    
    
    /**
     * native field 正则
     */
    public static final String REGEX_NATIVE_FIELD = "(^[\\[].+[\\]]$)|(^[{].+[}]$)";
    
    public CustomUpdateBuilder(String tableName){
    	this.tableName = tableName;
    }
    
    public void addField(String fieldName,Object value) {
    	fieldsMap.put(fieldName, value);
    }
    
    public void addWhere(String fieldName,Object value) {
    	wheresMap.put(fieldName, value);
    }
    
    /**
     * 是否native属性
     * 
     * @return
     */
    public boolean isNativeField(String name) {
        return name.matches(REGEX_NATIVE_FIELD);
    }

    public BoundSql build(Object entity, boolean isIgnoreNull, MappingHandler mappingHandler) {
        StringBuilder sql = new StringBuilder(COMMAND_OPEN);
        List<Object> params = new ArrayList<Object>();
        //tableName必须从whereBuilder中获取，以便水平分表时能正确获取表名
        sql.append(tableName).append(" SET ");
        for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
            String columnName = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                sql.append(columnName).append(" = NULL,");
            } else {
                sql.append(columnName).append(" = ?,");
                params.add(value);
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        return new CriteriaBoundSql(sql.toString(), params);
    }
}
