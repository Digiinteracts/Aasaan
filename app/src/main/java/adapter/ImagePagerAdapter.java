package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.assan.R;

import java.util.List;

import DTO.GallaryDTO;

/**
 * Created by DigiT-25 on 15-07-2016.
 */
public class ImagePagerAdapter extends PagerAdapter {
    Utils.ImageLoader imageLoader;
    Context context;
    List<GallaryDTO> imglist;

    public ImagePagerAdapter(Context context,  List<GallaryDTO> imglist){
        Log.e("imagepage","imageipaged 1" );
        this.context = context;
        this.imglist = imglist;
        imageLoader = new Utils.ImageLoader(context);
    }
    @Override
    public int getCount() {
        return imglist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.e("imagepage","imageipage" + imglist.get(position).getImage());
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(
                R.dimen.padding_medium);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //imageView.setImageResource(mImages[position]);
        imageLoader.DisplayImage(imglist.get(position).getImage(), imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
