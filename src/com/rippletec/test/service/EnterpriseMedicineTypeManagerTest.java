package com.rippletec.test.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.service.EnterpriseMedicineTypeManager;
import com.rippletec.test.dao.IBaseDaoTest;

/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EnterpriseMedicineTypeManagerTest implements IBaseDaoTest {
    
    @Resource(name=EnterpriseMedicineTypeManager.NAME)
    private EnterpriseMedicineTypeManager enterpriseMedicineTypeManager;

    @Override
    public void testSave() throws Exception {
    }

    @Override
    public void testDelete() throws Exception {
    }

    @Override
    public void testUpdate() throws Exception {
    }

    @Override
    public void testFind() throws Exception {
    }

    @Override
    public void testFindByPage() throws Exception {
    }
    

}
