package com.fh.controller;

import com.fh.model.Student;
import com.fh.poi.ExcelUtils;
import com.fh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController{

     @Autowired
    private StudentService studentService;

     @RequestMapping("excelPoi")
     public void excelPoi(HttpServletResponse response){
         List<Student> list=studentService.queryStudentList();
         ExcelUtils.excel(list,response);
     }
}
