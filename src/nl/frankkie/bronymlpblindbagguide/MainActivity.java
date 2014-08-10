package nl.frankkie.bronymlpblindbagguide;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
/**
 *
 * @author FrankkieNL
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }


    public void initUI(){
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_codes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WavesActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_credits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });
    }    
}
