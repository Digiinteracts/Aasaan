package Utils;

/**
 * Created by Digi-T1 on 5/10/2016.
 */
public class searchhvutil {
    String image,image_cat;
    String image_name;
    String sub_cat__id;
    public searchhvutil(String image,String image_name){
        this.image=image;
        this.image_name=image_name;

    }
    public String getImgaepath(){

        return this.image;
    }

    public String getSubName(){
        return this.image_name;
    }
   // public String getSubCatId(){
   //     return this.sub_cat__id;
   // }
}

