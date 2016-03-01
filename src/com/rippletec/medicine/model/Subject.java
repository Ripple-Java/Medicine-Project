package com.rippletec.medicine.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;


/**
 * 科目Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name=Subject.TABLE_NAME)
public class Subject {
    
    public static final String CLASS_NAME = "Subject";
    
    public static final String TABLE_NAME = "subject";
    
    public static final String PARENT_ID = "parent_id";
    public static final String PARENT_NAME = "parent_name";
    public static final String NAME = "name";
    
    public static final int DEFAULT_PARENT = -1;
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = PARENT_ID, nullable = false)
    private Integer parent_id;
    
    @Column(length = 255, nullable = false)
    private String name;
    
    @Column(length = 255, nullable = true)
    private String parent_name;
    
    
    public Subject() {
    }

    public Subject(Integer parent_id, String name, String parent_name) {
	super();
	this.parent_id = parent_id;
	this.name = name;
	this.parent_name = parent_name;
    }



    @Override
    public String toString() {
	return "Subject [parent_id=" + parent_id + ", name=" + name + "]";
    }
    
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    
    
    
    
    
    

}
