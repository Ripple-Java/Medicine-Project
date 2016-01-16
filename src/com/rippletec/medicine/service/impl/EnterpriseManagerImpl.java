package com.rippletec.medicine.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.EnterpriseInfoVO;

@Service(EnterpriseManager.NAME)
public class EnterpriseManagerImpl extends BaseManager<Enterprise> implements EnterpriseManager{

    @Resource(name=EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;
    @Resource(name=UserDao.NAME)
    private UserDao userDao;
    
    @Override
    protected FindAndSearchDao<Enterprise> getDao() {
	return this.enterpriseDao;
    }

    @Override
    public List<EnterpriseMedicineType> getEnterMedicineTypes(int id) throws DaoException {
	Enterprise enterprise = find(id);
	return new LinkedList<EnterpriseMedicineType>(enterprise.getMedicineTypeEnterprises());
    }

    @Override
    public List<Enterprise> getEnterprise(int size, int type, int currentPage) {
	return findByPage(Enterprise.TYPE, type, new PageBean(currentPage, 0, size));
    }

    @Override
    public void deleteByUser(int id) {
	List<Enterprise> enterprises = findBySql(Enterprise.TABLE_NAME, Enterprise.USER_ID, id);
	if(enterprises == null)
	    return;
	for (Enterprise enterprise : enterprises) {
	    delete(enterprise.getId());
	}
    }

    @Override
    public Enterprise findByUser(User user) {
	List<Enterprise> enterprises = enterpriseDao.findBySql(Enterprise.TABLE_NAME, Enterprise.USER_ID, user.getId());
	if(StringUtil.isEmpty(enterprises))
	    return null;
	return enterprises.get(0);
    }

    @Override
    public Result updateInfo(int enterpriseId, EnterpriseInfoVO vo) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(enterpriseId);
	if(enterprise == null){
	    return new Result(false, "企业信息不存在");
	}
	enterprise.setUpdate(vo);
	enterpriseDao.update(enterprise);
	return new Result(true);
    }

    @Override
    public Result active(int id) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(id);
	if(enterprise == null){
	    return new Result(false, "改企业信息不存在");
	}
	enterprise.setStatus(Enterprise.ON_PUBLISTH);
	User user = enterprise.getUser();
	user.setStatus(User.STATUS_NORMAL);
	userDao.update(user);
	return new Result(true);
    }

    @Override
    public Result block(int id) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(id);
	enterprise.setStatus(Enterprise.ON_CLOSE);
	enterpriseDao.update(enterprise);
	return new Result(true);
    }

    @Override
    public Result unblock(int id) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(id);
	enterprise.setStatus(Enterprise.ON_PUBLISTH);
	enterpriseDao.update(enterprise);
	return new Result(true);
    }

    @Override
    public List<Enterprise> getValiatedEnterprises(PageBean pageBean) {
	List<Object> values = new ArrayList<Object>();
	values.add(Enterprise.ON_PUBLISTH);
	values.add(Enterprise.ON_CLOSE);
	return getEnterprise(Enterprise.STATUS, values, pageBean, Enterprise.ID);
    }
    
    @Override
    public List<Enterprise> getEnterprise(final String Param, final List<Object> values,
	    final PageBean pBean, final String orderStr) {
	 String hql = "from "+Enterprise.CLASS_NAME+" q where q."+Param+" in (";
	for (int i = 0; i < values.size(); i++) {
	    hql += " ?,";
	}
	hql = hql.substring(0,hql.length()-1);
	final String excuHql = hql + ") order by "+orderStr;
	return getDaoHibernateTemplate().execute(new HibernateCallback<List<Enterprise>>() {

	    @Override
	    public List<Enterprise> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query query =  session.createQuery(excuHql);
		for (int i = 0; i < values.size(); i++) {
		    query.setParameter(i, values.get(i));
		}
		if(pBean == null){
		    return query.list();
		}
		System.out.println(pBean.offset+":"+pBean.pageSize);
		return query.setFirstResult(pBean.offset).setMaxResults(pBean.pageSize).list();
	    }
	});
    }

    @Override
    public void validEnterPrise(User user) {
	Enterprise enterprise = findByUser(user);
	if(enterprise != null){
	    enterprise.setStatus(Enterprise.ON_CHECKING);
	    enterpriseDao.update(enterprise);
	}
    }

    @Override
    public List<Enterprise> getEnterpriseByType(int type, PageBean pageBean) {
	ParamMap paramMap = new ParamMap().put(Enterprise.STATUS, Enterprise.ON_PUBLISTH)	
					  .put(Enterprise.TYPE, type);
	if(pageBean == null){
	    return enterpriseDao.findByParam(paramMap);
	}
	return enterpriseDao.findByPage(paramMap, pageBean);
    }

    

}
