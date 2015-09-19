package com.rippletec.medicine.service;

import java.util.Date;

import com.rippletec.medicine.model.Liveness;
import com.rippletec.medicine.model.User;

public interface LivenessManager extends IManager<Liveness> {
    public static final String NAME = "LivenessManager";

    void save(User user);

    void updateLogin(User user, Date time);

}
