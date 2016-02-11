package wafflestudio.beomsu.shafe;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SearchMainActivity extends AppCompatActivity {

    private EditText searchMainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        ImageView searchMainBack = (ImageView) findViewById(R.id.search_main_back);
        searchMainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout searchMainMySpot = (LinearLayout) findViewById(R.id.search_main_my_spot);
        searchMainMySpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : 현재 내 위치로 검색하는 옵션
            }
        });

        searchMainEditText = (EditText) findViewById(R.id.search_main_ET);
        Button searchMainSearchButton = (Button) findViewById(R.id.search_main_search_button);
        searchMainSearchButton.setOnClickListener(mainSearchButtonOnClickListener);

        ListView searchMainListView = (ListView) findViewById(R.id.search_main_recent);
        ArrayAdapter<String> searchMainAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list);
        searchMainListView.setAdapter(searchMainAdapter);
        initAdapter(searchMainAdapter);
        searchMainListView.setOnItemClickListener(listItemOnClickListener);

    }

    private final View.OnClickListener mainSearchButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String query = searchMainEditText.getText().toString();
            // TODO : query로 검색

            // Test code to save recent values : save 10 recent values
            SharedPreferences shPref = getSharedPreferences("spot_recent", MODE_PRIVATE);
            SharedPreferences.Editor editor = shPref.edit();
            int itemNum = shPref.getInt("num", 0); itemNum++;
            editor.putInt("num", itemNum);
            editor.putString(itemNum+"_name", query);
            editor.putString(itemNum+"_latitude", "38.0");
            editor.putString(itemNum+"_longitude", "137.0");
            if (itemNum>10) {
                itemNum-=10;
                editor.remove(itemNum+"_name");
                editor.remove(itemNum+"_latitude");
                editor.remove(itemNum+"_longitude");
            }
            editor.commit();

        }
    };

    private final AdapterView.OnItemClickListener listItemOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View v, int arg2, long arg3) {
            // TODO : recent 기록 잡아서 검색결과로 날아감
        }
    };

    private void initAdapter(ArrayAdapter<String> adapter) {
        SharedPreferences shPref = getSharedPreferences("spot_recent", MODE_PRIVATE);
        int itemNum = shPref.getInt("num", 0);
        for(int i=itemNum;i>=(itemNum<=10?1:itemNum-9);i--) {
            adapter.add(shPref.getString(i + "_name", " "));
        }
    }
}
