package wafflestudio.beomsu.shafe;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class SearchMainActivity extends AppCompatActivity {

    private SlidingUpPanelLayout SUPLayout;
    private EditText searchMainEditText;
    private int currentSelectedFilter;
    private int[] filterIDList = {R.id.search_main_filter1, R.id.search_main_filter2, R.id.search_main_filter3, R.id.search_main_filter4,
            R.id.search_main_filter5, R.id.search_main_filter6, R.id.search_main_filter7, R.id.search_main_filter8};

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
        searchMainEditText.setInputType(0);
        searchMainEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) v;
                et.setInputType(1);
            }
        });
        Button searchMainSearchButton = (Button) findViewById(R.id.search_main_search_button);
        searchMainSearchButton.setOnClickListener(mainSearchButtonOnClickListener);

        ListView searchMainListView = (ListView) findViewById(R.id.search_main_recent);
        ArrayAdapter<String> searchMainAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list);
        searchMainListView.setAdapter(searchMainAdapter);
        initAdapter(searchMainAdapter);
        searchMainListView.setOnItemClickListener(listItemOnClickListener);

        SUPLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        SUPLayout.setPanelSlideListener(panelSlideListener);
        SUPLayout.setDragView(R.id.search_main_up_icon);

        ImageView searchMainFilter1 = (ImageView) findViewById(R.id.search_main_filter1);
        searchMainFilter1.setSelected(true);
        currentSelectedFilter = 1;

        for(int i=0;i<filterIDList.length;i++) {
            findViewById(filterIDList[i]).setOnClickListener(filterOnClickListener);
        }

    }

    private final View.OnClickListener mainSearchButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.e("hihi", v.isEnabled()+"");
            if (!v.isEnabled()) return;
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
            if (!v.isEnabled()) return;
            // TODO : recent 기록 잡아서 검색결과로 날아감
        }
    };

    private final View.OnClickListener filterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int nowID = v.getId();
            for(int i=0;i<filterIDList.length;i++) {
                findViewById(filterIDList[i]).setSelected(false);
                if (nowID == filterIDList[i]) currentSelectedFilter = i+1;
            }
            v.setSelected(true);
        }
    };

    private final PanelSlideListener panelSlideListener = new PanelSlideListener() {

        public void onPanelSlide(View panel, float slideOffset) {
        }

        public void onPanelCollapsed(View panel) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.search_main_main_view);
            recursiveClickManager(ll, true);
        }

        public void onPanelExpanded(View panel) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.search_main_main_view);
            recursiveClickManager(ll, false);
        }

        public void onPanelAnchored(View panel) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.search_main_main_view);
            recursiveClickManager(ll, false);
        }

        public void onPanelHidden(View panel) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.search_main_main_view);
            recursiveClickManager(ll, true);
        }

    };

    private void recursiveClickManager(ViewGroup v, boolean choice) {
        int num = v.getChildCount();
        v.setClickable(choice);
        v.setEnabled(choice);
        for(int i=0;i<num;i++) {
            View view = v.getChildAt(i);
            view.setClickable(choice);
            view.setEnabled(choice);
            if (view instanceof ViewGroup) {
                recursiveClickManager((ViewGroup) view, choice);
            }
            else if (view instanceof ListView) {
                ListView lv = (ListView) view;
                int listChildCount = lv.getChildCount();
                for(int j=0;j<listChildCount;j++) {
                    lv.getChildAt(j).setClickable(choice);
                    lv.getChildAt(j).setEnabled(choice);
                }
            }
        }
    }

    private void initAdapter(ArrayAdapter<String> adapter) {
        SharedPreferences shPref = getSharedPreferences("spot_recent", MODE_PRIVATE);
        int itemNum = shPref.getInt("num", 0);
        for(int i=itemNum;i>=(itemNum<=10?1:itemNum-9);i--) {
            adapter.add(shPref.getString(i + "_name", " "));
        }
    }

    @Override
    public void onBackPressed() {
        if (SUPLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED || SUPLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ) {
            SUPLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else {
            super.onBackPressed();
        }
    }
}
