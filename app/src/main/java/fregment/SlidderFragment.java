package fregment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.assan.R;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubeStandalonePlayer;


/**
 * @author Pintu Kumar Patil 9977368049
 * @author 19-May-2015
 */


/**
 * Fragment for slider by using default drawable images .
 */
public class SlidderFragment extends Fragment {

    private int REQ_PLAYER_CODE = 1;
    private static String YT_KEY = "AIzaSyAuhyqopkW1UEFhfwq_09JhEOwmyR0Xyrc";
    private static String VIDEO_ID = "SPRrJ0vuRlg";    // Your video id here

    private Integer Img;
    private int Position;
    View view = null;

    public static Fragment newInstance(int argImage, int argPosition) {

        Fragment fragment = new SlidderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("image", argImage);
        bundle.putInt("position", argPosition);
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            Img = this.getArguments().getInt("image");
            Position = this.getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        readBundle(getArguments());

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.myslidder, container, false);
        } catch (InflateException e) {
            // map is already there, just return view as it is
        }
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv = (ImageView) view.findViewById(R.id.iv1_mytutorialviewpager);
        try {
            iv.setBackgroundResource(Img);
            //iv.setImageResource(Img);
            iv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

						/*Intent inte=new Intent(getActivity(), Videoactvity.class);
                        startActivity(inte);
						getActivity().finish();*/


                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    switch (Position) {
                        case 0:
                            intent.setData(Uri.parse("https://www.youtube.com/watch?v=P5jxRjnGQWI&feature=youtu.be"));
                            break;
                        case 1:
                            intent.setData(Uri.parse("https://www.youtube.com/watch?v=SPRrJ0vuRlg"));
                            break;
                        default:
                            intent.setData(Uri.parse("https://www.youtube.com/watch?v=P5jxRjnGQWI&feature=youtu.be"));

                    }
                    startActivity(intent);
						
						/*try {
							Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), YT_KEY, VIDEO_ID, 0, true, false);
							
							startActivityForResult(videoIntent, REQ_PLAYER_CODE);
							
						} catch (Exception e) {
							// TODO: handle exception
						}*/

                    //Toast.makeText(getActivity(), "test", 1000).show();
                }
            });
        } catch (NullPointerException e) {
            // TODO: handle exception
            e.printStackTrace();

        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            e.printStackTrace();

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    //   Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_container);
     //   fragment.onActivityResult(requestCode, resultCode, data);


//        if (requestCode == REQ_PLAYER_CODE && resultCode != -1) {
//            YouTubeInitializationResult errorReason = YouTubeStandalonePlayer.getReturnedInitializationResult(data);
//            if (errorReason.isUserRecoverableError()) {
//                errorReason.getErrorDialog(getActivity(), 0).show();
//            } else {
//                String errorMessage = String.format("PLAYER ERROR!!", errorReason.toString());
//                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
//            }
//        }
    }
}
