package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;

public class ItensAdicionaisSaida_LH extends AppCompatActivity {

    Toolbar mToolbar;
    TextView title;
    EditText Porta_lateral, Porta_traseira, Qt_notafiscal, Qt_devolucao, Responsavel;
    Button btn_continuar;
    Result result;
    boolean LineH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_adicionais_saida__lh);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Dados adicionais");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Inicialize();
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();

        Bundle ObjetoRecebido = getIntent().getExtras();
        if (ObjetoRecebido != null) {
            LineH = ObjetoRecebido.getBoolean("LineHal");
        }


        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                    if (!(Porta_traseira.getText().length() == 0 || Qt_devolucao.getText().length() == 0 || Qt_notafiscal.getText().length() == 0 || Responsavel.getText().length() == 0)) {
                        result.setNumeroLacrePortaLateral(Porta_lateral.getText().toString());
                        result.setNumeroLacrePortaTraseira(Porta_traseira.getText().toString());
                        result.setQtdeDevolucao(Integer.parseInt(Qt_devolucao.getText().toString()));
                        result.setQtdeNotasFiscais(Integer.parseInt(Qt_notafiscal.getText().toString()));
                        result.setResponsavelAmarracao(Responsavel.getText().toString());

                        BrasilRisk.SetResult(ItensAdicionaisSaida_LH.this, result);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("LineH", LineH);
                        Intent it = new Intent(ItensAdicionaisSaida_LH.this, FotosSaidaDoca.class);
                        it.putExtras(bundle);
                        startActivity(it);
                    } else
                        DialogShow_msg("Verifique os itens preenchidos, pois todos os itens são obrigatórios.");
                } else {
                    DialogShow_msg("Ocorreu um problema de conexão, tente novamente. ");
                }


            }
        });
    }

    void Inicialize() {
        result = BrasilRisk.GetResult(ItensAdicionaisSaida_LH.this);
        Porta_lateral = (EditText) findViewById(R.id.txt_NumeroLacrePortaLateral);
        Porta_traseira = (EditText) findViewById(R.id.txt_NumeroLacrePortaTraseira);
        Qt_notafiscal = (EditText) findViewById(R.id.txt_QtdeNotasFiscais);
        Qt_devolucao = (EditText) findViewById(R.id.txt_QtdeDevolucao);
        btn_continuar = (Button) findViewById(R.id.btn_lineH);
        Responsavel = (EditText) findViewById(R.id.txt_RespAmarracao);
    }

    private void DialogShow_msg(String msg) {
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


}
