package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assan.MaintenanceActivity;
import com.assan.R;




import java.util.ArrayList;
import java.util.List;

import ImageLoding.Loader;
import Utils.Constant;
import model.RecyclerViewHolder;
import model.catmodel;

/**
 * Created by sonu
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {



    Bitmap bitmap;


    private List<catmodel> menulistlist = null;
    private ArrayList<catmodel> arraylist;
    Context context;
    LayoutInflater inflater;

    public RecyclerAdapter(Context context,List<catmodel> menulistlist) {
        this.context=context;
        inflater=LayoutInflater.from(context);

        this.menulistlist=menulistlist;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.categroie_list_item, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tv1.setText(menulistlist.get(position).getName());
        holder.txt_id.setText(menulistlist.get(position).getId());
        Loader imageloader=new Loader(context);
        imageloader.displayImage(menulistlist.get(position).getImage(), holder.imageView);
        holder.imageView.setOnClickListener(clickListener);
        holder.imageView.setTag(holder);
        holder.imageView2.setOnClickListener(clickListener);
        holder.imageView2.setTag(holder);
        holder.tv1.setOnClickListener(clickListener);
        holder.tv1.setTag(holder);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();

            Intent intent=new Intent(context, MaintenanceActivity.class);
            intent.putExtra("id",menulistlist.get(position).getId());
            intent.putExtra("name", menulistlist.get(position).getName());
            intent.putExtra("latitude", Constant.LATITUDE);
            intent.putExtra("longitude",Constant.LONGITUDE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        }
    };




    public int getCount() {
        // TODO Auto-generated method stub
        return menulistlist.size();
    }


    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return menulistlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return menulistlist.size();
    }

}
