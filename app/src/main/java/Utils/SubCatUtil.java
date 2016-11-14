package Utils;

/**
 * Created by Sonu Saini on 4/26/2016.
 */

public class SubCatUtil {
    String image,image_cat;
    String image_name;
    String sub_cat__id;
    public SubCatUtil(String image,String image_name,String image_cat,String sub_cat__id){
        this.image=image;
        this.image_name=image_name;
        this.image_cat=image_cat;
        this.sub_cat__id=sub_cat__id;
    }
    public String getSubImgaepath(){

        return this.image;
    }
    public String getSubCatImgaepath(){

        return this.image_cat;
    }
    public String getSubName(){
        return this.image_name;
    }
    public String getSubCatId(){
        return this.sub_cat__id;
    }
}
