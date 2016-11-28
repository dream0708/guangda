package org.keega.idea.util.dao.tool;

import java.util.*;

/**
 * Created by zun.wei on 2016/10/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class MapTool{

    private List<Map> listMaps;
    private Map map;
    private List<Map> originalListMaps;
    private Map originalMap;
    private String[] excludeKeys;
    private List<Map> hasExcludeListMaps;
    private Map hasExcludeMap;

    private MapTool() {

    }

    public MapTool(List<Map> listMaps, String... exclodeKeys) {
        List one = new ArrayList();
        for (int i = 0; i < listMaps.size(); i++) {//TODO1  ...解决同一块内存问题。
            Map two = new HashMap();
            two.putAll(listMaps.get(i));
            one.add(two);
        }
        this.originalListMaps = one;
        this.excludeKeys = exclodeKeys;
        this.listMaps = listMaps;
        List<Map> hasExcludeListMaps = new ArrayList<Map>();
        if (exclodeKeys.length > 0) {
            for (Map map : listMaps) {
                for (int i = 0; i < exclodeKeys.length; i++) {
                    if (map.containsKey(exclodeKeys[i])) {
                        map.remove(exclodeKeys[i]);
                    }
                }
                hasExcludeListMaps.add(map);
            }
            this.hasExcludeListMaps = hasExcludeListMaps;
        } else {
            this.hasExcludeListMaps = listMaps;
        }
        //System.out.println("============"+originalListMaps);
    }

    public MapTool(Map map, String... exclodeKeys) {
        Map one = new HashMap();
        one.putAll(map);//TODO1  ...解决同一块内存问题。
        this.originalMap = one;
        this.excludeKeys = exclodeKeys;
        this.map = map;
        if (exclodeKeys.length > 0) {
            for (int i = 0; i < exclodeKeys.length; i++) {
                if (this.map.containsKey(exclodeKeys[i])) {
                    this.map.remove(exclodeKeys[i]);
                }
            }
            this.hasExcludeMap = this.map;
        } else {
            this.hasExcludeMap = this.map;
        }
    }

    //insert into tableName ---拼接的是后面这部分  (field1,field2,field3) values (value1,value2,value3)
    public SqlDto getListMapInsertSqlPart() {
        StringBuffer sb1 = new StringBuffer(" (");
        StringBuffer sb2 = new StringBuffer(" (");
        Object[] realValues = null;
        int k = 0;
        int kk = 0;
        for (int i = 0; i < this.hasExcludeListMaps.size(); i++) {
            Set<String> stringSet = this.hasExcludeListMaps.get(i).keySet();
            Iterator<String> it = stringSet.iterator();
            while (it.hasNext()) {
                String key = it.next();
                kk++;
            }
        }
        realValues = new Object[kk];
        for (int i = 0; i < this.hasExcludeListMaps.size(); i++) {
            Set<String> stringSet = this.hasExcludeListMaps.get(i).keySet();
            //realValues = new Object[this.hasExcludeListMaps.get(i).size()];
            Iterator<String> it = stringSet.iterator();
            while (it.hasNext()) {
                String key = it.next();
                sb1.append(key).append(",");
                sb2.append("?").append(",");
                realValues[k] = this.hasExcludeListMaps.get(i).get(key);
                k++;
            }
        }
        SqlDto sqlDto = new SqlDto();
        sqlDto.setSqlPart(sb1.toString().substring(0, sb1.toString().length() - 1)+")"
                + " values " + sb2.toString().substring(0, sb2.toString().length() - 1)+")");
        sqlDto.setSqlParams(realValues);
        return sqlDto;
    }

    //insert into tableName ---拼接的是后面这部分  (field1,field2,field3) values (value1,value2,value3)
    public SqlDto getSingleMapInsertSqlPart() {
        StringBuffer sb1 = new StringBuffer(" (");
        StringBuffer sb2 = new StringBuffer(" (");
        Object[] realValues = null;
        Set<String> stringSet = this.hasExcludeMap.keySet();
        realValues = new Object[this.hasExcludeMap.size()];
        Iterator<String> it = stringSet.iterator();
        int k = 0;
        while (it.hasNext()) {
            String key = it.next();
            sb1.append(key).append(",");
            sb2.append("?").append(",");
            realValues[k] = this.hasExcludeMap.get(key);
            k++;
        }
        SqlDto sqlDto = new SqlDto();
        sqlDto.setSqlPart(sb1.toString().substring(0, sb1.toString().length() - 1)+")"
                + " values " + sb2.toString().substring(0, sb2.toString().length() - 1)+")");
        sqlDto.setSqlParams(realValues);
        return sqlDto;
    }

    public SqlDto getListMapUpdateSqlPart() {
        StringBuffer sb1 = new StringBuffer(" ");
        Object[] realValues = null;
        for (int i = 0; i < this.hasExcludeListMaps.size(); i++) {
            Set<String> stringSet = this.hasExcludeListMaps.get(i).keySet();
            realValues = new Object[this.hasExcludeListMaps.get(i).size()];
            Iterator<String> it = stringSet.iterator();
            int k = 0;
            while (it.hasNext()) {
                String key = it.next();
                sb1.append(key).append("=").append("?").append(",");
                realValues[k] = this.hasExcludeListMaps.get(i).get(key);
                k++;
            }
        }
        SqlDto sqlDto = new SqlDto();
        sqlDto.setSqlPart(sb1.toString().substring(0, sb1.toString().length() - 1));
        sqlDto.setSqlParams(realValues);
        return sqlDto;
    }

    public SqlDto getSingleMapUpdateSqlPart() {
        StringBuffer sb1 = new StringBuffer(" ");
        Object[] realValues = null;
            Set<String> stringSet = this.hasExcludeMap.keySet();
            realValues = new Object[this.hasExcludeMap.size()];
            Iterator<String> it = stringSet.iterator();
            int k = 0;
            while (it.hasNext()) {
                String key = it.next();
                sb1.append(key).append("=").append("?").append(",");
                realValues[k] = this.hasExcludeMap.get(key);
                k++;
            }
        SqlDto sqlDto = new SqlDto();
        sqlDto.setSqlPart(sb1.toString().substring(0, sb1.toString().length() - 1));
        sqlDto.setSqlParams(realValues);
        return sqlDto;
    }

/*    public SqlDto getDeleteSqlPart() {

        return null;
    }*/

    public List<Object> getListMapExcludeKeyValue(String key) {
        if (this.originalListMaps == null) throw new RuntimeException("map不能为空！");
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < this.originalListMaps.size(); i++) {
            if (this.originalListMaps.get(i).containsKey(key)) {
                list.add(this.originalListMaps.get(i).get(key));
            }
        }
        return list;
    }


    public Object getSingleMapExcludeKeyValue(String key) {
        if (this.originalMap == null) throw new RuntimeException("map不能为空！");
        if (this.originalMap.containsKey(key)) {
            return this.originalMap.get(key);
        } else {
            return null;
        }
    }


}
