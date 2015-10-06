package com.rippletec.test.service;

import java.util.List;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.test.dao.IBaseDaoTest;

/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EnterpriseManagerTesst implements IBaseDaoTest {

    @Resource(name=EnterpriseManager.NAME)
    private EnterpriseManager enterpriseManager;
    
    @Override
    @Test
    public void testDelete() throws Exception {
	enterpriseManager.delete(1);
    }

    @Override
    @Test
    public void testFind() throws Exception {
	System.out.println(enterpriseManager.find(21));
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
	List<Enterprise> enterprises = enterpriseManager.findByPage(Enterprise.TYPE, Enterprise.FOREIGN, new PageBean(0, 10));
	JsonUtil util = new JsonUtil();
	System.out.println(util.setModelList(enterprises).setResultFail().setJsonObject("page", new PageBean(0, 10)).toJsonString("/enterprise"));
    }

    @Override
    @Test
    public void testSave() throws Exception {
//	for (int i = 0; i < 10; i++) {
//	    Enterprise enterprise = new Enterprise(Enterprise.FOREIGN, "ServiceName--"+i, "logo", "phone", "email");
//	    enterpriseManager.save(enterprise);
//	}
	
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
	Enterprise enterprise  = enterpriseManager.find(21);
	enterprise.setName("updateName");
	enterpriseManager.update(enterprise);
    }

}
