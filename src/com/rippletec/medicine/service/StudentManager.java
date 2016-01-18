package com.rippletec.medicine.service;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Student;

public interface StudentManager extends IManager<Student> {
    public static final String NAME = "StudentManager";

    void setStuInfo(String account, String name, String school,
	    int degree, String major) throws DaoException;

}
