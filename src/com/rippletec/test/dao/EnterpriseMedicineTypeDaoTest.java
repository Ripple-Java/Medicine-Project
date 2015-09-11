package com.rippletec.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;

/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class EnterpriseMedicineTypeDaoTest implements IBaseDaoTest {
    
    @Resource(name=EnterpriseMedicineTypeDao.NAME)
    private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;
    @Resource(name=EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;


    @Override
    @Test
    public void testDelete() throws Exception {
	enterpriseMedicineTypeDao.delete(1);
    }

    @Override
    @Test
    public void testFind() throws Exception {
	EnterpriseMedicineType enterpriseMedicineType = enterpriseMedicineTypeDao.find(13);
//	System.out.println(enterpriseMedicineType.toString());
//	System.out.println(enterpriseMedicineType.getEnterprise().toString());
	Medicine medicine = enterpriseMedicineType.getMedicines().iterator().next();
	
    }

    @Test
    public void testFindByName() throws Exception {
	List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME,EnterpriseMedicineType.ENTERPRISE_ID, 1);
	for (EnterpriseMedicineType enterpriseMedicineType : enterpriseMedicineTypes) {
	    System.out.println(enterpriseMedicineType.toString());
	}
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
	List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeDao.findByPage(new PageBean(0, 10));
	for (EnterpriseMedicineType enterpriseMedicineType : enterpriseMedicineTypes) {
	    System.out.println(enterpriseMedicineType.toString());
	}
    }

    @Override
    @Test
    public void testSave() throws Exception {
	Enterprise enterprise = enterpriseDao.find(15);
	EnterpriseMedicineType enterpriseMedicineType = new EnterpriseMedicineType("企业药品分类", EnterpriseMedicineType.WEST,enterprise);
	enterpriseMedicineTypeDao.save(enterpriseMedicineType);
    }
    
    @Override
    @Test
    public void testUpdate() throws Exception {
    }

}
