package com.cops.bloodbankclone.data.api;

import com.cops.bloodbankclone.data.model.contactUs.ContactUs;
import com.cops.bloodbankclone.data.model.donation.Donation;
import com.cops.bloodbankclone.data.model.donationDetails.DonationDetails;
import com.cops.bloodbankclone.data.model.generalResponce.GeneralResponce;
import com.cops.bloodbankclone.data.model.login.Login;
import com.cops.bloodbankclone.data.model.newPassword.NewPassword;
import com.cops.bloodbankclone.data.model.notification.Notification;
import com.cops.bloodbankclone.data.model.notificationSetting.NotificationSetting;
import com.cops.bloodbankclone.data.model.post.Post;
import com.cops.bloodbankclone.data.model.postDetails.PostDetails;
import com.cops.bloodbankclone.data.model.profile.Profile;
import com.cops.bloodbankclone.data.model.requestDonation.RequestDonation;
import com.cops.bloodbankclone.data.model.resetPassword.ResetPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("profile")
    @FormUrlEncoded
    Call<Profile> getProfile(@Field("api_token") String apiToken);


    @POST("profile")
    @FormUrlEncoded
    Call<Profile> setProfile(@Field("name") String name,
                             @Field("email") String email,
                             @Field("birth_date")String birthDate,
                             @Field("city_id") String cityId,
                             @Field("phone") String phone,
                             @Field("donation_last_date") String donationLastDate,
                             @Field("password") String password,
                             @Field("password_confirmation") String passwordConfirmation,
                             @Field("blood_type_id") String bloodTypeId,
                             @Field("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSetting> setNotificationsSetting(@Field("api_token") String apiToken,
                                                      @Field("governorates[]")List<Integer> governorates,
                                                      @Field("blood_types[]")List<Integer> bloodTypes);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSetting> getNotificationsSetting(@Field("api_token") String apiToken);



    @POST("signup")
    @FormUrlEncoded
    Call<Login> signup(@Field("name") String name,
                        @Field("email") String email,
                        @Field("birth_date")String birthDate,
                        @Field("city_id") String cityId,
                        @Field("phone") String phone,
                        @Field("donation_last_date") String donationLastDate,
                        @Field("password") String password,
                        @Field("password_confirmation") String passwordConfirmation,
                        @Field("blood_type_id") String bloodTypeId);


    @POST("login")
    @FormUrlEncoded
    Call<Login> login(@Field("phone") String phone,
                      @Field("password") String password);

    @POST("register-token")
    @FormUrlEncoded
    Call<Login> pushToken(@Field("token") String token,
                          @Field("api_token") String apiToken,
                          @Field("type") String type);

    @POST("remove-token")
    @FormUrlEncoded
    Call<Login> removeToken(@Field("token") String token,
                          @Field("api_token") String apiToken);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<Post> setFavourite(@Field("post_id") int postId,
                      @Field("api_token") String apiToken);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> resetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> newPassword(@Field("password") String password,
                                  @Field("password_confirmation") String passwordConfirmation ,
                                  @Field("pin_code") String pinCode,
                                  @Field("phone") String phone);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<RequestDonation> createDonation(@Field("api_token") String apiToken,
                                         @Field("patient_name") String patientName,
                                         @Field("patient_age") String patientAge,
                                         @Field("blood_type_id") String bloodTypeId,
                                         @Field("bags_num") String bagsNum,
                                         @Field("hospital_name") String hospitalName,
                                         @Field("hospital_address") String hospitalAddress,
                                         @Field("city_id") String cityId,
                                         @Field("phone") String phone,
                                         @Field("notes") String notes,
                                         @Field("latitude") String latitude,
                                         @Field("longitude") String longitude);

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> contactUs(@Field("api_token") String apiToken,
                              @Field("title") String title,
                              @Field("message") String message);


    @GET("governorates")
    Call<GeneralResponce> getGovernment();

    @GET("categories")
    Call<GeneralResponce> getCategories();


    @GET("cities")
    Call<GeneralResponce> getCity(@Query("governorate_id") int governorateId);

    @GET("blood-types")
    Call<GeneralResponce> getBloodType();

    @GET("donation-requests")
    Call<Donation> donationRequest(@Query("api_token") String apiToken,
                               @Query("page") int page);

    @GET("donation-requests")
    Call<Donation> donationRequest(@Query("api_token") String apiToken,
                                   @Query("blood_type_id") String bloodTypeId,
                                   @Query("city_id") String cityId,
                                   @Query("page") int page);

    @GET("donation-request")
    Call<DonationDetails> getDonationDetails(@Query("api_token") String apiToken,
                                             @Query("donation_id") int donationId);


    @GET("notifications")
    Call<Notification> getNotification(@Query("api_token") String apiToken,
                                       @Query("page") int page);

    @GET("posts")
    Call<Post> getPost(@Query("api_token") String apiToken,
                       @Query("page") int page);

    @GET("posts")
    Call<Post> getPostFilter(@Query("api_token") String apiToken,
                                     @Query("page") int page,
                                     @Query("keyword") String keyword,
                                     @Query("category_id") String categoryId);
    @GET("post")
    Call<PostDetails> getPostDetails(@Query("api_token") String apiToken,
                                     @Query("post_id") int postId,
                                     @Query("page") int page);

    @GET("my-favourites")
    Call<Post> getFavourite(@Query("api_token") String apiToken);



}
