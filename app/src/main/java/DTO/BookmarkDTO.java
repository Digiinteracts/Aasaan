package DTO;

import java.util.List;

/**
 * Created by Sonu Saini  on 5/11/2016.
 */

public class BookmarkDTO {
    private String name="";
    private String market_type="";
    private String bookmark="";
    private String rating="";
    private String phone_no="";
    private String distance="";
    private String image="";
    private String location="";
    private String success="";
    private String id="";

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    private String like="";



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setMarketType(String market_type) {
        this.market_type = market_type;
    }
    public String getMarketType() {
        return market_type;
    }

    public String getBookmark() {
        return bookmark;
    }
    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }
    public String getrating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMobile() {
        return phone_no;
    }
    public void setMobile(String phone_no) {
        this.phone_no = phone_no;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }

}

