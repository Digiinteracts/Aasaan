package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.assan.R;
import com.assan.SplashActivity;

public class AlertDialogManager {

	Context context;

	public AlertDialogManager(Context context){
		this.context = context;
	}

	public void showAlertDialog(Context context, String title, String message,
								Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if (status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public void DialogBox(String msg) {
	final Dialog dialog = new Dialog(context);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	dialog.setContentView(R.layout.filter);
	TextView title = (TextView) dialog.findViewById(R.id.title);
	title.setText(msg);
		title.setGravity(Gravity.CENTER);
	TextView ok = (TextView) dialog.findViewById(R.id.ok);
	ok.setOnClickListener(new View.OnClickListener()
	{
		@Override
		public void onClick (View v){
		dialog.dismiss();
	}
	}
	);
	dialog.show();
}

}
