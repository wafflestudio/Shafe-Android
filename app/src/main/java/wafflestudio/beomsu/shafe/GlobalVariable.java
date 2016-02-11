package wafflestudio.beomsu.shafe;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by Beomsu on 2016-02-11.
 */
public class GlobalVariable extends Application {

    private int deviceHeightpx;

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        deviceHeightpx = dm.heightPixels;
    }

    public int getDeviceHeight() {
        return deviceHeightpx;
    }

}
