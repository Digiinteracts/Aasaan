package Utils;

/**
 * Created by Sonu Saini on 5/9/2016.
 */
public class homeutil{
    String image,image_cat;
    String image_name;
    String sub_cat__id;

    public homeutil(String image,String image_name,String sub_cat__id){
        this.image=image;
        this.image_name=image_name;

        this.sub_cat__id=sub_cat__id;
    }
    public String getSubImgaepath1(){

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
