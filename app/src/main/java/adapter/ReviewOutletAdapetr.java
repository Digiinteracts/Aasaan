package adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.OutletDetails;
import com.assan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DAO.SearchCategoriesDAO;
import DTO.SearchCategoriesDTO;
import Utils.CommonUtils;

/**
 * Created by Sonu Saini on 5/11/2016.
 */
public class ReviewOutletAdapetr extends BaseAdapter {


    Utils.ImageLoader imageLoader;

    ProgressDialog mProgressDialog;
    Bitmap bitmap;
    Context mContext;
    LayoutInflater inflater;
    private List<SearchCategoriesDTO> searchdto = null;
    private ArrayList<SearchCategoriesDTO> arraylist;
    String id;
    AlertDialog alertDialog;

    SearchCategoriesDTO categoriesDTO ;

    public ReviewOutletAdapetr(Context context, List<SearchCategoriesDTO> searchdto) {

        mContext = context;
        this.searchdto = searchdto;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchCategoriesDTO>();
        imageLoader = new Utils.ImageLoader(mContext);
        this.arraylist.addAll(searchdto);
    }


    @Override
    public int getCount() {
        return searchdto.size();
    }

    @Override
    public Object getItem(int position) {
        return searchdto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Viewholder holder;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = inflater.inflate(R.layout.myreview_item, null);

            holder.txt_review=(TextView)convertView.findViewById(R.id.txt_review);
            holder.txtlike_count=(TextView)convertView.findViewById(R.id.txtlike_count);
            holder.subimage = (com.assan.CircleImageView) convertView.findViewById(R.id.iv_item_image);

            holder.circulimg= (ImageView) convertView.findViewById(R.id.circulimg);
            holder.txt_address= (TextView)convertView.findViewById(R.id.text_address);
            holder.txtrating = (Utils.RegularText)convertView.findViewById(R.id.txtrating);
            holder.txtdays = (Utils.RegularText)convertView.findViewById(R.id.txtdays);
            holder.Delete = (Utils.RegularText)convertView.findViewById(R.id.delete);
            holder.panding = (Utils.RegularText)convertView.findViewById(R.id.panding);
            holder.txt_rating= (TextView)convertView.findViewById(R.id.txtrating);
            holder.text_cat_name= (TextView)convertView.findViewById(R.id.text_cat_name);

            convertView.setTag(holder);

        } else {
            holder = (Viewholder) convertView.getTag();
        }
        holder.txtlike_count.setText("Likes"+"("+searchdto.get(position).getLikecount()+")");
        holder.txt_address.setText(searchdto.get(position).getLocation());

        holder.txt_rating.setText(searchdto.get(position).getrating());
        holder.text_cat_name.setText(searchdto.get(position).getName());
        holder.txt_review.setText(searchdto.get(position).getReview());
        holder.txtdays.setText(searchdto.get(position).getDay_ago());
        holder.panding.setText(searchdto.get(position).getStatus());

        imageLoader.DisplayImage(searchdto.get(position).getImage(), holder.subimage);

        if (searchdto.get(position).getStatus().equals("pending")){
            holder.circulimg.setBackgroundResource(R.drawable.panding);
        }
        else if(searchdto.get(position).getStatus().equals("rejected"))
            holder.circulimg.setBackgroundResource(R.drawable.rejected);
        else if(searchdto.get(position).getStatus().equals("approved"))
            holder.circulimg.setBackgroundResource(R.drawable.green);

        holder.txtrating.setText(searchdto.get(position).getrating());
        float ratingno = Float.valueOf(searchdto.get(position).getrating());
        if (ratingno <= 2) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(0, Color.parseColor("#c4171d"));
            drawable.setColor(Color.parseColor("#c4171d"));
            holder.txtrating.setBackgroundDrawable(drawable);
        } else if (ratingno > 2 && ratingno < 4) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(0, Color.parseColor("#f8a820"));
            drawable.setColor(Color.parseColor("#f8a820"));
            holder.txtrating.setBackgroundDrawable(drawable);
        } else if (ratingno >= 4) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(0, Color.parseColor("#009812"));
            drawable.setColor(Color.parseColor("#009812"));
            holder.txtrating.setBackgroundDrawable(drawable);
        }

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                // LayoutInflater inflater = this.getLayoutInflater();
                alertDialogBuilder.setTitle(Html.fromHtml("<font color='#000000'>Are you sure want to delete?</font>"));
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id = searchdto.get(position).getId();
                        new DeleteReview(position).execute();
                        searchdto.remove(position);
                        ReviewOutletAdapetr.this.notifyDataSetChanged();

                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return convertView;
    }
public  String getdays(){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date d1 = null;
    Date d2 = null;

    try {
        d1 = format.parse("2016-05-11 10:08:42");
        d2 = format.parse("2016-05-12 14:39:20");
    } catch (ParseException e) {
        e.printStackTrace();
    }

//in milliseconds
    long diff = d2.getTime() - d1.getTime();

    long diffSeconds = diff / 1000 % 60;
    long diffMinutes = diff / (60 * 1000) % 60;
    long diffHours = diff / (60 * 60 * 1000) % 24;
    long diffDays = diff / (24 * 60 * 60 * 1000);

    System.out.print(diffDays + " days, ");
    System.out.print(diffHours + " hours, ");
    System.out.print(diffMinutes + " minutes, ");
    System.out.print(diffSeconds + " seconds.");
    return String.valueOf(diffDays);
}
    class Viewholder {
        TextView txt_distance,txt_rating,txt_review,txtlike_count,txt_like,txt_curentdate,txt_reviewcreated;
        TextView txt_address,text_cat_name,text_market;
        com.assan.CircleImageView subimage,iv_right_arrow,iv_bookmark,iv_mobile;
        Utils.RegularText txtrating,txtdays,Delete,panding;
        ImageView circulimg;

    }

    public class DeleteReview extends AsyncTask<Void,Void,Void>{

        int position;
        public DeleteReview(int position){
            this.position = position;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle("Please wait..");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SearchCategoriesDAO userDAO = new SearchCategoriesDAO(mContext);
            categoriesDTO =   userDAO.deleteReview(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //if (categoriesDTO.getDeletemsg_status().equals("1"))
            mProgressDialog.dismiss();
            alertDialog.dismiss();
        }
    }
}
