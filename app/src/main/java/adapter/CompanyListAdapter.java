package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assan.AddNewCompany;
import com.assan.AddNewStore;
import com.assan.OutletDetails;
import com.assan.R;
import com.assan.RegisterStoreOwner;
import com.assan.StoreStats;
import com.assan.Subscriptions;

import java.util.ArrayList;
import java.util.List;

import DTO.CompanyListDTO;

/**
 * Created by DigiT-25 on 30-08-2016.
 */
public class CompanyListAdapter extends ArrayAdapter<CompanyListDTO> {

    List<CompanyListDTO> item = new ArrayList<>();
    CompanyListDTO companyListDTO;
    Context context;
    LayoutInflater inflater;
    int analytics_flag = 0;

    public CompanyListAdapter(Context context,  List<CompanyListDTO> item,int analytics_flag) {
        super(context,0,item);

        this.analytics_flag = analytics_flag;
        this.context = context;
        this.item = item;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       // LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.company_list_adapter, null, true);

        TextView title = (TextView)listViewItem.findViewById(R.id.title);
        TextView email  = (TextView)listViewItem.findViewById(R.id.email);
        TextView status = (TextView)listViewItem.findViewById(R.id.status);
        TextView edit = (TextView)listViewItem.findViewById(R.id.Edit);

        if (analytics_flag == 8){
            title.setText(item.get(position).getTitle());
            email.setVisibility(View.GONE);
            status.setVisibility(View.GONE);

            edit.setTextColor(context.getResources().getColor(R.color.green));
            edit.setText("View");
            edit.setPadding(0,0,60,0);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("name ","d "+item.get(position).getTitle()+"id "+item.get(position).getId());
                Intent intent = new Intent(context, StoreStats.class);
                intent.putExtra("company_List_Adapter_flag",9);
                intent.putExtra("store_name",item.get(position).getTitle());
                intent.putExtra("store_id",item.get(position).getId());
                context.startActivity(intent);
                }
            });

        }
        else {
            title.setText(item.get(position).getTitle());

            if (item.get(position).getStatus().equals("1"))
                status.setText("Active");
            else
                status.setText("Inactive");

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(context, AddNewStore.class);
                intent.putExtra("company_List_Adapter_flag",7);
                intent.putExtra("store_id",item.get(position).getId());
                context.startActivity(intent);
                }
            });

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewIntent = new Intent(context, Subscriptions.class);
                    context.startActivity(viewIntent);
                }
            });
        }
        return  listViewItem;
    }

   /* @Override
    public int getCount() {
        return 1;
    }*/
}