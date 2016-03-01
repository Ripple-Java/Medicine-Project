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
import com.rippletec.medicine.vo.web.EnterChineseVO;

/**
 * 企业中药Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name="enter_chinese_medicine")
public class EnterChineseMedicine extends BaseModel{

    private static final long serialVersionUID = -7463751072679829110L;

    public static final String CLASS_NAME = "EnterChineseMedicine";
    public static final String TABLE_NAME = "enter_chinese_medicine";
    public static final String ENTER_MEDICINE_TYPE_ID = "enter_medicine_type_id";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String CHIN_MEDICINE_ID = "chin_medicine_id";
    public static final String MEDICINE_TYPE_ID = "medicine_type_id";
    public static final String ENTERPRISE_ID = "enterpriese_id";
    public static final String STATUS = "status";
    public static final String ENTERPRISE_NAME = "enterprise_name";
    public static final String ID = "id";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;

    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // 关联药品表
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = MEDICINE_ID)
    private Medicine medicine;

    // 关联所属类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_TYPE_ID)
    private MedicineType medicineType;
    
    
    // 关联所属企业类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTER_MEDICINE_TYPE_ID)
    private EnterpriseMedicineType enterpriseMedicineType;
    
    // 关联通用中药
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = CHIN_MEDICINE_ID)
    private ChineseMedicine chineseMedicine;
    
    // 关联企业
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    // 企业名称
    @Column(name="enterprise_name",length=50,nullable=false)
    private String enterprise_name;

    // 中药药品名称
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // 药物成分
    @Column(name = "content", length = 255, nullable = false)
    private String content;

    // 功效主治
    @Column(name = "efficacy", columnDefinition = "TEXT", nullable = true)
    private String efficacy;

    // 用药监护
    @Column(name = "annouce", columnDefinition = "TEXT", nullable = true)
    private String annouce;

    // 制剂规格
    @Column(name = "preparations", columnDefinition = "TEXT", nullable = true)
    private String preparations;

    // 用法用量
    @Column(name = "manual", columnDefinition = "TEXT", nullable = true)
    private String manual;

    // 贮法
    @Column(name = "store", columnDefinition = "TEXT", nullable = true)
    private String store;

    // 管理分类
    @Column(name = "category", columnDefinition = "TEXT", nullable = true)
    private String category;

    // 药品价格
    @Column(name = "price", length = 10, nullable = false, precision = 2)
    private Double price;
    
    // 药品发布状态
    @Column(name= "status", length=1, nullable=false)
    private Integer status;
    
    @Column(name = "sortKey", length=255, nullable=false)
    private String sortKey;
    
    // 最后一次修改时间
    @Temporal(TemporalType.DATE)
    @Column(name = "updateTime", nullable = false)
    private Date updateTime;
    
    public EnterChineseMedicine() {
    }
       
    public EnterChineseMedicine(Integer id, Medicine medicine,
	    MedicineType medicineType,
	    EnterpriseMedicineType enterpriseMedicineType,
	    ChineseMedicine chineseMedicine, Enterprise enterprise,
	    String enterprise_name, String name, String content,
	    String efficacy, String annouce, String preparations,
	    String manual, String store, String category, Double price,
	    Integer status, String sortKey, Date updateTime) {
	super();
	this.id = id;
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.enterpriseMedicineType = enterpriseMedicineType;
	this.chineseMedicine = chineseMedicine;
	this.enterprise = enterprise;
	this.enterprise_name = enterprise_name;
	this.name = name;
	this.content = content;
	this.efficacy = efficacy;
	this.annouce = annouce;
	this.preparations = preparations;
	this.manual = manual;
	this.store = store;
	this.category = category;
	this.price = price;
	this.status = status;
	this.sortKey = sortKey;
	this.updateTime = updateTime;
    }




    public EnterChineseMedicine(Medicine medicine,
	    MedicineType medicineType,Enterprise enterprise, EnterChineseVO enterChineseVO, String sortKey, Date updateTime) {
	super();
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.enterprise_name = enterprise.getName();
	this.name = enterChineseVO.getName();
	this.content = enterChineseVO.getContent();
	this.efficacy = enterChineseVO.getEfficacy();
	this.annouce = enterChineseVO.getAnnouce();
	this.preparations = enterChineseVO.getPreparations();
	this.manual = enterChineseVO.getManual();
	this.store = enterChineseVO.getStore();
	this.category = enterChineseVO.getCategory();
	this.price = enterChineseVO.getPrice();
	this.status = ON_CHECKING;
	this.sortKey = sortKey;
	this.enterprise = enterprise;
	this.updateTime = updateTime;
    }
    
    public void setUpdate(EnterChineseVO entChineseVO, ChineseMedicine chineseMedicine) {
	this.annouce = entChineseVO.getAnnouce();
	this.category = entChineseVO.getCategory();
	this.content = entChineseVO.getContent();
	this.efficacy = entChineseVO.getEfficacy();
	this.manual = entChineseVO.getManual();
	this.name = entChineseVO.getName();
	this.preparations = entChineseVO.getPreparations();
	this.price = entChineseVO.getPrice();
	this.store = entChineseVO.getStore();
	this.chineseMedicine = chineseMedicine;
	this.medicineType = chineseMedicine.getMedicineType();
	this.updateTime = new Date();

    }
    
    
    
    
    

    @Override
    public String toString() {
	return "EnterChineseMedicine [id=" + id + ", enterprise_name="
		+ enterprise_name + ", name=" + name + ", content=" + content
		+ ", efficacy=" + efficacy + ", annouce=" + annouce
		+ ", preparations=" + preparations + ", manual=" + manual
		+ ", store=" + store + ", category=" + category + ", price="
		+ price + ", status=" + status + "]";
    }
    public String getAnnouce() {
	return annouce;
    }

    public String getCategory() {
	return category;
    }

    public String getContent() {
	return content;
    }

    public String getEfficacy() {
	return efficacy;
    }


    public Integer getId() {
	return id;
    }

    public String getManual() {
	return manual;
    }
    public String getName() {
	return name;
    }

    public String getPreparations() {
	return preparations;
    }

    public Double getPrice() {
	return price;
    }

    public String getStore() {
	return store;
    }

    public void setAnnouce(String annouce) {
	this.annouce = annouce;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public void setEfficacy(String efficacy) {
	this.efficacy = efficacy;
    }


    public void setId(Integer id) {
	this.id = id;
    }

    public void setManual(String manual) {
	this.manual = manual;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPreparations(String preparations) {
	this.preparations = preparations;
    }

    public void setPrice(Double price) {
	this.price = price;
    }

    public void setStore(String store) {
	this.store = store;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }
    
    public Integer getStatus() {
        return status;
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
    public ChineseMedicine getChineseMedicine() {
        return chineseMedicine;
    }
    public void setChineseMedicine(ChineseMedicine chineseMedicine) {
        this.chineseMedicine = chineseMedicine;
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

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
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
    
    
  
}
