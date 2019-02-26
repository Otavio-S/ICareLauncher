package com.example.otavio.tcc.Telas.SOS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.SOS;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaSOS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class TelaSOS extends AppCompatActivity {

    private static final String[] phoneProjection = new String[]{ContactsContract.CommonDataKinds.Phone.DATA};
    private final Activity activity = this;
    String message = "";
    private Uri picUri;
    private String num = "";
    private View.OnClickListener btnContato1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());
            num = tabelaSOS.carregaDadosPorID(0).getNumero();

            if (num != null && !num.equals("")) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 0);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num)));
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.defina_contato, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener btnContato2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());
            num = tabelaSOS.carregaDadosPorID(1).getNumero();

            if (num != null && !num.equals("")) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 0);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num)));
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.defina_contato, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener btnMensagem1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());
            num = tabelaSOS.carregaDadosPorID(0).getNumero();

            if (num != null && !num.equals("")) {
                try {
                    message = tabelaSOS.carregaDadosPorID(2).getMensagem();
                } catch (Exception ignored) {
                }
                System.out.println("MENSAGEM::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+message);
                if (message != null && !message.equals("")) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 1);
                    } else {
                        Uri uri = Uri.parse("smsto: " + num);
                        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                        it.putExtra("sms_body", message);
                        startActivity(it);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.defina_mensagem, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), R.string.defina_contato, Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener btnMensagem2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());
            num = tabelaSOS.carregaDadosPorID(1).getNumero();

            if (num != null && !num.equals("")) {
                try {
                    message = tabelaSOS.carregaDadosPorID(2).getMensagem();
                } catch (Exception ignored) {
                }
                if (message != null && !message.equals("")) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 1);
                    } else {
                        Uri uri = Uri.parse("smsto: " + num);
                        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                        it.putExtra("sms_body", message);
                        startActivity(it);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.defina_mensagem, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), R.string.defina_contato, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnLongClickListener imgView1OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);
            } else {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 101);
            }
            return false;
        }
    };

    private View.OnLongClickListener imgView2OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 202);
            } else {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 102);
            }
            return false;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num)));
                }
                break;
            case 1:
                Uri uri = Uri.parse("smsto: " + num);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", message);
                startActivity(it);
                break;
            case 201:
                Intent pickPhoto1 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto1, 101);
                break;
            case 202:
                Intent pickPhoto2 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto2, 102);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imgView1;
        ImageView imgView2;

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri contactUri = data.getData();
                    if (null == contactUri) return;
                    Cursor cursor = getContentResolver().query(contactUri, phoneProjection, null, null, null);
                    if (null == cursor) return;
                    String number1 = "";
                    try {
                        while (cursor.moveToNext()) {
                            number1 = cursor.getString(0);
                        }
                    } finally {
                        cursor.close();
                    }

                    TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());
                    String id = null;
                    try {
                        id = tabelaSOS.carregaDadosPorID(0).getNumero();
                    } catch (Exception ignored) {
                    }

                    if (id == null) {
                        SOS sos = new SOS();
                        sos.setId("0");
                        sos.setNumero(number1);
                        tabelaSOS.insereDado(sos);
                        Toast.makeText(getApplicationContext(), R.string.numero1_salvo, Toast.LENGTH_SHORT).show();
                    } else {
                        SOS sos = new SOS();
                        sos.setId("0");
                        sos.setNumero(number1);
                        tabelaSOS.alteraRegistro(sos);
                        Toast.makeText(getApplicationContext(), R.string.numero1_atualizado, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri contactUri = data.getData();
                    if (null == contactUri) return;
                    Cursor cursor = getContentResolver().query(contactUri, phoneProjection, null, null, null);
                    if (null == cursor) return;
                    String number2 = "";
                    try {
                        while (cursor.moveToNext()) {
                            number2 = cursor.getString(0);
                        }
                    } finally {
                        cursor.close();
                    }

                    TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());
                    String id = null;
                    try {
                        id = tabelaSOS.carregaDadosPorID(1).getNumero();
                    } catch (Exception ignored) {
                    }

                    if (id == null) {
                        SOS sos = new SOS();
                        sos.setId("1");
                        sos.setNumero(number2);
                        tabelaSOS.insereDado(sos);
                        Toast.makeText(getApplicationContext(), R.string.numero2_salvo, Toast.LENGTH_SHORT).show();
                    } else {
                        SOS sos = new SOS();
                        sos.setId("1");
                        sos.setNumero(number2);
                        tabelaSOS.alteraRegistro(sos);
                        Toast.makeText(getApplicationContext(), R.string.numero2_atualizado, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case 101:
                if (data != null) {
                    picUri = data.getData();
                    performCrop1();
                }
                break;
            case 102:
                if (data != null) {
                    picUri = data.getData();
                    performCrop2();
                }
                break;

            case 201:
                if (data != null) {
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap bitmap = (Bitmap) Objects.requireNonNull(extras).get("data");

                        Resources res = getResources();
                        RoundedBitmapDrawable dr =
                                RoundedBitmapDrawableFactory.create(res, bitmap);
                        dr.setCornerRadius(Math.min(Objects.requireNonNull(bitmap).getWidth(), bitmap.getHeight()) / 4.0f);

                        imgView1 = findViewById(R.id.image1);
                        imgView1.setImageDrawable(dr);

                        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                        File file = wrapper.getDir("Images", MODE_PRIVATE);

                        file = new File(file, "Photo1.jpg");

                        OutputStream stream;
                        stream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 202:
                if (data != null) {
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");

                        Resources res = getResources();
                        RoundedBitmapDrawable dr =
                                RoundedBitmapDrawableFactory.create(res, bitmap);
                        dr.setCornerRadius(Math.min(Objects.requireNonNull(bitmap).getWidth(), bitmap.getHeight()) / 4.0f);

                        imgView2 = findViewById(R.id.image2);
                        imgView2.setImageDrawable(dr);

                        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                        File file = wrapper.getDir("Images", MODE_PRIVATE);

                        file = new File(file, "Photo2.jpg");

                        OutputStream stream;
                        stream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void performCrop1() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 201);
        } catch (Exception ignored) {
        }
    }

    private void performCrop2() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 202);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_sos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item1:
                Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent1.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent1, 1);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent2.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent2, 2);
                return true;
            case R.id.item3:
                Intent intent3 = new Intent(TelaSOS.this, Mensagem.class);
                startActivity(intent3);
                return true;
            default:
                return false;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        ImageButton btnContato1 = findViewById(R.id.btnContato1);
        btnContato1.setOnClickListener(btnContato1OnClickListener);

        ImageButton btnContato2 = findViewById(R.id.btnContato2);
        btnContato2.setOnClickListener(btnContato2OnClickListener);

        ImageButton btnMensagem1 = findViewById(R.id.btnMensagem1);
        btnMensagem1.setOnClickListener(btnMensagem1OnClickListener);

        ImageButton btnMensagem2 = findViewById(R.id.btnMensagem2);
        btnMensagem2.setOnClickListener(btnMensagem2OnClickListener);

        ImageView imgView1 = findViewById(R.id.image1);
        imgView1.setOnLongClickListener(imgView1OnLongClickListener);
        imgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.escolha_imagem, Toast.LENGTH_SHORT).show();
            }
        });

        try {
            @SuppressLint("SdCardPath") String caminho1 = "/data/user/0/com.example.otavio.tcc/app_Images/Photo1.jpg";
            Drawable drawable = Drawable.createFromPath(caminho1);

            Bitmap bitmap = ((BitmapDrawable) Objects.requireNonNull(drawable)).getBitmap();

            Resources res = getResources();
            RoundedBitmapDrawable dr =
                    RoundedBitmapDrawableFactory.create(res, bitmap);
            dr.setCornerRadius(Math.min(Objects.requireNonNull(bitmap).getWidth(), bitmap.getHeight()) / 4.0f);

            imgView1.setImageDrawable(dr);

        } catch (Exception ignored) {
        }

        ImageView imgView2 = findViewById(R.id.image2);
        imgView2.setOnLongClickListener(imgView2OnLongClickListener);
        imgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.escolha_imagem, Toast.LENGTH_SHORT).show();
            }
        });

        try {
            @SuppressLint("SdCardPath") String caminho2 = "/data/user/0/com.example.otavio.tcc/app_Images/Photo2.jpg";
            Drawable drawable = Drawable.createFromPath(caminho2);

            Bitmap bitmap = ((BitmapDrawable) Objects.requireNonNull(drawable)).getBitmap();

            Resources res = getResources();
            RoundedBitmapDrawable dr =
                    RoundedBitmapDrawableFactory.create(res, bitmap);
            dr.setCornerRadius(Math.min(Objects.requireNonNull(bitmap).getWidth(), bitmap.getHeight()) / 4.0f);

            imgView2.setImageDrawable(dr);
        } catch (Exception ignored) {
        }
    }

}