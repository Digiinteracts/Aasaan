package DTO;

/**
 * Created by Sonu Saini on 5/10/2016.
 */
public class searchhvDTO {
    private String name="";

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    private String Success="";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String image="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id="";
}
