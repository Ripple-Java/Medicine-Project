package com.rippletec.test.dao;

import javax.annotation.Resource;

import com.rippletec.medicine.service.FeedBackMassManager;

public class FeedBackDaoTest implements IBaseDaoTest{
    
    @Resource(name=FeedBackMassManager.NAME)
    private FeedBackMassManager feedBackMassManager;

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
