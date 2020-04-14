package com.invisee.finvest.data.realm.model;

import io.realm.RealmObject;

/**
 * Created by fajarfatur on 2/18/16.
 */
public class KycLookupModel extends RealmObject {

    private String  category;
    private String  code;
    private String  value;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
