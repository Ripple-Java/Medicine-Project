package com.rippletec.medicine.service;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ProjectConfig;

public interface ProjectConfigManager extends IManager<ProjectConfig> {
    
    public static final String NAME = "ProjectConfigManager";

    ProjectConfig findByKey(String key) throws DaoException;

}
