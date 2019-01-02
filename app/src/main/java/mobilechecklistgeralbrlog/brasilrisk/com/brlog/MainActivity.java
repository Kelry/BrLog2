package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.PostRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Get_Login;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    Button login;
    private AppCompatEditText senha;
    private AppCompatEditText usuario;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    Result result;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.btn_continuar);
        senha = (AppCompatEditText) findViewById(R.id.txt_senha);
        usuario = (AppCompatEditText) findViewById(R.id.txt_usuario);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.txtlayout_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.txtlayout_password);

        login.setOnClickListener(new View.OnClickListener() {
            Intent it = null;

            @Override
            public void onClick(View view) {

                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo netInfor = cn.getActiveNetworkInfo();

                if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                    if (usuario.getText().toString().equals("") && (senha.getText().toString().equals(""))) {
                        Toast.makeText(MainActivity.this, "Verificar os Campos", Toast.LENGTH_SHORT).show();
                    } else {
                        if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                            try {
                                final PostRequestAsyncTask send = new PostRequestAsyncTask(MainActivity.this, "validar/LoginAcessoMobile");
                                send.AddParam("login", usuario.getText().toString());
                                send.AddParam("senha", senha.getText().toString());
                                send.EnableProgressdialog();

                                try {
                                    send.setOnPostExecute(new OnPostExecute() {
                                        @Override
                                        public void OnPostExecute() {

                                            if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                                                Type type2 = new TypeToken<Get_Login>() {
                                                }.getType();


                                                try {
                                                    Get_Login get_login = (Get_Login) new GsonBuilder().create().fromJson(send.getResponse(), type2);
                                                    if (get_login.getStatusCode() == 200) {

                                                        SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putInt(Shered_Cache.COD_EMPRESA, get_login.getCodEmpresa());
                                                        editor.putInt(Shered_Cache.COD_EMPRESA_USUARIO, get_login.getCodEmpresaUsuario());
                                                        editor.putBoolean(Shered_Cache.SELECIONAR_TIPO_OPERACAO, get_login.isSelecionarTipoOperacao());
                                                        editor.putBoolean(Shered_Cache.PROSSEGUIR_SEM_DOCUMENTO, get_login.isProsseguirSemNumeroDeSm());
                                                        editor.putString(Shered_Cache.NOME_TRANSPORTADORA, null);
                                                        editor.putString(Shered_Cache.NUMERO_MINUTA, null);
                                                        editor.putBoolean(Shered_Cache.SELECIONAR_TIPO_CHECKLIST, get_login.getEscolherTipoDoChecklist());
                                                        editor.putBoolean(Shered_Cache.SELECIONAR_TIPO_CARRETA, get_login.isSelecionarTipoCarreta());
                                                        editor.putBoolean(Shered_Cache.PERMITIDO_SEGUIRSEM_MOTORISTA, get_login.isPermitidoRealizarChecklistSemMotorista());
                                                        editor.putBoolean(Shered_Cache.PERMITIDO_SEGUIRSEM_VEICULO, get_login.isPermitidoRealizarChecklistSemVeiculo());
                                                        editor.putBoolean(Shered_Cache.EXIBIR_INFORMACAOMOTORISTA_AptoInapto,get_login.isExibirInformacaoMotoristaAptoInapto());
                                                        editor.putBoolean(Shered_Cache.EXIBIR_INFORMACAOVEICULO_AptoInapto,get_login.isExibirInformacaoVeiculoAptoInapto());
                                                        editor.commit();

                                                        result = BrasilRisk.GetResult(MainActivity.this);
                                                        result.setCodEmpresaUsuario(get_login.getCodEmpresaUsuario());
                                                        BrasilRisk.SetResult(MainActivity.this, result);

                                                        if (get_login.getEscolherTipoDoChecklist()) {
                                                            editor.putBoolean(Shered_Cache.SELECIONAR_TIPO_CHECKLIST, get_login.getEscolherTipoDoChecklist());
                                                            editor.commit();
                                                            BrasilRisk.SetResult(MainActivity.this, result);
                                                            //se vem true ele precisa escolher o tipo
                                                            Intent it = new Intent(MainActivity.this, TipoChecklist.class);
                                                            startActivity(it);
                                                        } else {
                                                            //se vem false ele nao precisa escolher
                                                            get_login.setEscolherTipoDoChecklist(null);
                                                            BrasilRisk.SetResult(MainActivity.this, result);
                                                            Intent it = new Intent(MainActivity.this, Activity_sm.class);
                                                            startActivity(it);
                                                        }
                                                    } else {

                                                        DialogShow_Cadastro(get_login.getMensagem());
                                                    }

                                                } catch (Exception ex) {
                                                    DialogShow_Cadastro("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                                                }

                                            } else {
                                                DialogShow();
                                            }
                                        }
                                    });

                                    try {
                                        send.execute();
                                    } catch (Exception ex) {
                                        Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                //DialogShow_Cadastro(e.toString());
                            }

                        }
                    }
                } else {
                    DialogShow();
                }
            }
        });
    }

    private void DialogShow() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conexão");
        builder.setMessage("Dispositivo sem acesso a internet ");
        builder.setPositiveButton("Reconectar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                Intent it = new Intent(MainActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        alert = builder.create();
        alert.show();
    }

    private void DialogShow_Cadastro(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                usuario.setText("");
                senha.setText("");
            }
        });
        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        alert = builder.create();
        alert.show();
    }
}
