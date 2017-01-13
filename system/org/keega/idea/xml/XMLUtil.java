package org.keega.idea.xml;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/*
 * 单例模式指的是所有的对象只有一个，这样子可以提高访问的效率
 * 即：把userDocument设置成静态
 */
public class XMLUtil {
    private static Document tableDocument = null;

    public static Document getTableDocument() {
        // 如果document存在直接返回
        if (tableDocument != null) {
            return tableDocument;
        }
        // 如果不存在就创建对象
        SAXReader reader = null;
        try {
            reader = new SAXReader();
            //System.out.println(XMLUtil.class.getClassLoader().getResource("org/keega/idea/xml/sets.xml"));
            tableDocument = reader.read(XMLUtil.class.getClassLoader().getResource("org/keega/idea/xml/sets.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return tableDocument;
    }

    public static void write2XML(Document d, String name) {
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(
                    XMLUtil.class.getClassLoader().getResource("xml/" + name + ".xml").getPath().replace("bin", "src")),
                    OutputFormat.createPrettyPrint());
            writer.write(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
