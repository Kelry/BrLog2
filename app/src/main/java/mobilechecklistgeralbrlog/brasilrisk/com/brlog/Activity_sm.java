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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.GetRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_Documentos;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_sm;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_sm extends AppCompatActivity {

    Button prosseguir;
    Switch sw_sm;
    EditText sm;
    Result result;
    Toolbar mToolbar;
    AlertDialog alert;
    TextView title;
    Spinner spinner;
    List<Get_Documentos> Listar_Spinner;
    private boolean EscolherCheckList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Validação de viagem");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        result = BrasilRisk.GetResult(Activity_sm.this);
        sw_sm = (Switch) findViewById(R.id.sw_sm);
        prosseguir = (Button) findViewById(R.id.btn_continuar);
        sm = (EditText) findViewById(R.id.txt_sm);
        spinner = (Spinner) findViewById(R.id.sp_sm);
        sm.setText("");

        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Shered_Cache.COD_PEDIDO, 0);
        editor.commit();


        boolean ProsseguirSemNumeroDeDocumento = preferences.getBoolean(Shered_Cache.PROSSEGUIR_SEM_DOCUMENTO, false);
        final boolean selecionarTipoOperacao = preferences.getBoolean(Shered_Cache.SELECIONAR_TIPO_OPERACAO, false);

        final boolean PermitidoRealizarChecklistSemMotorista = preferences.getBoolean(Shered_Cache.PERMITIDO_SEGUIRSEM_MOTORISTA, false);
        final boolean PermitidoRealizarChecklistSemVeiculo = preferences.getBoolean(Shered_Cache.PERMITIDO_SEGUIRSEM_VEICULO, false);


        int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
        final String codEmpresa = String.valueOf(cod);
        EscolherCheckList = preferences.getBoolean(Shered_Cache.SELECIONAR_TIPO_CHECKLIST, false);

        // Alimentação do Spinner
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            try {
                final GetRequestAsyncTask send = new GetRequestAsyncTask(this, "listar/ListarTipoDeConsultaSML", null);
                send.EnableProgressdialog("Carregando");

                try {
                    send.setOnPostExecute(new OnPostExecute() {
                        @Override
                        public void OnPostExecute() {

                            Type type2 = new TypeToken<ArrayList<Get_Documentos>>() {
                            }.getType();

                            try {
                                Listar_Spinner = new Gson().fromJson(send.getResponse(), type2);

                                if (Listar_Spinner.get(0).getStatusCode() == 200) {

                                    Object[] temp = Listar_Spinner.toArray();
                                    ArrayAdapter<Object> arrayAdapter;
                                    arrayAdapter = new ArrayAdapter<Object>(Activity_sm.this, R.layout.item_spinner, temp);
                                    arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                                    spinner.setAdapter(arrayAdapter);

                                } else {
                                    DialogShow_Empty(Listar_Spinner.get(0).getMensagem());
                                    prosseguir.setEnabled(false);
                                }

                            } catch (Exception ex) {
                                DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                            }

                        }
                    });

                } catch (Exception ex) {
                    DialogShow_Empty("Ocorreu alguma instabilidade no sistema, entre em contato com o suporte.");
                }

                send.execute();

            } catch (Exception ex) {
                DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
            }

        } else {
            DialogShow_net();
        }

        //verifcação de campos escolhidos
        sw_sm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    sm.setText("");
                if (sm.getText().length() > 0)
                    sw_sm.setChecked(false);
            }
        });

        //Validação de permissão de seguir com ou sem SM
        if (ProsseguirSemNumeroDeDocumento) {

            prosseguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                        if (sw_sm.isChecked() && sm.getText().length() > 0) {
                            sm.setText("");
                            DialogShow_SM("Escolha uma opção apenas!");

                        } else if (sw_sm.isChecked()) {
                            sm.setText("");
                            editor.putString(Shered_Cache.COD_TIPO_TRAJETO, null);
                            editor.commit();
                            result.setSolicitacaoMonitoramentoId(null);
                            editor.putString(Shered_Cache.COD_PEDIDO, null);
                            editor.putString(Shered_Cache.COD_TRANSPORTADORA, null);
                            editor.putString(Shered_Cache.NOME_TRANSPORTADORA, null);
                            result.setCodTransportadora(null);
                            BrasilRisk.SetResult(Activity_sm.this, result);

                            if (selecionarTipoOperacao) {
                                editor.putString(Shered_Cache.NOME_TRANSPORTADORA, null);
                                Intent it = new Intent(Activity_sm.this, Activity_Operacao.class);
                                startActivity(it);

                            } else {
                                editor.putString(Shered_Cache.SELECIONAR_TIPO_OPERACAO, null);
                                editor.putString(Shered_Cache.NOME_TRANSPORTADORA, null);
                                editor.commit();
                                Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                startActivity(it);
                            }

                        } else {

                            //pega o item do spinner
                            final Get_Documentos item = (Get_Documentos) spinner.getSelectedItem();
                            String codigoDocumento = String.valueOf(item.getCodTipoConsulta());
                            ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfor = cn.getActiveNetworkInfo();

                            if (sm.getText().length() != 0) {
                                if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                                    final GetRequestAsyncTask send = new GetRequestAsyncTask(Activity_sm.this, "validar/sml", null);
                                    send.AddFirstParam(Shered_Cache.COD_TIPO_CONSULTA_SML, codigoDocumento);
                                    send.AddParam(Shered_Cache.NR_DOCUMENTO, sm.getText().toString());
                                    send.AddParam(Shered_Cache.COD_EMPRESA, codEmpresa);
                                    send.EnableProgressdialog("Carregando");

                                    send.setOnPostExecute(new OnPostExecute() {
                                        @Override
                                        public void OnPostExecute() {
                                            Type type2 = new TypeToken<Get_sm>() {
                                            }.getType();

                                            try {

                                                Get_sm get_sm = (Get_sm) new GsonBuilder().create().fromJson(send.getResponse(), type2);

                                                if (get_sm.getStatusCode() == 200) {

                                                    result.setSolicitacaoMonitoramentoId(String.valueOf(get_sm.getCodPedido()));
                                                    result.setPlacaCarreta(get_sm.getPlacaCarreta());
                                                    result.setPlacaCarreta2(get_sm.getPlacaCarreta2());
                                                    result.setCodTransportadora(null);
                                                    result.setPlacaCarreta(get_sm.getPlacaCarreta());
                                                    result.setPlacaCarreta2(get_sm.getPlacaCarreta2());
                                                    BrasilRisk.SetResult(Activity_sm.this, result);

                                                    if (selecionarTipoOperacao == false) {

                                                        editor.putInt(Shered_Cache.COD_TIPO_TRAJETO, get_sm.getCodTipoTrajeto());
                                                        editor.putInt(Shered_Cache.COD_TRANSPORTADORA, get_sm.getCodTransportadora());
                                                        editor.putInt(Shered_Cache.COD_PEDIDO, get_sm.getCodPedido());
                                                        editor.putString(Shered_Cache.ORDEM_COLETA, get_sm.getOrdemColeta());
                                                        editor.putString(Shered_Cache.NUMERO_MINUTA, get_sm.getMinuta());
                                                        editor.commit();

                                                        if (Shered_Cache.AVON == get_sm.getCodEmpresa()) {

                                                            if (PermitidoRealizarChecklistSemMotorista && get_sm.getMotorista() == null) {
                                                                result.setCodMotorista(null);
                                                                result.setCpfMotorista(null);
                                                                BrasilRisk.SetResult(Activity_sm.this, result);
                                                                if (PermitidoRealizarChecklistSemVeiculo && get_sm.getVeiculo() == null) {
                                                                    Intent it = new Intent(Activity_sm.this, ValidarTipoVeiculo.class);
                                                                    startActivity(it);
                                                                } else {
                                                                    Intent it = new Intent(Activity_sm.this, Activity_veiculo.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putSerializable(Shered_Cache.OBJETO_VEICULO_SM, get_sm);
                                                                    it.putExtras(bundle);
                                                                    startActivity(it);
                                                                }
                                                            } else {
                                                                Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                                it.putExtras(bundle);
                                                                startActivity(it);
                                                            }
                                                        } else {
                                                            Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                            it.putExtras(bundle);
                                                            startActivity(it);
                                                        }

                                                    } else {

                                                        if (get_sm.getCodTipoTrajeto() != 0) {

                                                            editor.putInt(Shered_Cache.COD_TIPO_TRAJETO, get_sm.getCodTipoTrajeto());
                                                            editor.putInt(Shered_Cache.COD_TRANSPORTADORA, get_sm.getCodTransportadora());
                                                            editor.putInt(Shered_Cache.COD_PEDIDO, get_sm.getCodPedido());
                                                            editor.putString(Shered_Cache.ORDEM_COLETA, get_sm.getOrdemColeta());
                                                            editor.putString(Shered_Cache.NUMERO_MINUTA, get_sm.getMinuta());
                                                            editor.commit();

                                                            if (get_sm.getCodEmpresa() == Shered_Cache.AVON) {
                                                                if (PermitidoRealizarChecklistSemMotorista && get_sm.getMotorista() == null) {
                                                                    result.setCodMotorista(null);
                                                                    result.setCpfMotorista(null);
                                                                    BrasilRisk.SetResult(Activity_sm.this, result);
                                                                    if (PermitidoRealizarChecklistSemVeiculo && get_sm.getVeiculo() == null) {
                                                                        Intent it = new Intent(Activity_sm.this, ValidarTipoVeiculo.class);
                                                                        startActivity(it);

                                                                    } else {
                                                                        Intent it = new Intent(Activity_sm.this, Activity_veiculo.class);
                                                                        Bundle bundle = new Bundle();
                                                                        bundle.putSerializable(Shered_Cache.OBJETO_VEICULO_SM, get_sm);
                                                                        it.putExtras(bundle);
                                                                        startActivity(it);
                                                                    }
                                                                } else {
                                                                    Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                                    it.putExtras(bundle);
                                                                    startActivity(it);
                                                                }
                                                            } else {

                                                                Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                                it.putExtras(bundle);
                                                                startActivity(it);
                                                            }
                                                        } else {

                                                            Intent it = new Intent(Activity_sm.this, Activity_Operacao.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                            it.putExtras(bundle);
                                                            startActivity(it);
                                                        }
                                                    }

                                                } else {
                                                    DialogShow_SM(get_sm.getMensagem());
                                                }

                                            } catch (Exception ex) {
                                                DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");

                                            }

                                        }
                                    });

                                    send.execute();

                                } else {
                                    DialogShow_net();
                                }
                            } else {
                                DialogShow_SM("É necessário informar um número de documento.");
                            }
                        }
                    } else {
                        DialogShow_net();
                    }

                }
            });

        } else {

            // validação Avon que é obrigatório incluir a sml
            sw_sm.setVisibility(View.INVISIBLE);
            prosseguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                        //pega o item do spinner
                        final Get_Documentos item = (Get_Documentos) spinner.getSelectedItem();
                        String codigoDocumento = String.valueOf(item.getCodTipoConsulta());

                        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfor = cn.getActiveNetworkInfo();

                        if (sm.getText().length() != 0) {
                            if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                                final GetRequestAsyncTask send = new GetRequestAsyncTask(Activity_sm.this, "validar/sml", null);
                                send.AddFirstParam(Shered_Cache.COD_TIPO_CONSULTA_SML, codigoDocumento);
                                send.AddParam(Shered_Cache.NR_DOCUMENTO, sm.getText().toString());
                                send.AddParam(Shered_Cache.COD_EMPRESA, codEmpresa);
                                send.EnableProgressdialog("Carregando");

                                send.setOnPostExecute(new OnPostExecute() {
                                    @Override
                                    public void OnPostExecute() {
                                        Type type2 = new TypeToken<Get_sm>() {
                                        }.getType();

                                        try {
                                            Get_sm get_sm = (Get_sm) new GsonBuilder().create().fromJson(send.getResponse(), type2);

                                            if (get_sm.getStatusCode() == 200) {

                                                result.setSolicitacaoMonitoramentoId(String.valueOf(get_sm.getCodPedido()));
                                                result.setPlacaCarreta(get_sm.getPlacaCarreta());
                                                result.setPlacaCarreta2(get_sm.getPlacaCarreta2());
                                                result.setCodTransportadora(null);
                                                BrasilRisk.SetResult(Activity_sm.this, result);

                                                if (selecionarTipoOperacao == false) {

                                                    editor.putInt(Shered_Cache.COD_TIPO_TRAJETO, get_sm.getCodTipoTrajeto());
                                                    editor.putInt(Shered_Cache.COD_TRANSPORTADORA, get_sm.getCodTransportadora());
                                                    editor.putInt(Shered_Cache.COD_PEDIDO, get_sm.getCodPedido());
                                                    editor.putString(Shered_Cache.ORDEM_COLETA, get_sm.getOrdemColeta());
                                                    editor.putString(Shered_Cache.NUMERO_MINUTA, get_sm.getMinuta());
                                                    editor.putString(Shered_Cache.NOME_TRANSPORTADORA, get_sm.getNomeTransportadora());
                                                    editor.commit();

                                                    if (get_sm.getCodEmpresa() == Shered_Cache.AVON) {
                                                        if (PermitidoRealizarChecklistSemMotorista && get_sm.getMotorista() == null) {
                                                            result.setCodMotorista(null);
                                                            result.setCpfMotorista(null);
                                                            BrasilRisk.SetResult(Activity_sm.this, result);
                                                            if (PermitidoRealizarChecklistSemVeiculo && get_sm.getVeiculo() == null) {
                                                                Intent it = new Intent(Activity_sm.this, ValidarTipoVeiculo.class);
                                                                startActivity(it);

                                                            } else {
                                                                Intent it = new Intent(Activity_sm.this, Activity_veiculo.class);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable(Shered_Cache.OBJETO_VEICULO_SM, get_sm);
                                                                it.putExtras(bundle);
                                                                startActivity(it);
                                                            }
                                                        } else {
                                                            Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                            it.putExtras(bundle);
                                                            startActivity(it);
                                                        }
                                                    } else {
                                                        Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                        it.putExtras(bundle);
                                                        startActivity(it);
                                                    }

                                                } else {

                                                    if (get_sm.getCodTipoTrajeto() != 0) {

                                                        editor.putInt(Shered_Cache.COD_TIPO_TRAJETO, get_sm.getCodTipoTrajeto());
                                                        editor.putInt(Shered_Cache.COD_TRANSPORTADORA, get_sm.getCodTransportadora());
                                                        editor.putInt(Shered_Cache.COD_PEDIDO, get_sm.getCodPedido());
                                                        editor.putString(Shered_Cache.ORDEM_COLETA, get_sm.getOrdemColeta());
                                                        editor.putString(Shered_Cache.NUMERO_MINUTA, get_sm.getMinuta());
                                                        editor.putString(Shered_Cache.NOME_TRANSPORTADORA, get_sm.getNomeTransportadora());
                                                        editor.commit();

                                                        if (Shered_Cache.AVON == get_sm.getCodEmpresa()) {
                                                            if (PermitidoRealizarChecklistSemMotorista && get_sm.getMotorista() == null) {
                                                                result.setCodMotorista(null);
                                                                result.setCpfMotorista(null);
                                                                BrasilRisk.SetResult(Activity_sm.this, result);
                                                                if (PermitidoRealizarChecklistSemVeiculo && get_sm.getVeiculo() == null) {
                                                                    Intent it = new Intent(Activity_sm.this, ValidarTipoVeiculo.class);
                                                                    startActivity(it);

                                                                } else {
                                                                    Intent it = new Intent(Activity_sm.this, Activity_veiculo.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putSerializable(Shered_Cache.OBJETO_VEICULO_SM, get_sm);
                                                                    it.putExtras(bundle);
                                                                    startActivity(it);
                                                                }
                                                            } else {
                                                                Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                                it.putExtras(bundle);
                                                                startActivity(it);
                                                            }
                                                        } else {
                                                            Intent it = new Intent(Activity_sm.this, Activity_motorista.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                            it.putExtras(bundle);
                                                            startActivity(it);
                                                        }
                                                    } else {
                                                        Intent it = new Intent(Activity_sm.this, Activity_Operacao.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putSerializable(Shered_Cache.OBJETO_MOTORISTA_SM, get_sm);
                                                        it.putExtras(bundle);
                                                        startActivity(it);
                                                    }
                                                }

                                            } else {
                                                DialogShow_SM(get_sm.getMensagem());
                                            }

                                        } catch (Exception ex) {
                                            DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                                        }

                                    }
                                });

                                send.execute();

                            } else {
                                DialogShow_net();
                            }
                        } else {
                            DialogShow_SM("É necessário informar um número de documento.");
                        }
                    } else {
                        DialogShow_net();
                    }

                }
            });
        }
    }

    private void DialogShow_SM(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("BRLog Checklist");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alert = builder.create();
        alert.show();
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

        if (EscolherCheckList == true) {
            Intent it = new Intent(Activity_sm.this, TipoChecklist.class);
            startActivity(it);
        } else {
            Intent it = new Intent(Activity_sm.this, MainActivity.class);
            startActivity(it);
        }
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