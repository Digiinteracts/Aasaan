package com.assan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DTO.GallaryDTO;
import ImageLoding.Loader;
import Utils.Constant;
import adapter.DetailGalllary;
import zoom.ExtendedViewPager;
import zoom.TouchImageView;

/**
 * Created by DigiT-25 on 15-07-2016.
 */
public class SlidingImgs extends Activity {

    int img_posi = 0;
   public List<GallaryDTO> imgslist;
    boolean check = true;
    TextView imgposition,totalimg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_img_activity);

        Constant.REQURIED_SIZE = 1;
        Bundle bundle = getIntent().getExtras();
        imgposition = (TextView)findViewById(R.id.imgposi);
        if(bundle != null){
            img_posi = bundle.getInt("imageposition");
        }
        DetailGalllary detailGalllary = DetailGalllary.getInstance();

        ImageView cancelbutn = (ImageView)findViewById(R.id.cancel);

        cancelbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ExtendedViewPager viewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(SlidingImgs.this,detailGalllary.getImglist());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(img_posi);

        String ss = ""+img_posi;
        String ss2 = ""+ detailGalllary.getImglist().size();
        totalimg = (TextView)findViewById(R.id.totalimg);
        totalimg.setText(ss2);

    }

    public void getImgs(List<GallaryDTO> IMG){

        this.imgslist =IMG;

    }
    private class ImagePagerAdapter extends PagerAdapter {
      //  Utils.ImageLoader imageLoader;
        Loader loader;
        public List<GallaryDTO> imgslist;
        Context context;
        public ImagePagerAdapter(Context context,List<GallaryDTO> imgslist){
            this.imgslist = imgslist;
            this.context =context;
            loader=new Loader(context);
          //  imageLoader = new Utils.ImageLoader(context);
        }

        @Override
        public int getCount() {

        return imgslist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = SlidingImgs.this;
            TouchImageView imageView = new TouchImageView(context);
            int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_medium);

            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                imgposition.setText(String.valueOf(position));

                loader.displayImage(imgslist.get(position).getImage(), imageView);
                ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}
