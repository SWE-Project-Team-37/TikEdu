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
    private final String serverIp = "153.33.76.164";
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

    public void makeSignInRequest(final String username, final String password, final SignCallback callback)
    {
        if(!isSigning)
        {
            this.isSigning = true;
            executor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    Pair<Boolean, String> result = makeSignInRequest_background(username, password);
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
            Log.d("Sign Up", "Made it 1");
            //create JSON string to send to server
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            jsonRequest.put("usertype", usertype);

            //send sign up request to server with JSON string
            URL url = new URL("http", serverIp, 8080, "signUp");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Accept-Encoding", "identity");
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);
            Log.d("Sign Up", "Made it 2");
            httpConnection.getOutputStream().write(jsonRequest.toString().getBytes(StandardCharsets.UTF_8));
            Log.d("Sign Up", "Made it 3");
            //decode response from server
            String signUpResponse = convertStreamToString(httpConnection.getInputStream());
            Log.d("Sign Up", signUpResponse);
            org.json.simple.JSONObject jsonResponse = /*(JSONObject)*/ (org.json.simple.JSONObject) new JSONParser().parse(signUpResponse);
            boolean signUpIsSuccessful = (boolean) jsonResponse.get("signUpIsSuccessful");
            String userAccessToken = String.valueOf(jsonResponse.get("userAccessToken"));
            String errorMessage = (String) jsonResponse.get("errorMessage");

            httpConnection.disconnect();

            Boolean isSuccessful = new Boolean(signUpIsSuccessful);
            Log.d("Sign Up", "Made it 4");
            if(signUpIsSuccessful)
            {
                UserRepository.createUser(userAccessToken);
            }

            this.isSigning = false;
            return new Pair<Boolean, String>(isSuccessful, errorMessage);
        } catch (Exception e)
        {
            Log.d("Sign Up", "", e);
            this.isSigning = false;
            return new Pair<Boolean, String>(Boolean.FALSE, "Something happened and sign up operation was not successful");
        }
    }

    public Pair<Boolean, String> makeSignInRequest_background(String username, String password)
    {
        try
        {
            //create JSON string to send to server
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);

            //send sign up request to server with JSON string
            URL url = new URL("http", serverIp, 8080, "signIn");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Accept-Encoding", "identity");
            httpConnection.setDoOutput(true);
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);

            httpConnection.getOutputStream().write(jsonRequest.toString().getBytes(StandardCharsets.UTF_8));

            //decode response from server
            String signInResponse = convertStreamToString(httpConnection.getInputStream());
            Log.d("Sign Up", signInResponse);
            org.json.simple.JSONObject jsonResponse = (org.json.simple.JSONObject) new JSONParser().parse(signInResponse);
            boolean signInIsSuccessful = (boolean) jsonResponse.get("signInIsSuccessful");
            String userAccessToken = String.valueOf(jsonResponse.get("userAccessToken"));
            String errorMessage = (String) jsonResponse.get("errorMessage");

            httpConnection.disconnect();

            Boolean isSuccessful = new Boolean(signInIsSuccessful);

            if(signInIsSuccessful)
            {
                UserRepository.createUser(userAccessToken);
            }

            this.isSigning = false;
            return new Pair<Boolean, String>(isSuccessful, errorMessage);
        } catch (Exception e)
        {
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
