package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.MedicineDocument;

public interface MedicineDocumentManager extends IManager<MedicineDocument> {
    public static final String NAME = "MedicineDocumentManager";

    List<MedicineDocument> getDocument(int medicineId, int type) throws DaoException;

}
