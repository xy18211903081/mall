package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.poi.ExcelAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_student")
public class Student{
  @TableId(value = "id",type = IdType.AUTO)
  @ExcelAnnotation(headName="学号",order=0)
    private Integer id;
  @ExcelAnnotation(headName="姓名",order=1)
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelAnnotation(headName="生日",order=2,type = ExcelAnnotation.DataType.Date,datePattern = "yyyy-MM-dd")
    private Date birthday;
  @ExcelAnnotation(headName="收获地址",order=3)
    private String address;//收获地址
  @ExcelAnnotation(headName="是否删除",order=4)
    private Integer isdel;//是否删除
  @ExcelAnnotation(headName="IP",order=5)
  private String ips;//

  public String getIps() {
    return ips;
  }

  public void setIps(String ips) {
    this.ips = ips;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getIsdel() {
    return isdel;
  }

  public void setIsdel(Integer isdel) {
    this.isdel = isdel;
  }
}
