package DTO;

/**
 * Created by DigiT-25 on 24-06-2016.
 */
public class GallaryDTO {
    private String image="";
    private String review="";
    private String comment="";
    private String rating="";
    private String ago="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name="";

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAgo() {
        return ago;
    }

    public void setAgo(String ago) {
        this.ago = ago;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address="";

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image_id="";
}
