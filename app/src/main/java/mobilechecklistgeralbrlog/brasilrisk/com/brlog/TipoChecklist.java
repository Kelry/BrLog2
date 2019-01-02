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
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Tipo_Checklist;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class TipoChecklist extends AppCompatActivity {


    Button btn_continuar;
    Spinner spinner;
    Result r;
    private Toolbar mToolbar;
    TextView title;
    AlertDialog alert;
    List<Tipo_Checklist> tipoChecklists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_checklist);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Tipo de Checklist");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_continuar = (Button) findViewById(R.id.btn_continuar);
        spinner = (Spinner) findViewById(R.id.sp_tipo);


        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        if (netInfor != null && netInfor.isConnectedOrConnecting()) {

            final GetRequestAsyncTask send = new GetRequestAsyncTask(this, "listar/ListarTipoDeChecklist", null);
            send.EnableProgressdialog("Carregando");

            try {

                send.setOnPostExecute(new OnPostExecute() {
                    @Override
                    public void OnPostExecute() {
                        Type type2 = new TypeToken<ArrayList<Tipo_Checklist>>() {
                        }.getType();

                        try {

                            tipoChecklists = new Gson().fromJson(send.getResponse(), type2);

                            if (tipoChecklists.get(0).getStatusCode() == 200) {

                                Object[] temp = tipoChecklists.toArray();
                                ArrayAdapter<Object> arrayAdapter;
                                arrayAdapter = new ArrayAdapter<Object>(TipoChecklist.this, R.layout.item_spinner, temp);
                                arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                                spinner.setAdapter(arrayAdapter);

                            } else {
                                DialogShow_Empty(tipoChecklists.get(0).getMensagem());
                                btn_continuar.setEnabled(false);
                            }

                        } catch (Exception ex) {
                            DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                        }


                    }
                });

            } catch (Exception ex) {
                DialogShow_Empty("Problema de instabilidade, verifique a cinexão ou entre em contato com o suporte.");
            }

            send.execute();
        } else {
            DialogShow_net();
        }

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                    final Tipo_Checklist item = (Tipo_Checklist) spinner.getSelectedItem();
                    Result r = BrasilRisk.GetResult(TipoChecklist.this);
                    SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putInt(Shered_Cache.COD_TIPO_CHECKLIST, item.getCodTipoChecklist());
                    editor.commit();
                    r.setCodTipoChecklist(item.getCodTipoChecklist());
                    r.setNumeroDaDoca(null);
                    r.setNumeroDaRampa(null);
                    r.setComprimentoVeiculo(0);
                    r.setLarguraVeiculo(0);
                    r.setAlturaVeiculo(0);
                    r.setNumeroLacrePortaTraseira(null);
                    r.setNumeroLacrePortaLateral(null);
                    r.setQtdeDevolucao(0);
                    r.setQtdeNotasFiscais(0);
                    BrasilRisk.SetResult(TipoChecklist.this, r);
                    if (item.getCodTipoChecklist() == 5) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("CodTipoChecklist", item.getCodTipoChecklist());
                        editor.putInt("Saida_Doca", item.getCodTipoChecklist());
                        editor.commit();
                        Intent it = new Intent(TipoChecklist.this, BuscarDocaSaida.class);
                        it.putExtras(bundle);
                        startActivity(it);

                    } else {

                        Intent it = new Intent(TipoChecklist.this, Activity_sm.class);
                        startActivity(it);
                    }

                } else {
                    DialogShow_net();
                }


            }
        });

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

    private void DialogShow_Empty(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onBackPressed() {

        Intent it = new Intent(TipoChecklist.this, MainActivity.class);
        startActivity(it);

        // para que a pessoa não possa voltar pela função
        //voltar do app assim não tendo duplicidade de dados
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
