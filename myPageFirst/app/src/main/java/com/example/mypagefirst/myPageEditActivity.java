package com.example.mypagefirst;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;


public class myPageEditActivity extends AppCompatActivity {


    static Uri externImageUri;
    Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_edit);
        ImageView imageView = findViewById(R.id.imageView);
        Button btnSaveInfo = findViewById(R.id.saveMyInfo);
        Button btnEditPhoto = findViewById(R.id.editMyPhotoButton);
        setTitle("정보 수정");
        getSupportActionBar().setTitle("정보 수정");

        Intent intent = getIntent();

        EditText editTextName = (EditText) findViewById(R.id.nameEditText);
        EditText editTextStdnum = (EditText) findViewById(R.id.stdnumEditText);
        EditText editTextMajor = (EditText) findViewById(R.id.majorEditText);
        EditText editTextPlace = (EditText) findViewById(R.id.placeEditText);

        editTextName.setText(intent.getStringExtra("externName"));
        editTextStdnum.setText(intent.getStringExtra("externStdnum"));
        editTextPlace.setText(intent.getStringExtra("externPlace"));
        editTextMajor.setText(intent.getStringExtra("externMajor"));

        String externDisabledReceived = intent.getStringExtra("externDisabled");
        String[] externDisabledSet = externDisabledReceived.split(" ");

        String externDisabledType = externDisabledSet[0];
        String externDisabledClass = externDisabledSet[1];

        Log.d(TAG, "spliced Data: " + externDisabledType + ", " + externDisabledClass  + "   end.");

        Spinner disabledSpinner = (Spinner) findViewById(R.id.disabledSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.disabledSpinner, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disabledSpinner.setAdapter(adapter);

        selectSpinnerValue(disabledSpinner, externDisabledType);


        Spinner disabledClassSpinner = (Spinner) findViewById(R.id.disabledClass);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_two = ArrayAdapter.createFromResource(this,
                R.array.disabledClass, R.layout.spinner_layout);
// Specify the layout to use when the list of choices appears
        adapter_two.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        disabledClassSpinner.setAdapter(adapter_two);

        selectSpinnerValue(disabledClassSpinner, externDisabledClass);

        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                i.setType("image/*");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivityForResult(i.createChooser(i, "Open"), 10);
            }
        });

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editTextName = (EditText) findViewById(R.id.nameEditText);
                EditText editTextStdnum = (EditText) findViewById(R.id.stdnumEditText);
                EditText editTextMajor = (EditText) findViewById(R.id.majorEditText);
                EditText editTextPlace = (EditText) findViewById(R.id.placeEditText);



                String disabledType = disabledSpinner.getSelectedItem().toString();
                String disabledClass = disabledClassSpinner.getSelectedItem().toString();

                Intent returnIntent = getIntent();





                returnIntent.putExtra("userName", editTextName.getText().toString());
                returnIntent.putExtra("userStdnum", editTextStdnum.getText().toString());
                returnIntent.putExtra("userMajor", editTextMajor.getText().toString());
                returnIntent.putExtra("userPlace", editTextPlace.getText().toString());
                String disabledCombined = disabledType + " " + disabledClass;
                returnIntent.putExtra("userDisabled", disabledCombined);
                returnIntent.putExtra("userPhoto", externImageUri);
                Log.d(TAG, "received Extern URI" + externImageUri + "   end.");
                returnIntent.putExtra("bitmap", bitmap);


                setResult(Activity.RESULT_OK, returnIntent);


                finish();
            }
        });
    }



    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.imageView);
        if(requestCode == 10 && resultCode == RESULT_OK) {
            Uri selectImageUri = data.getData();
            externImageUri = selectImageUri;
            imageView.setImageURI(selectImageUri);


            try {
                Bitmap obitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), externImageUri);
                bitmap =  Bitmap.createScaledBitmap(obitmap, 200, 200, true);
                Log.d(TAG, "bitmap 압축 진행");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    }
