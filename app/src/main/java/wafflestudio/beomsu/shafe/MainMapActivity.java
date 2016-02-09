package wafflestudio.beomsu.shafe;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;

public class MainMapActivity extends AppCompatActivity {

    public static String API_KEY = "ee2d2958a71eef9dbd48dd52ed638587";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(API_KEY);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.main_map_mapview);
        mapViewContainer.addView(mapView);
        FloatingActionButton mainMapFab = (FloatingActionButton) findViewById(R.id.main_map_FAB);
        mainMapFab.bringToFront();

        

    }
}
