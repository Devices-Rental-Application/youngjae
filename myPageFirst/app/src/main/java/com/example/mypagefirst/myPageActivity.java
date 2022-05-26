package com.example.mypagefirst;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class myPageActivity extends AppCompatActivity implements View.OnClickListener {

    static String externName = "홍길동";
    static String externStdnum = "1234";
    static String externMajor = "소프트";
    static String externPlace = "행복주택";
    static String externDisabled = "시각장애 1급";
    static Bitmap externBitmap;
    static Uri externImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnEditMyInfo = (Button) findViewById(R.id.editMyInfoButton);
        setTitle("내 정보");
        getSupportActionBar().setTitle("내 정보");


        btnEditMyInfo.setOnClickListener(this);



        TextView textViewName = (TextView) findViewById(R.id.userName);
        TextView textViewMajor = (TextView) findViewById(R.id.userMajor);
        TextView textViewDisabled = (TextView) findViewById(R.id.userDisabled);

        textViewName.setText(externName);
        textViewMajor.setText(externMajor);
        textViewDisabled.setText(externDisabled);


    }

    @Override
        public void onClick(View v) {
            Intent intentEditInfo = new Intent(myPageActivity.this, myPageEditActivity.class);
            intentEditInfo.putExtra("externName", externName);
            intentEditInfo.putExtra("externMajor", externMajor);
            intentEditInfo.putExtra("externStdnum", externStdnum);
            intentEditInfo.putExtra("externDisabled", externDisabled);
            intentEditInfo.putExtra("externPlace", externPlace);
            startActivityResult.launch(intentEditInfo);
        }



    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {


                    TextView textViewName = (TextView) findViewById(R.id.userName);
                    TextView textViewMajor = (TextView) findViewById(R.id.userMajor);
                    TextView textViewDisabled = (TextView) findViewById(R.id.userDisabled);
                    ImageView imageView = (ImageView) findViewById(R.id.myPhoto);



                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent receivedIntent = result.getData();
                        textViewName.setText(receivedIntent.getStringExtra("userName"));
                        externName = (receivedIntent.getStringExtra("userName"));
                       Log.d(TAG, "received Extern Data" + externName + "   end.");
                        textViewMajor.setText(receivedIntent.getStringExtra("userMajor"));
                        externMajor = (receivedIntent.getStringExtra("userMajor"));
                        textViewDisabled.setText(receivedIntent.getStringExtra("userDisabled"));
                        externPlace = (receivedIntent.getStringExtra("userPlace"));
                        externStdnum = (receivedIntent.getStringExtra("userStdnum"));
                        externDisabled = (receivedIntent.getStringExtra("userDisabled"));
                        externBitmap = (receivedIntent.getParcelableExtra("bitmap"));
                        imageView.setImageBitmap(externBitmap);
                        Log.d(TAG, "MainActivity로 돌아왔다. ");
                    }
                }
            });


    protected void OnActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView textViewName = (TextView) findViewById(R.id.userName);
        TextView textViewMajor = (TextView) findViewById(R.id.userMajor);
        TextView textViewDisabled = (TextView) findViewById(R.id.userDisabled);
        ImageView imageView = (ImageView) findViewById(R.id.myPhoto);

        switch(requestCode) {
            case 1001:
                if(resultCode == RESULT_OK) {
                    textViewName.setText(data.getStringExtra("userName"));
                    textViewMajor.setText(data.getStringExtra("userMajor"));
                    textViewDisabled.setText(data.getStringExtra("userDisabled"));
                    externBitmap = (data.getParcelableExtra("bitmap"));
                    imageView.setImageBitmap(externBitmap);
                    Log.d(TAG, "image 반환 성공");
                }

        }
    }









}