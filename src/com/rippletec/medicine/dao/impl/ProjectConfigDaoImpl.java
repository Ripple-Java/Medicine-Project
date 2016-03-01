package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.ProjectConfigDao;
import com.rippletec.medicine.model.ProjectConfig;

@Repository(ProjectConfigDao.NAME)
public class ProjectConfigDaoImpl extends BaseDaoImpl<ProjectConfig> implements ProjectConfigDao{

    @Override
    public String getClassName() {
	return ProjectConfig.CLASS_NAME;
    }

    @Override
    public Class<ProjectConfig> getPersistClass() {
	return ProjectConfig.class;
    }

}
