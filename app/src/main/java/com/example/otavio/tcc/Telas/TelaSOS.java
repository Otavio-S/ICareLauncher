package com.example.otavio.tcc.Telas;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.otavio.tcc.R;

import java.io.IOException;
import java.util.Objects;

public class TelaSOS extends Activity {

    Activity activity = this;
    ImageView imgView1;
    ImageView imgView2;

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
                                choosePhotoFromGallery();
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
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera2();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    private void takePhotoFromCamera1() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 2);
    }

    private void takePhotoFromCamera2() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imgView1 = findViewById(R.id.image1);
                    imgView1.setImageBitmap(bitmap);
                    //imgView1.setScaleY((float) 0.3);
                    //imgView1.setScaleX((float) 0.3);
                    //imgView1.getCropToPadding();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (requestCode == 2) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1);
            Uri selectedImage = data.getData();
            Toast.makeText(getApplicationContext(), selectedImage.toString(), Toast.LENGTH_SHORT).show();
        } else if (requestCode == 3) {
            if (data != null) {
                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imgView2 = findViewById(R.id.image2);
                    imgView2.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getActionBar()).setElevation(8);
        getWindow().setStatusBarColor(Color.rgb(130, 100, 80));
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
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

        ImageView imgView2 = findViewById(R.id.image2);
        imgView2.setOnLongClickListener(imgView2OnLongClickListener);


    }

}
