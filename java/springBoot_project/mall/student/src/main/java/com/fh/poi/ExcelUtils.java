package com.fh.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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



    /**
     * excel的导出
     * @param out
     * @param infos
     */
    public void exportExcel (OutputStream out, List<?> infos) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet sheet = xssfWorkbook.createSheet();
            sheet.createRow(0);

            Map<Field,Integer> map = new LinkedHashMap<>();
            for (Object o : infos ) {
                Field[] fields = o.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                        ExcelAnnotation annotation = field.getAnnotation(ExcelAnnotation.class);
                        map.put(field, annotation.order());
                    }
                }
            }
            List<Map.Entry<Field,Integer>> list = new ArrayList<Map.Entry<Field,Integer>>(map.entrySet());
            Collections.sort(list, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

            List<Field> excelFields = new ArrayList<>();
            for(Map.Entry<Field,Integer> map1 : list){
                excelFields.add(map1.getKey());
            }

            List<ExcelAnnotation> annotations = new ArrayList<>();
            for (Field excelField : excelFields) {
                annotations.add(excelField.getAnnotation(ExcelAnnotation.class));
            }
            addDataToExcel(xssfWorkbook, infos, excelFields, annotations, sheet);
            xssfWorkbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private <T> void addDataToExcel(XSSFWorkbook wb, List<T> dataset, List<Field> excelFields, List<ExcelAnnotation> attributes,
                                    Sheet sheet){
        XSSFCellStyle style = wb.createCellStyle();
        // 居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // excel放入第一行列的名称
        Row row = sheet.createRow(0);
        for (int j = 0; j < excelFields.size(); j++) {
            Cell cell = row.createCell(j);
            ExcelAnnotation oneAttribute = attributes.get(j);
            cell.setCellValue(oneAttribute.headName());
            cell.setCellStyle(style);
        }
        // 添加数据到excel
        for(int i=0;i<dataset.size();i++) {
            // 数据行号从1开始,因为第0行放的是列的名称
            row = sheet.createRow(i + 1);
            for (int j = 0; j < attributes.size(); j++) {
                Cell cell = row.createCell(j);
                ExcelAnnotation annotation = attributes.get(j);
                style = wb.createCellStyle();
                // 居中
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                // 四个边框
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                cell.setCellStyle(style);

                System.out.println();
                //数据准被好了


                // 根据属性名获取属性值
         /*       String cellValue = BeanUtils.getProperty( dataset.get(i), excelFields.get(j).getName());
                if (DataType.Date.equals(annotation.type())){
                    String date = DateTimeUtil
                            .getFormatDateFromGLWZString(cellValue, annotation.datePattern());
                    cell.setCellValue(date);
                }else {
                    cell.setCellValue(cellValue);
            }*/
            }
        }

    }




}
