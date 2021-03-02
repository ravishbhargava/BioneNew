package com.bione.network;


import com.bione.model.CommonResponse;
import com.bione.model.PaymentReceipt;
import com.bione.model.availableSlots.Slot;
import com.bione.model.counsellors.Counselling;
import com.bione.model.customerdata.SignInDatum;
import com.bione.model.customerkit.CustomerKit;
import com.bione.model.paymentreceiptlist.ReceiptList;
import com.bione.model.salesdetail.SalesDetail;
import com.bione.model.testNameList.TestNameList;
import com.bione.model.updateprofile.UpdateProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import com.bione.model.counsellors.Counselling;
import com.bione.model.customerkit.CustomerKit;


/**
 * Developer: Bione
 * <p>
 * The API interface for your application
 */
public interface ApiInterface {

    // todo add these params to your API key constant file and remove them from here

    String AUTHORIZATION = "authorization";
    String CONTENT_TYPE = "content-type";
    String CONTENT_LANG = "content-language";
    String LIMIT = "limit";
    String SKIP = "skip";

    //todo Declare your API endpoints here

    String GET_NOTIFICATIONS = "/driver/getNotifications";


    @POST("rest/V1/integration/admin/token")
    Call<String> adminToken(@QueryMap(encoded = true) Map<String, String> map);

    /**
     * Dummy sign in endpoint
     *
     * @param map the map of params to go along with reqquest
     * @return parsed common response object
     */

    @POST("/rest/V1/bioneapi-customersendotp/sendotp")
    Call<List<CommonResponse>> sendOtp(@QueryMap Map<String, String> map);

    @GET("/rest/V1/bioneapi-customersendotp/verifyotp")
    Call<List<SignInDatum>> verifyOtp(@QueryMap Map<String, String> map);

    @GET("/rest/V1/bione-salesapi/salesstatus")
    Call<List<SalesDetail>> isSalesPerson(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bione/signupapi")
    Call<List<CommonResponse>> sendOtpRegister(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bione/veryfyotpregister")
    Call<List<CommonResponse>> sendOtpVerify(@QueryMap Map<String, String> map);


    @POST("/rest/V1/bione/veryfyotp")
    Call<List<CommonResponse>> verifyMobile(@QueryMap Map<String, String> map);


    @GET("/rest/V1/customers/me")
    Call<UpdateProfile> getCustomerDetails(@HeaderMap HashMap<String, String> headerMap);

    @POST("rest/V1/integration/customer/token")
    Call<String> getCustomerToken(@QueryMap(encoded = true) Map<String, String> map);

    @POST("/rest/V1/bioneapi/sociallogin")
    Call<List<SignInDatum>> socialLogin(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bioneapi/updatefeedback")
    Call<List<CommonResponse>> updateFeedback(@QueryMap Map<String, String> map);

    @GET("/rest/V1/bioneapi/availability")
    Call<List<Slot>> availabilitySlots(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bioneapi/counsellor")
    Call<List<CommonResponse>> scheduleCall(@QueryMap Map<String, String> map);

    @GET("/rest/V1/bioneapi/counsellorlist")
    Call<List<Counselling>> getCounsellings(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bioneapi/cancelcouselling")
    Call<List<CommonResponse>> cancelBooking(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bioneapi/updateslot")
    Call<List<CommonResponse>> updateBooking(@QueryMap Map<String, String> map);

    @POST("/rest/V1/bioneapi/contact")
    Call<List<CommonResponse>> contactUs(@QueryMap Map<String, String> map);

    @GET("/rest/V1/bioneapi/kitorderlist")
    Call<List<CustomerKit>> kitOrders(@QueryMap Map<String, String> map);



    @GET("/mobilelogin/index/ajaxforgot/")
    Call<String> forgotSendOtp(@QueryMap Map<String, String> map);

    @GET("/mobilelogin/index/ajaxforgototpverify")
    Call<Boolean> forgotVerifyOtp(@QueryMap Map<String, String> map);

    @GET("/mobilelogin/index/ajaxupdatepassotp")
    Call<Boolean> forgotPasswordUpdate(@QueryMap Map<String, String> map);


    @PUT("/rest/V1/customers/password")
    Call<Boolean> emailReset(@QueryMap Map<String, String> map);

    @GET("/rest/V1/bioneproduct/productitems")
    Call<List<TestNameList>> testNames();


    /**
     * checkUnique call.
     *
     * @param headerMap  the header map
     * @param customerId the id of the customer
     * @param json       jsonobject
     * @return the call
     */
    @PUT("/rest/V1/customers/{customers}")
    Call<UpdateProfile> updateProfile2(@HeaderMap HashMap<String, String> headerMap,
                                       @Path("customers") String customerId,
                                       @Body RequestBody json);

    /**
     * checkUnique call.
     *
     * @param headerMap  the header map
     * @param customerId the id of the customer
     * @param json       jsonobject
     * @return the call
     */
    @POST("/rest//V1/customers")
    Call<UpdateProfile> createAccount(@Body RequestBody json);


    /**
     *
     * @param map
     * @return
     */
    @GET("/api/Receiptlist")
    Call<ReceiptList> paymentReceiptList(@QueryMap Map<String, String> map);



    /**
     * checkUnique call.
     *
     * @param headerMap  the header map
     * @param customerId the id of the customer
     * @param json       jsonobject
     * @return the call
     */
    @PUT("/rest/V1/customers/me/password")
    Call<Boolean> changePassword(@HeaderMap HashMap<String, String> headerMap,
                                 @QueryMap Map<String, String> map,
                                 @Body RequestBody json);

    /**
     * OTP verification
     *
     * @param map the map of params to go along with request
     * @return parsed common response object
     */
    @FormUrlEncoded
    @POST("/api/receiptapi")
    Call<PaymentReceipt> customerReceiptSubmit(@FieldMap Map<String, String> map);


    @POST("/rest/V1/bioneapi-customer/update")
    Call<List<SignInDatum>> updateProfile(@QueryMap Map<String, String> map);

    @GET("/api/unknown")
    Call<CommonResponse> doGetListResources();

    @GET("/data.json")
    Call<CommonResponse> getCoronaResults();

    /**
     * OTP verification
     *
     * @param map the map of params to go along with request
     * @return parsed common response object
     */
    @FormUrlEncoded
    @POST("/signIn")
    Call<CommonResponse> signIn(@FieldMap Map<String, String> map);

    /**
     * checkUnique call.
     *
     * @param headerMap  the header map
     * @param customerId the id of the customer
     * @param json       jsonobject
     * @return the call
     */
    @Multipart
    @PUT("/rest/V1/customers/{customers}")
    Call<List<SignInDatum>> updateProfile3(@HeaderMap HashMap<String, String> headerMap,
                                           @Path("customers") String customerId,
//                                           @Body String json);
                                           @PartMap HashMap<String, RequestBody> partMap);


//    @PartMap
//    HashMap<String, RequestBody> partMap);
//
//    /**
//     * * Fetch notifications data from server
//     *
//     * @param authorization auth
//     * @param contentlang   en
//     * @param limit         limit
//     * @param skip          skip
//     * @return parsed notifications response object
//     */
//    @GET(GET_NOTIFICATIONS)
//    Call<NotificationsResponse> getNotifications(@Header(AUTHORIZATION) String authorization,
//                                                 @Header(CONTENT_LANG) String contentlang,
//                                                 @Query(LIMIT) int limit,


//    @FormUrlEncoded
//    @POST("rest/V1/integration/admin/token")
//    Call<String> adminToken2(@FieldMap Map<String, String> map);
//
//
//    @POST
//    Call<String> adminToken3(@Query(PARAM_USERNAME) String username, @Query(PARAM_PASSWORD) String password, @Url String fullUrl);
//
//    @POST("rest/V1/integration/admin/token")
//    Call<String> adminToken4(@Query(value = PARAM_USERNAME) String username, @Query(PARAM_PASSWORD) String password);@Query(SKIP) int skip);

//  @Headers({"Content-Type: application/json; charset=utf-8"})
//                                                 @GET("/rest/V1/bioneapi/kitorderlist")
//                                                         Call<List<CustomerKit>> kitOrders(@QueryMap Map<String, String>map);
//
//
//    @GET("/rest/V1/bioneapi/counsellorlist")
//    Call<List<Counselling>> getCounsellings(@QueryMap Map<String, String> map);
}
