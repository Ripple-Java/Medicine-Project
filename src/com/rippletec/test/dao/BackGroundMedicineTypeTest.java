package com.rippletec.test.dao;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.BackGroundMedicineTypeDao;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.dao.WestMedicineDao;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class BackGroundMedicineTypeTest implements IBaseDaoTest {
    
    @Resource(name=BackGroundMedicineTypeDao.NAME)
    private BackGroundMedicineTypeDao backGroundMedicineTypeDao;
    
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    
    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao ;
    
    @Resource(name = MedicineTypeDao.NAME)
    private MedicineTypeDao medicineTypeDao;

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
    
 

}
