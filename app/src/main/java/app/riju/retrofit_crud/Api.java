package app.riju.retrofit_crud;

import app.riju.retrofit_crud.ModelResponse.FetchUserResponse;
import app.riju.retrofit_crud.ModelResponse.LoginResponse;
import app.riju.retrofit_crud.ModelResponse.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUserAccount(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<RegisterResponse> updateUserPassword(
            @Field("email") String email,
            @Field("current") String currentPass,
            @Field("new") String newPass
    );
    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<RegisterResponse> deleteAccount(
            @Field("id") int userId
    );


    @GET("fetchusers.php")
    Call<FetchUserResponse> fetchAllUsers();
}
