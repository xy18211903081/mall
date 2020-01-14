package com.fh.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtils {

    private static ExcelUtils instance;

    private ExcelUtils(){};

    /**
     * 单例
     * @return
     */
    public static ExcelUtils getInstance() {
        if (instance == null) {
            instance = new ExcelUtils();
        }
        return instance;
    }


    public static void excel(List list, HttpServletResponse response) {
        //处理标题
        Object o = list.get(0);//得到要下载的对象
        Class oclass = o.getClass();//得到类的类对象
        //获取标题名   标题名在注解上
        ExcleHeard annotation = (ExcleHeard) oclass.getAnnotation(ExcleHeard.class);
        String title = annotation.title();//获取标题名
        XSSFWorkbook book= new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet(title);
        XSSFRow row = sheet.createRow(0);
        //处理列头

        //得到类的所有属性
        Field[] declaredFields = oclass.getDeclaredFields();
        int cellNum=0;
        for (int i = 0; i <declaredFields.length ; i++) {
            //具体的每一个属性
            Field field=declaredFields[i];
            //判断此属性 是否为要导出的属性
            ExcelFild annotation1 = field.getAnnotation(ExcelFild.class);
            if(annotation1!=null){
                String rowName = annotation1.Field();
                //将列名 放入具体的列中
                XSSFCell cell = row.createCell(cellNum);
                cell.setCellValue(rowName);
                cellNum++;
            }
        }
        //处理数据

        for (int i = 0; i <list.size() ; i++) {
            // 获取具体的数据
            Object o1 = list.get(i);
            //创建行  为啥是i+1
            XSSFRow row1 = sheet.createRow(i + 1);

            int celln=0;
            for (int j = 0; j <declaredFields.length ; j++) {
                //具体的每一个属性
                Field field=declaredFields[j];
                boolean annotationPresent = field.isAnnotationPresent(ExcelFild.class);
                if(annotationPresent==true){
                    XSSFCell cell = row1.createCell(celln);
                    //获取属性的值  在反射中  对类对象来说
                    XSSFCellStyle cellStyle = book.createCellStyle();
                    // 居中
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    // 四个边框
                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    cellStyle.setBorderTop(BorderStyle.THIN);
                    cell.setCellStyle(cellStyle);
                    try {
                        field.setAccessible(true);//暴力访问  私有的属性
                        Object o2 = field.get(o1);
                        if(o2!=null){
                            //判断属性的类型
                            Class type = field.getType();
                            if(type== Date.class){
                                //格式化日期
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                Date da= (Date) o2;
                                String format = sdf.format(da);
                                cell.setCellValue(format);
                            }else{
                                cell.setCellValue(o2.toString());
                            }
                        }else {
                            cell.setCellValue("");
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    celln++;
                }
            }
        }




        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\""+ UUID.randomUUID().toString()+".xlsx\"");

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            book.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
