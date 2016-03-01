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
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.utils.ErrorCode;
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
    public List<Enterprise> getEnterprise(int size, int type, int currentPage) throws DaoException {
	return findByPage(Enterprise.TYPE, type, new PageBean(currentPage, 0, size));
    }

    @Override
    public void deleteByUser(int id) throws DaoException {
	List<Enterprise> enterprises = findBySql(Enterprise.TABLE_NAME, Enterprise.USER_ID, id);
	for (Enterprise enterprise : enterprises) {
	    delete(enterprise.getId());
	}
    }

    @Override
    public Enterprise findByUser(User user) throws DaoException {
	List<Enterprise> enterprises = enterpriseDao.findBySql(Enterprise.TABLE_NAME, Enterprise.USER_ID, user.getId());
	return enterprises.get(0);
    }

    @Override
    public void updateInfo(int enterpriseId, EnterpriseInfoVO vo) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(enterpriseId);
	enterprise.setUpdate(vo);
	enterpriseDao.update(enterprise);
    }

    @Override
    public void active(int id) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(id);
	enterprise.setStatus(Enterprise.ON_PUBLISTH);
	User user = enterprise.getUser();
	user.setStatus(User.STATUS_NORMAL);
	userDao.update(user);
    }

    @Override
    public void block(int id) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(id);
	enterprise.setStatus(Enterprise.ON_CLOSE);
	enterpriseDao.update(enterprise);
    }

    @Override
    public void unblock(int id) throws DaoException {
	Enterprise enterprise = enterpriseDao.find(id);
	enterprise.setStatus(Enterprise.ON_PUBLISTH);
	enterpriseDao.update(enterprise);
    }

    @Override
    public List<Enterprise> getValiatedEnterprises(PageBean pageBean) throws ServiceException {
	List<Object> values = new ArrayList<Object>();
	values.add(Enterprise.ON_PUBLISTH);
	values.add(Enterprise.ON_CLOSE);
	return getEnterprise(Enterprise.STATUS, values, pageBean, Enterprise.ID);
    }
    
    @Override
    public List<Enterprise> getEnterprise(final String Param, final List<Object> values,
	    final PageBean pBean, final String orderStr) throws ServiceException {
	if(pBean.currentPage <0 || pBean.offset <0 || pBean.pageSize <0){
	    throw new ServiceException(ErrorCode.PARAM_ERROR);
	}
	 String hql = "from "+Enterprise.CLASS_NAME+" q where q."+Param+" in (";
	for (int i = 0; i < values.size(); i++) {
	    hql += " ?,";
	}
	hql = hql.substring(0,hql.length()-1);
	final String excuHql = hql + ") order by "+orderStr;
	List<Enterprise> enterprises = getDaoHibernateTemplate().execute(new HibernateCallback<List<Enterprise>>() {

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
	if(enterprises == null || enterprises.size()<1){
	    throw new ServiceException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return enterprises;
    }

    @Override
    public void validEnterPrise(User user) throws DaoException {
	Enterprise enterprise = findByUser(user);
	enterprise.setStatus(Enterprise.ON_CHECKING);
	enterpriseDao.update(enterprise);
    }

    @Override
    public List<Enterprise> getEnterpriseByType(int type, PageBean pageBean) throws DaoException {
	ParamMap paramMap = new ParamMap().put(Enterprise.STATUS, Enterprise.ON_PUBLISTH)	
					  .put(Enterprise.TYPE, type);
	if(pageBean == null){
	    return enterpriseDao.findByParam(paramMap);
	}
	return enterpriseDao.findByPage(paramMap, pageBean);
    }

    

}
