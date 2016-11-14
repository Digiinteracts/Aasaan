package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonu Saini on 5/6/2016.
 */
public class OutletDTO {
    private String name="";
    private String market_type="";
    private String bookmark="";
    private String rating="";
    private String rating_outlate="";
    private String phone_no="";
    private String location="";
    private String distance="";
    private String success="";
    private String review_name;
    private String review_adress;
    private String review_time;
    private String review_rating;
    private String review_imge;
    private String review_type;
    private String review_count;
    private String like_count;
    private String outlet_lat;

    public String getReview_details() {
        return review_details;
    }

    public void setReview_details(String review_details) {
        this.review_details = review_details;
    }

    private String review_details;

    public String getOutlet_long() {
        return outlet_long;
    }

    public void setOutlet_long(String outlet_long) {
        this.outlet_long = outlet_long;
    }

    public String getOutlet_lat() {
        return outlet_lat;
    }

    public void setOutlet_lat(String outlet_lat) {
        this.outlet_lat = outlet_lat;
    }

    private String outlet_long;

    public String getToday_status() {
        return today_status;
    }

    public void setToday_status(String today_status) {
        this.today_status = today_status;
    }

    private String today_status;

    public String getReview_ago() {
        return review_ago;
    }

    public void setReview_ago(String review_ago) {
        this.review_ago = review_ago;
    }

    private String review_ago;

    public String getToday_day() {
        return today_day;
    }

    public void setToday_day(String today_day) {
        this.today_day = today_day;
    }

    private String today_day;

    public String getFridayBrkTime() {
        return fridayBrkTime;
    }

    public void setFridayBrkTime(String fridayBrkTime) {
        this.fridayBrkTime = fridayBrkTime;
    }

    private String fridayBrkTime;

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    private String day_name;

    public String getBrkTime() {
        return BrkTime;
    }

    public void setBrkTime(String brkTime) {
        BrkTime = brkTime;
    }

    private String BrkTime;

    public String gettoday_Opentime() {
        return today_Opentime;
    }

    public void settoday_Opentime(String today_Opentime) {
        this.today_Opentime = today_Opentime;
    }

    private String today_Opentime;

    public String getToday_Closetime() {
        return today_Closetime;
    }

    public void setToday_Closetime(String today_Closetime) {
        this.today_Closetime = today_Closetime;
    }

    private String today_Closetime;
    private String today_brktime;

    public String getToday_brktime() {
        return today_brktime;
    }

    public void setToday_brktime(String today_brktime) {
        this.today_brktime = today_brktime;
    }

    public String getToday_fribrktime() {
        return today_fribrktime;
    }

    public void setToday_fribrktime(String today_fribrktime) {
        this.today_fribrktime = today_fribrktime;
    }

    private String today_fribrktime;

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    private String openTime;
    private String closeTime;

    public List<OutletDTO> getGetOutletInfo() {
        return getOutletInfo;
    }

    public void setGetOutletInfo(List<OutletDTO> getOutletInfo) {
        this.getOutletInfo = getOutletInfo;
    }

    private List<OutletDTO> getOutletInfo = new ArrayList<>();

    private List<GallaryDTO> gallaryDTOs = new ArrayList<>() ;

    public List<GallaryDTO> getGallaryDTOs() {
        return gallaryDTOs;
    }

    public void setGallaryDTOs(List<GallaryDTO> gallaryDTOs) {
        this.gallaryDTOs = gallaryDTOs;
    }

    public String getHome_delivery() {
        return home_delivery;
    }

    public void setHome_delivery(String home_delivery) {
        this.home_delivery = home_delivery;
    }

    public String getCurrency_accepted() {
        return currency_accepted;
    }

    public void setCurrency_accepted(String currency_accepted) {
        this.currency_accepted = currency_accepted;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    private String home_delivery;
    private String currency_accepted;
    private String credit_card;

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    private String landmark;

    public String getFull_location() {
        return full_location;
    }

    public void setFull_location(String full_location) {
        this.full_location = full_location;
    }

    private String full_location;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    private String like;
    private String background_imge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public void setRating_outlate(String rating_outlate) {
        this.rating_outlate = rating_outlate;
    }

    public String getRating_outlate() {
        return rating_outlate;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getReview_type() {
        return review_type;
    }

    public void setReview_type(String review_type) {
        this.review_type = review_type;
    }

    public void setBackground_imge(String background_imge) {
        this.background_imge = background_imge;
    }

    public String getBackground_imge() {
        return background_imge;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    private String banner="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket_type() {
        return market_type;
    }

    public void setMarket_type(String market_type) {
        this.market_type = market_type;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getReview_name() {
        return review_name;
    }

    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }

    public String getReview_adress() {
        return review_adress;
    }

    public void setReview_adress(String review_adress) {
        this.review_adress = review_adress;
    }

    public String getReview_imge() {
        return review_imge;
    }

    public void setReview_imge(String review_imge) {
        this.review_imge = review_imge;
    }

    public String getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(String review_rating) {
        this.review_rating = review_rating;
    }

    public String getReview_time() {
        return review_time;
    }

    public void setReview_time(String review_time) {
        this.review_time = review_time;
    }
}
