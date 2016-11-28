package org.keega.idea.upload;

/**
 * Created by zun.wei on 2016/11/9.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class RequestUitl {

    private static final ThreadLocal<String> uploadPath = new ThreadLocal<String>();

    public static String getUploadPath() {
        return uploadPath.get();
    }

    public static void setUploadPath(String _uploadPath) {
        uploadPath.set(_uploadPath);
    }

    public static void removeUploadPath() {
        uploadPath.remove();
    }

}
