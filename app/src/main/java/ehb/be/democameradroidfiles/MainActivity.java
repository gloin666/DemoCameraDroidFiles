package ehb.be.democameradroidfiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.EXTRA_OUTPUT;

public class MainActivity extends AppCompatActivity {
    //contante
    private final int TheQuestForTheMagicPhoteRequest = 1;
    //ui widget
    private EditText etInput;
    private ImageView imageView;
    //File naar foto
    private File photofile;
    private View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takeAPhote();
        }
    };

    private void takeAPhote() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageFileName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault()).format(new Date());
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photofile = new File(storageDirectory + "/" + imageFileName +  ".jpg");
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "ehb.be.democameradroidfiles", photofile);
        intent.putExtra(EXTRA_OUTPUT, photoURI);

        Log.d("Path", photofile.getAbsolutePath());

        if(intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent, TheQuestForTheMagicPhoteRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TheQuestForTheMagicPhoteRequest && resultCode == RESULT_OK){
            //Bitmap mbitmap = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(mbitmap);
            Picasso.get().load(photofile).into(imageView);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = findViewById(R.id.et_input);
        imageView = findViewById(R.id.img_field);
        imageView.setOnClickListener(imageClickListener);
    }
    public void onReadClick(View v){
        Log.d("Test", "Clicked the button");
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("mijnBestand.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ObjectSentDroid toRead = (ObjectSentDroid) objectInputStream.readObject();

            etInput.setText(toRead.getInhoudString());
            objectInputStream.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File not found",Toast.LENGTH_LONG).show();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void onWriteClick (View v){
        Log.d("test", "Write click test");
        try {
            //waar staat de file
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("mijnBestand.txt", MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //wat slaan we op
            ObjectSentDroid toWrite = new ObjectSentDroid(etInput.getText().toString());
            //effectief opslaan
            objectOutputStream.writeObject(toWrite);
            objectOutputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
