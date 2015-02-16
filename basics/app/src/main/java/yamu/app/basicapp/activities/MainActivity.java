package yamu.app.basicapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import yamu.app.basicapp.R;


public class MainActivity extends ActionBarActivity {

    Button btnLauncherOne;
    Button btnLauncherTwo;
    Button btnLauncherThree;
    Button btnLauncherFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    private void setupViews() {
        btnLauncherOne = (Button) findViewById(R.id.btnLauncherOne);
        btnLauncherTwo = (Button) findViewById(R.id.btnLauncherTwo);
        btnLauncherThree = (Button) findViewById(R.id.btnLauncherThree);
        btnLauncherFour  = (Button) findViewById(R.id.btnLauncherFour);

        btnLauncherOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewSample.class);
                startActivity(intent);
            }

        });

        btnLauncherTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventHandlingExample.class);
                startActivity(intent);
            }
        });
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
}
