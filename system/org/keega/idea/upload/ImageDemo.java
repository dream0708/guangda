package org.keega.idea.upload;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator 测试写入数据库以及从数据库中读取
 */
@Component
public class ImageDemo {

    @Inject
    private DBUtil dbUtli;

    // 将图片插入数据库
    public void readImage2DB(String A0100, String path) {
        //String path = "D:/1.png";
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        ResultSet rs = null;
        try {
            in = ImageUtil.readImage(path);
            conn = dbUtli.getConn();
            String sql = null;

            sql = "SELECT COUNT (A0100) AS ole FROM UsrA00 WHERE A0100=? AND Flag = 'P'";
            ps = conn.prepareStatement(sql);
            //sql = "UPDATE UsrA00 SET Ole=? WHERE A0100=? AND I9999='1'";
            ps.setString(1,A0100);
            rs = ps.executeQuery();
            int countOle = 0;
            while (rs.next()) {
                countOle = rs.getInt("ole");
            }
            if (countOle < 1) {
                sql = "insert into UsrA00 (A0100,I9999,Ole,Flag) values (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, A0100);
                ps.setString(2, "66");
                ps.setBinaryStream(3, in, in.available());
                ps.setString(4,"P");
                //System.out.println("insert---");
            } else {
                sql = "UPDATE UsrA00 SET Ole=? WHERE A0100=? AND Flag = 'P'";
                ps = conn.prepareStatement(sql);
                ps.setBinaryStream(1, in, in.available());
                ps.setString(2, A0100);
                //System.out.println("update--");
            }
            int count = ps.executeUpdate();
            /*if (count > 0) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DBUtil.closeConn(conn);使用了dataSource的Connection之后就不用手动关闭了。
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 读取数据库中图片
    public void readDB2Image(String A0100, String targetPath) {
        //String targetPath = "D:/image/1.png";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dbUtli.getConn();
            String sql = "select Ole from UsrA00 where A0100=? and Flag='P'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, A0100);
            rs = ps.executeQuery();
            while (rs.next()) {
                InputStream in = rs.getBinaryStream("Ole");
                ImageUtil.readBin2Image(in, targetPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DBUtil.closeConn(conn);使用了dataSource的Connection之后就不用手动关闭了。
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //检查是否用户是否有照片
    public boolean hasPhoto(String A0100) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream in = null;
        String sql = "select Ole from UsrA00 where Flag='P' and A0100=?";
        try {
            conn = dbUtli.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,A0100);
            rs = ps.executeQuery();
            while (rs.next()) {
                in = rs.getBinaryStream("Ole");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //DBUtil.closeConn(conn);使用了dataSource的Connection之后就不用手动关闭了。
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return in != null;
    }

    //从数据库中读出照片的输入流
    public InputStream getImageFromDb(String A0100,OutputStream outputStream) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream in = null;
        try {
            conn = dbUtli.getConn();
            String sql = "select Ole from UsrA00 where A0100=? and Flag='P'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, A0100);
            rs = ps.executeQuery();
            while (rs.next()) {
                in = rs.getBinaryStream("Ole");
                if (in != null) {//如果数据库中有图片。
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((len = in.read(buf, 0, 1024)) != -1) {
                        outputStream.write(buf, 0, len);
                    }
                }
            }
            if (in == null) {//如果数据库中没有图片，使用默认图片
                //String uploadPath = RequestUitl.getUploadPath()+"/"+"nophoto"+".gif";
                String uploadPath = DataHamal.getRealPath()+"/"+"nophoto"+".gif";
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(uploadPath);
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((len = fileInputStream.read(buf, 0, 1024)) != -1) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    RequestUitl.removeUploadPath();//alt="还没有个人照片.."
                }
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DBUtil.closeConn(conn);使用了dataSource的Connection之后就不用手动关闭了。
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return in;
    }

}
