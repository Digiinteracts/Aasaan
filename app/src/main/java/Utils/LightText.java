package Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Sonu Saini on 4/21/2016.
 */


public class LightText extends TextView {

    public LightText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LightText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LightText(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "font/OpenSans-Light.ttf");
        setTypeface(tf);
    }

}
