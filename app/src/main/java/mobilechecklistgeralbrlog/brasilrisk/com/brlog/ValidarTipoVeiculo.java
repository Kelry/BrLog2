package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.GetRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_TipoCarreta;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class ValidarTipoVeiculo extends AppCompatActivity {

    Spinner spinner;
    Button btn_continuar;
    private Toolbar mToolbar;
    TextView title;
    List<Get_TipoCarreta> Carretas;
    Result result;
    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_tipo_veiculo);
        Inicialize();
        ListarCarreta();
        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        result = BrasilRisk.GetResult(ValidarTipoVeiculo.this);
        result.setCodVeiculo(null);
        result.setPlaca(null);


        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                    Get_TipoCarreta item = (Get_TipoCarreta) spinner.getSelectedItem();
                    if (item != null) {
                        result.setCodVeiculoTipo(String.valueOf(item.getCodVeiculoTipo()));
                        editor.putInt(Shered_Cache.COD_TIPO_CARRETA, item.getCodVeiculoTipo());
                        BrasilRisk.SetResult(ValidarTipoVeiculo.this, result);
                        it = new Intent(ValidarTipoVeiculo.this, Activity_transportadora.class);
                        startActivity(it);

                    } else {
                        result.setCodVeiculoTipo(null);
                        BrasilRisk.SetResult(ValidarTipoVeiculo.this, result);
                        it = new Intent(ValidarTipoVeiculo.this, Activity_transportadora.class);
                        startActivity(it);
                    }
                } else {
                    DialogShow_Empty("Ocorreu um problema de conexão, tente novamente.");
                }

            }
        });

    }

    public void Inicialize() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Validar tipo do Veículo ");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner = (Spinner) findViewById(R.id.sp_ValidarTipoVeiculo);
        btn_continuar = (Button) findViewById(R.id.btn_CONTINUAR_);
    }

    public void ListarCarreta() {
        final GetRequestAsyncTask send = new GetRequestAsyncTask(this, "listar/listartipocarreta", null);
        send.EnableProgressdialog("Carregando");
        send.setOnPostExecute(new OnPostExecute() {
            @Override
            public void OnPostExecute() {
                Type type2 = new TypeToken<ArrayList<Get_TipoCarreta>>() {
                }.getType();

                try {

                    Carretas = new Gson().fromJson(send.getResponse(), type2);
                    if (Carretas.get(0).getStatusCode() == 200) {
                        Object[] temp = Carretas.toArray();
                        ArrayAdapter<Object> arrayAdapter;
                        arrayAdapter = new ArrayAdapter<Object>(ValidarTipoVeiculo.this, R.layout.item_spinner, temp);
                        arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                        spinner.setAdapter(arrayAdapter);
                    } else {
                        DialogShow_Empty(Carretas.get(0).getMensagem());
                    }

                } catch (Exception ex) {
                    DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");

                }


            }
        });
        send.execute();
    }

    private void DialogShow_Empty(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void DialogShow_ERRO(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


}
