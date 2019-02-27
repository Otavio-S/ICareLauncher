package com.example.otavio.tcc.Telas.Home;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Icone;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaIcones;
import com.example.otavio.tcc.Telas.Alarmes.TelaAlarmes;
import com.example.otavio.tcc.Telas.Nota.TelaNotas;
import com.example.otavio.tcc.Telas.SOS.TelaSOS;

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
    private TextView tdate;
    private TextClock textClock;
    private int w, h;

    private View.OnClickListener btnSOSOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentS = new Intent(HomeScreen.this, TelaSOS.class);
            startActivity(intentS);
        }
    };


    private View.OnClickListener btnNotasOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentN = new Intent(HomeScreen.this, TelaNotas.class);
            startActivity(intentN);
        }
    };

    private View.OnClickListener btnAlarmeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentA = new Intent(HomeScreen.this, TelaAlarmes.class);
            startActivity(intentA);
        }
    };

    private View.OnClickListener btnContatosOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent contactsIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("content://contacts/people"));
            startActivity(contactsIntent);
        }
    };

    private View.OnClickListener btnCameraOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                btnCamera.setEnabled(false);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(cameraIntent);
            }
        }
    };
    private View.OnClickListener btnGaleriaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent galleryIntent = new Intent(Intent.ACTION_VIEW);
            galleryIntent.setType("image/*");
            startActivity(galleryIntent);
        }
    };
    private View.OnClickListener btn1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                startActivity(intent1);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), R.string.escolha_app, Toast.LENGTH_LONG).show();
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

            startActivityForResult(pickIntent, 1);
            return true;
        }
    };
    private View.OnClickListener btn2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                startActivity(intent2);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), R.string.escolha_app, Toast.LENGTH_LONG).show();
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

            startActivityForResult(pickIntent, 2);
            return true;
        }
    };

    private View.OnClickListener btn3OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                startActivity(intent3);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), R.string.escolha_app, Toast.LENGTH_LONG).show();
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

            startActivityForResult(pickIntent, 3);
            return true;
        }
    };

    private View.OnLongClickListener btnChangeColor = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int cor = textClock.getCurrentTextColor();
            if (cor == Color.WHITE) {
                textClock.setTextColor(Color.BLACK);
                tdate.setTextColor(Color.BLACK);
                Drawable d = btn1.getBackground();

                String Classe = "";
                try {
                    Classe = tabelaIcones.carregaDados().get(0).getClasse();
                } catch (Exception ignored) {
                }

                if (Classe.equals("")) {
                    btn1.setBackgroundResource(R.drawable.add);
                }

                String Classe2 = "";
                try {
                    Classe2 = tabelaIcones.carregaDados().get(1).getClasse();
                } catch (Exception ignored) {
                }

                if (Classe2.equals("")) {
                    btn2.setBackgroundResource(R.drawable.add);
                }

                String Classe3 = "";
                try {
                    Classe3 = tabelaIcones.carregaDados().get(2).getClasse();
                } catch (Exception ignored) {
                }

                if (Classe3.equals("")) {
                    btn3.setBackgroundResource(R.drawable.add);
                }
            } else {
                textClock.setTextColor(Color.WHITE);
                tdate.setTextColor(Color.WHITE);

                String Classe = "";
                try {
                    Classe = tabelaIcones.carregaDados().get(0).getClasse();
                } catch (Exception ignored) {
                }

                if (Classe.equals("")) {
                    btn1.setBackgroundResource(R.drawable.add_white);
                }

                String Classe2 = "";
                try {
                    Classe2 = tabelaIcones.carregaDados().get(1).getClasse();
                } catch (Exception ignored) {
                }

                if (Classe2.equals("")) {
                    btn2.setBackgroundResource(R.drawable.add_white);
                }

                String Classe3 = "";
                try {
                    Classe3 = tabelaIcones.carregaDados().get(2).getClasse();
                } catch (Exception ignored) {
                }

                if (Classe3.equals("")) {
                    btn3.setBackgroundResource(R.drawable.add_white);
                }
            }
            return false;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(cameraIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Icone icone1 = new Icone();
            icone1.setId("1");
            if (resultCode == RESULT_OK) {
                String Classe = Objects.requireNonNull(data.getComponent()).getClassName();
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
                    btn1.setMaxHeight(h);
                    btn1.setMinimumHeight(h);
                    btn1.setMaxWidth(h);
                    btn1.setMinimumWidth(h);
                    btn1.setBackground(icone);
                } catch (PackageManager.NameNotFoundException ignored) {
                }
            }
        } else if (requestCode == 2) {
            Icone icone2 = new Icone();
            icone2.setId("2");
            if (resultCode == RESULT_OK) {
                String Classe = Objects.requireNonNull(data.getComponent()).getClassName();
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
                    btn2.setMaxHeight(h);
                    btn2.setMinimumHeight(h);
                    btn2.setMaxWidth(h);
                    btn2.setMinimumWidth(h);
                    btn2.setBackground(icone);
                } catch (PackageManager.NameNotFoundException ignored) {

                }
            }
        } else if (requestCode == 3) {
            Icone icone3 = new Icone();
            icone3.setId("3");
            if (resultCode == RESULT_OK) {
                String Classe = Objects.requireNonNull(data.getComponent()).getClassName();
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
                    btn3.setMaxHeight(h);
                    btn3.setMinimumHeight(h);
                    btn3.setMinimumWidth(w);
                    btn3.setMaxWidth(w);
                    btn3.setBackground(icone);
                } catch (PackageManager.NameNotFoundException ignored) {

                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        tdate = findViewById(R.id.date);
        btn1 = findViewById(R.id.btn1);
        btn1.setBackgroundResource(R.drawable.add);
        btn2 = findViewById(R.id.btn2);
        btn2.setBackgroundResource(R.drawable.add);
        btn3 = findViewById(R.id.btn3);
        btn3.setBackgroundResource(R.drawable.add);


        ImageButton btnGaleria = findViewById(R.id.btnGaleria);
        w = btnGaleria.getWidth();
        h = btnGaleria.getHeight();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

        textClock = findViewById(R.id.textClock);
        textClock.setText(textClock.getText());

        textClock.setOnLongClickListener(btnChangeColor);

        try {
            this.Classe1 = tabelaIcones.carregaDados().get(0).getClasse();
            String Pacote1 = tabelaIcones.carregaDados().get(0).getPacote();

            intent1 = new Intent();
            intent1.setComponent(new ComponentName(Pacote1, Classe1));
            PackageManager pm = this.getPackageManager();

            Drawable icone1 = (pm.getActivityIcon(intent1));
            btn1.setMaxHeight(h);
            btn1.setMinimumHeight(h);
            btn1.setMaxWidth(w);
            btn1.setMinimumWidth(w);
            btn1.setBackground(icone1);

            this.Classe2 = tabelaIcones.carregaDados().get(1).getClasse();
            String Pacote2 = tabelaIcones.carregaDados().get(1).getPacote();

            intent2 = new Intent();
            intent2.setComponent(new ComponentName(Pacote2, Classe2));
            pm = this.getPackageManager();

            Drawable icone2 = (pm.getActivityIcon(intent2));
            btn2.setMaxHeight(h);
            btn2.setMinimumHeight(h);
            btn2.setMaxWidth(w);
            btn2.setMinimumWidth(w);
            btn2.setBackground(icone2);

            this.Classe3 = tabelaIcones.carregaDados().get(2).getClasse();
            String Pacote3 = tabelaIcones.carregaDados().get(2).getPacote();

            intent3 = new Intent();
            intent3.setComponent(new ComponentName(Pacote3, Classe3));
            pm = this.getPackageManager();

            Drawable icone3 = (pm.getActivityIcon(intent3));
            btn3.setMaxHeight(h);
            btn3.setMinimumHeight(h);
            btn3.setMinimumWidth(w);
            btn3.setMaxWidth(w);
            btn3.setBackground(icone3);

        } catch (Exception ignored) {
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

        btnGaleria.setOnClickListener(btnGaleriaOnClickListener);

        btn1.setOnClickListener(btn1OnClickListener);
        btn1.setOnLongClickListener(btn1OnLongClickListener);

        btn2.setOnClickListener(btn2OnClickListener);
        btn2.setOnLongClickListener(btn2OnLongClickListener);

        btn3.setOnClickListener(btn3OnClickListener);
        btn3.setOnLongClickListener(btn3OnLongClickListener);

    }

}
