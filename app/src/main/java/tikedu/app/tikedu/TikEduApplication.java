package tikedu.app.tikedu;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TikEduApplication extends Application
{
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static TikEduApplication application = null;

    public void onCreate()
    {
        super.onCreate();
        application = this;
    }

    public SignRepository getSignRepository()
    {
        return SignRepository.getInstance(executorService);
    }

    public static TikEduApplication getInstance()
    {
        return application;
    }
}
