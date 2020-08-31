package com.eaglecabs.provider.data.network;

import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.data.models.CityResponse;
import com.eaglecabs.provider.data.models.UpcomingAcceptedTripsModel;
import com.eaglecabs.provider.data.network.model.AddressResponse;
import com.eaglecabs.provider.data.network.model.Document;
import com.eaglecabs.provider.data.network.model.EarningsList;
import com.eaglecabs.provider.data.network.model.ForgotResponse;
import com.eaglecabs.provider.data.network.model.Help;
import com.eaglecabs.provider.data.network.model.HistoryDetail;
import com.eaglecabs.provider.data.network.model.HistoryList;
import com.eaglecabs.provider.data.network.model.MyOTP;
import com.eaglecabs.provider.data.network.model.Notification;
import com.eaglecabs.provider.data.network.model.OTPResponse;
import com.eaglecabs.provider.data.network.model.Rating;
import com.eaglecabs.provider.data.network.model.ServicesModel;
import com.eaglecabs.provider.data.network.model.Status;
import com.eaglecabs.provider.data.network.model.Summary;
import com.eaglecabs.provider.data.network.model.Token;
import com.eaglecabs.provider.data.network.model.TripResponse;
import com.eaglecabs.provider.data.network.model.User;
import com.eaglecabs.provider.data.network.model.Vehicle;
import com.eaglecabs.provider.data.network.model.WalletResponse;
import com.eaglecabs.provider.data.scheduledrides.ScheduledReidesResponse;
import com.eaglecabs.provider.ui.activity.bankdetail.BankDetails;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Lenovo on 02-04-2018.
 */

public interface ApiInterface {

/*    @GET("worldD")
    Observable<PlacesModel> doPlaces(@Query(value = "type", encoded = true) String type, @Query(value = "location", encoded = true) String location, @Query(value = "keyword", encoded = true) String key);

    @FormUrlEncoded
    @POST("api/provider/oauth/token")
    Observable<Object> getAddress(@FieldMap HashMap<String, Object> params);*/


    @FormUrlEncoded
    @POST("api/provider/login")
    Observable<User> login(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/provider/auth/google")
    Observable<Token> loginGoogle(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/provider/auth/facebook")
    Observable<Token> loginFacebook(@FieldMap HashMap<String, Object> params);


    @GET("https://maps.googleapis.com/maps/api/geocode/json?components=country:in&sensor=true&key=AIzaSyCg1Hwub1dxL5-Nh7roJ-sMncjNT-LqC2o")
    Observable<Object> getPlaces();

    @GET("api/provider/services")
    Observable<List<ServicesModel>> getServices();

    @GET("api/provider/profile/documents")
    Observable<List<Document>> documents();


    @GET("api/user/getallcity")
    Observable<CityResponse> getAllCities();


    @GET("api/provider/driver/available_trips")
    Observable<List<ScheduledReidesResponse>> getAllScheduledRides();

    @Multipart
    @POST("api/provider/profile/documents")
    Observable<Object> documents(@Part List<MultipartBody.Part> file);

    @Multipart
    @POST("api/provider/register")
    Observable<User> register(@PartMap Map<String, RequestBody> params, @Part List<MultipartBody.Part> file);


    @FormUrlEncoded
    @POST("provider/check/mobile")
    Observable<Status> verifyMobileAlreadyExits(@Field("username") Object params);

    @GET("api/provider/profile")
    Observable<User> getProfile();

    @GET("api/provider/profile")
    Observable<User> getBank();

    @Multipart
    @POST("api/provider/profile")
    Observable<User> profileUpdate(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("api/provider/logout")
    Observable<Object> logout(@FieldMap HashMap<String, Object> params);


    @Multipart
    @POST("api/provider/add_bank_detail")
    Observable<Object> addUpdateBankDetail(@PartMap  HashMap<String, RequestBody> params, @Part MultipartBody.Part file);

    @GET("api/provider/trip")
    Observable<TripResponse> getTrip(@QueryMap HashMap<String, Object> params, @Query("manual_accept") int manual_accept);


    @GET("api/provider/trip")
    Observable<TripResponse> getTrip(@QueryMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/provider/profile/available")
    Observable<Object> providerAvailable(@FieldMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/provider/forgot/password")
    Observable<ForgotResponse> forgotPassword(@FieldMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/provider/reset/password")
    Observable<Object> resetPassword(@FieldMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/provider/profile/password")
    Observable<Object> changePassword(@FieldMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/provider/trip/{request_id}")
    Observable<Object> acceptRequest(@Field("dummy") String dummy, @Path("request_id") Integer request_id, @Query("arrival_time") Object arrivalTime, @Query("manual_accept") int manual_accept);


    @FormUrlEncoded
    @POST("api/provider/trip/{request_id}")
    Observable<Object> acceptRequestManual(@Field("dummy") String dummy, @Path("request_id") Integer request_id, @Query("arrival_time") Object arrivalTime, @Query("manual_accept") int manual_accept);

    @DELETE("api/provider/trip/{request_id}")
    Observable<Object> rejectRequest(@Path("request_id") Integer request_id);


    @DELETE("api/provider/trip/{request_id}")
    Observable<Object> cancelManual(@Path("request_id") Integer request_id);

    @FormUrlEncoded
    @POST("api/provider/cancel")
    Observable<Object> cancelRequest(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/provider/trip/{request_id}")
    Observable<Object> updateRequest(@FieldMap HashMap<String, Object> params, @Path("request_id") Integer request_id);


    @FormUrlEncoded
    @POST("api/provider/trip/{request_id}/rate")
    Observable<Rating> ratingRequest(@FieldMap HashMap<String, Object> params, @Path("request_id") Integer request_id);

    @GET("api/provider/requests/history")
    Observable<List<HistoryList>> getHistory();

    @GET("api/provider/notification")
    Observable<List<Notification>> notifications();


    @GET("api/provider/requests/history/details")
    Observable<HistoryDetail> getHistoryDetail(@Query("request_id") Object request_id);

    @GET("api/provider/requests/upcoming")
    Observable<List<HistoryList>> getUpcoming();


    @GET("api/provider/driver/upcommingtrip")
    Observable<List<UpcomingAcceptedTripsModel>> getAcceptedUpcoming();


    @GET("api/provider/requests/upcoming/details")
    Observable<HistoryDetail> getUpcomingDetail(@Query("request_id") Object request_id);

    @DELETE("api/provider/logout/{user_id}")
    Observable<Object> logout(@Path("user_id") Integer user_id);


    @GET("api/provider/target")
    Observable<EarningsList> getEarnings();

    @GET("api/provider/targetAll")
    Observable<EarningsList> getEarningsAll();

    @FormUrlEncoded
    @POST("api/provider/summary")
    Observable<Summary> getSummary(@Field("data") String data);

    @GET("api/provider/help")
    Observable<Help> getHelp();

    @Headers({"Content-Type: application/json", "Authorization: key=" + BuildConfig.FCM_SERRVER_KEY})
    @POST("fcm/send")
    Observable<Object> sendFcm(@Body JsonObject jsonObject);


    @FormUrlEncoded
    @POST("api/provider/send/otp")
    Observable<MyOTP> sendOtp(@Field("username") Object params);

    @FormUrlEncoded
    @POST("api/provider/verify/otp")
    Observable<User> verifyOTP(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/provider/voice/sms")
    Observable<MyOTP> sendVoiceOtp(@Field("username") Object params);


    @FormUrlEncoded
    @POST("api/provider/profile/location")
    Observable<Object> serverLocationUpdate(@Field("latitude") Object latitude, @Field("longitude") Object longidue);


    @FormUrlEncoded
    @POST("api/provider/trip/{trip_id}/calculate")
    Observable<Object> calculateDistance(@FieldMap HashMap<String, Object> params, @Path("trip_id") Integer trip_id);

    @FormUrlEncoded
    @POST("api/provider/instant-ride")
    Observable<OTPResponse> instantRideEstimateFare(@FieldMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/provider/chat/push")
    Observable<Object> chatPush(@FieldMap HashMap<String, Object> params);

    @GET("api/user/location")
    Observable<AddressResponse> address();


    @FormUrlEncoded
    @POST("api/provider/instant-ride/now")
    Observable<OTPResponse> instantRideSendRequest(@FieldMap HashMap<String, Object> params);

    @GET("api/provider/vehicle")
    Observable<List<Vehicle>> vehicles();

    @FormUrlEncoded
    @POST("api/provider/vehicle")
    Observable<OTPResponse> setVehicle(@Field("id") Object id);


    @GET("api/provider/wallet/passbooks")
    Observable<WalletResponse> walletHistory();
}
