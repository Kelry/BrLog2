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
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brasilrisk.mobilechecklistgeralbrlog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Mask;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Utils.Shered_Cache;

public class Activity_adicionais extends AppCompatActivity {

    Button iniciar;
    EditText pedagio, palete, alturabau, qtd_lacres, qtd_eixos, minuta, moop, validade_moop, n_rampa, n_doca, Altura_veiculo, Larg_veiculo, Comp_veiculo;
    ScrollView scrollView;
    private Toolbar mToolbar;
    TextView title;
    Result result;
    Boolean date_validacao;

    static final int REQUEST_LOCATIION = 1;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionais);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            checkGps();
        } catch (Exception e) {
            createNoGpsDialog();
        }



        result = BrasilRisk.GetResult(Activity_adicionais.this);
        getLocation();

        iniciar = (Button) findViewById(R.id.btn_continuar_adicionais);
        pedagio = (EditText) findViewById(R.id.txt_pedagio);
        palete = (EditText) findViewById(R.id.txt_palete);
        alturabau = (EditText) findViewById(R.id.txt_alturabau);
        qtd_lacres = (EditText) findViewById(R.id.txt_qtd_lacres);
        qtd_eixos = (EditText) findViewById(R.id.txt_qtd_eixos);
        minuta = (EditText) findViewById(R.id.txt_minuta);
        moop = (EditText) findViewById(R.id.txt_moop);
        validade_moop = (EditText) findViewById(R.id.txt_validade_moop);
        n_rampa = (EditText) findViewById(R.id.txt_Numerorampa);
        n_doca = (EditText) findViewById(R.id.txt_NumeroDoca);
        Larg_veiculo = (EditText) findViewById(R.id.txt_largura_veiculo);
        Altura_veiculo = (EditText) findViewById(R.id.txt_altura_veiculo);
        Comp_veiculo = (EditText) findViewById(R.id.txt_comprimento_veiculo);
        validade_moop.addTextChangedListener(Mask.insert("##/##/####", validade_moop));


        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Dados adicionais");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Shered_Cache.NOME_TRANSPORTADORA, null);
        String minuta_sm = preferences.getString(Shered_Cache.NUMERO_MINUTA, null);
        final int CodEmpresa = preferences.getInt(Shered_Cache.COD_EMPRESA, -1);


        if (minuta_sm == null) {
            minuta.setText("");
        } else {
            minuta.setText(minuta_sm.toString());
        }

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfor = cn.getActiveNetworkInfo();

                if (netInfor != null && netInfor.isConnectedOrConnecting()) {

                    if (alturabau.getText().toString().isEmpty()) {
                        result.setAlturaBauSider(0);
                    } else {
                        float altura = Float.parseFloat(alturabau.getText().toString());
                        result.setAlturaBauSider(altura);
                    }

                    if (pedagio.getText().toString().length() == 0) {
                        result.setCartaoPedagio(null);

                    } else {
                        result.setCartaoPedagio(pedagio.getText().toString());
                    }

                    if (palete.getText().toString().length() == 0) {
                        result.setPosicaoPaletesNaCarreta(null);

                    } else {
                        result.setPosicaoPaletesNaCarreta(palete.getText().toString());
                    }


                    if (qtd_eixos.getText().toString().length() == 0) {
                        result.setQtdeEixos(null);
                    } else {

                        result.setQtdeEixos(qtd_eixos.getText().toString());
                    }

                    if (qtd_lacres.getText().toString().length() == 0) {
                        result.setQtdeLacres(null);
                    } else {

                        result.setQtdeLacres(qtd_lacres.getText().toString());
                    }

                    //

                    if (n_rampa.getText().length() != 0) {
                        result.setNumeroDaRampa(n_rampa.getText().toString());
                    } else
                        result.setNumeroDaRampa(null);

                    if (n_doca.getText().length() != 0) {
                        result.setNumeroDaDoca(n_doca.getText().toString());
                    } else
                        result.setNumeroDaDoca(null);

                    if (Altura_veiculo.getText().length() != 0) {
                        float altura_veiculo = Float.parseFloat(Altura_veiculo.getText().toString());
                        result.setAlturaVeiculo(altura_veiculo);
                    } else
                        result.setAlturaVeiculo(0);

                    if (Larg_veiculo.getText().length() != 0) {
                        float larg_veiculo = Float.parseFloat(Larg_veiculo.getText().toString());
                        result.setLarguraVeiculo(larg_veiculo);
                    } else
                        result.setLarguraVeiculo(0);

                    if (Comp_veiculo.getText().length() != 0) {
                        float comp_veiculo = Float.parseFloat(Comp_veiculo.getText().toString());
                        result.setComprimentoVeiculo(comp_veiculo);
                    } else
                        result.setComprimentoVeiculo(0);

                    if (moop.getText().toString().length() == 0) {
                        result.setNrMoop(null);
                    } else
                        result.setNrMoop(moop.getText().toString());


                    if (validade_moop.getText().toString().length() == 0) {
                        result.setValidadeMoop(null);
                        BrasilRisk.SetResult(Activity_adicionais.this, result);
                        Intent it = new Intent(Activity_adicionais.this, Activity_checklist.class);
                        startActivity(it);

                    } else {

                        try {

                            if (validade_moop.getText().length() != 0) {

                                if (validade_moop.getText().length() != 10) {
                                    DialogShow_msg("Seguir os padrões da Data **/**/**** ");
                                } else {
                                    result.setValidadeMoop(GettimeNow(validade_moop.getText().toString()));
                                    BrasilRisk.SetResult(Activity_adicionais.this, result);
                                    Intent it = new Intent(Activity_adicionais.this, Activity_checklist.class);
                                    startActivity(it);
                                }
                            }

                        } catch (ParseException e) {
                            if (date_validacao == false)
                                DialogShow_msg("Por favor verifique os campos referentes a data e hora.");
                            e.printStackTrace();
                        }
                    }

                } else {

                    DialogShow_net();
                }

            }
        });
    }

    //Conversor de data
    private String GettimeNow(String data_v) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        date_validacao = sdf.isLenient();
        Date dataEntrada = sdf.parse(data_v);
        String dataFormatada = new SimpleDateFormat("yyyy-MM-dd").format(dataEntrada);
        return dataFormatada;
    }

    private String GettimeNow_DateHora(String data_v) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setLenient(false);
        date_validacao = sdf.isLenient();
        Date dataEntrada = sdf.parse(data_v);
        String dataFormatada = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dataEntrada);
        return dataFormatada;
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


    private void createNoGpsDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog mNoGpsDialog = builder.setMessage("Por favor ative seu GPS para usar esse aplicativo.")
                .setTitle("GPS")
                .setPositiveButton("Ativar", dialogClickListener)
                .setNegativeButton("Prosseguir", dialogClickListener)
                .create();
        mNoGpsDialog.show();

    }
    private void getLocation() {

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(Activity_adicionais.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATIION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                result.setLatitude(latitude);
                result.setLongitude(longitude);
            }
        }
    }
    public void checkGps() throws Exception {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            throw new Exception("gps off");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATIION:
                getLocation();
                break;
        }
    }
}
