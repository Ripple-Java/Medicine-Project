package com.rippletec.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.test.dao.IBaseDaoTest;

/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MedicineTypeManagerTest implements IBaseDaoTest {
    
    @Resource(name=MedicineTypeManager.NAME)
    private MedicineTypeManager medicineTypeManager;

    @Override
    public void testDelete() throws Exception {
    }

    @Override
    public void testFind() throws Exception {
    }

    @Override
    public void testFindByPage() throws Exception {
    }

    @Test
    public void testFindByParam() throws Exception {
	List<MedicineType> medicineTypes = medicineTypeManager.getTypeByParentId(13);
	for (MedicineType medicineType : medicineTypes) {
	    System.out.println(medicineType.toString());
	}
    }

    @Override
    public void testSave() throws Exception {
    }
    
    @Override
    public void testUpdate() throws Exception {
    }

}
