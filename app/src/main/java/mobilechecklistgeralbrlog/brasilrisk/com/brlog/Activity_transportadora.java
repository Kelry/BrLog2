package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

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

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.PostRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_Transportadora;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_transportadora extends AppCompatActivity {

    Spinner spinner_transp;
    Button iniciar;
    List<Get_Transportadora> Transportadoras;
    private Toolbar mToolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportadora);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Transportadora");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        iniciar = (Button) findViewById(R.id.btn_continuar);
        spinner_transp = (Spinner) findViewById(R.id.sp_transportadora);

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        SharedPreferences.Editor editor = preferences.edit();

        int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
        String CodEmpresa = String.valueOf(cod);

        final int Codtransportadora = preferences.getInt(Shered_Cache.COD_TRANSPORTADORA, -1);
        final int codT = Codtransportadora;
        String CodTransp = String.valueOf(Codtransportadora);

        String Nometransportadora = preferences.getString(Shered_Cache.NOME_TRANSPORTADORA, null);

        final int CodPedido = preferences.getInt(Shered_Cache.COD_PEDIDO, -1);
        final String Codp;
        if (CodPedido == -1) {
            Codp = null;
        } else {
            Codp = String.valueOf(CodPedido);
        }


        final boolean SelecionarCarreta = preferences.getBoolean(Shered_Cache.SELECIONAR_TIPO_CARRETA, false);

        if (Nometransportadora == null) {
            //se transportadora vier vazio
            if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                final PostRequestAsyncTask send = new PostRequestAsyncTask
                        (Activity_transportadora.this, "listar/listarTransportadora");
                send.AddParam(Shered_Cache.COD_EMPRESA, CodEmpresa);
                send.EnableProgressdialog();

                try {
                    send.setOnPostExecute(new OnPostExecute() {
                        @Override
                        public void OnPostExecute() {
                            Type type2 = new TypeToken<ArrayList<Get_Transportadora>>() {
                            }.getType();

                            try {

                                Transportadoras = new Gson().fromJson(send.getResponse(), type2);

                                if (Transportadoras.get(0).getStatusCode() == 200) {
                                    Object[] temp = Transportadoras.toArray();
                                    ArrayAdapter<Object> arrayAdapter;
                                    arrayAdapter = new ArrayAdapter<Object>
                                            (Activity_transportadora.this,
                                                    R.layout.item_spinner, temp);
                                    arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                                    spinner_transp.setAdapter(arrayAdapter);
                                } else {
                                    DialogShow_Empty(Transportadoras.get(0).getMensagem());
                                    iniciar.setEnabled(false);
                                }

                            } catch (Exception ex) {
                                DialogShow_ERRO("Ocorreu uma instabilidade no sistema, entre em contato com o suporte.");
                            }

                        }
                    });
                } catch (Exception ex) {
                    DialogShow_Empty("Ocorreu uma instabilidade no sistema, entre em contato com o suporte.");
                }


                send.execute();

                iniciar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                            final Get_Transportadora item = (Get_Transportadora) spinner_transp.getSelectedItem();
                            Result r = BrasilRisk.GetResult(Activity_transportadora.this);
                            r.setCodTransportadora(String.valueOf(item.getCodTransportadora()));
                            r.setSolicitacaoMonitoramentoId(Codp);
                            BrasilRisk.SetResult(Activity_transportadora.this, r);
                            Intent it = new Intent(Activity_transportadora.this, Activity_adicionais.class);
                            startActivity(it);
                        } else {
                            DialogShow_net();
                        }

                    }
                });

            } else {

                DialogShow_net();
            }

        } else {
            //se já vier a transportadora
            String trans[] = {Nometransportadora};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, trans);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.dropd_item);
            spinner_transp.setAdapter(spinnerArrayAdapter);

            iniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Result r = BrasilRisk.GetResult(Activity_transportadora.this);
                    r.setCodTransportadora(String.valueOf(codT));
                    r.setSolicitacaoMonitoramentoId(Codp);
                    BrasilRisk.SetResult(Activity_transportadora.this, r);
                    Intent it = new Intent(Activity_transportadora.this, Activity_adicionais.class);
                    startActivity(it);
                }
            });
        }
    }

    private void DialogShow_net() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Conexão");
        builder.setMessage("Dispositivo sem acesso a internet. Verifique sua conexão para continuar.");
        builder.setPositiveButton("Reconectar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
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

