package Utils;

/**
 * Created by Sonu Saini on 4/29/2016.
 */
public class HorizontalUtil  {
    String logo;
    String image_name;
    String sub_cat__id;
    public HorizontalUtil(String logo,String image_name,String sub_cat__id){
        this.logo=logo;
        this.image_name=image_name;
        this.sub_cat__id=sub_cat__id;

    }

    public String getSubImgaepath(){
        return this.logo;
    }

    public String getSubName(){
        return this.image_name;
    }
    public String getSubCatId(){
        return this.sub_cat__id;
    }



}