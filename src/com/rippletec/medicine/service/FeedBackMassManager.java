package com.rippletec.medicine.service;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.FeedBackMass;

public interface FeedBackMassManager extends IManager<FeedBackMass> {
    
    public static final String NAME = "FeedBackMassManager";

    int addFeedBackMass(FeedBackMass feedBackMass,String account) throws DaoException;
}
