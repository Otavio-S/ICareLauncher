package com.example.otavio.tcc.Telas;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Icone;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaIcones;
import com.example.otavio.tcc.Testes;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class HomeScreen extends Activity {

    private Activity activity = this;
    private ImageButton btnCamera;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private Intent intent1;
    private Intent intent2;
    private Intent intent3;
    private String Classe1;
    private String Classe2;
    private String Classe3;
    private TabelaIcones tabelaIcones = new TabelaIcones(this);

    private View.OnClickListener btnSOSOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentS = new Intent(HomeScreen.this, TelaSOS.class);
            getWindow().setEnterTransition(new Fade());
            startActivity(intentS, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    };

    private View.OnClickListener btnNotasOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentN = new Intent(HomeScreen.this, Testes.class);
            getWindow().setEnterTransition(new Fade());
            startActivity(intentN, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    };

    private View.OnClickListener btnAlarmeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentA = new Intent(HomeScreen.this, TelaAlarmes.class);
            getWindow().setEnterTransition(new Fade());
            startActivity(intentA, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    };

    private View.OnClickListener btnContatosOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent contactsIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("content://contacts/people"));
            getWindow().setEnterTransition(new Fade());
            startActivity(contactsIntent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    };

    private View.OnClickListener btnCameraOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                btnCamera.setEnabled(false);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                getWindow().setEnterTransition(new Fade());
                startActivity(cameraIntent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }
        }
    };

    private View.OnClickListener btnGaleriaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent galleryIntent = new Intent(Intent.ACTION_VIEW);
            galleryIntent.setType("image/*");
            getWindow().setEnterTransition(new Fade());
            startActivity(galleryIntent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    };

    private View.OnClickListener btn1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                getWindow().setEnterTransition(new Fade());
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Escolha um app primeiro.", Toast.LENGTH_LONG).show();
            }
        }
    };

    private View.OnLongClickListener btn1OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);

            getWindow().setEnterTransition(new Fade());
            startActivityForResult(pickIntent, 1, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            return true;
        }
    };

    private View.OnClickListener btn2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                getWindow().setEnterTransition(new Fade());
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Escolha um app primeiro.", Toast.LENGTH_LONG).show();
            }
        }
    };

    private View.OnLongClickListener btn2OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);

            getWindow().setEnterTransition(new Fade());
            startActivityForResult(pickIntent, 2);
            return true;
        }
    };

    private View.OnClickListener btn3OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                getWindow().setEnterTransition(new Fade());
                startActivity(intent3);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Escolha um app primeiro.", Toast.LENGTH_LONG).show();
            }
        }
    };

    private View.OnLongClickListener btn3OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);

            getWindow().setEnterTransition(new Fade());
            startActivityForResult(pickIntent, 3);
            return true;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Icone icone1 = new Icone();
            icone1.setId("1");
            if (resultCode == RESULT_OK) {
                String NomeAplicação = data.getComponent().flattenToString();
                String Classe = data.getComponent().getClassName();
                icone1.setClasse(Classe);
                String Pacote = data.getComponent().getPackageName();
                icone1.setPacote(Pacote);
                if (this.Classe1 == null) {
                    System.out.println(tabelaIcones.insereDado(icone1));
                } else {
                    System.out.println(tabelaIcones.alteraRegistro(icone1));
                }

                btn1 = findViewById(R.id.btn1);
                intent1 = new Intent();
                intent1.setComponent(new ComponentName(Pacote, Classe));
                PackageManager pm = this.getPackageManager();
                try {
                    Drawable icone = (pm.getActivityIcon(intent1));
                    btn1.setImageDrawable(icone);
                    btn1.setBackground(null);
                    btn1.setScaleX((float) 1.5);
                    btn1.setScaleY((float) 1.5);
                } catch (PackageManager.NameNotFoundException e) {

                }
            }
        } else if (requestCode == 2) {
            Icone icone2 = new Icone();
            icone2.setId("2");
            if (resultCode == RESULT_OK) {
                String NomeAplicação = data.getComponent().flattenToString();
                String Classe = data.getComponent().getClassName();
                icone2.setClasse(Classe);
                String Pacote = data.getComponent().getPackageName();
                icone2.setPacote(Pacote);
                if (this.Classe2 == null) {
                    System.out.println(tabelaIcones.insereDado(icone2));
                } else {
                    System.out.println(tabelaIcones.alteraRegistro(icone2));
                }

                btn2 = findViewById(R.id.btn2);
                intent2 = new Intent();
                intent2.setComponent(new ComponentName(Pacote, Classe));
                PackageManager pm = this.getPackageManager();
                try {
                    Drawable icone = (pm.getActivityIcon(intent2));
                    btn2.setImageDrawable(icone);
                    btn2.setBackground(null);
                    btn2.setScaleX((float) 1.5);
                    btn2.setScaleY((float) 1.5);
                } catch (PackageManager.NameNotFoundException e) {

                }
            }
        } else if (requestCode == 3) {
            Icone icone3 = new Icone();
            icone3.setId("3");
            if (resultCode == RESULT_OK) {
                String NomeAplicação = data.getComponent().flattenToString();
                String Classe = data.getComponent().getClassName();
                icone3.setClasse(Classe);
                String Pacote = data.getComponent().getPackageName();
                icone3.setPacote(Pacote);
                if (this.Classe3 == null) {
                    System.out.println(tabelaIcones.insereDado(icone3));
                } else {
                    System.out.println(tabelaIcones.alteraRegistro(icone3));
                }

                btn3 = findViewById(R.id.btn3);
                intent3 = new Intent();
                intent3.setComponent(new ComponentName(Pacote, Classe));
                PackageManager pm = this.getPackageManager();
                try {
                    Drawable icone = (pm.getActivityIcon(intent3));
                    btn3.setImageDrawable(icone);
                    btn3.setBackground(null);
                    btn3.setScaleX((float) 1.5);
                    btn3.setScaleY((float) 1.5);
                } catch (PackageManager.NameNotFoundException e) {

                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getActionBar()).hide();
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_home_screen);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = findViewById(R.id.date);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        t.start();

        try {
            this.Classe1 = tabelaIcones.carregaDados().get(0).getClasse();
            String Pacote1 = tabelaIcones.carregaDados().get(0).getPacote();

            btn1 = findViewById(R.id.btn1);
            intent1 = new Intent();
            intent1.setComponent(new ComponentName(Pacote1, Classe1));
            PackageManager pm = this.getPackageManager();

            Drawable icone1 = (pm.getActivityIcon(intent1));
            btn1.setImageDrawable(icone1);
            btn1.setBackground(null);
            btn1.setScaleX((float) 1.5);
            btn1.setScaleY((float) 1.5);

            this.Classe2 = tabelaIcones.carregaDados().get(1).getClasse();
            String Pacote2 = tabelaIcones.carregaDados().get(1).getPacote();

            btn2 = findViewById(R.id.btn2);
            intent2 = new Intent();
            intent2.setComponent(new ComponentName(Pacote2, Classe2));
            pm = this.getPackageManager();

            Drawable icone2 = (pm.getActivityIcon(intent2));
            btn2.setImageDrawable(icone2);
            btn2.setBackground(null);
            btn2.setScaleX((float) 1.5);
            btn2.setScaleY((float) 1.5);

            this.Classe3 = tabelaIcones.carregaDados().get(2).getClasse();
            String Pacote3 = tabelaIcones.carregaDados().get(2).getPacote();

            btn3 = findViewById(R.id.btn3);
            intent3 = new Intent();
            intent3.setComponent(new ComponentName(Pacote3, Classe3));
            pm = this.getPackageManager();

            Drawable icone3 = (pm.getActivityIcon(intent3));
            btn3.setImageDrawable(icone3);
            btn3.setBackground(null);
            btn3.setScaleX((float) 1.5);
            btn3.setScaleY((float) 1.5);

        } catch (Exception e) {
        }

        ImageButton btnSOS = findViewById(R.id.btnSOS);
        btnSOS.setOnClickListener(btnSOSOnClickListener);

        ImageButton btnNotas = findViewById(R.id.btnNotas);
        btnNotas.setOnClickListener(btnNotasOnClickListener);

        ImageButton btnAlarme = findViewById(R.id.btnAlarme);
        btnAlarme.setOnClickListener(btnAlarmeOnClickListener);

        ImageButton btnContatos = findViewById(R.id.btnContatos);
        btnContatos.setOnClickListener(btnContatosOnClickListener);

        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(btnCameraOnClickListener);

        ImageButton btnGaleria = findViewById(R.id.btnGaleria);
        btnGaleria.setOnClickListener(btnGaleriaOnClickListener);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(btn1OnClickListener);
        btn1.setOnLongClickListener(btn1OnLongClickListener);

        ImageButton btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(btn2OnClickListener);
        btn2.setOnLongClickListener(btn2OnLongClickListener);

        ImageButton btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(btn3OnClickListener);
        btn3.setOnLongClickListener(btn3OnLongClickListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnCamera.setEnabled(true);
            }
        }
    }
}
