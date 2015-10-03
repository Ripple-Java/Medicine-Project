package com.rippletec.test.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.test.dao.IBaseDaoTest;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagerTest implements IBaseDaoTest {
    
    @Resource(name=UserManager.NAME)
    private UserManager userManager; 

    @Override
    public void testDelete() throws Exception {
    }

    @Override
    public void testFind() throws Exception {
    }

    @Override
    public void testFindByPage() throws Exception {
    }

    @Override
    public void testSave() throws Exception {
    }

    @Override
    public void testUpdate() throws Exception {
    }
    
    @Test
    public void testGetBackPassword() throws Exception {
	User user = userManager.findByAccount("15622739759");
	System.out.println(user.toString());
	userManager.getBackPassword(user.getAccount(), "23456");
    }

}
