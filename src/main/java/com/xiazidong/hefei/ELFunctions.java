package com.xiazidong.hefei;

/**
 * @Author: xiazidong
 * @Date : 2020/5/10 18:02
 *
 * 自定义函数，需要在一个扩展名为.tld的xml文件中进行注册
 * tld tag libray definition,标签库定义
 * xml文件是需要约束的，即需要配置文件头部，这个头部文件约束可以从以下文件中复制
 * tomcat安装目录下E:\tool\apache-tomcat-9.0.34\webapps\examples\WEB-INF\jsp2
 * jsp2-example-taglib.tld
 * 这个tld 的xml文件，需要定义在WEB-INF下
 */
public class ELFunctions {
    public static String lowerToUpper(String source){
        return source.toUpperCase();
    }
    public static String upperToLower(String source){
        return source.toLowerCase();
    }
}
