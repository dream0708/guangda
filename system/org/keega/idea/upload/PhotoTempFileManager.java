package org.keega.idea.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 * 删除服务器上的文件
 * @author xiaoqun.yi
 */
//实现Runnable接口(推荐)，可以线程接口，预留一个extends(继承)，方便扩展
public class PhotoTempFileManager implements Runnable {
    //private static String path;//路径
    private String path;//路径

    private static String RETENTION_TIME = "";// 文件保存的时间

    static {
        Properties prop = new Properties();
        InputStream inStrem = PhotoTempFileManager.class.getClassLoader()
                .getResourceAsStream("/org/keega/idea/upload/photo.properties");
                //.getResourceAsStream("photo.properties");
        try {
            prop.load(inStrem);
            RETENTION_TIME = prop.getProperty("file_retention_time");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStrem != null) {
                    inStrem.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构造函数。初始化参数
     * @param path
     */
    public PhotoTempFileManager(String path) {
        this.path = path;
        //System.out.println("path____________________" + path);
    }
    /**
     * 把线程要执行的代码放在run()中
     */
    public void run() {
        //System.out.println("========临时照片管理开始=========");
        //path = path + "exportExcel";
        path = path + "upload\\photo\\";
        //System.out.println("path?????" + path);
        File file = new File(path);
        deletefiles(file);//这个是原来的人家的方法。

    }


    /**
     * 批量删除文件
     *
     * @param folder
     */
    public void deletefiles(File folder) {
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteFile(files[i]);
        }
    }

    /**
     * 删除文件
     *
     * @param file
     */
    private void deleteFile(File file) {
        try {
            if (file.isFile()) {
                // 删除符合条件的文件
                if (canDeleteFile(file)) {
                    if (file.delete()) {
                        //System.out.println("临时照片" + file.getName() + "删除成功!");
                    } else {
                        System.out.println("临时照片" + file.getName()+ "删除失败!此临时照片可能正在被使用");
                    }
                } else {

                }
            } else {
                //System.out.println("没有可以删除的临时照片了");
            }

        } catch (Exception e) {
            System.out.println("删除临时照片失败========");
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否能够被删除
     */
    private boolean canDeleteFile(File file) {
        Date fileDate = getfileDate(file);
        Date date = new Date();
        long time = (date.getTime() - fileDate.getTime()) / 1000 / 60
                - Integer.parseInt(RETENTION_TIME);// 当前时间与文件间隔的分钟
        if (time > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file
     * @return
     */
    private Date getfileDate(File file) {
        long modifiedTime = file.lastModified();
        Date d = new Date(modifiedTime);
        return d;
    }

    /**
     * 格式化日期,没有用到
     */
    private String formatDate(Date date) {
        // SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd hh:mm:ss");//12小时制
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//24小时制
        String formateDate = f.format(date);
        return formateDate;
    }

    /**
     * 删除文件夹
     *
     * @param folder
     */
    public void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFolder(files[i]);
            }

            // 非当前目录，删除
            if (!folder.getAbsolutePath().equalsIgnoreCase(path)) {

                // 只删除在30分钟前创建的文件
                if (canDeleteFile(folder)) {
                    if (folder.delete()) {
                        System.out.println("文件夹" + folder.getName() + "删除成功!");
                    } else {
                        System.out.println("文件夹" + folder.getName()
                                + "删除失败!此文件夹内的文件可能正在被使用");
                    }
                }
            }
        } else {
            deleteFile(folder);
        }

    }

}