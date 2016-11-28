package org.keega.idea.util.dao.bduti_utill;

import org.keega.idea.util.dao.annotation.SqlTableName;
import org.springframework.stereotype.Component;

/**
 * Created by asus_n56 on 2016/9/15.
 */
@Component("sqlDeleteUtil")
public class SqlDeleteUtil<T> {

    /**
     * 获取泛型里的Class对象
     */
 /*   private Class<T> clz;

    @SuppressWarnings("unchecked")
    private Class<T> getClz() {
        if (clz == null) {
            clz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return clz;
    }*/

    public String getTableName(Class<T> clz) {
        String tableName = clz.getAnnotation(SqlTableName.class).value();
        if ("".equals(tableName.trim())) {
            tableName = clz.getSimpleName();
        }
        return tableName;
    }

}
