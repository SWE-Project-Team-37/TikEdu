package tikedu.app.tikedu;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

public class SignRepository
{
    private final String serverIp = "192.168.0.78";
    private final Executor executor;
    private final Handler handler;
    private boolean isSigning = false;
    private int timeout = 5000; //5000 milliseconds = 5s
    private static SignRepository signRepository = null;

    private SignRepository(Executor executor, Handler handler)
    {
        this.executor = executor;
        this.handler = handler;
    }

    public static SignRepository getInstance(Executor executor, Handler handler)
    {
        if(signRepository == null)
        {
            signRepository = new SignRepository(executor, handler);
        }

        return signRepository;
    }

    public void makeSignUpRequest(final String username, final String password, final String usertype, final SignCallback callback)
    {
        if(!isSigning)
        {
            this.isSigning = true;
            executor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    Pair<Boolean, String> result = makeSignUpRequest_background(username, password, usertype);
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            callback.onComplete(result);
                        }
                    });
                }
            });
        }
    }

    public Pair<Boolean, String> makeSignUpRequest_background(String username, String password, String usertype)
    {
        try
        {
            //TODO: Get rid of Logging statements
            Log.d("Sign Up", "made it here 1");
            //create JSON string to send to server
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            jsonRequest.put("usertype", usertype);

            //send sign up request to server with JSON string
            URL url = new URL("http", serverIp, 8080, "signUp");
            Log.d("Sign Up", "made it here 1.2");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            Log.d("Sign Up", "made it here 1.3");
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);

            httpConnection.getOutputStream().write(jsonRequest.toString().getBytes(StandardCharsets.UTF_8));
            Log.d("Sign Up", "made it here 2");
            //decode response from server
            String signUpResponse = convertStreamToString(httpConnection.getInputStream());
            JSONObject jsonResponse = (JSONObject) new JSONParser().parse(signUpResponse);
            boolean signUpIsSuccessful = (boolean) jsonResponse.get("isSuccess");
            String userAccessToken = (String) jsonResponse.get("userAccessToken");
            String errorMessage = (String) jsonResponse.get("errorMessage");

            httpConnection.disconnect();

            Boolean isSuccessful = new Boolean(signUpIsSuccessful);

            if(signUpIsSuccessful)
            {
                UserRepository.createUser(userAccessToken);
            }

            this.isSigning = false;
            return new Pair<Boolean, String>(isSuccessful, errorMessage);
        } catch (Exception e)
        {
            Log.d("Sign Up", "made it here 3");
            Log.d("Sign Up", "", e);
            this.isSigning = false;
            return new Pair<Boolean, String>(Boolean.FALSE, "Something happened and sign up operation was not successful");
        }
    }

    private String convertStreamToString(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = inputStream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }

        return result.toString("UTF-8");
    }

}
