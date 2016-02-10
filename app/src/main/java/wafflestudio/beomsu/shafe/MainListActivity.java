package wafflestudio.beomsu.shafe;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainListActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        EditText mainListFindET = (EditText) findViewById(R.id.main_list_find);
        mainListFindET.setOnClickListener(findOnClickListener);

        ImageButton mainListImageButton = (ImageButton) findViewById(R.id.main_list_map_button);
        mainListImageButton.setOnClickListener(goToMapOnClickListener);
    }

    private final View.OnClickListener findOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainListActivity.this, SearchMainActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener goToMapOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainListActivity.this, MainMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()<backKeyPressedTime+2000) {
            MainListActivity.this.moveTaskToBack(true);
            MainListActivity.this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        else {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(MainListActivity.this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
