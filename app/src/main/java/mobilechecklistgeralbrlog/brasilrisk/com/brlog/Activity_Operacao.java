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
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_operacao;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_sm;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_Operacao extends AppCompatActivity {


    Spinner spinner_operacao;
    Button continuar;
    List<Get_operacao> operacaos;
    private Toolbar mToolbar;
    TextView title;
    Get_sm objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__operacao);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Validação de operação");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        continuar = (Button) findViewById(R.id.btn_continuar);
        spinner_operacao = (Spinner) findViewById(R.id.sp_operacao);
        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        final boolean PermitidoRealizarChecklistSemMotorista = preferences.getBoolean(Shered_Cache.PERMITIDO_SEGUIRSEM_MOTORISTA, false);
        final boolean PermitidoRealizarChecklistSemVeiculo = preferences.getBoolean(Shered_Cache.PERMITIDO_SEGUIRSEM_VEICULO, false);


        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        Bundle ObjetoRecebido = getIntent().getExtras();

        if (ObjetoRecebido != null) {

            objeto = (Get_sm) ObjetoRecebido.getSerializable(Shered_Cache.OBJETO_MOTORISTA_SM);
        }


        if (netInfor != null && netInfor.isConnectedOrConnecting()) {

            final PostRequestAsyncTask send = new PostRequestAsyncTask(Activity_Operacao.this, "listar/ListarTipoOperacao");
            SharedPreferences.Editor editor = preferences.edit();

            int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
            String codE = String.valueOf(cod);
            send.AddParam(Shered_Cache.COD_EMPRESA, codE);
            send.EnableProgressdialog();

            send.setOnPostExecute(new OnPostExecute() {
                @Override
                public void OnPostExecute() {

                    if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                        Type type2 = new TypeToken<ArrayList<Get_operacao>>() {
                        }.getType();

                        try {
                            operacaos = new Gson().fromJson(send.getResponse(), type2);
                            if (operacaos.get(0).getStatusCode() == 200) {

                                Object[] temp = operacaos.toArray();
                                ArrayAdapter<Object> arrayAdapter;
                                arrayAdapter = new ArrayAdapter<Object>(Activity_Operacao.this, R.layout.item_spinner, temp);
                                arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                                spinner_operacao.setAdapter(arrayAdapter);
                            } else {
                                DialogShow_Empty(operacaos.get(0).getMensagem());
                                continuar.setEnabled(false);
                            }
                        } catch (Exception ex) {
                            DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                        }

                    } else {
                        DialogShow_net();
                    }

                }
            });

            send.execute();
        } else {
            DialogShow_net();
        }


        //Clique do botão para que quando ele clicar eu adicione la na frente no checklist o CodTipoOperacao
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Get_operacao item = (Get_operacao) spinner_operacao.getSelectedItem();
                Result r = BrasilRisk.GetResult(Activity_Operacao.this);
                SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
                SharedPreferences.Editor editor = preferences.edit();
                String codtioop = String.valueOf(item.getCodTipoOperacao());
                editor.putString(Shered_Cache.COD_TIPO_OPERACAO, codtioop);
                editor.commit();

                if (Shered_Cache.AVON == objeto.getCodEmpresa()) {
                    if (PermitidoRealizarChecklistSemMotorista && objeto.getMotorista() == null) {
                        Result result = BrasilRisk.GetResult(Activity_Operacao.this);
                        result.setCodMotorista(null);
                        BrasilRisk.SetResult(Activity_Operacao.this, result);
                        if (PermitidoRealizarChecklistSemVeiculo && objeto.getVeiculo() == null) {
                            Intent it = new Intent(Activity_Operacao.this, ValidarTipoVeiculo.class);
                            startActivity(it);

                        } else {

                            Intent it = new Intent(Activity_Operacao.this, Activity_veiculo.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Shered_Cache.OBJETO_VEICULO_SM, objeto);
                            it.putExtras(bundle);
                            startActivity(it);

                        }
                    } else {
                        Intent it = new Intent(Activity_Operacao.this, Activity_motorista.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, objeto);
                        it.putExtras(bundle);
                        startActivity(it);
                    }
                } else {
                    Intent it = new Intent(Activity_Operacao.this, Activity_motorista.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, objeto);
                    it.putExtras(bundle);
                    startActivity(it);
                }
            }
        });

    }

    //---------------------------------------------------
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