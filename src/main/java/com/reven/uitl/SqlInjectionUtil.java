package com.reven.uitl;

/** 
 * 防SQL注入工具类 
 * 把SQL关键字替换为空字符串 
 */
public class SqlInjectionUtil {

    public static final String REGEX = "'|%|--|;|and|or|not|use|insert|delete|update|select|count|group|union"
            + "|create|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|source|sql";

    /** 
     * 把SQL关键字替换为空字符串 
     * @param param 
     * @return 
     */
    public static String filter(String param) {
        if (param == null) {
            return param;
        }
        // (?i)不区分大小写替换
        return param.replaceAll("(?i)" + REGEX, "");
    }

    public static void main(String[] args) {
        // 过时的方法
        // drop table test
        System.out.println(SqlInjectionUtil.filter("zhangshan ' or 1=1"));
        System.out.println(SqlInjectionUtil.filter("'zhangshan ' ; drop table test"));
        System.out.println(SqlInjectionUtil.filter("zhangshan ' or 1=1 --"));
        System.out.println(SqlInjectionUtil.filter("name' ; select 1'"));
        
    }
}