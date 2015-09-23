/**
 * 
 */
package com.rippletec.medicine.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.annotation.DBLogModel;

/**
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "west_medicine")
@DBLogModel
public class WestMedicine extends BaseModel {

    public static final String CLASS_NAME = "WestMedicine";
    public static final String TABLE_NAME = "west_medicine";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String MEDICINE_TYPE_ID = "medicine_type_id";
    public static final String ENTER_MEDICINE_TYPE_ID = "enter_medicine_type_id";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    
    private static final long serialVersionUID = 5451907990648871088L;
    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 关联药品表
    @OneToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = MEDICINE_ID)
    private Medicine medicine;


    // 关联所属类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_TYPE_ID)
    private MedicineType medicineType;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "westMedicine")
    @Cascade(CascadeType.ALL)
    private Set<EnterWestMedicine> enterWestMedicines = new LinkedHashSet<EnterWestMedicine>();
    
    // 西药药品名称
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    
    // 别名
    @Column(name = "other_name", columnDefinition = "TEXT", nullable = false)
    private String other_name;

    // 组成成分
    @Column(name = "content", columnDefinition = "TEXT", nullable = true)
    private String content;

    // 临床运用
    @Column(name = "current_application", columnDefinition = "TEXT", nullable = true)
    private String current_application;

    // 药理学
    @Column(name = "pharmacolo", columnDefinition = "TEXT", nullable = true)
    private String pharmacolo;

    // 药效学
    @Column(name = "pk_pd", columnDefinition = "TEXT", nullable = true)
    private String pk_pd;

    // 药动学
    @Column(name = "pharmacokinetics", columnDefinition = "TEXT", nullable = true)
    private String pharmacokinetics;

    // 注意事项
    @Column(name = "warn", columnDefinition = "TEXT", nullable = true)
    private String warn;

    // 不良反应
    @Column(name = "ADRS", columnDefinition = "TEXT", nullable = true)
    private String ADRS;

    // 药物相互作用
    @Column(name = "interaction", columnDefinition = "TEXT", nullable = true)
    private String interaction;

    // 给药说明
    @Column(name = "dose_explain", columnDefinition = "TEXT", nullable = true)
    private String dose_explain;

    // 用法用量
    @Column(name = "manual", columnDefinition = "TEXT", nullable = true)
    private String  manual;

    // 成人用量
    @Column(name = "adult_dose", columnDefinition = "TEXT", nullable = true)
    private String adult_dose;

    // 国外用法用量参考
    @Column(name = "foreign_dose", columnDefinition = "TEXT", nullable = true)
    private String foreign_dose;

    // 制剂与规格
    @Column(name = "preparations", columnDefinition = "TEXT", nullable = true)
    private String preparations;

    // 贮法
    @Column(name = "store", columnDefinition = "TEXT", nullable = true)
    private String store;

    // 药品价格
    @Column(name = "price", length = 10, nullable = false, precision = 2)
    private Double price;
    
    // 药品发布状态
    @Column(name= "status", length=1, nullable=false)
    private Integer status;
    
    @Column(name="sortKey", length=255, nullable=false)
    private String sortKey;

    public WestMedicine() {
    }
    
   

    public WestMedicine(Medicine medicine, MedicineType medicineType,
	    String name, String other_name, String content,
	    String current_application, String pharmacolo, String pk_pd,
	    String pharmacokinetics, String warn, String aDRS,
	    String interaction, String dose_explain, String manual,
	    String adult_dose, String foreign_dose, String preparations,
	    String store, Double price, Integer status, String sortKey) {
	super();
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.name = name;
	this.other_name = other_name;
	this.content = content;
	this.current_application = current_application;
	this.pharmacolo = pharmacolo;
	this.pk_pd = pk_pd;
	this.pharmacokinetics = pharmacokinetics;
	this.warn = warn;
	ADRS = aDRS;
	this.interaction = interaction;
	this.dose_explain = dose_explain;
	this.manual = manual;
	this.adult_dose = adult_dose;
	this.foreign_dose = foreign_dose;
	this.preparations = preparations;
	this.store = store;
	this.price = price;
	this.status = status;
	this.sortKey = sortKey;
    }



    public String getADRS() {
	return ADRS;
    }

    public String getAdult_dose() {
	return adult_dose;
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


    public String getForeign_dose() {
	return foreign_dose;
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

    public Medicine getMedicine() {
	return medicine;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public String getName() {
	return name;
    }

    public String getOther_name() {
	return other_name;
    }

    public String getPharmacokinetics() {
	return pharmacokinetics;
    }

    public String getPharmacolo() {
	return pharmacolo;
    }

    public String getPk_pd() {
	return pk_pd;
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

    public String getWarn() {
	return warn;
    }

    public void setADRS(String aDRS) {
	ADRS = aDRS;
    }

    public void setAdult_dose(String adult_dose) {
	this.adult_dose = adult_dose;
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

    public void setForeign_dose(String foreign_dose) {
	this.foreign_dose = foreign_dose;
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

    public void setMedicine(Medicine medicine) {
	this.medicine = medicine;
    }

    public void setMedicineType(MedicineType medicineType) {
        this.medicineType = medicineType;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setOther_name(String other_name) {
	this.other_name = other_name;
    }

    public void setPharmacokinetics(String pharmacokinetics) {
	this.pharmacokinetics = pharmacokinetics;
    }

    public void setPharmacolo(String pharmacolo) {
	this.pharmacolo = pharmacolo;
    }

    public void setPk_pd(String pk_pd) {
	this.pk_pd = pk_pd;
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

    public void setWarn(String warn) {
	this.warn = warn;
    }
    
    

    public Integer getStatus() {
        return status;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    
    

    public Set<EnterWestMedicine> getEnterWestMedicines() {
        return enterWestMedicines;
    }

    public void setEnterWestMedicines(Set<EnterWestMedicine> enterWestMedicines) {
        this.enterWestMedicines = enterWestMedicines;
    }

    @Override
    public String toString() {
	return "WestMedicine [id=" + id + ", name=" + name + ", other_name="
		+ other_name + ", content=" + content
		+ ", current_application=" + current_application
		+ ", pharmacolo=" + pharmacolo + ", pk_pd=" + pk_pd
		+ ", pharmacokinetics=" + pharmacokinetics + ", warn=" + warn
		+ ", ADRS=" + ADRS + ", interaction=" + interaction
		+ ", dose_explain=" + dose_explain + ", manual=" + manual
		+ ", adult_dose=" + adult_dose + ", foreign_dose="
		+ foreign_dose + ", preparations=" + preparations + ", store="
		+ store + ", price=" + price + ", status=" + status + "]";
    }

    
    

}
