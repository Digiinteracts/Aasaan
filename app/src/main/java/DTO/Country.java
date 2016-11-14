package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class Country {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
        private String id;



    public List<Country> getCountry_list() {
        return country_list;
    }

    public void setCountry_list(List<Country> country_list) {
        this.country_list = country_list;
    }

    public List<Country> country_list = new ArrayList<>();
}
