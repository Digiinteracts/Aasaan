package DTO;

import java.util.List;

/**
 * Created by Sonu Saini on 5/4/2016.
 */


public class SearchCategoriesDTO {

    private String current_date="";
    private String review="";
    private String like="";

    public List<SearchCategoriesDTO> getReviewLiset() {
        return reviewLiset;
    }

    public void setReviewLiset(List<SearchCategoriesDTO> reviewLiset) {
        this.reviewLiset = reviewLiset;
    }

    public List<SearchCategoriesDTO> reviewLiset;
    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getReview_created() {
        return review_created;
    }

    public void setReview_created(String review_created) {
        this.review_created = review_created;
    }

    private String review_created="";
    private String likecount="";
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
    private String searchcat_lat="";

    public String getDeletemsg_status() {
        return deletemsg_status;
    }

    public void setDeletemsg_status(String deletemsg_status) {
        this.deletemsg_status = deletemsg_status;
    }

    private String deletemsg_status="";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String msg="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status="";

    public String getDay_ago() {
        return day_ago;
    }

    public void setDay_ago(String day_ago) {
        this.day_ago = day_ago;
    }

    private String day_ago="";

    public String getSearchcat_long() {
        return searchcat_long;
    }

    public void setSearchcat_long(String searchcat_long) {
        this.searchcat_long = searchcat_long;
    }

    public String getSearchcat_lat() {
        return searchcat_lat;
    }

    public void setSearchcat_lat(String searchcat_lat) {
        this.searchcat_lat = searchcat_lat;
    }

    private String searchcat_long="";

    public List<SearchCategoriesDTO> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<SearchCategoriesDTO> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<SearchCategoriesDTO> categoriesList;

    public List<searchhvDTO> getCategoriesDtos() {
        return categoriesDtos;
    }

    public void setCategoriesDtos(List<searchhvDTO> categoriesDtos) {
        this.categoriesDtos = categoriesDtos;
    }

    List<searchhvDTO> categoriesDtos;

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
