package com.rippletec.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.model.ProjectConfig;
import com.rippletec.medicine.service.ProjectConfigManager;
import com.rippletec.test.dao.IBaseDaoTest;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class ProjectConfigManagerTest implements IBaseDaoTest{
    
    @Resource(name = ProjectConfigManager.NAME)
    private ProjectConfigManager projectConfigManager;

    @Override
    @Test
    public void testDelete() throws Exception {
    }

    @Override
    @Test
    public void testFind() throws Exception {
	ProjectConfig projectConfig = projectConfigManager.findByKey(ProjectConfig.APP_UPDATE_MESSAGE);
	String oldString = projectConfig.getCon_value();
	String newString = oldString.replaceAll("\r\n", "\n");
	projectConfig.setCon_value(newString);
	projectConfigManager.update(projectConfig);
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
    }

    @Override
    @Test
    public void testSave() throws Exception {
	ProjectConfig appConfig = new ProjectConfig("ios_version", "1.0");
	projectConfigManager.save(appConfig);
    }
    
    @Test
    public void testSave2() throws Exception {
	ProjectConfig appConfig = new ProjectConfig("ios_updtae_message", "IOS默认更新说明");
	projectConfigManager.save(appConfig);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }
    
    

}
