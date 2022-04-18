package tikedu.app.tikedu;

import android.app.Application;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;

import androidx.core.os.HandlerCompat;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TikEduApplication extends Application
{
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    ArrayList<Pair<Uri,Boolean>> postedVideos = new ArrayList<Pair<Uri, Boolean>>();


    private static TikEduApplication application = null;

    public void onCreate()
    {
        super.onCreate();
        application = this;
    }

    public SignRepository getSignRepository()
    {
        return SignRepository.getInstance(executorService, mainThreadHandler);
    }

    public ArrayList<Pair<Uri, Boolean>> getPostedVideos()
    {
        return postedVideos;
    }

    public void addPostedVideo(Uri uri, Boolean bool)
    {
        postedVideos.add(new Pair<Uri, Boolean>(uri, bool));
    }

    public static TikEduApplication getInstance()
    {
        return application;
    }
}
