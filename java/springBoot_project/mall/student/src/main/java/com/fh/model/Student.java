package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.poi.ExcelFild;
import com.fh.poi.ExcleHeard;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_student")
@ExcleHeard(title = "学生信息")
public class Student{
  @TableId(value = "id",type = IdType.AUTO)
  @ExcelFild(Field = "主键")
    private Integer id;
  @ExcelFild(Field = "姓名")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelFild(Field = "生日")
    private Date birthday;
    @ExcelFild(Field="收获地址")
    private String address;//收获地址
  @ExcelFild(Field = "是否删除")
  @TableField("xueli")
    private Integer isdel;//是否删除
  @ExcelFild(Field = "IP")
  @TableField("ipaddr")
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
