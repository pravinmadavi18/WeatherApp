
package com.virtusa.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesModel {

    @SerializedName("le")
    @Expose
    private List<Le> le = null;

    public List<Le> getLe() {
        return le;
    }

    public void setLe(List<Le> le) {
        this.le = le;
    }

}
