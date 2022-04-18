package tikedu.app.tikedu;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserClient {
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadVideo(
        //@Part("description") RequestBody description,
        @Part("userID") RequestBody userID, //Integer userID,
        @Part("classID")  RequestBody classID, //Integer classID,
        @Part MultipartBody.Part video
    );

    //@GET()
    //Call<ResponseBody> downloadVideo()
}
