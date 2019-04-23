package com.reven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.reven.model.entity.DemoExcel;
import com.reven.uitl.ExcelUtil;

/**
 * 测试文件导出
 */
public class TestExcelUtil {

    public static void main(String[] args) throws Exception {
        // 准备数据
        List<DemoExcel> list = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            if (i < 500) {
                list.add(new DemoExcel(1 + i, "张三" + i, new Date(), "zhangsan" + i, true, ((float) 1.3 + i), 1.4 + i,
                        (long) 1.5 + i));
            } else {
                list.add(new DemoExcel(1 + i, "李四" + i, new Date(), "lisi" + i, true, ((float) 2.3 + i), 28899.8884 + i,
                        (long) 20001.0005 + i));
            }
        }
        LinkedHashMap<String, String> filedMap = new LinkedHashMap<String, String>();
        filedMap.put("id", "ID");
        filedMap.put("name", "姓名");
        filedMap.put("date", "日期");
        filedMap.put("enName", "英文名");
        filedMap.put("numFloat", "float");
        filedMap.put("numDouble", "double");
        filedMap.put("numLong", "long");

        ExcelUtil.exportExcel("用户导出", 500, list, filedMap, new FileOutputStream("D:/test2007.xlsx"),
                ExcelUtil.EXCEL_FILE_2007);
        ExcelUtil.exportExcel("用户导出", 500, list, filedMap, new FileOutputStream("D:/test2003.xls"),
                ExcelUtil.EXCEL_FILE_2003);

        File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/excel/正常文件场景.xlsx");
        List<DemoExcel> importlist = ExcelUtil.excelToList(file.getName(), new FileInputStream(file), 0, 0,
                DemoExcel.class, filedMap);
        System.out.println(importlist.size());

        file = new File(System.getProperty("user.dir") + "/src/main/resources/static/excel/导入第二个sheet页.xlsx");
        importlist = ExcelUtil.excelToList(file.getName(), new FileInputStream(file), 1, 0, DemoExcel.class, filedMap);
        System.out.println(importlist.size());

        file = new File(System.getProperty("user.dir") + "/src/main/resources/static/excel/中间有空行.xlsx");
        importlist = ExcelUtil.excelToList(file.getName(), new FileInputStream(file), 0, 0, DemoExcel.class, filedMap);
        System.out.println(importlist.size());

        file = new File(System.getProperty("user.dir") + "/src/main/resources/static/excel/部分空数据.xlsx");
        importlist = ExcelUtil.excelToList(file.getName(), new FileInputStream(file), 0, 0, DemoExcel.class, filedMap);
        System.out.println(importlist.size());

        try {
            file = new File(System.getProperty("user.dir") + "/src/main/resources/static/excel/完全为空.xlsx");
            importlist = ExcelUtil.excelToList(file.getName(), new FileInputStream(file), 0, 0, DemoExcel.class,
                    filedMap);
            System.out.println(importlist.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            file = new File(System.getProperty("user.dir") + "/src/main/resources/static/excel/错误格式数据.xlsx");
            importlist = ExcelUtil.excelToList(file.getName(), new FileInputStream(file), 0, 0, DemoExcel.class,
                    filedMap);
            System.out.println(importlist.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}