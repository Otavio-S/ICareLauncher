package com.example.otavio.tcc.Telas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.otavio.tcc.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class TelaSOS extends Activity {

    private final Activity activity = this;

    private View.OnClickListener btnContato1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 0);
            }
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:01537999143325")));
            }
        }
    };

    private View.OnClickListener btnContato2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 0);
            }
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:0153732313325")));
            }
        }
    };

    private View.OnClickListener btnMensagem1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse("smsto: 01537999143325");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Me ajude! Estou com algum problema!");
                startActivity(it);
            }
        }
    };

    private View.OnClickListener btnMensagem2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse("smsto: 01531996998501");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Me ajude! Estou com algum problema!");
                startActivity(it);
            }
        }
    };

    private View.OnLongClickListener imgView1OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20);
            }
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                showPictureDialog1();
            }
            return false;
        }
    };

    private View.OnLongClickListener imgView2OnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20);
            }
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                showPictureDialog2();
            }
            return false;
        }
    };

    private void showPictureDialog1() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Selecione a Ação");
        String[] pictureDialogItems = {
                "Selecionar uma foto da galeria",
                "Capturar uma foto da câmera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                choosePhotoFromGallery1();
                                break;
                            case 1:
                                takePhotoFromCamera1();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void showPictureDialog2() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Selecione a Ação");
        String[] pictureDialogItems = {
                "Selecionar uma foto da galeria",
                "Capturar uma foto da câmera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                choosePhotoFromGallery2();
                                break;
                            case 1:
                                takePhotoFromCamera2();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery1() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 101);
    }

    private void takePhotoFromCamera1() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 102);
    }

    public void choosePhotoFromGallery2() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 201);
    }

    private void takePhotoFromCamera2() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 202);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imgView1;
        ImageView imgView2;
        if (requestCode == 101) {//Galeria
            if (data != null) {
                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imgView1 = findViewById(R.id.image1);
                    imgView1.setImageBitmap(bitmap);


/////////////////////////////

                    ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                    File file = wrapper.getDir("Images", MODE_PRIVATE);

                    file = new File(file, "Photo1.jpg");

                    OutputStream stream;
                    stream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.flush();
                    stream.close();

//////////////////////////


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 102) {//Camera
            if (data != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent, "Tire uma Foto"), 103);
            }
        } else if (requestCode == 103) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imgView1 = findViewById(R.id.image1);
            imgView1.setImageBitmap(bitmap);

            /////////////////////////////
            try {
                ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                File file = wrapper.getDir("Images", MODE_PRIVATE);

                file = new File(file, "Photo1.jpg");

                OutputStream stream;
                stream = new FileOutputStream(file);
                Objects.requireNonNull(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
            } catch (Exception ignored) {

            }
            //////////////////////////


        } else if (requestCode == 201) {//Galeria
            if (data != null) {

                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imgView2 = findViewById(R.id.image2);
                    imgView2.setImageBitmap(bitmap);

                    /////////////////////////////

                    ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                    File file = wrapper.getDir("Images", MODE_PRIVATE);

                    file = new File(file, "Photo2.jpg");

                    OutputStream stream;
                    stream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.flush();
                    stream.close();

                    //////////////////////////

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 202) {//Camera
            if (data != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent, "Tire uma Foto"), 203);
            }
        } else if (requestCode == 203) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imgView2 = findViewById(R.id.image2);
            imgView2.setImageBitmap(bitmap);


            /////////////////////////////
            try {
                ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

                File file = wrapper.getDir("Images", MODE_PRIVATE);

                file = new File(file, "Photo2.jpg");

                OutputStream stream;
                stream = new FileOutputStream(file);
                Objects.requireNonNull(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
            } catch (Exception ignored) {

            }
            //////////////////////////


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getActionBar()).setElevation(8);
        getWindow().setStatusBarColor(Color.rgb(130, 100, 80));
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_sos);

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
                Toast.makeText(getApplicationContext(), "Escolha uma imagem!", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            @SuppressLint("SdCardPath") String caminho1 = "/data/user/0/com.example.otavio.tcc/app_Images/Photo1.jpg";
            imgView1.setImageDrawable(Drawable.createFromPath(caminho1));
        } catch (Exception ignored) {

        }

        ImageView imgView2 = findViewById(R.id.image2);
        imgView2.setOnLongClickListener(imgView2OnLongClickListener);
        imgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Escolha uma imagem!", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            @SuppressLint("SdCardPath") String caminho2 = "/data/user/0/com.example.otavio.tcc/app_Images/Photo2.jpg";
            imgView2.setImageDrawable(Drawable.createFromPath(caminho2));
        } catch (Exception ignored) {

        }
    }

}