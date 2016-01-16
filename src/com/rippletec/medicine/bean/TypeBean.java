package com.rippletec.medicine.bean;

import java.util.LinkedList;
import java.util.List;

public class TypeBean {
    
    public int id;
    
    public String name;
    
    public int parentId;
    
    public TypeBean() {
    }
    
    
    

    public TypeBean(int id, String name, int parentId) {
	super();
	this.id = id;
	this.name = name;
	this.parentId = parentId;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    


    public int getParentId() {
        return parentId;
    }




    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


    @Override
    public int hashCode() {
	return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
           if(obj instanceof TypeBean) {
               	TypeBean oBean = (TypeBean) obj;
               	if(oBean.getId() == id && oBean.getParentId() == parentId)
               	    return true;
          }
          return false;
    }
       

}
