package com.rippletec.medicine.service;

import com.rippletec.medicine.model.Doctor;

public interface DoctorManager extends IManager<Doctor> {
    
    public static final String NAME = "DoctorManager";

    boolean setInfo(String account, String name, String hospital,
	    String office, String officePhone, String profession);

}
