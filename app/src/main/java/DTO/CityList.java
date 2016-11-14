package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class CityList {

    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityList> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<CityList> city_list) {
        this.city_list = city_list;
    }

    List<CityList> city_list = new ArrayList<>();
}
