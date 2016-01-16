package com.rippletec.medicine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.EnterWestVO;

/**
 * 企业西药Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "enter_west_medicine")
public class EnterWestMedicine extends BaseModel {

    protected static final long serialVersionUID = 7475276297003606075L;
    public static final String CLASS_NAME = "EnterWestMedicine";
    public static final String TABLE_NAME = "enter_west_medicine";
    public static final String ENTER_MEDICINE_TYPE_ID = "enter_medicine_type_id";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String WEST_MEDICINE_ID = "west_medicine_id";
    public static final String MEDICINE_TYPE_ID = "medicine_type_id";
    public static final String NAME = "name";
    public static final String ENTERPRISE_ID = "enterpriese_id";
    public static final String STATUS = "status";
    public static final String ENTERPRISE_NAME = "enterprise_name";
    

    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    public static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    // 关联药品表
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = MEDICINE_ID)
    protected Medicine medicine;

    // 关联所属类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_TYPE_ID)
    protected MedicineType medicineType;
    
    // 关联所属类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTER_MEDICINE_TYPE_ID)
    protected EnterpriseMedicineType enterpriseMedicineType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = WEST_MEDICINE_ID)
    protected WestMedicine westMedicine;
    
    // 关联企业
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    protected Enterprise enterprise;
    
    

    // 企业名称
    @Column(name = "enterprise_name", length = 50, nullable = false)
    protected String enterprise_name;

    // 西药药品名称
    @Column(name = "name", length = 255, nullable = false)
    protected String name;
    
    // 别名
    @Column(name = "other_name", columnDefinition = "TEXT", nullable = false)
    protected String other_name;

    // 组成成分
    @Column(name = "content", columnDefinition = "TEXT", nullable = true)
    protected String content;

    // 临床运用
    @Column(name = "current_application", columnDefinition = "TEXT", nullable = true)
    protected String current_application;

    // 药理学
    @Column(name = "pharmacolo", columnDefinition = "TEXT", nullable = true)
    protected String pharmacolo;

    // 注意事项
    @Column(name = "warn", columnDefinition = "TEXT", nullable = true)
    protected String warn;

    // 不良反应
    @Column(name = "ADRS", columnDefinition = "TEXT", nullable = true)
    protected String adrs;

    // 药物相互作用
    @Column(name = "interaction", columnDefinition = "TEXT", nullable = true)
    protected String interaction;

    // 给药说明
    @Column(name = "dose_explain", columnDefinition = "TEXT", nullable = true)
    protected String dose_explain;

    // 用法用量
    @Column(name = "manual", columnDefinition = "TEXT", nullable = true)
    protected String  manual;

    // 制剂与规格
    @Column(name = "preparations", columnDefinition = "TEXT", nullable = true)
    protected String preparations;

    // 药品价格
    @Column(name = "price", length = 10, nullable = false, precision = 2)
    protected Double price;

    // 药品发布状态
    @Column(name = "status", length = 1, nullable = false)
    protected Integer status;
    
    @Column(name="sortKey", length = 255, nullable=false)
    protected String sortKey;
    
    // 最后一次修改时间
    @Temporal(TemporalType.DATE)
    @Column(name = "updateTime", nullable = false)
    protected Date updateTime;

    public EnterWestMedicine() {
    }
    
    public EnterWestMedicine(Integer id, String enterprise_name, String name,
	    String other_name, String content, String current_application,
	    String pharmacolo, String warn, String adrs, String interaction,
	    String dose_explain, String manual, String preparations,
	    Double price, Integer status, String sortKey, Date updateTime) {
	super();
	this.id = id;
	this.enterprise_name = enterprise_name;
	this.name = name;
	this.other_name = other_name;
	this.content = content;
	this.current_application = current_application;
	this.pharmacolo = pharmacolo;
	this.warn = warn;
	this.adrs = adrs;
	this.interaction = interaction;
	this.dose_explain = dose_explain;
	this.manual = manual;
	this.preparations = preparations;
	this.price = price;
	this.status = status;
	this.sortKey = sortKey;
	this.updateTime = updateTime;
    }




    public EnterWestMedicine(Medicine medicine,
	    MedicineType medicineType,Enterprise enterprise , EnterWestVO enterWestVO, String sortKey, Date updateTime) {
	super();
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.enterprise_name = enterprise.getName();
	this.name = enterWestVO.getName();
	this.other_name = enterWestVO.getOther_name();
	this.content = enterWestVO.getContent();
	this.current_application = enterWestVO.getCurrent_application();
	this.pharmacolo = enterWestVO.getPharmacolo();
	this.warn = enterWestVO.getWarn();
	this.adrs = enterWestVO.getAdrs();
	this.interaction = enterWestVO.getInteraction();
	this.dose_explain = enterWestVO.getDose_explain();
	this.manual = enterWestVO.getManual();
	this.preparations = enterWestVO.getPreparations();
	this.price = enterWestVO.getPrice();
	this.status = ON_PUBLISTH;
	this.sortKey = sortKey;
	this.enterprise = enterprise;
	this.updateTime = updateTime;
    }
    
    public void setUpdate(EnterWestVO enterWestVO, WestMedicine westMedicine) {
	this.adrs = enterWestVO.getAdrs();
	this.content = enterWestVO.getContent();
	this.current_application = enterWestVO.getCurrent_application();
	this.dose_explain = enterWestVO.getDose_explain();
	this.interaction = enterWestVO.getInteraction();
	this.manual = enterWestVO.getManual();
	this.name = enterWestVO.getName();
	this.other_name = enterWestVO.getOther_name();
	this.pharmacolo = enterWestVO.getPharmacolo();
	this.preparations = enterWestVO.getPreparations();
	this.price = enterWestVO.getPrice();
	this.warn = enterWestVO.getWarn();
	this.westMedicine = westMedicine;
	this.medicineType = westMedicine.getMedicineType();
	this.updateTime = new Date();
    }

    
    public String getContent() {
	return content;
    }

    public String getCurrent_application() {
	return current_application;
    }

    public String getDose_explain() {
	return dose_explain;
    }


    public Integer getId() {
	return id;
    }

    public String getInteraction() {
	return interaction;
    }

    public String getManual() {
	return manual;
    }

    public String getName() {
	return name;
    }

    public String getOther_name() {
	return other_name;
    }



    public String getPharmacolo() {
	return pharmacolo;
    }


    public String getPreparations() {
	return preparations;
    }

    public Double getPrice() {
	return price;
    }


    public String getWarn() {
	return warn;
    }

    

    public void setContent(String content) {
	this.content = content;
    }

    public void setCurrent_application(String current_application) {
	this.current_application = current_application;
    }

    public void setDose_explain(String dose_explain) {
	this.dose_explain = dose_explain;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setInteraction(String interaction) {
	this.interaction = interaction;
    }

    public void setManual(String manual) {
	this.manual = manual;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setOther_name(String other_name) {
	this.other_name = other_name;
    }


    public void setPharmacolo(String pharmacolo) {
	this.pharmacolo = pharmacolo;
    }

    public void setPreparations(String preparations) {
	this.preparations = preparations;
    }

    public void setPrice(Double price) {
	this.price = price;
    }


    public void setWarn(String warn) {
	this.warn = warn;
    }

    public String getEnterprise_name() {
	return enterprise_name;
    }

    public Integer getStatus() {
	return status;
    }

    public void setEnterprise_name(String enterprise_name) {
	this.enterprise_name = enterprise_name;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Medicine getMedicine() {
	return medicine;
    }

    public void setMedicine(Medicine medicine) {
	this.medicine = medicine;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public WestMedicine getWestMedicine() {
        return westMedicine;
    }

    public void setWestMedicine(WestMedicine westMedicine) {
        this.westMedicine = westMedicine;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(MedicineType medicineType) {
        this.medicineType = medicineType;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public EnterpriseMedicineType getEnterpriseMedicineType() {
        return enterpriseMedicineType;
    }

    public void setEnterpriseMedicineType(
    	EnterpriseMedicineType enterpriseMedicineType) {
        this.enterpriseMedicineType = enterpriseMedicineType;
    }

    public String getAdrs() {
        return adrs;
    }

    public void setAdrs(String adrs) {
        this.adrs = adrs;
    }


    
    
    
    
    
    

}
