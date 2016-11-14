package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.assan.R;
import com.assan.SlidingImgs;

import java.util.ArrayList;
import java.util.List;

import DTO.GallaryDTO;


/**
 * Created by Sonu Saini  on 4/29/2016.
 */

public class DetailGalllary extends ArrayAdapter<GallaryDTO> {
    Utils.ImageLoader imageLoader;
    public static DetailGalllary instance;
    ProgressDialog pDialog;
    Bitmap bitmap;
    Context mContext;

    LayoutInflater inflater;

    private List<GallaryDTO> menulistlist = null;

    private ArrayList<GallaryDTO> arraylist;

    public DetailGalllary(Context context, List<GallaryDTO> menulistlist) {
        super(context,0,menulistlist);

        instance = this;
        mContext = context;
        this.menulistlist = menulistlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<GallaryDTO>();
        imageLoader = new Utils.ImageLoader(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.gallary_item, null);
            ImageView subimage = (ImageView) convertView.findViewById(R.id.image_gallary);
            final ViewPager viewPager = (ViewPager) convertView.findViewById(R.id.view_pager);
            imageLoader.DisplayImage(menulistlist.get(position)
                    .getImage(), subimage);

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SlidingImgs.class);
                    intent.putExtra("imageposition",position);
                    mContext.startActivity(intent);

                }
            });
        }
        return convertView;
    }

    public List<GallaryDTO> getImglist(){
        return menulistlist;
    }
    public static DetailGalllary getInstance(){
        return instance;
    }


}

