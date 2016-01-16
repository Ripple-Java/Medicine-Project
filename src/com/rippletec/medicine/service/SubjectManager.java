package com.rippletec.medicine.service;

import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.Subject;


public interface SubjectManager extends IManager<Subject> {
    
    public static final String NAME = "SubjectManager";
    
    boolean frushToDB() throws UtilException;

}
