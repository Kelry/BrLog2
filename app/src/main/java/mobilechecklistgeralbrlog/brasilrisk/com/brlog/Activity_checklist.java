package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Adapters.ChecklistAdapter;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Asyncs.GetRequestAsyncTask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.CheckListResultado;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Checklist;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnCheckChangeListener;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnPostExecute;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_checklist extends AppCompatActivity {
    RecyclerView mRecyclerView;
    Button btn_ck;
    ArrayList<Checklist> Checklists;
    ArrayList<Checklist> ListaFinal;
    ChecklistAdapter AdapterList;
    Result Checklist_Result;
    private Toolbar mToolbar;
    TextView title;
    boolean aprovado;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Itens do checklist");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Checklist_Result = BrasilRisk.GetResult(this);
        Checklist_Result.setDhInicioChecklist(GettimeNow());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        btn_ck = (Button) findViewById(R.id.btn_finalChecklist);
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfor = cn.getActiveNetworkInfo();
        btn_ck = (Button) findViewById(R.id.btn_finalChecklist);



        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            Checklist_Result.setImagem(null);
            btn_ck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (netInfor != null && netInfor.isConnectedOrConnecting()) {
                        if (SetChecklist_Result()) {
                            if (aprovado) {
                                Intent it = new Intent(Activity_checklist.this, Activity_fotos.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("checklist_result", Checklist_Result);
                                it.putExtras(bundle);
                                startActivity(it);
                            } else DialogShow_Reprovado();
                        } else {
                            DialogShow();
                        }
                    } else {
                        DialogShow_net();
                    }

                }
            });
            MakeRequet();
        } else {

            DialogShow_net();

        }
    }
    //--------------------------------------------------------------------------------------------------------------
    private boolean SetChecklist_Result() {
        aprovado = true;
        boolean permitido = false;
        ArrayList<CheckListResultado> checkListResultados = new ArrayList<CheckListResultado>();
        for (Checklist checklist : Checklists) {
            if (checklist.isItemDeReprova())
                if (!checklist.isChecked())
                    aprovado = false;
            CheckListResultado ck = new CheckListResultado();
            ck.setCodChecklistItem(checklist.getCodCheckListItem());
            ck.setValido(checklist.isChecked());
            checkListResultados.add(ck);
            if (checklist.isChecked())
                permitido = true;
        }
        if (Checklists.size() == 1)
            permitido = true;

        Checklist_Result.setCheckListResultado(checkListResultados);
        Checklist_Result.setAprovado(aprovado);
        Checklist_Result.setDhFimChecklist(GettimeNow());
        BrasilRisk.SetResult(this, Checklist_Result);
        return permitido;

    }

    private void CreateList() {
        ListaFinal = new ArrayList<Checklist>(); // Lista que sera enviada para preencher o recycleview
        int header = Checklists.get(0).getCodCheckListCategoria();
        if (Checklists.get(0).getNomeCategoria() != null) {
            //primeiro item para definir o primeiro cabeçalho
            ListaFinal.add(0, new Checklist(true, Checklists.get(0).getNomeCategoria()));//add primeiro cabeçalho
            for (Checklist checklist : Checklists) {
                if (checklist.getCodCheckListCategoria() != header) { //add novo cabeçalho se mudar a categoria
                    ListaFinal.add(new Checklist(true, checklist.getNomeCategoria()));//add novo cabeçalho
                    header = checklist.getCodCheckListCategoria();//mudando o cabeçalho para comparação
                }
                ListaFinal.add(checklist);
            }

            AdapterList = new ChecklistAdapter(ListaFinal, Activity_checklist.this);
            AdapterList.setOnCheckChangeListener(new OnCheckChangeListener() {
                @Override
                public void OnCheckChange(View view, boolean checked, int position) {
                    ListaFinal.get(position).setChecked(checked);
                    if (!checked) {
                        if (ListaFinal.get(position).isItemDeReprova())
                            ExibirAlerta(position);

                    }
                    AdapterList.notifyItemChanged(position);
                }
            });

            mRecyclerView.setAdapter(AdapterList);

        } else {
            DialogShow_Empty(Checklists.get(0).getMensagem());
            btn_ck.setEnabled(false);
        }
    }

    private void MakeRequet() {

        try {

            final GetRequestAsyncTask getcheck = new GetRequestAsyncTask(this, "listar/ListarItensCheckList", null);
            getcheck.EnableProgressdialog("Carregando...");
            btn_ck.setVisibility(View.VISIBLE);
            SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
            SharedPreferences.Editor editor = preferences.edit();
            int cod = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);
            String codEmpresa = String.valueOf(cod);
            int CodTipoTrajeto = preferences.getInt(Shered_Cache.COD_TIPO_TRAJETO, -1);
            String codTipoTrajeto = String.valueOf(CodTipoTrajeto);
            String CodOperacao = preferences.getString(Shered_Cache.COD_TIPO_OPERACAO, null);
            int codtipo = preferences.getInt(Shered_Cache.COD_TIPO_CHECKLIST, -1);
            String codTipochecklist = String.valueOf(codtipo);

            //Itens de parametro do Checklist
            getcheck.AddFirstParam(Shered_Cache.COD_EMPRESA, codEmpresa);
            getcheck.AddParam(Shered_Cache.COD_TIPO_CHECKLIST, codTipochecklist);
            getcheck.AddParam(Shered_Cache.COD_TIPO_TRAJETO, codTipoTrajeto);
            getcheck.AddParam(Shered_Cache.COD_TIPO_OPERACAO, CodOperacao);
            getcheck.AddParam(Shered_Cache.COD_TIPO_VEICULO, Checklist_Result.CodVeiculoTipo);


            getcheck.setOnPostExecute(new OnPostExecute() {
                @Override
                public void OnPostExecute() {
                    Type type2 = new TypeToken<ArrayList<Checklist>>() {
                    }.getType();

                    try {
                        Checklists = (ArrayList<Checklist>) new GsonBuilder().create().fromJson(getcheck.getResponse(), type2);
                        if (Checklists != null)
                            CreateList();
                        else
                            DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");

                    } catch (Exception ex) {
                        DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                    }

                }
            });

            getcheck.execute();

        } catch (Exception ex) {
            DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
        }
    }

    private String GettimeNow() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        return dateFormat.format(data_atual);

    }

    private void ExibirAlerta(final int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Esse item é de reprovação!");
        builder.setMessage("Deseja desmarcar mesmo assim?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                ListaFinal.get(pos).setChecked(false);
                AdapterList.notifyItemChanged(pos);
                if (SetChecklist_Result()) {
                    if (aprovado) {
                        Intent it = new Intent(Activity_checklist.this, Activity_fotos.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("checklist_result", Checklist_Result);
                        it.putExtras(bundle);
                        startActivity(it);
                    } else DialogShow_Reprovado();
                } else {
                    DialogShow();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ListaFinal.get(pos).setChecked(true);
                AdapterList.notifyItemChanged(pos);
            }
        });

        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void DialogShow() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage("O sistema não pode prosseguir sem que seja feito o checklist de forma correta");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void DialogShow_Reprovado() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage("O checklist será reprovado! Deseja continuar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                Intent it = new Intent(Activity_checklist.this, Activity_fotos.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("checklist_result", Checklist_Result);
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        android.app.AlertDialog alert = builder.create();
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

    private void DialogShow_Empty(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // finish();
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