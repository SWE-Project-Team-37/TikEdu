package tikedu.app.tikedu;

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
    private boolean isSigning = false;
    private static SignRepository signRepository = null;

    private SignRepository(Executor executor)
    {
        this.executor = executor;
    }

    public static SignRepository getInstance(Executor executor)
    {
        if(signRepository == null)
        {
            signRepository = new SignRepository(executor);
        }

        return signRepository;
    }

    public void makeSignUpRequest(String username, String password, String usertype, SignCallback callback)
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
                    //TODO: Calbback using handler
                    callback.onComplete(result);
                }
            });
        }
    }

    public Pair<Boolean, String> makeSignUpRequest_background(String username, String password, String usertype)
    {
        try
        {
            //create JSON string to send to server
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            jsonRequest.put("usertype", usertype);

            //send sign up request to server with JSON string
            URL url = new URL("https", serverIp, -1, "signUp");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(jsonRequest.toString().getBytes(StandardCharsets.UTF_8));

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
