package com.rippletec.medicine.model;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.vo.web.EnterChineseVO;

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
    
    // 关联通用中药
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = CHIN_MEDICINE_ID)
    private ChineseMedicine chineseMedicine;
    
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
    private String sortkey;
    
    public EnterChineseMedicine() {
    }
    public EnterChineseMedicine(Medicine medicine,
	    MedicineType medicineType,
	    String enterprise_name, String name, String content,
	    String efficacy, String annouce, String preparations,
	    String manual, String store, String category, Double price,
	    Integer status) {
	super();
	this.medicine = medicine;
	this.medicineType = medicineType;
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
    }
    
    public EnterChineseMedicine(Medicine medicine,
	    MedicineType medicineType,Enterprise enterprise, EnterChineseVO enterChineseVO, String sortKey) {
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
	this.sortkey = sortKey;
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
    public String getSortkey() {
        return sortkey;
    }
    public void setSortkey(String sortkey) {
        this.sortkey = sortkey;
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
    
    
    
    
    
    


  
   
}
