/**
 * 
 */
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

/**
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "medicine_document")
public class MedicineDocument extends BaseModel {

    public static final String CLASS_NAME = "MedicineDocument";
    public static final String TABLE_NAME = "medicine_document";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String TYPE = "type";
    public static final int GUIDEBOOK = 1 ;
    public static final int LITERATURE = 2 ;
    public static final int CASES = 3 ;
    
    private static final long serialVersionUID = 1344487290503116724L;
    public static final String TITLE = "title";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 文章所关联的药物
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_ID)
    private Medicine medicine;

    // 药品文章类型(1-指南，2-文献，3-病例)
    @Column(name = "type", length = 1, nullable = false)
    private Integer type;

    // 药品文章标题
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    // 药品文章内容
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // 药品文章作者
    @Column(name = "author", length = 100, nullable = false)
    private String author;

    // 药品文章发布日期
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    public MedicineDocument() {

    }

    public MedicineDocument(Medicine medicine, Integer type, String title,
	    String content, String author, Date date) {
	super();
	this.medicine = medicine;
	this.type = type;
	this.title = title;
	this.content = content;
	this.author = author;
	this.date = date;
    }

    

    public String getAuthor() {
	return author;
    }

    public String getContent() {
	return content;
    }

    public Date getDate() {
	return date;
    }

    public Integer getId() {
	return id;
    }

    public Medicine getMedicine() {
	return medicine;
    }

    public String getTitle() {
	return title;
    }

    public Integer getType() {
	return type;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setMedicine(Medicine medicine) {
	this.medicine = medicine;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    @Override
    public String toString() {
	return "MedicineDocument [id=" + id + ", type=" + type + ", title="
		+ title + ", content=" + content + ", author=" + author
		+ ", date=" + date + "]";
    }

}
