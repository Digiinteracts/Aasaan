package Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sonu Saini on 4/20/2016.
 */
public class OpenSansText extends TextView {

    public OpenSansText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OpenSansText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenSansText(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "font/OpenSans-Regular");
        setTypeface(tf);
    }

}