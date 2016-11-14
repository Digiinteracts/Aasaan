package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 12-07-2016.
 */
public class RecentOutlet implements Serializable {

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

    public String getRecent_id() {
        return recent_id;
    }

    public void setRecent_id(String recent_id) {
        this.recent_id = recent_id;
    }

    public String getRecent_name() {
        return recent_name;
    }

    public void setRecent_name(String recent_name) {
        this.recent_name = recent_name;
    }

    public String getRecent_location() {
        return recent_location;
    }

    public void setRecent_location(String recent_location) {
        this.recent_location = recent_location;
    }

    public String getRecent_rating() {
        return recent_rating;
    }

    public void setRecent_rating(String recent_rating) {
        this.recent_rating = recent_rating;
    }

    public String getRecentt_type() {
        return recentt_type;
    }

    public void setRecentt_type(String recentt_type) {
        this.recentt_type = recentt_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecent_time() {
        return recent_time;
    }

    public void setRecent_time(String recent_time) {
        this.recent_time = recent_time;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    String Sponsered_image;
    String Sponsered_name;
    String recent_id;
    String recent_name;
    String recent_location;
    String recent_rating;
    String recentt_type;
    String image;
    String recent_time;
    private String Success;
    String recent_long;
    String recent_lat;

    public String getRecent_phonNO() {
        return recent_phonNO;
    }

    public void setRecent_phonNO(String recent_phonNO) {
        this.recent_phonNO = recent_phonNO;
    }

    public String getRecent_lat() {
        return recent_lat;
    }

    public void setRecent_lat(String recent_lat) {
        this.recent_lat = recent_lat;
    }

    public String getRecent_long() {
        return recent_long;
    }

    public void setRecent_long(String recent_long) {
        this.recent_long = recent_long;
    }

    String recent_phonNO;
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    String data;


    public List<RecentOutlet> getRecentList() {
        return recentList;
    }

    public void setRecentList(List<RecentOutlet> recentList) {
        this.recentList = recentList;
    }

    List<RecentOutlet> recentList = new ArrayList<>();

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    String currentTime;
}
