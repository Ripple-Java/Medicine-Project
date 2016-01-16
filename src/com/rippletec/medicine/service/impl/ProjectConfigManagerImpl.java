package com.rippletec.medicine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.ProjectConfigDao;
import com.rippletec.medicine.model.ProjectConfig;
import com.rippletec.medicine.service.ProjectConfigManager;

@Service(ProjectConfigManager.NAME)
public class ProjectConfigManagerImpl extends BaseManager<ProjectConfig> implements ProjectConfigManager {
    
    @Resource(name=ProjectConfigDao.NAME)
    private ProjectConfigDao projectConfigDao;

    @Override
    protected FindAndSearchDao<ProjectConfig> getDao() {
	return this.projectConfigDao;
    }

    @Override
    public ProjectConfig findByKey(String key) {
	if(null == key)
	    return null;
	List<ProjectConfig> projectConfigs = projectConfigDao.findByParam(ProjectConfig.KEY, key);
	if(projectConfigs == null || projectConfigs.size() < 1)
	    return null;
	return projectConfigs.get(0);
    }

}
