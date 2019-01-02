package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * Created by evox on 27/10/16.
 */

public class Loads {

    public static ProgressDialog ProgressDialog(Context context,String titulo) {
       // AlertDialog progressdialog = new SpotsDialog(context, R.style.Custom);
        ProgressDialog progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(titulo);
        //ProgressDialog progressdialog = new ProgressDialog(context);
        //progressdialog.setMessage("Carregando... ");
        //progressdialog.setIndeterminate(true);
        //progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setCancelable(false);

        return  progressdialog;
    }
    public static ProgressDialog ProgressDialog2(Context context) {
        ProgressDialog progressdialog = new ProgressDialog(context);
        //ProgressDialog progressdialog = new ProgressDialog(context);
        //progressdialog.setMessage("Carregando... ");
        //progressdialog.setIndeterminate(true);
        //progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setCancelable(false);

        return  progressdialog;
    }
}
