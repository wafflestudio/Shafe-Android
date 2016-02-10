package wafflestudio.beomsu.shafe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainMapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    public static String API_KEY = "ee2d2958a71eef9dbd48dd52ed638587";
    private MapView mapView;
    private MapPoint currentLocation = null;
    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(API_KEY);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.main_map_mapview);
        mapViewContainer.addView(mapView);
        FloatingActionButton mainMapFab = (FloatingActionButton) findViewById(R.id.main_map_FAB);
        mainMapFab.bringToFront();
        mainMapFab.setOnClickListener(locationSetOnClickListener);

        EditText mainMapFindET = (EditText) findViewById(R.id.main_map_find);
        mainMapFindET.setOnClickListener(findOnClickListener);

        ImageButton mainMapImageButton = (ImageButton) findViewById(R.id.main_map_list_button);
        mainMapImageButton.setOnClickListener(goToListOnClickListener);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint nowCurrentLocation, float accuracyInMeters) {
        currentLocation = nowCurrentLocation;
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    private final View.OnClickListener locationSetOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(currentLocation!=null) {
                mapView.setMapCenterPoint(currentLocation, true);
            }
            else {
                Toast.makeText(MainMapActivity.this, "내 위치 탐색중...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final View.OnClickListener findOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainMapActivity.this, SearchMainActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener goToListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainMapActivity.this, MainListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);
        mapView.setShowCurrentLocationMarker(true);

        // TODO : 처음 MainMap이 실행되었을 때 주변 카페 핀을 꼽는 작업
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        // TODO : map center가 움직였을 시에 타이머로 일정 시간을 재고, 멈추면 다시 핀을 꼽는 작업
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()<backKeyPressedTime+2000) {
            MainMapActivity.this.moveTaskToBack(true);
            MainMapActivity.this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        else {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(MainMapActivity.this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
