package com.cdac.iaf.bookmycoolie.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.utils.FileUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddCoolieActivity extends AppCompatActivity {

    ShapeableImageView shapeableImageView, shapeableImageView2;
    Button captimg, captimgadhar, btn_rgstrc;
    boolean camFlag;
    String capturedPhoto;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    byte[] bytesCapFile;

    FileUtil fileUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coolie);

        shapeableImageView = findViewById(R.id.shapeableImageView);
        shapeableImageView2 = findViewById(R.id.shapeableImageView2);
        captimg= findViewById(R.id.captimg);
        captimgadhar= findViewById(R.id.captimgadhar);
        btn_rgstrc = findViewById(R.id.btn_rgstrc);

        btn_rgstrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(AddCoolieActivity.this);
                m.setTitle("ALERT!")
                        .setMessage("COOLIE SUCCESSFULLY REGISTERE!")
                        .setPositiveButton("HOME", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddCoolieActivity.this, AdminHomeActivity.class));
                            }
                        }).show();

            }
        });

        captimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (ContextCompat.checkSelfPermission(AddCoolieActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 12);

                    //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
                }
                else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddCoolieActivity.this, Manifest.permission.CAMERA)){
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AddCoolieActivity.this);
                        materialAlertDialogBuilder.setTitle("Camera Permission Needed")
                                .setMessage("Access to Camera Permission is needed")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ActivityCompat.requestPermissions(AddCoolieActivity.this,
                                                new String[]{Manifest.permission.CAMERA}, 1);
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                    }
                    else{
                        ActivityCompat.requestPermissions(AddCoolieActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 12);
                    }
                }*/
                cameraIntentMaker(1);
            }
        });
        captimgadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraIntentMaker(2);
            }
        });






    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //adapter.onActivityResult(requestCode, resultCode, data);


        try {
            if (requestCode == 12) {


                ContentResolver cR = getApplicationContext().getContentResolver();


                Bitmap photo = (Bitmap) data.getExtras().get("data");
                shapeableImageView.setVisibility(View.VISIBLE);
                shapeableImageView.setImageBitmap(photo);

                Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(cR, photo, "Image", null));
                bytesCapFile = fileUtil.getBytes(imageUri);

                MimeTypeMap mime = MimeTypeMap.getSingleton();
                //picFileMime = mime.getExtensionFromMimeType(cR.getType(imageUri));

            }
        } catch (Exception e) {

            //Snackbar.make(baseLayout, "File not Selected!!!", Snackbar.ANIMATION_MODE_SLIDE).show();
            ////Log.e("indra", "in activity" + e.toString());
            Toast.makeText(getApplicationContext(), "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
            System.out.println("Error is"+e.toString());
        }

    }*/
    ActivityResultLauncher<Intent> camlauncer = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Add same code that you want to add in onActivityResult method
                    /*System.out.println("In Result "+result.);
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    iv2.setImageBitmap(photo);*/
                    System.out.println("Result is "+result.getResultCode());
                    if (result.getResultCode() == Activity.RESULT_OK ){
                        try{
                            File imageFile2 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_image.jpg");
                            // Retrieve the captured image URI
                            Uri imageUri2 = FileProvider.getUriForFile(AddCoolieActivity.this, "com.cdac.iaf.bookmycoolie.fileprovider", imageFile2);

                            // Load the full-resolution image from the URI
                            Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri2);

                            ExifInterface exif = new ExifInterface(imageFile2.getAbsolutePath());
                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            photo = rotateBitmap(photo, orientation);

                            System.out.println("Size before scaling"+photo.getByteCount()+" "+photo.getDensity());

                            float aspectRatio = (float) photo.getWidth() / photo.getHeight();

                            // Define the target width and height for the 720p resolution
                            int targetWidth = 720;
                            int targetHeight = Math.round(targetWidth / aspectRatio);

                            // Create a scaled bitmap with the target resolution
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, targetWidth, targetHeight, false);
                            System.out.println("Size after scaling"+scaledBitmap.getByteCount()+" "+scaledBitmap.getDensity());


                            //Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                            shapeableImageView.setImageBitmap(scaledBitmap);
                            shapeableImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            camFlag = true;
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            System.out.println("Size after compression"+scaledBitmap.getByteCount()+" "+scaledBitmap.getDensity());

                            byte[] byteArray = byteArrayOutputStream .toByteArray();
                            capturedPhoto = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                            System.out.println("CAMERA PHOTO"+scaledBitmap.getWidth()+" "+scaledBitmap.getHeight());
                        } catch (Exception e){
                            camFlag = false;
                            System.out.println("activity cam excep: "+ e);
                            Toast.makeText(AddCoolieActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> camlauncer2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Add same code that you want to add in onActivityResult method
                    /*System.out.println("In Result "+result.);
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    iv2.setImageBitmap(photo);*/
                    System.out.println("Result is "+result.getResultCode());
                    if (result.getResultCode() == Activity.RESULT_OK ){
                        try{
                            File imageFile2 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_image.jpg");
                            // Retrieve the captured image URI
                            Uri imageUri2 = FileProvider.getUriForFile(AddCoolieActivity.this, "com.cdac.iaf.bookmycoolie.fileprovider", imageFile2);

                            // Load the full-resolution image from the URI
                            Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri2);

                            ExifInterface exif = new ExifInterface(imageFile2.getAbsolutePath());
                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            photo = rotateBitmap(photo, orientation);

                            System.out.println("Size before scaling"+photo.getByteCount()+" "+photo.getDensity());

                            float aspectRatio = (float) photo.getWidth() / photo.getHeight();

                            // Define the target width and height for the 720p resolution
                            int targetWidth = 720;
                            int targetHeight = Math.round(targetWidth / aspectRatio);

                            // Create a scaled bitmap with the target resolution
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, targetWidth, targetHeight, false);
                            System.out.println("Size after scaling"+scaledBitmap.getByteCount()+" "+scaledBitmap.getDensity());


                            //Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                            shapeableImageView2.setImageBitmap(scaledBitmap);
                            shapeableImageView2.setScaleType(ImageView.ScaleType.FIT_XY);
                            camFlag = true;
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            System.out.println("Size after compression"+scaledBitmap.getByteCount()+" "+scaledBitmap.getDensity());

                            byte[] byteArray = byteArrayOutputStream .toByteArray();
                            capturedPhoto = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                            System.out.println("CAMERA PHOTO"+scaledBitmap.getWidth()+" "+scaledBitmap.getHeight());
                        } catch (Exception e){
                            camFlag = false;
                            System.out.println("activity cam excep: "+ e);
                            Toast.makeText(AddCoolieActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void cameraIntentMaker(int mode){


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_image.jpg");
        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.cdac.iaf.bookmycoolie.fileprovider", imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if(mode==1){
            camlauncer.launch(cameraIntent);
        } else if (mode==2) {
            camlauncer2.launch(cameraIntent);
        }

    }
}