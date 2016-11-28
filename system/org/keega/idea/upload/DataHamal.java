package org.keega.idea.upload;

/**
 * Created by zun.wei on 2016/11/17.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class DataHamal {

    private DataHamal(){}

    private static volatile String realPath = null;

    public static String getRealPath() {
        return realPath;
    }

    public static void setRealPath(String _realPath) {
        if (realPath == null) {
            synchronized (DataHamal.class) {
                if (realPath == null) {
                    realPath = _realPath;
                }
            }
        }
        //DataHamal.realPath = realPath;
    }

}
