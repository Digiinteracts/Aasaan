package Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Digi-T1 on 4/21/2016.
 */
public class RegularText extends TextView {

    public RegularText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularText(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "font/OpenSans-Regular.ttf");
        setTypeface(tf);
    }

}
