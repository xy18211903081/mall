package com.fh.service.impl;

import com.fh.dao.StudentDao;
import com.fh.model.Student;
import com.fh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSertviceimpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> queryStudentList() {
        return studentDao.selectList(null);
    }
}
