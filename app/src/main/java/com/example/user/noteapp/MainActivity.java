package com.example.user.noteapp;

/**
 * Some of the worst code I've ever written. To be honest, I didn't even write.
 * This is purely for learning purposes. Good Lord, it will actually be used, won't it?
 *
 */

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.example.user.noteapp.R.id.editText3;

public class MainActivity extends AppCompatActivity {

    // Fields
    public EditText EditText1;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save("Note1.txt");
            }
        });

        // Example of a call to a native method
        EditText1 = (EditText) findViewById(R.id.editText3);
        EditText1.setText(Open("Note1.txt"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Own methods */

    public void Save(String fileName)
    {
        try
        {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName,0));
            out.write(EditText1.getText().toString());
            out.close();
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        }
        catch (IOException exception) {
            Toast.makeText(this,"Unable to save note: " + exception.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public String Open(String fileName)
    {
        String content = "";
        if (FileExists(fileName))
        {
            try
            {
                InputStream in = openFileInput(fileName);
                if (in != null)
                {
                    InputStreamReader inReader = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(inReader);
                    String string;
                    StringBuilder buffered = new StringBuilder();
                    while ((string = reader.readLine())!= null)
                    {
                        buffered.append(string + "\n");
                    }
                    in.close();
                    content = buffered.toString();
                }
            }
            catch (Exception exception)
            {
                Toast.makeText(this,"Unable to open note: " + exception.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    public boolean FileExists(String fileName)
    {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
