package wafflestudio.beomsu.shafe;

/**
 * Created by Beomsu on 2016-02-08.
 */
public class Singleton {
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {

    }
}
