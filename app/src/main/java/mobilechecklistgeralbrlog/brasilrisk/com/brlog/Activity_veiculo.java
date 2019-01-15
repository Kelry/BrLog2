package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_TipoCarreta;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_Veiculo;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_sm;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Mask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_veiculo extends AppCompatActivity implements View.OnClickListener {
    public static final int Avon = 6740;
    Button btn_;
    EditText cavalo;
    EditText carreta1;
    EditText carreta2;
    TextView txtrast, info_posicao, dataUltima_posicao, Apto_Inapto,placaUF,carretaUF,Placa_DataCheckList,TituloUltimoCheckList,Carreta_DataCheckList;
    TextView txtterm, txtget_carreta;
    TextView rastreamento;
    TextView terminal;
    Intent it = null;
    android.app.AlertDialog alert;
    private Toolbar mToolbar;
    TextView title;
    Get_sm sm_veiculo = null;
    List<Get_TipoCarreta> Carretas;
    Spinner spinner_carretas;
    private Boolean ListarCarretas;
    ScrollView scrollView;
    ImageView icon_wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo);
        Inicializar();

        final SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        ListarCarretas = preferences.getBoolean(Shered_Cache.SELECIONAR_TIPO_CARRETA, false);
        int tipochecklist = preferences.getInt(Shered_Cache.COD_TIPO_CHECKLIST, -1);
        int codEmpresa = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
        boolean ExibirApto = preferences.getBoolean(Shered_Cache.EXIBIR_INFORMACAOVEICULO_AptoInapto, false);

        Spinner_TipoVeiculo(ListarCarretas, codEmpresa, tipochecklist);
        Bundle ObjetoRecebido = getIntent().getExtras();

        if (ObjetoRecebido != null) {
            sm_veiculo = (Get_sm) ObjetoRecebido.getSerializable(Shered_Cache.OBJETO_VEICULO_SM);

            if (sm_veiculo != null) {
                editor.putString(Shered_Cache.NOME_TRANSPORTADORA, sm_veiculo.getNomeTransportadora());
                editor.commit();

                if (sm_veiculo.getVeiculo() != null) {

                    placaUF.setVisibility(View.VISIBLE);
                    carretaUF.setVisibility(View.VISIBLE);

                    placaUF.setText("Placa: "+Verificacao(sm_veiculo.getVeiculo().getPlaca()));
                    carretaUF.setText("Carreta: "+Verificacao(sm_veiculo.getPlacaCarreta()));

                    cavalo.setText(sm_veiculo.getVeiculo().getPlaca());
                    carreta1.setText(sm_veiculo.getPlacaCarreta() );
                    carreta2.setText(sm_veiculo.getPlacaCarreta2());
                    txtrast.setVisibility(View.VISIBLE);
                    txtterm.setVisibility(View.VISIBLE);
                    dataUltima_posicao.setVisibility(View.VISIBLE);
                    info_posicao.setVisibility(View.VISIBLE);
                    rastreamento.setText(sm_veiculo.getVeiculo().getTecnologia());
                    terminal.setText(sm_veiculo.getVeiculo().getNumeroRastreador());

                    CheckList_Colorido(sm_veiculo.getVeiculo().getCorStatusUltimoChecklist());
                    TituloUltimoCheckList.setVisibility(View.VISIBLE);

                    Placa_DataCheckList.setVisibility(View.VISIBLE);
                    Carreta_DataCheckList.setVisibility(View.VISIBLE);

                    Placa_DataCheckList.setText("Placa: "+sm_veiculo.getVeiculo().getDataUltimoChecklist());

                    Carreta_DataCheckList.setText("Carreta: "+sm_veiculo.getVeiculo().getDataUltimoChecklistCarreta());
                    CheckList_CorCarreta(sm_veiculo.getVeiculo().getCorStatusUltimoChecklistCarreta());

                    if (ExibirApto)
                        Apto_Inapto.setText(sm_veiculo.getVeiculo().getSituacaoVeiculo());

                    if (sm_veiculo.getVeiculo().getCorStatusSinal() != null) {
                        Icon_Colorido(sm_veiculo.getVeiculo().getCorStatusSinal());
                        if (sm_veiculo.getVeiculo().getCorStatusSinal().equals("cinza")) {
                            dataUltima_posicao.setText("Veículo sem espelhamento");
                        } else {
                            dataUltima_posicao.setText("Ultima posição do veículo " +
                                    sm_veiculo.getVeiculo().getDataUltimaPosicao());
                            info_posicao.setText(sm_veiculo.getVeiculo().getLocalizacao());
                        }
                    } else {
                        dataUltima_posicao.setText("Entre em contato com o desenvolvedor do sistema");
                    }

                    //Inserindo no Result os dados que precisam
                    final Result r = BrasilRisk.GetResult(Activity_veiculo.this);
                    r.setPlacaCarreta(sm_veiculo.getPlacaCarreta());
                    r.setPlacaCarreta2(sm_veiculo.getPlacaCarreta2());
                    r.setPlaca(sm_veiculo.getVeiculo().getPlaca());
                    r.setCodVeiculo(String.valueOf(sm_veiculo.getVeiculo().getVeiculoId()));
                    r.setVeiculoApto(sm_veiculo.getVeiculo().isVeiculoApto());
                    BrasilRisk.SetResult(Activity_veiculo.this, r);
                    EnableContinuar();

                } else {

                    EnableBuscar();
                }
            } else {
                EnableBuscar();
            }
        } else {

            cavalo.addTextChangedListener(Mask.insert("###-####", cavalo));
            carreta1.addTextChangedListener(Mask.insert("###-####", carreta1));
            carreta2.addTextChangedListener(Mask.insert("###-####", carreta2));

            EnableBuscar();
        }
    }

    //------------------------------------------------------------------------------------------------------

    public void Inicializar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Validação do veículo");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        scrollView = (ScrollView) findViewById(R.id.scrollView2);
        cavalo = (EditText) findViewById(R.id.txt_cavalo);
        carreta1 = (EditText) findViewById(R.id.txt_carreta1);
        carreta2 = (EditText) findViewById(R.id.txt_carreta2);
        rastreamento = (TextView) findViewById(R.id.txtget_tecnologiarastreamento);
        terminal = (TextView) findViewById(R.id.txtget_terminal);
        btn_ = (Button) findViewById(R.id.btn_);
        txtrast = (TextView) findViewById(R.id.txt_rastreamento);
        txtterm = (TextView) findViewById(R.id.txt_terminal);
        spinner_carretas = (Spinner) findViewById(R.id.sp_carreta);
        spinner_carretas.setVisibility(View.INVISIBLE);
        txtget_carreta = (TextView) findViewById(R.id.txtget_carreta);
        txtget_carreta.setVisibility(View.INVISIBLE);
        dataUltima_posicao = (TextView) findViewById(R.id.txt_informacao_posicao);
        info_posicao = (TextView) findViewById(R.id.txt_posicao);
        icon_wifi = (ImageView) findViewById(R.id.icon_wifi);
        Apto_Inapto = (TextView) findViewById(R.id.Apto_Inapto);
        txtrast.setVisibility(View.INVISIBLE);
        txtterm.setVisibility(View.INVISIBLE);
        dataUltima_posicao.setVisibility(View.INVISIBLE);
        info_posicao.setVisibility(View.INVISIBLE);
        placaUF=(TextView)findViewById(R.id.PlacaUF);
        carretaUF=(TextView)findViewById(R.id.CarretaUF);
        placaUF.setVisibility(View.INVISIBLE);
        carretaUF.setVisibility(View.INVISIBLE);
        Placa_DataCheckList=(TextView)findViewById(R.id.PLaca_DataCheckList);
        Carreta_DataCheckList=(TextView)findViewById(R.id.Carreta_DataCheckList);
        TituloUltimoCheckList=(TextView)findViewById(R.id.ultimochecklist) ;
        Placa_DataCheckList.setVisibility(View.INVISIBLE);
        Carreta_DataCheckList.setVisibility(View.INVISIBLE);
        TituloUltimoCheckList.setVisibility(View.INVISIBLE);
    }

    private void EnableBuscar() {

        btn_.setText("BUSCAR");
        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfor = cn.getActiveNetworkInfo();
                if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                    if (carreta1.getText().toString().equals("")) {
                        DialogShow_Carreta("O campo carreta 1 item obrigatório .");
                    } else {
                        BuscarVeiculo();
                    }
                } else {
                    DialogShow_net();
                }


            }
        });
    }

    private void EnableContinuar() {
        btn_.setText("CONTINUAR");
        btn_.setOnClickListener(this);
    }

    private void Continuar() {
        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        SharedPreferences.Editor editor = preferences.edit();
        Result r = BrasilRisk.GetResult(Activity_veiculo.this);

        if (ListarCarretas) {
            BrasilRisk.SetResult(Activity_veiculo.this, r);
            final Get_TipoCarreta item = (Get_TipoCarreta) spinner_carretas.getSelectedItem();

            if (item != null) {
                r.setCodVeiculoTipo(String.valueOf(item.getCodVeiculoTipo()));
                editor.putInt(Shered_Cache.COD_TIPO_CARRETA, item.getCodVeiculoTipo());
                BrasilRisk.SetResult(Activity_veiculo.this, r);
                it = new Intent(Activity_veiculo.this, Activity_transportadora.class);
                startActivity(it);
            } else {
                r.setCodVeiculoTipo(null);
                BrasilRisk.SetResult(Activity_veiculo.this, r);
                it = new Intent(Activity_veiculo.this, Activity_transportadora.class);
                startActivity(it);
            }

        } else {

            r.setCodVeiculoTipo(null);
            BrasilRisk.SetResult(Activity_veiculo.this, r);
            it = new Intent(Activity_veiculo.this, Activity_transportadora.class);
            startActivity(it);
        }


    }

    private void BuscarVeiculo() {

        if (cavalo.getText().toString().equals("")) {
            DialogShow_Carreta("Verificar todos os campos obrigatórios!");

        } else {
            ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfor = cn.getActiveNetworkInfo();
            if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
                SharedPreferences.Editor editor = preferences.edit();
                int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
                String codE = String.valueOf(cod);
                final boolean ExibirApto = preferences.getBoolean(Shered_Cache.EXIBIR_INFORMACAOVEICULO_AptoInapto, false);
                final GetRequestAsyncTask send = new GetRequestAsyncTask(Activity_veiculo.this, "validar/VeiculoCarretaCavalo", null);
                send.EnableProgressdialog("Carregando");
                send.AddFirstParam("placaVeiculo", cavalo.getText().toString());
                send.AddParam(Shered_Cache.COD_EMPRESA, codE);
                send.AddParam("placaCarreta",carreta1.getText().toString());


                send.setOnPostExecute(new OnPostExecute() {
                    @Override
                    public void OnPostExecute() {
                        Type type2 = new TypeToken<Get_Veiculo>() {
                        }.getType();

                        try {
                            Get_Veiculo get_veiculo = (Get_Veiculo) new GsonBuilder().create().fromJson(send.getResponse(), type2);
                            if (get_veiculo.getStatusCode() == 200) {

                                txtrast.setVisibility(View.VISIBLE);
                                txtterm.setVisibility(View.VISIBLE);
                                rastreamento.setText(get_veiculo.getTecnologia());
                                terminal.setText(get_veiculo.getNumeroRastreador());
                                dataUltima_posicao.setVisibility(View.VISIBLE);
                                info_posicao.setVisibility(View.VISIBLE);
                                placaUF.setVisibility(View.VISIBLE);
                                carretaUF.setVisibility(View.VISIBLE);
                                placaUF.setText("Placa: "+Verificacao(get_veiculo.getPlaca()));
                                carretaUF.setText("Carreta: "+Verificacao(get_veiculo.getCarretaPlaca()));
                                TituloUltimoCheckList.setVisibility(View.VISIBLE);

                                CheckList_Colorido(get_veiculo.getCorStatusUltimoChecklist());
                                Placa_DataCheckList.setVisibility(View.VISIBLE);
                                Placa_DataCheckList.setText("Placa: "+get_veiculo.getDataUltimoChecklist());

                                Carreta_DataCheckList.setVisibility(View.VISIBLE);
                                Carreta_DataCheckList.setText("Carreta: "+get_veiculo.getDataUltimoChecklistCarreta());
                                CheckList_CorCarreta(get_veiculo.getCorStatusUltimoChecklistCarreta());

                                if (ExibirApto)
                                    Apto_Inapto.setText(get_veiculo.getSituacaoVeiculo());

                                if (get_veiculo.getCorStatusSinal() != null) {
                                    Icon_Colorido(get_veiculo.getCorStatusSinal());
                                    if (get_veiculo.getCorStatusSinal().equals("cinza")) {
                                        dataUltima_posicao.setText("Veículo sem espelhamento");
                                    } else {
                                        dataUltima_posicao.setText("Ultima posição do veículo " + get_veiculo.getDataUltimaPosicao());
                                        info_posicao.setText(get_veiculo.getLocalizacao());
                                    }
                                } else {
                                    dataUltima_posicao.setText("Entre em contato com o desenvolvedor do sistema");
                                }

                                final Result r = BrasilRisk.GetResult(Activity_veiculo.this);
                                r.setPlacaCarreta(carreta1.getText().toString());
                                r.setPlacaCarreta2(carreta2.getText().toString());
                                r.setPlaca(get_veiculo.getPlaca());
                                r.setCodVeiculo(String.valueOf(get_veiculo.getVeiculoId()));
                                r.setVeiculoApto(get_veiculo.isVeiculoApto());

                                BrasilRisk.SetResult(Activity_veiculo.this, r);
                                SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
                                SharedPreferences.Editor editor = preferences.edit();
                                String Trans = preferences.getString(Shered_Cache.NOME_TRANSPORTADORA, null);
                                if (Trans != null) {
                                    editor.putString(Shered_Cache.NOME_TRANSPORTADORA, Trans);
                                    editor.commit();
                                } else {
                                    editor.putString(Shered_Cache.NOME_TRANSPORTADORA, null);
                                    editor.commit();
                                }
                                EnableContinuar();
                            } else {
                                rastreamento.setText("");
                                terminal.setText("");
                                DialogShow_Carreta(get_veiculo.getMensagem());
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
    }

    @Override
    public void onClick(View v) {
        Continuar();
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    private void DialogShow_Carreta(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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

    public void ListarCarreta() {

        final GetRequestAsyncTask send = new GetRequestAsyncTask(this, "listar/listartipocarreta", null);
        send.EnableProgressdialog("Carregando");

        try {
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
                            arrayAdapter = new ArrayAdapter<Object>(Activity_veiculo.this, R.layout.item_spinner, temp);
                            arrayAdapter.setDropDownViewResource(R.layout.dropd_item);
                            spinner_carretas.setAdapter(arrayAdapter);
                        } else {
                            DialogShow_Empty(Carretas.get(0).getMensagem());
                        }
                    } catch (Exception ex) {
                        DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                    }

                }
            });
        } catch (Exception ex) {
            DialogShow_Empty("Problemas de instabilidade do sistema, entre em contato com o suporte.");
        }
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

    public void Icon_Colorido(String cor) {

        if (cor.equals("verde")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon_wifi.setImageDrawable(getDrawable(R.drawable.ic_action_wifi_green));
            }
        }
        if (cor.equals("amarelo")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon_wifi.setImageDrawable(getDrawable(R.drawable.ic_action_wifi_yellow));
            }
        }
        if (cor.equals("vermelho")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon_wifi.setImageDrawable(getDrawable(R.drawable.ic_action_wifi_red));
            }
        }
        if (cor.equals("cinza")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon_wifi.setImageDrawable(getDrawable(R.drawable.ic_action_cinza));
            }
        }
    }

    public void Spinner_TipoVeiculo(boolean PermissaoListar, int CodEmpresa, int TipoChecklist) {

        if (PermissaoListar) {
            if (CodEmpresa != Avon) {
                ListarCarreta();
                spinner_carretas.setVisibility(View.VISIBLE);
                txtget_carreta.setVisibility(View.VISIBLE);
            } else {
                if (TipoChecklist == 4) {
                    ListarCarreta();
                    spinner_carretas.setVisibility(View.VISIBLE);
                    txtget_carreta.setVisibility(View.VISIBLE);
                } else {
                    spinner_carretas.setVisibility(View.INVISIBLE);
                    txtget_carreta.setVisibility(View.INVISIBLE);
                }
            }
        } else {

            spinner_carretas.setVisibility(View.INVISIBLE);
            txtget_carreta.setVisibility(View.INVISIBLE);
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

    public String Verificacao(String Placa){
        if(Placa==null)
            Placa="";

        return Placa;
    }

    public void CheckList_Colorido(String cor) {

        if (cor.equals("verde")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               Placa_DataCheckList.setTextColor(Color.parseColor("#53A475"));
              // Carreta_DataCheckList.setTextColor(Color.parseColor("#53A475"));
            }
        }

        if (cor.equals("vermelho")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               Placa_DataCheckList.setTextColor(Color.parseColor("#E55055"));
              // Carreta_DataCheckList.setTextColor(Color.parseColor("#E55055"));
            }
        }
    }

    public void CheckList_CorCarreta(String cor){

        if (cor.equals("verde")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //Placa_DataCheckList.setTextColor(Color.parseColor("#53A475"));
                Carreta_DataCheckList.setTextColor(Color.parseColor("#53A475"));
            }
        }

        if (cor.equals("vermelho")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //Placa_DataCheckList.setTextColor(Color.parseColor("#E55055"));
                Carreta_DataCheckList.setTextColor(Color.parseColor("#E55055"));
            }
        }
    }
}