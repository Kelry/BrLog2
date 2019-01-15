package mobilechecklistgeralbrlog.brasilrisk.com.brlog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brasilrisk.mobilechecklistgeralbrlog.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.BrasilRisk;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Imagem;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Result;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Retorno;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.RetornoDec;
import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.UploadService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_fotos extends AppCompatActivity {

    ImageView img, img3, img2, img4, img5;
    private AlertDialog alert;
    Button btn_fim;
    String encodedImage;
    Imagem imagem;
    Result result = null;
    private Toolbar mToolbar;
    BrasilRisk r;
    TextView title;
    ProgressDialog dialog;
    TextView txt_status;
    String filename;
    Bitmap bitmap;
    static final int REQUEST_CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titulo_toolbar);
        title.setText("Fotos");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txt_status = (TextView) findViewById(R.id.txt_status);
        img = (ImageView) findViewById(R.id.img_v);
        img2 = (ImageView) findViewById(R.id.img_v2);
        img3 = (ImageView) findViewById(R.id.img_v3);
        img4 = (ImageView) findViewById(R.id.img_v4);
        img5 = (ImageView) findViewById(R.id.img_v5);
        final SharedPreferences preferences = getSharedPreferences("BrasilRisk_2018", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        final Button btn_fim = (Button) findViewById(R.id.btn_fim);
        imagem = new Imagem();

        btn_fim.setEnabled(true);
        img.setEnabled(true);
        img2.setEnabled(true);
        img3.setEnabled(true);
        img4.setEnabled(true);
        img5.setEnabled(true);

        Bundle ObjetoRecebido = getIntent().getExtras();
        if (ObjetoRecebido != null) {
            result = (Result) ObjetoRecebido.getSerializable("checklist_result");
            if (result.isAprovado()) {
                txt_status.setTextColor(Color.rgb(83, 164, 117));
                txt_status.setText("Checklist aprovado!");
            } else if (!result.isAprovado()) {
                txt_status.setTextColor(Color.RED);
                txt_status.setText("Checklist reprovado!");
            }
        }


        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfor = cn.getActiveNetworkInfo();

        btn_fim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                    if (imagem.getPrimeiraFoto() != null || imagem.getSegundaFoto() != null
                            || imagem.getTerceiraFoto() != null || imagem.getQuartaFoto() != null  || imagem.getQuintaFoto() != null) {

                        btn_fim.setEnabled(false);
                        img.setEnabled(false);
                        img2.setEnabled(false);
                        img3.setEnabled(false);
                        img4.setEnabled(false);
                        img5.setEnabled(false);

                        dialog = new ProgressDialog(Activity_fotos.this);
                        dialog.setMessage("Enviando...");
                        dialog.setCancelable(false);
                        dialog.show();
                        result.setImagem(imagem);

                        try {

                            Gson g = new GsonBuilder().registerTypeAdapter(Retorno.class, new RetornoDec()).create();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BrasilRisk.URL_Conetcion)
                                    .addConverterFactory(GsonConverterFactory.create(g))
                                    .build();
                            final UploadService uploadService = retrofit.create(UploadService.class);
                            Call<Retorno> dados = uploadService.InserirDados(result);

                            dados.enqueue(new Callback<Retorno>() {
                                @Override
                                public void onResponse(Call<Retorno> call, Response<Retorno> response) {
                                    if (response.isSuccessful()) {
                                        dialog.dismiss();
                                        if (response.body().getStatusCode() == 200) {

                                            Intent it = new Intent(Activity_fotos.this, Activity_Protocol.class);
                                            Bundle params = new Bundle();
                                            params.putString("protocolo", response.body().getMensagemProtocolo());
                                            editor.putString("protocolo_fotos", response.body().getMensagemProtocolo());
                                            it.putExtras(params);
                                            startActivity(it);
                                        } else {
                                            DialogShow_Fotos(response.body().getMensagem());
                                        }
                                    } else {
                                        dialog.dismiss();
                                        DialogShow_Fotos(response.body().getMensagem());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Retorno> call, Throwable t) {
                                    dialog.dismiss();
                                    DialogShow_ERRO("Ocorreu um erro de instabilidade no sistema, verifique sua conexão com a internet.");
                                }
                            });

                        } catch (Exception ex) {
                            dialog.dismiss();
                            DialogShow_Fotos("Ocorreu uma instabilidade no sistema, tente novamente.");
                        }

                    } else {
                        DialogShow_Fotos("Para dar continuidade, é necessário que sejam fotografadas pelo menos 1 imagens !");
                    }
            }

        });


        if (netInfor != null && netInfor.isConnectedOrConnecting()) {
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenCamera(1);
                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenCamera(2);
                }
            });
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenCamera(3);
                }
            });
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenCamera(4);
                }
            });
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenCamera(5);
                }
            });

        } else {
            DialogShow_net();
        }
    }

    //---------------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCod, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {

            if (resultCode == Activity.RESULT_OK) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals(filename)) {
                        f = temp;
                        break;
                    }
                }

                try {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap resize = getScaledDownBitmap(bitmap, 550, true);
                    resize.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    f.delete();
                    encodedImage = encodeImage(resize);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            switch (requestCod) {

                case 1:
                    img.setImageBitmap(DecodificarImagem(encodedImage));
                    imagem.setPrimeiraFoto(encodedImage);
                    break;

                case 2:
                    img2.setImageBitmap(DecodificarImagem(encodedImage));
                    imagem.setSegundaFoto(encodedImage);
                    break;

                case 3:
                    img3.setImageBitmap(DecodificarImagem(encodedImage));
                    imagem.setTerceiraFoto(encodedImage);
                    break;

                case 4:
                    img4.setImageBitmap(DecodificarImagem(encodedImage));
                    imagem.setQuartaFoto(encodedImage);
                    break;

                case 5:
                    img5.setImageBitmap(DecodificarImagem(encodedImage));
                    imagem.setQuintaFoto(encodedImage);
                    break;
            }
        } else {
            DialogShow_Fotos("Imagem cancelada.");
        }


    }

    public static Bitmap getScaledDownBitmap(Bitmap bitmap, int threshold,
                                             boolean isNecessaryToKeepOrig) {


        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = width;
        int newHeight = height;

        if (width > height && width > threshold) {
            newWidth = threshold;
            newHeight = (int) (height * (float) newWidth / width);
        }

        if (width > height && width <= threshold) {
            return bitmap;
        }

        if (width < height && height > threshold) {
            newHeight = threshold;
            newWidth = (int) (width * (float) newHeight / height);
        }

        if (width < height && height <= threshold) {
            return bitmap;
        }

        if (width == height && width > threshold) {
            newWidth = threshold;
            newHeight = newWidth;
        }

        if (width == height && width <= threshold) {
            return bitmap;
        }

        return getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig);
    }

    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight,
                                           boolean isNecessaryToKeepOrig) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        if (!isNecessaryToKeepOrig) {
            bm.recycle();
        }
        return resizedBitmap;
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bm.getByteCount());
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = new String(Base64.encodeToString(b, Base64.DEFAULT));
        return encImage;

    }

    private void OpenCamera(int code) {

        if ((ContextCompat.checkSelfPermission
                (Activity_fotos.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(Activity_fotos.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(Activity_fotos.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                ) {

            filename = getTimeStamp() + ".jpg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), filename);
            Uri fileUri = FileProvider.getUriForFile(getApplicationContext(), "com.brasilrisk.mobilechecklistgeralbrlog", f);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, code);

        } else {

            if (!(ContextCompat.checkSelfPermission(Activity_fotos.this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(Activity_fotos.this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

            if (!(ContextCompat.checkSelfPermission(Activity_fotos.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(Activity_fotos.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CAMERA);

            if (!(ContextCompat.checkSelfPermission(Activity_fotos.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(Activity_fotos.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);

        }
    }

    public Bitmap DecodificarImagem(String imagem) {
        byte[] decodedString = Base64.decode(imagem, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    private void DialogShow_Fotos(String msg) {
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

    public static String getTimeStamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();
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