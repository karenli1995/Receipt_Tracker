package com.example.karenli.budgetingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.squareup.picasso.Picasso;

public class HomeActivity extends BaseActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private FloatingActionButton myAddReceiptBtn;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpView();
    }

    private void setUpView() {
        setUpToolbar();

        myAddReceiptBtn = (FloatingActionButton) findViewById(R.id.btnAddReceipt);
        this.addOnClickListener();

        mImageView = (ImageView) findViewById(R.id.imageView1);
    }

    private void addOnClickListener(){
        myAddReceiptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){
            Toast.makeText(getApplicationContext(),
                    "Data is null", Toast.LENGTH_LONG)
                    .show();
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    mImageView.setImageBitmap(photo);
//                testSetPic();
//                previewCapturedImage();
                createReceiptActivity();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void createReceiptActivity() {
        //TODO: delete
        Toast.makeText(getApplicationContext(),
                getMyCurrentPhotoPath(), Toast.LENGTH_LONG)
                .show();

        Intent intent = new Intent(this, CreateReceiptActivity.class);
        intent.putExtra("imgPath", myCurrentPhotoPath);
        intent.putExtra("year", myYear);
        intent.putExtra("month", myMonth);
        startActivity(intent);
    }

    private void previewCapturedImage() {
        try {
            mImageView.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(myCurrentPhotoPath,
                    options);
            mImageView.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void testSetPic() {
        Picasso.with(this).load(myCurrentPhotoPath).into(mImageView);
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(myCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(myCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);
        mImageView.setVisibility(View.VISIBLE);
    }


        private static final String JPEG_FILE_PREFIX = "IMG_";
        private static final String JPEG_FILE_SUFFIX = ".jpg";

        private String myCurrentPhotoPath;
        private int myYear;
        private int myMonth;

        public String getMyCurrentPhotoPath() {
            return myCurrentPhotoPath;
        }

        public void dispatchTakePictureIntent() {
            File f = null;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    f = setUpPhotoFile();
                    myCurrentPhotoPath = f.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    myCurrentPhotoPath = null;
                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        private File setUpPhotoFile() throws IOException {

            File f = createImageFile();
            myCurrentPhotoPath = f.getAbsolutePath();

            return f;
        }

        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            myYear = Integer.parseInt(timeStamp.substring(0,4));
            myMonth = Integer.parseInt(timeStamp.substring(4,6)) - 1;
            String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
            File albumF = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File albumF = getAlbumDir();
            File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
            return imageF;
        }

}
