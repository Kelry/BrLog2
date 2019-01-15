package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.GetRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_Motorista;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_sm;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Mask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;


public class Activity_motorista extends AppCompatActivity implements View.OnClickListener {

    Button btn_;
    TextInputLayout layout;
    AppCompatEditText cpf;
    TextView nome, Apto_Inapto_Motorista;
    TextView cnh;
    TextView date;
    private Toolbar mToolbar;
    TextView title;
    TextView txtnome, txtdate, txtcnh;
    AlertDialog alert;
    Get_sm objeto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista);
        Inicializar();

        EnableBuscar();
        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        boolean SituacaoMotorista = preferences.getBoolean(Shered_Cache.EXIBIR_INFORMACAOMOTORISTA_AptoInapto, false);

        Bundle ObjetoRecebido = getIntent().getExtras();

        if (ObjetoRecebido != null) {

            objeto = (Get_sm) ObjetoRecebido.getSerializable(Shered_Cache.OBJETO_MOTORISTA_SM);

            if (objeto.getMotorista() != null) {

                cpf.setText(objeto.getMotorista().getCPF());
                txtcnh.setVisibility(View.VISIBLE);
                txtdate.setVisibility(View.VISIBLE);
                txtnome.setVisibility(View.VISIBLE);
                String Date = objeto.getMotorista().getDataNascimento();
                String cnh_ = objeto.getMotorista().getNumeroCNH();
                cpf.setVisibility(View.VISIBLE);
                cnh.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                date.setText(Date);
                nome.setText(objeto.getMotorista().getNomeMotorista());
                cnh.setText(cnh_);
                if (SituacaoMotorista)
                    Apto_Inapto_Motorista.setText(objeto.getMotorista().getSituacaoMotorista());
                else
                    Apto_Inapto_Motorista.setText("");
                Result r = BrasilRisk.GetResult(Activity_motorista.this);
                r.setCodMotorista(String.valueOf(objeto.getCodMotorista()));
                r.setCpfMotorista(objeto.getMotorista().getCPF());
                r.setMotoristaApto(objeto.getMotorista().isMotoristaApto());
                BrasilRisk.SetResult(Activity_motorista.this, r);
                EnableContinuar();

            } else {

                EnableBuscar();
            }
        } else {
            EnableBuscar();
        }
    }

    //------------------------------------------------------------------------------------------------------------

    public void Inicializar ()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Validação do motorista");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btn_ = (Button) findViewById(R.id.btn_);
        cpf = (AppCompatEditText) findViewById(R.id.txt_cpf);
        cnh = (TextView) findViewById(R.id.gettext_cnh);
        date = (TextView) findViewById(R.id.gettext_date);
        nome = (TextView) findViewById(R.id.gettext_name);
        txtcnh = (TextView) findViewById(R.id.text_cnh);
        txtdate = (TextView) findViewById(R.id.text_date);
        txtnome = (TextView) findViewById(R.id.text_name);
        Apto_Inapto_Motorista = (TextView) findViewById(R.id.Apto_Inapto_Motorista);
        layout = (TextInputLayout) findViewById(R.id.txtlayout_cpf);
        cpf.addTextChangedListener(Mask.insert("###.###.###-##", cpf));
        txtnome.setVisibility(View.INVISIBLE);
        txtdate.setVisibility(View.INVISIBLE);
        txtcnh.setVisibility(View.INVISIBLE);
        cnh.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);

    }


    private void EnableBuscar() {

        btn_.setText("BUSCAR");
        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarMotorista();
            }
        });
    }

    private void EnableContinuar() {
        btn_.setText("CONTINUAR");
        btn_.setOnClickListener(this);
    }

    private void BuscarMotorista() {

        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        SharedPreferences.Editor editor = preferences.edit();
        int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
        String codE = String.valueOf(cod);
        final boolean SituacaoMotoristaB = preferences.getBoolean(Shered_Cache.EXIBIR_INFORMACAOMOTORISTA_AptoInapto, false);
        final GetRequestAsyncTask send = new GetRequestAsyncTask(this, "validar/motorista", null);
        send.AddFirstParam("cpfMotorista", cpf.getText().toString());
        send.AddParam(Shered_Cache.COD_EMPRESA, codE);
        send.EnableProgressdialog("Carregando");

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            send.setOnPostExecute(new OnPostExecute() {
                @Override
                public void OnPostExecute() {

                    if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                        Type type2 = new TypeToken<Get_Motorista>() {
                        }.getType();

                        try {
                            Get_Motorista get_motorista = (Get_Motorista) new GsonBuilder().create().fromJson(send.getResponse(), Get_Motorista.class);
                            if (get_motorista.getStatusCode() == 200) {

                                cnh.setText(get_motorista.getNumeroCNH());
                                date.setText(get_motorista.getDataNascimento());
                                Result r = BrasilRisk.GetResult(Activity_motorista.this);
                                r.setCodMotorista(String.valueOf(get_motorista.getCodMotorista()));
                                r.setCpfMotorista(get_motorista.getCPF());
                                r.setMotoristaApto(get_motorista.isMotoristaApto());
                                BrasilRisk.SetResult(Activity_motorista.this, r);

                                txtcnh.setVisibility(View.VISIBLE);
                                txtdate.setVisibility(View.VISIBLE);
                                txtnome.setVisibility(View.VISIBLE);
                                cnh.setText(get_motorista.getNumeroCNH());
                                date.setText(get_motorista.getDataNascimento());
                                nome.setText(get_motorista.getNomeMotorista());
                                if (SituacaoMotoristaB)
                                    Apto_Inapto_Motorista.setText(get_motorista.getSituacaoMotorista());
                                else
                                    Apto_Inapto_Motorista.setText("");

                                cpf.setVisibility(View.VISIBLE);
                                cnh.setVisibility(View.VISIBLE);
                                date.setVisibility(View.VISIBLE);
                                EnableContinuar();

                            } else {
                                cnh.setText("");
                                date.setText("");
                                cpf.setText("");
                                DialogShow_Motorista(get_motorista.getMensagem());
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
    }

    @Override
    public void onClick(View v) {

        if (objeto != null) {
            if (objeto.getVeiculo() == null && objeto.getCodEmpresa() == 6740) {
                Intent it = new Intent(Activity_motorista.this, ValidarTipoVeiculo.class);
                startActivity(it);
            } else {
                Intent it = new Intent(Activity_motorista.this, Activity_veiculo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Shered_Cache.OBJETO_VEICULO_SM, objeto);
                it.putExtras(bundle);
                startActivity(it);
            }
        } else {
            Intent it = new Intent(Activity_motorista.this, Activity_veiculo.class);
            startActivity(it);

        }
    }

    private void DialogShow_Motorista(String msg) {
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