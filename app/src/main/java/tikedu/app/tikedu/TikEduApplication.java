package tikedu.app.tikedu;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TikEduApplication extends Application
{
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

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

    public static TikEduApplication getInstance()
    {
        return application;
    }
}
