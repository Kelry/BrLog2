package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_Protocol extends AppCompatActivity {


    private Toolbar mToolbar;
    private AlertDialog alert;
    String protocolo;
    Result result;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__protocol);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        Intent it = getIntent();
        if (it != null) {
            Bundle params = it.getExtras();
            protocolo = params.getString("protocolo");
        }

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfor = cn.getActiveNetworkInfo();

        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            DialogShow();
        } else {

            DialogShow_net();
        }
    }

    private void DialogShow() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Protocolo");
        builder.setMessage(protocolo);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
                SharedPreferences.Editor editor = preferences.edit();
                Boolean EscolherTipoDoChecklist = preferences.getBoolean(Shered_Cache.SELECIONAR_TIPO_CHECKLIST, false);

                if (EscolherTipoDoChecklist) {

                    Intent it = new Intent(Activity_Protocol.this, TipoChecklist.class);
                    startActivity(it);

                } else {

                    Intent it = new Intent(Activity_Protocol.this, Activity_sm.class);
                    startActivity(it);
                }
            }
        });

        alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        // para que a pessoa não possa voltar pela função
        //voltar do app assim não tendo duplicidade de dados
    }

    private void DialogShow_net() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conexão");
        builder.setMessage("Dispositivo sem acesso a internet. Verifique sua conexão para continuar.");
        builder.setPositiveButton("Reconectar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
