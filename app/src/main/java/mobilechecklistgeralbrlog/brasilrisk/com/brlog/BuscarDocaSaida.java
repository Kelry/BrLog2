package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.GetRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_Documentos;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_sm;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class BuscarDocaSaida extends AppCompatActivity implements View.OnClickListener {

    Button btn_;
    Spinner documento_spinner;
    TextView nome, trans, cavalo, carreta, doc, perfil;
    EditText Documento_pesquisa;
    Toolbar mToolbar;
    TextView title;
    AlertDialog alert;
    List<Get_Documentos> Listar_Spinner;
    ScrollView scrollView;
    Result result;
    boolean LineHal = false;
    int CodTipoChecklist;
    String nome_, doc_, trans_, carreta_, cavalo_, perfil_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_doca_saida);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Saída de Doca");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_ = (Button) findViewById(R.id.btn_);
        documento_spinner = (Spinner) findViewById(R.id.sp_documento_doca);
        result = BrasilRisk.GetResult(BuscarDocaSaida.this);
        nome = (TextView) findViewById(R.id.gettext_nome);
        doc = (TextView) findViewById(R.id.gettext_doc);
        trans = (TextView) findViewById(R.id.gettext_transp);
        cavalo = (TextView) findViewById(R.id.gettext_cavalo);
        carreta = (TextView) findViewById(R.id.gettext_carreta);
        perfil = (TextView) findViewById(R.id.gettxt_perfil);
        Documento_pesquisa = (EditText) findViewById(R.id.txt_documento_pesquisa);
        scrollView = (ScrollView) findViewById(R.id.scroll_Doca);
        result.setCodTipoChecklist(0);

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();
        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            Bundle ObjetoRecebido = getIntent().getExtras();
            if (ObjetoRecebido != null) {
                CodTipoChecklist = ObjetoRecebido.getInt("CodTipoChecklist");
            }
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
                                arrayAdapter = new ArrayAdapter<Object>(BuscarDocaSaida.this, R.layout.item_spinner, temp);
                                arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                                documento_spinner.setAdapter(arrayAdapter);
                            } else {
                                DialogShow_Empty(Listar_Spinner.get(0).getMensagem());
                            }

                        } catch (Exception ex) {
                            DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                        }

                    }
                });
            } catch (Exception ex) {
                DialogShow_Empty("Ocorreu um problema de instabilidade no sistema, entre em contato com o suporte. ");
            }

            send.execute();
        } else {
            DialogShow_net();
        }

        btn_.setText("Pesquisar");
        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                    final Get_Documentos item = (Get_Documentos) documento_spinner.getSelectedItem();
                    int codigoDocumento = item.getCodTipoConsulta();
                    if (Documento_pesquisa.getText().length() != 0) {
                        BuscarDocumento(Documento_pesquisa.getText().toString(), codigoDocumento);
                    } else {
                        DialogShow_Empty("Não foi digitado um número de documento para pesquisa");
                    }
                } else {
                    DialogShow_net();
                }


            }
        });
    }

    private void BuscarDocumento(String Documento_digitado, final int Cod_Documento) {
        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        SharedPreferences.Editor editor = preferences.edit();
        final GetRequestAsyncTask send = new GetRequestAsyncTask(this, "validar/SMLDocaSaida", null);
        send.AddFirstParam(Shered_Cache.COD_TIPO_CONSULTA_SML, String.valueOf(Cod_Documento));
        send.AddParam("nrDocumento", String.valueOf(Documento_digitado));
        int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
        final String codEmpresa = String.valueOf(cod);
        send.AddParam(Shered_Cache.COD_EMPRESA, codEmpresa);
        send.EnableProgressdialog("Carregando");

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfor = cn.getActiveNetworkInfo();
        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            send.setOnPostExecute(new OnPostExecute() {
                @Override
                public void OnPostExecute() {
                    Type type2 = new TypeToken<Get_sm>() {
                    }.getType();

                    try {

                        Get_sm get_dados_saida = (Get_sm) new GsonBuilder().create().fromJson(send.getResponse(), Get_sm.class);
                        if (get_dados_saida.getStatusCode() == 200) {


                            if (get_dados_saida.getVeiculo() == null && get_dados_saida.getMotorista() == null) {

                                result.setCpfMotorista(null);
                                result.setCodMotorista(null);
                                result.setPlaca("");
                                result.setCodVeiculo(null);

                                cavalo_ = "";
                                nome_ = "";
                                perfil_ = "";


                            } else if (get_dados_saida.getMotorista() == null && get_dados_saida.getVeiculo() != null) {

                                nome_ = "";
                                perfil_ = "";
                                doc_ = String.valueOf(get_dados_saida.getCodPedido());
                                trans_ = get_dados_saida.getNomeTransportadora();
                                carreta_ = get_dados_saida.getPlacaCarreta();
                                result.setCpfMotorista(null);
                                result.setCodMotorista(null);


                            } else if (get_dados_saida.getVeiculo() == null && get_dados_saida.getMotorista() != null) {
                                result.setPlaca("");
                                cavalo_ = "";
                                nome_ = get_dados_saida.getMotorista().getNomeMotorista();


                                perfil_ = get_dados_saida.getMotorista().getPerfilMotorista();
                                result.setCpfMotorista(get_dados_saida.getMotorista().getCPF());
                                result.setCodMotorista(String.valueOf(get_dados_saida.getCodMotorista()));


                            } else if (get_dados_saida.getVeiculo() != null && get_dados_saida.getMotorista() != null) {
                                nome_ = get_dados_saida.getMotorista().getNomeMotorista();
                                perfil_ = get_dados_saida.getMotorista().getPerfilMotorista();
                                result.setCpfMotorista(get_dados_saida.getMotorista().getCPF());
                                result.setCodMotorista(String.valueOf(get_dados_saida.getCodMotorista()));
                                cavalo_ = get_dados_saida.getVeiculo().getPlaca();
                            }

                            doc_ = String.valueOf(get_dados_saida.getCodPedido());
                            trans_ = get_dados_saida.getNomeTransportadora();
                            carreta_ = get_dados_saida.getPlacaCarreta();


                            ArrayList<Integer> Itens_LineH = new ArrayList<>();
                            Itens_LineH.add(Shered_Cache.LinH_);
                            Itens_LineH.add(Shered_Cache.LinH_Balsa);
                            Itens_LineH.add(Shered_Cache.LinH_Dev);


                            if (Itens_LineH.contains(get_dados_saida.getCodTipoTrajeto()))
                                LineHal = true;

                            nome.setText(nome_);
                            doc.setText(doc_);
                            trans.setText(trans_);
                            carreta.setText(carreta_);
                            cavalo.setText(cavalo_);
                            perfil.setText(perfil_);
                            result.setCodStatusCheckList(1);
                            result.setAprovado(true);
                            result.setCodTransportadora(String.valueOf(get_dados_saida.getCodTransportadora()));
                            result.setSolicitacaoMonitoramentoId(String.valueOf(get_dados_saida.getCodPedido()));
                            result.setCodTipoChecklist(CodTipoChecklist);
                            result.setCheckListResultado(null);
                            result.setLatitude(0);
                            result.setLongitude(0);
                            result.setCodSegmentoTransporte(null);
                            result.setProtocolo(null);
                            result.setCartaoPedagio(null);
                            result.setPosicaoPaletesNaCarreta(null);
                            result.setAlturaBauSider(0);
                            result.setQtdeEixos(null);
                            result.setQtdeLacres(null);
                            result.setNrMinuta(null);
                            result.setNrMoop(null);
                            result.setValidadeMoop(null);
                            result.setImagem(null);
                            result.setDhInicioChecklist(null);
                            result.setDhFimChecklist(null);
                            result.setNumeroDaDoca(null);
                            result.setNumeroDaRampa(null);
                            result.setComprimentoVeiculo(0);
                            result.setLarguraVeiculo(0);
                            result.setAlturaVeiculo(0);
                            result.setNumeroLacrePortaTraseira(null);
                            result.setNumeroLacrePortaLateral(null);
                            result.setQtdeDevolucao(0);
                            result.setQtdeNotasFiscais(0);
                            result.setVeiculoApto(false);
                            result.setMotoristaApto(false);
                            BrasilRisk.SetResult(BuscarDocaSaida.this, result);
                            EnableContinuar();

                        } else {

                            nome.setText("");
                            doc.setText("");
                            trans.setText("");
                            cavalo.setText("");
                            carreta.setText("");
                            DialogShow_Empty(get_dados_saida.getMensagem());
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
    }

    private void EnableContinuar() {
        if (LineHal) {
            btn_.setText("Continuar");
        } else
            btn_.setText("Capturar as fotos");
        btn_.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("LineHal", LineHal);

        if (LineHal) {
            Intent it = new Intent(BuscarDocaSaida.this, ItensAdicionaisSaida_LH.class);
            it.putExtras(bundle);
            startActivity(it);
        } else {
            Intent it = new Intent(BuscarDocaSaida.this, FotosSaidaDoca.class);
            it.putExtras(bundle);
            startActivity(it);
        }
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
