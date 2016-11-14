package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digi-T25 on 5/16/2016.
 */
public class OutletListModel {

    String Sponsered_image;
    String Sponsered_name;

    public String getSponsered_id() {
        return Sponsered_id;
    }

    public void setSponsered_id(String sponsered_id) {
        Sponsered_id = sponsered_id;
    }

    String Sponsered_id;
    String outlet_id;
    String outlet_name;
    String outlet_location;
    String outlet_rating;
    String outlet_type;
    String image;
    String outlet_time;

    public String getOutlet_lat() {
        return outlet_lat;
    }

    public void setOutlet_lat(String outlet_lat) {
        this.outlet_lat = outlet_lat;
    }

    public String getOutlet_long() {
        return outlet_long;
    }

    public void setOutlet_long(String outlet_long) {
        this.outlet_long = outlet_long;
    }

    String outlet_lat;
    String outlet_long;


    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    String phone_no;
    private String Success;

    public String getSponsered_image() {
        return Sponsered_image;
    }

    public void setSponsered_image(String sponsered_image) {
        Sponsered_image = sponsered_image;
    }

    public String getSponsered_name() {
        return Sponsered_name;
    }

    public void setSponsered_name(String sponsered_name) {
        Sponsered_name = sponsered_name;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    List<OutletListModel> advertiseDTOs = new ArrayList<>();

    public List<OutletListModel> getheaderImg() {
        return setheaderImg;
    }

    public void setheaderImg(List<OutletListModel> setheaderImg) {
        this.setheaderImg = setheaderImg;
    }

    List<OutletListModel> setheaderImg = null;

    public void setAdvertiseDTOs(List<OutletListModel> advertiseDTOs) {
        this.advertiseDTOs = advertiseDTOs;
    }

    public List<OutletListModel> getAdvertiseDTOs() {
        return advertiseDTOs;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getOutlet_name() {
        return outlet_name;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public String getOutlet_location() {
        return outlet_location;
    }

    public void setOutlet_location(String outlet_location) {
        this.outlet_location = outlet_location;
    }

    public String getOutlet_type() {
        return outlet_type;
    }

    public void setOutlet_type(String outlet_type) {
        this.outlet_type = outlet_type;
    }

    public String getOutlet_rating() {
        return outlet_rating;
    }

    public void setOutlet_rating(String outlet_rating) {
        this.outlet_rating = outlet_rating;
    }

    public String getOutlet_time() {
        return outlet_time;
    }

    public void setOutlet_time(String outlet_time) {
        this.outlet_time = outlet_time;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
