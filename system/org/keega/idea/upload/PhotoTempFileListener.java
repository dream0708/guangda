package org.keega.idea.upload;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zun.wei on 2016/11/12.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class PhotoTempFileListener implements ServletContextListener {

    private Timer timer = new Timer();
    public static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
        try {

            // 第一个参数是要运行的代码，第二个参数是从什么时候开始运行，第三个参数是每隔多久在运行一次。
            // timer.schedule(new SystemTaskTest(),0,(5*1000));
            sce.getServletContext().log("定时器已启动");
            // 设置在每晚24:0分执行任务
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 ,可以更改时间
            calendar.set(Calendar.MINUTE, 1); // 0可以更改分数
            calendar.set(Calendar.SECOND, 0);// 0 //默认为0,不以秒计
            Date date = calendar.getTime();//晚上12点1分0秒执行删除。
            // 监听器获取网站的根目录
            String path = sce.getServletContext().getRealPath("/");
            // 第一个参数 是要运行的代码，第二个参数是指定的时间
            //System.out.println(path+"upload\\photo\\");
            //timer.schedule(new SystemTaskTest(path), date,1000*60);//10000表示10秒执行一次清除。
            timer.schedule(new SystemTaskTest(path), new Date(),1000*60*2);//10000表示10秒执行一次清除。
            sce.getServletContext().log("已经添加任务调度表");
        } catch (Exception e) {

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            timer.cancel();
        } catch (Exception e) {
        }
    }

    /**
     * 时间任务器
     *
     * @author xiaoqun.yi
     */
    class SystemTaskTest extends TimerTask {
        private String path;

        public SystemTaskTest(String path) {
            this.path = path;
        }

        /**
         * 把要定时执行的任务就在run中
         */
        public void run() {
            try {
                /*System.out.println("path======" + path);
                System.out.println("date===" + new Date().getMinutes());
                for (int i = 0; i < 10; i++) {
                    System.out.println("say hello");
                }*/
                PhotoTempFileManager etf = new PhotoTempFileManager(path);
                etf.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
