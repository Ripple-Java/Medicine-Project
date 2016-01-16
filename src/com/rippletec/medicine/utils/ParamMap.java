package com.rippletec.medicine.utils;

import java.util.HashMap;

public class ParamMap extends HashMap<String, Object>{
    
    private static final long serialVersionUID = 8209336791969656916L;

    @Override
    public ParamMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    
 
}
