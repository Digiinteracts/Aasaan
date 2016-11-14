package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fregment.SlidderFragment;

import java.util.List;

/**
 * @author Sonu Saini
 *
 */
public class SlidderAdapter extends FragmentStatePagerAdapter {
    List<Integer> list;

    public SlidderAdapter(Context context, FragmentManager fm,
                          List<Integer> list) {
        super(fm);
        this.list = list;
        if (list.size() == 0) {
            list.add(0);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();

        if (position == 0) {
            f = SlidderFragment.newInstance(list.get(position), position);
        } else {
            f = SlidderFragment.newInstance(list.get(position), position);
        }

        return f;
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
