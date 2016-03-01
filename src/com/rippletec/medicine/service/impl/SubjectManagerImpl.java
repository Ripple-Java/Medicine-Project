package com.rippletec.medicine.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.ProjectConfigDao;
import com.rippletec.medicine.dao.SubjectDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.ProjectConfig;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.service.SubjectManager;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.utils.StringUtil;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

@Service(SubjectManager.NAME)
public class SubjectManagerImpl extends BaseManager<Subject> implements SubjectManager{
    
    @Resource(name = SubjectDao.NAME)
    private SubjectDao subjectDao;
    
    @Resource(name = ProjectConfigDao.NAME)
    private ProjectConfigDao projectConfigDao;

    @Override
    protected FindAndSearchDao<Subject> getDao() {
	return this.subjectDao;
    }

    @Override
    public boolean frushToDB() throws UtilException, DaoException {
	Map<String ,List<Map<String, Object>>> first = new HashMap<String, List<Map<String,Object>>>();
	List<Subject> firstlList = subjectDao.findByParam(Subject.PARENT_ID, Subject.DEFAULT_PARENT);
	for (Subject subject : firstlList) {
	    List<Subject> sedcondlList = subjectDao.findByParam(Subject.PARENT_ID, subject.getId());
	    List<Map<String, Object>> secondList = new LinkedList<Map<String,Object>>();
	    for (Subject subject2 : sedcondlList) {
		HashMap<String, Object> second = new HashMap<String, Object>();
		second.put("id", subject2.getId());
		second.put(Subject.NAME, subject2.getName());
		secondList.add(second);
	    }
	    first.put(subject.getName(), secondList);
	}
	JsonUtil jsonUtil = new JsonUtil();
	String respongJson = jsonUtil.setObject("first", first).setSuccessRes().toJson();
	ProjectConfig config  = null;
	List<ProjectConfig> existConfig;
	try {
	    existConfig = projectConfigDao.findByParam(ProjectConfig.KEY, ProjectConfig.SUBJECT_RESPONSE_JSON);
	    config = existConfig.get(0);
	    config.setCon_value(respongJson);
	    projectConfigDao.update(config);
	} catch (DaoException e) {
	    config = new ProjectConfig(ProjectConfig.SUBJECT_RESPONSE_JSON, respongJson);
	    projectConfigDao.save(config);
	}
	return true;
    }

}
