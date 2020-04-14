package com.invisee.finvest.data.api;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.invisee.finvest.data.api.beans.Bank;
import com.invisee.finvest.data.api.beans.Branch;
import com.invisee.finvest.data.api.beans.City;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.beans.FatcaQuestion;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.KycLookup;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioChartData;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.beans.RiskProfileQuestion;
import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.beans.State;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.data.api.requests.AmountSummaryRequest;
import com.invisee.finvest.data.api.requests.AndroidVersionRequest;
import com.invisee.finvest.data.api.requests.CheckPinRequest;
import com.invisee.finvest.data.api.requests.ConfirmCheckPinRequest;
import com.invisee.finvest.data.api.requests.ConfirmationPartialRedemptionRequest;
import com.invisee.finvest.data.api.requests.ConfirmationRedemptionRequest;
import com.invisee.finvest.data.api.requests.CreatePartialRedemptionRequest;
import com.invisee.finvest.data.api.requests.CreatePartialRequest;
import com.invisee.finvest.data.api.requests.CreateRedemptionRequest;
import com.invisee.finvest.data.api.requests.DeleteReminderRequest;
import com.invisee.finvest.data.api.requests.DetailWaitingTopUpRequest;
import com.invisee.finvest.data.api.requests.InvestmentPerformanceRequest;
import com.invisee.finvest.data.api.requests.JoinPromoRequest;
import com.invisee.finvest.data.api.requests.ListRequest;
import com.invisee.finvest.data.api.requests.PackagePerformanceRequest;
import com.invisee.finvest.data.api.requests.PayWalletRequest;
import com.invisee.finvest.data.api.requests.PaymentPayProRequest;
import com.invisee.finvest.data.api.requests.PieChartRequest;
import com.invisee.finvest.data.api.requests.PromoDetailRequest;
import com.invisee.finvest.data.api.requests.RetryPayProRequest;
import com.invisee.finvest.data.api.requests.SaveFatcaRequest;
import com.invisee.finvest.data.api.requests.SaveReminderRequest;
import com.invisee.finvest.data.api.requests.TopUpViseePayRequest;
import com.invisee.finvest.data.api.requests.TransactionTransferRequest;
import com.invisee.finvest.data.api.requests.WalletRequest;
import com.invisee.finvest.data.api.responses.AmountSummaryResponse;
import com.invisee.finvest.data.api.responses.AndroidVersionResponse;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.CheckRedemptionResponse;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.ConfirmCheckPinResponse;
import com.invisee.finvest.data.api.responses.ConfirmationRedemptionResponse;
import com.invisee.finvest.data.api.responses.CreateRedemptionResponse;
import com.invisee.finvest.data.api.responses.CustomerDocumentResponse;
import com.invisee.finvest.data.api.responses.CustomerDocumentSelfieResponse;
import com.invisee.finvest.data.api.responses.CustomerDocumentSettlementResponse;
import com.invisee.finvest.data.api.responses.CustomerDocumentSignatureResponse;
import com.invisee.finvest.data.api.responses.DocTransactionResponse;
import com.invisee.finvest.data.api.responses.FaqResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.api.responses.GeneratePINResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.InvestmentAccountResponse;
import com.invisee.finvest.data.api.responses.InvestmentPerformanceResponse;
import com.invisee.finvest.data.api.responses.LoadKycDataResponse;
import com.invisee.finvest.data.api.responses.LoginResponse;
import com.invisee.finvest.data.api.responses.MaxScoreResponse;
import com.invisee.finvest.data.api.responses.NewsFeedResponse;
import com.invisee.finvest.data.api.responses.PackageByTokenResponse;
import com.invisee.finvest.data.api.responses.PackageResponse;
import com.invisee.finvest.data.api.responses.PackagesPerformanceResponse;
import com.invisee.finvest.data.api.responses.PartialRedemtionResponse;
import com.invisee.finvest.data.api.responses.PaymentMethodResponse;
import com.invisee.finvest.data.api.responses.PendingOrderDetailResponse;
import com.invisee.finvest.data.api.responses.PieChartResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.data.api.responses.ProductResponse;
import com.invisee.finvest.data.api.responses.PromoDetailResponse;
import com.invisee.finvest.data.api.responses.PromoListResponse;
import com.invisee.finvest.data.api.responses.RedeemFeeResponse;
import com.invisee.finvest.data.api.responses.RedemptionOrderResponse;
import com.invisee.finvest.data.api.responses.ReferralResponse;
import com.invisee.finvest.data.api.responses.ReminderDetailResponse;
import com.invisee.finvest.data.api.responses.RiskProfileResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.api.responses.SliderResponse;
import com.invisee.finvest.data.api.responses.StatusCustomerResponse;
import com.invisee.finvest.data.api.responses.TopUpFinPayResponse;
import com.invisee.finvest.data.api.responses.TopUpViseePayResponse;
import com.invisee.finvest.data.api.responses.TransactionTransferResponse;
import com.invisee.finvest.data.api.responses.UploadResponse;
import com.invisee.finvest.data.api.responses.UserInfoResponse;
import com.invisee.finvest.data.api.responses.WalletBalanceResponse;
import com.invisee.finvest.data.api.responses.WalletHistoryResponse;
import com.invisee.finvest.ui.receiver.Tls12SocketFactory;
import com.invisee.finvest.util.Constant;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fajarfatur on 2/1/16.
 */
public class InviseeService {

    private Api api;

    /*Development*/
//    public final static String BASE_URL = "http://54.169.154.234:8080/avantrade-portal-core/";
//    public final static String BASE_URL = "http://13.250.182.89:8080/avantrade-portal-core/";
//    public final static String BASE_URL = "http://52.76.229.157:8080/avantrade-portal-core/";

    /*Production*/
    public final static String BASE_URL = "https://orec.invisee.com/";

    public final static String PORTAL_URL = "attachFile/download?id=";
    public final static String IMAGE_DOWNLOAD_URL = BASE_URL + "attachFile/download?id=";
    public final static String ASSETS_DOWNLOAD_URL = BASE_URL +  "attachFile/download?";
    public final static String IMAGE_CUSTOMER_DOWNLOAD_URL = BASE_URL + "customerDocument/download?id=";
    private final static String MOBILE_URL = "mobile-invisee/service/";
    public final static String UPLOAD = BASE_URL + "customerDocument/uploadByCustomer";
    public final static String Download = BASE_URL + "customerDocument/download";

    public InviseeService(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(provideOkHttpClient(context))
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(Api.class);
    }

    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder httpClient) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                httpClient.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                httpClient.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return httpClient;
    }

    private OkHttpClient provideOkHttpClient(final Context context) {
        HttpLoggingInterceptor httpLoggingInterceptorinterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptorinterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(500, TimeUnit.SECONDS);
        httpClient.connectTimeout(500, TimeUnit.SECONDS);
        httpClient.addInterceptor(httpLoggingInterceptorinterceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                invalidTokenHandler(response, context);
                return response;
            }
        });
        return enableTls12OnPreLollipop(httpClient).build();
    }

    private void invalidTokenHandler(Response response, Context context) throws IOException {
        String responseBody = response.peekBody(100000L).string();
        try {
            Object json = new JSONTokener(responseBody).nextValue();
            if (json instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(responseBody);
                if (jsonObject.has("code") && jsonObject.getInt("code") == Constant.INVALID_TOKEN) {
                    Intent intent = new Intent("Invalid");
                    context.sendBroadcast(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Api getApi() {
        return api;
    }

    public interface Api {

        @POST("question/loadSecurityQuestion")
        Observable<List<SecurityQuestion>> loadSecurityQuestion();

        @POST("customersApi/registerCustomer")
        Observable<GenericResponse> regsiterCustomer(@Query("security_answer") String answerNote,
                                                     @Query("email") String email,
                                                     @Query("first_name") String firstName,
                                                     @Query("phone_number") String homePhoneNumber,
                                                     @Query("last_name") String lastName,
                                                     @Query("middle_name") String middleName,
                                                     @Query("mobile_number") String mobileNumber,
                                                     @Query("password") String password,
                                                     @Query("confirm_password") String confirmPassword,
                                                     @Query("security_question") String question,
                                                     @Query("agent") String agent);

        @POST("customersApi/loginEmailPassword")
        Observable<LoginResponse> login(@Query("email") String email,
                                        @Query("password") String password);

        @POST("customersApi/urgentForgotPassword")
        Observable<GenericResponse> urgentForgotPassword(@Query("email") String email);


        @POST("customersApi/forgotPassword")
        Observable<GenericResponse> forgotPassword(@Query("email") String email,
                                                   @Query("answer") String answer,
                                                   @Query("question") String question);

        @POST("customersApi/doResetPassword")
        Observable<GenericResponse> resetPassword(@Query("email") String email,
                                                  @Query("confPassword") String confPassword,
                                                  @Query("resetCode") String resetCode);

        @POST("customersApi/generateResetCode")
        Observable<GenericResponse> resendCode(@Query("email") String email,
                                               @Query("securityAnswer") String securityAnswer,
                                               @Query("securityQuestion") String securityQuestion);

        @POST("customersApi/changeNewPassword")
        Observable<GenericResponse> changePassword(@Query("email") String email,
                                                   @Query("confPassword") String confPassword,
                                                   @Query("newPassword") String newPassword,
                                                   @Query("oldPassword") String oldPassword,
                                                   @Query("token") String token);

        @POST("customersApi/inputSecurityActivationCode")
        Observable<LoginResponse> activateUserWithActivationCode(@Query("email") String email,
                                                                 @Query("reset_code") String activationCode,
                                                                 @Query("token") String token);

        @POST("customersApi/resendCode")
        Observable<GenericResponse> resendActivationCode(@Query("email") String email);

        @POST("kycApi/load")
        Observable<LoadKycDataResponse> loadKycData(@Query("email") String email,
                                                    @Query("token") String token);

        @POST("KycApi/saveKyc")
        Observable<GenericResponse> saveKycData(@Query("salutation") String salutation,
                                                @Query("firstName") String firstName,
                                                @Query("middleName") String middleName,
                                                @Query("lastName") String lastName,
                                                @Query("email") String email,
                                                @Query("birthDate") String birthDate,
                                                @Query("birthPlace") String birthPlace,
                                                @Query("occupation") String occupation,
                                                @Query("natureOfBusiness") String natureOfBusiness,
                                                @Query("employerName") String employerName,
                                                @Query("homeCountry") String homeCountry,
                                                @Query("homeProvince") String homeProvince,
                                                @Query("homeCity") String homeCity,
                                                @Query("homeAddress") String homeAddress,
                                                @Query("homePostalCode") String homePostalCode,
                                                @Query("homePhoneNumber") String homePhoneNumber,
                                                @Query("legalCountry") String legalCountry,
                                                @Query("legalProvince") String legalProvince,
                                                @Query("legalCity") String legalCity,
                                                @Query("legalAddress") String legalAddress,
                                                @Query("legalPostalCode") String legalPostalCode,
                                                @Query("legalPhoneNumber") String legalPhoneNumber,
                                                @Query("idType") String idType,
                                                @Query("idNumber") String idNumber,
                                                @Query("idExpirationDate") String idExpirationDate,
                                                @Query("nationality") String nationality,
                                                @Query("citizenship") String citizenship,
                                                @Query("gender") String gender,
                                                @Query("religion") String religion,
                                                @Query("maritalStatus") String maritalStatus,
                                                @Query("educationBackground") String educationBackground,
                                                @Query("sourceOfIncome") String sourceOfIncome,
                                                @Query("totalIncomePa") String totalIncomePa,
                                                @Query("totalAsset") String totalAsset,
                                                @Query("investmentExperience") String investmentExperience,
                                                @Query("investmentPurpose") String investmentPurpose,
                                                @Query("otherInvestmentExperience") String otherInvestmentExperience,
                                                @Query("pepName") String pepName,
                                               /* @Query("pepCountry") String pepCountry,*/
                                                @Query("pepPosition") String pepPosition,
                                                @Query("pepPublicFunction") String pepPublicFunction,
                                                @Query("pepYearOfService") String pepYearOfService,
                                                @Query("pepRelationship") String pepRelationship,
                                                @Query("motherMaidenName") String motherMaidenName,
                                                @Query("beneficiaryName") String beneficiaryName,
                                                @Query("beneficiaryRelationship") String beneficiaryRelationship,
                                                @Query("preferredMailingAddress") String preferredMailingAddress,
                                                @Query("taxId") String taxId,
//                                                @Query("taxIdRegisDate") String taxIdRegisDate,
                                                @Query("token") String token,
                                                @Query("referral") String referral,
                                                @Query("referralName") String referralName,
                                                @Query("spouseName") String spouseName,
                                                @Query("bankId") String bankId,
                                                @Query("branchId") String branchId ,
                                                @Query("settlementAccountName") String settlementAccountName,
                                                @Query("settlementAccountNo") String settlementAccountNo);

        @POST("KycApi/saveOrUpdateSettlementInformation")
        Observable<GenericResponse> saveOrUpdateSettlementInformation(@Query("bankId.id") String bankId,
                                                                      @Query("branchId.id") String branchId,
                                                                      @Query("email") String email,
                                                                      @Query("settlementAccountName") String settlementAccountName,
                                                                      @Query("settlementAccountNo") String settlementAccountNo,
                                                                      @Query("token") String token);

        @POST("lookup/getAllLookup")
        Observable<List<KycLookup>> getKycLookup(@Query("token") String token);

        @POST("country/getAllCountry")
        Observable<List<Country>> getAllCountry(@Query("token") String token);

        @POST("state/getStateByCountry")
        Observable<List<State>> getStateByCountry(@Query("countryId") String countryId,
                                                  @Query("token") String token);

        @POST("city/getCityByState")
        Observable<List<City>> getCityByState(@Query("state") String state,
                                              @Query("token") String token);

        @POST("kycApi/loadFatchaQuestionAndAnswer")
        Observable<ArrayList<FatcaQuestion>> loadFatcaQuestionAndAnswer(@Query("token") String token);

        @POST("kycApi/loadRiskProfileQuestionAndAnswer")
        Observable<ArrayList<RiskProfileQuestion>> loadRiskProfileQuestionAndAnswer(@Query("token") String token);

        @POST("kycApi/saveOrUpdateCustomerAnswer")
        Observable<GenericResponse> saveCunstomerAnswer(@Query("batch") String batch,
                                                        @Query("questionaireId") String questionaireId,
                                                        @Query("token") String token);

        @POST("kycApi/saveOrUpdateCustomerAnswer")
        Observable<GenericResponse> saveFatca(@Query("batch") String batch,
                                              @Query("questionaireId") String questionaireId,
                                              @Query("token") String token,
                                              @Body SaveFatcaRequest request);


        @POST("kycApi/loadSettlementInformation")
        Observable<SettlementInfoResponse> loadSettlementInformation(@Query("email") String email,
                                                                     @Query("token") String token);

        @POST("kycApi/getRiskProfileResult")
        Observable<RiskProfileResponse> getRiskProfileResult(@Query("token") String token);

        @POST("bank/getAll")
        Observable<List<Bank>> getAllBank(@Query("max") String max,
                                          @Query("offset") String offset,
                                          @Query("token") String token);

        @POST("branch/getListBranchByBankCode")
        Observable<List<Branch>> getListBranchByBankCode(@Query("bankId") String bankId,
                                                         @Query("token") String token);

        @POST("customersApi/completenessPercentage")
        Observable<CompletenessPercentageResponse> getCompletenessPercentage(@Query("token") String token);


        @POST("userApi/pendingCustomer")
        Observable<GenericResponse> postPendingCustomer(@Query("token") String token);

        @POST("fundPackagesApi/fundPackagesMobile")
        Observable<List<Packages>> packageList(@Query("token") String token);

        @GET("fundPackages/getProductLists")
        Observable<ProductResponse> getProductList(@Query("token") String token); //ditambahin token untuk ITB demo

        @GET("fundPackages/getPackageById")
        Observable<PackageResponse> packageDetail(@Query("id") Long id,
                                                  @Query("token") String token);

        @GET("fundPackages/getMaxScore")
        Observable<MaxScoreResponse> getMaxScore(@Query("id") Long id,
                                                 @Query("token") String token);

        @POST("fundPackagesApi/fundAllocationInfo")
        Observable<FundAllocationResponse> fundAllocationInfo(@Query("packageId") Long id,
                                                              @Query("token") String token);


        @POST("performance/getPackagePerformance")
        Observable<PackagesPerformanceResponse> packagePerformanceInfo(@Body PackagePerformanceRequest request);

        @GET("fundPackageFeeSetup/getSubscriptionFee")
        Observable<List<Fee>> subscriptionFee(@Query("fp") Long packageId,
                                              @Query("type") Long type,
                                              @Query("token") String token);

        @GET("fundPackageFeeSetup/getRedemptionFee")
        Observable<List<Fee>> redemptionFee(@Query("fp") Long packageId,
                                            @Query("type") Long type,
                                            @Query("token") String token);

        @GET("redemption/redemptionOrderDetails")
        Observable<RedemptionOrderResponse> redemptionOrderDetails(@Query("investmentAccountNo") String investmentAccountNo,
                                                                   @Query("token") String token);

        @GET("redemption/checkRedemptionTransaction")
        Observable<CheckRedemptionResponse> checkRedemptionTransaction(@Query("investmentAccountNo") String investmentAccountNo,
                                                                       @Query("token") String token);

        @GET("trxApi/createRedemption")
        Observable<CreateRedemptionResponse> createRedemption(@Query("index") String index,
                                                              @Query("investId") String investId,
                                                              @Query("pin") String pin,
                                                              @Query("token") String token);

        //new
//        @POST("trxApi/createRedemption")
//        Observable<CreateRedemptionResponse> createRedemption(@Query("token") String token,
//                                                              @Body CreateRedemptionRequest request);

        @GET("trxApi/confirmTransRedemption")
        Observable<ConfirmationRedemptionResponse> confirmTransRedemption(@Query("confirm") String confirm,
                                                                          @Query("pin") String pin,
                                                                          @Query("index") String index,
                                                                          @Query("investId") String investId,
                                                                          @Query("token") String token);

        //new
//        @POST("trxApi/confirmTransRedemption")
//        Observable<ConfirmationRedemptionResponse> confirmTransRedemption(@Query("token") String token,
//                                                                          @Body ConfirmationRedemptionRequest request);

        @GET("trxApi/getDetailPendingOrderByOrderNumber")
        Observable<PendingOrderDetailResponse> getDetailPendingOrder(@Query("investment_number") String invesmentNumber,
                                                                     @Query("orderNumber") String orderNumber,
                                                                     @Query("token") String token);


        //INVESTMENT LIST PORTOFOLIO
        @POST("customersApi/getInvestmentList")
        Observable<PortfolioInvestmentListResponse> getInvestmentList(@Query("token") String token);

        @POST("performance/getInvestmentPerformance")
        Observable<InvestmentPerformanceResponse> getInvestmentPerformance(@Query("token") String token,
                                                                           @Body InvestmentPerformanceRequest request);

        @POST("customersApi/getInvestmentSummary")
        Observable<PortfolioInvestmentSummaryResponse> getInvestmentSummary(@Query("token") String token);

        @POST("trxApi/createTrxCart")
        Observable<GenericResponse> createTrxCart(@Query("amount") String amount,
                                                  @Query("investAccountNo") String investAccountNo,
                                                  @Query("packageCode") String packageCode,
                                                  @Query("trxType") String trxType,
                                                  @Query("token") String token);

        @GET("redemption/topup_validation")
        Observable<GenericResponse> topUpValidation(@Query("investmentAccountNo") String investAccountNo,
                                                    @Query("token") String token);


        @GET("trxApi/checkTrxCartByInvestmentNumber")
        Observable<GenericResponse> checkTrxCartByInvestmentNumber(@Query("investmentAccountNo") String investAccountNo,
                                                                   @Query("token") String token);



        @POST("trxApi/cartList")
        Observable<List<CartListResponse>> getCartList(@Query("token") String token);

        @POST("trxApi/deleteCart")
        Observable<GenericResponse> deleteCart(@Query("id") String id,
                                               @Query("token") String token);

        @GET("customerDocument/getCustomerDocument")
        Observable<CustomerDocumentResponse> getDocumentDoc01(@Query("code") String id,
                                                              @Query("token") String token);


        @GET("customerDocument/getCustomerDocument")
        Observable<CustomerDocumentSignatureResponse> getDocumentDoc03(@Query("code") String id,
                                                                       @Query("token") String token);

        @GET("customerDocument/getCustomerDocument")
        Observable<CustomerDocumentSettlementResponse> getDocumentDoc04(@Query("code") String id,
                                                                        @Query("token") String token);


        @GET("customerDocument/getCustomerDocument")
        Observable<CustomerDocumentSelfieResponse> getDocumentDoc05(@Query("code") String id,
                                                                    @Query("token") String token); //selfie

        @GET("customerDocument/getDocTransactionByOrderNo")
        Observable<DocTransactionResponse> getDocTransaction(@Query("orderno") String orderno,
                                                             @Query("token") String token);

        @POST("paymentTransaction/checkPin")
        Observable<AmountSummaryResponse> checkPin(@Query("token") String token,
                                             @Body CheckPinRequest request);

        @POST("paymentTransaction/confirmCheckPin")
        Observable<ConfirmCheckPinResponse> confirmCheckPin(@Query("token") String token,
                                                            @Body ConfirmCheckPinRequest request);

        @POST("paymentTransaction/confirmCheckPin")
        Observable<AmountSummaryResponse> confirmCheckPinPaypro(@Query("token") String token,
                                                            @Body ConfirmCheckPinRequest request);


        @POST("paymentTransaction/transactionTransfer")
        Observable<TransactionTransferResponse> transactionTransfer(@Query("token") String token,
                                                                    @Body TransactionTransferRequest request);

        @POST("paymentTransaction/payWallet")
        Observable<GenericResponse> payWallet(@Query("token") String token,
                                              @Body PayWalletRequest request);

        @POST("paymentTransaction/amountSummaryWallet")
        Observable<AmountSummaryResponse> amountSummaryWallet(@Query("token") String token,
                                                              @Body AmountSummaryRequest request);


        @POST("trxApi/pieChart")
        Observable<List<PortfolioChartData>> getPieChart(@Query("token") String token);

        @POST("customersApi/getPieChartDashboard")
        Observable<PieChartResponse> getPieChartDashboard(@Query("token") String token,
                                                          @Body PieChartRequest request);

        @POST("trxApi/transactionHistory")
        Observable<List<TransactionHistory>> getPendingTransaction(@Query("token") String token,
                                                                   @Query("trxStatus") String trxStatus,
                                                                   @Query("max") String max);

        @POST("trxApi/transactionHistory")
        Observable<List<TransactionHistory>> getTransactionHistory(@Query("token") String token,
                                                                   @Query("max") String max,
                                                                   @Query("trxType") String trxStatus);

        @POST(MOBILE_URL + "investment/list")
        Observable<GenericResponse> investmentList(@Header("token") String token,
                                                   @Body ListRequest request);


        //NEWS LIST
        @POST("News/getAllNews")
        Observable<NewsFeedResponse> getAllNews(@Query("token") String token);

        @POST(MOBILE_URL + "news/load")
        Observable<GenericResponse> newsList(@Body PackagePerformanceRequest request);


        @FormUrlEncoded
        @POST("paymentTransaction/transactionWalletBalance")
        Observable<WalletBalanceResponse> requestWalletBalance(@Field("token") String token);

        @POST("paymentTransaction/transactionWalletHistory")
        Observable<WalletHistoryResponse> requestWalletHistory(@Query("token") String token,
                                                               @Body WalletRequest request);

        @FormUrlEncoded
        @POST("reminder/list")
        Observable<List<Reminder>> reminderList(@Field("token") String token);

        @POST("reminder/delete")
        Observable<GenericResponse> deleteReminder(@Query("token") String token,
                                                   @Body DeleteReminderRequest request);


        @FormUrlEncoded
        @POST("reminder/viewPackageName")
        Observable<PackageByTokenResponse> viewPackageName(@Field("token") String token);


        @POST("reminder/viewInvestementAccount")
        Observable<InvestmentAccountResponse> viewInvestementAccount(@Query("fundPackage") String fundPackage,
                                                                     @Query("token") String token);

        @FormUrlEncoded
        @POST("reminder/viewInvestementAccount")
        Observable<InvestmentAccountResponse> viewInvestementAccountrEeminder(@Field("token") String fundPackage,
                                                                              @Field("fundPackage") String token);

        @POST("reminder/saveOrUpdate")
        Observable<GenericResponse> saveOrUpdateReminder(@Query("token") String token,
                                                         @Body SaveReminderRequest request);

        @POST("reminder/getById")
        Observable<ReminderDetailResponse> getReimderDetail(@Query("id") String id,
                                                            @Query("token") String token);

        @FormUrlEncoded
        @POST("reminder/getById")
        Observable<GenericResponse> getReminderById(@Field("token") String token,
                                                    @Field("endDate") String endDate,
                                                    @Field("id") String id,
                                                    @Field("packagename") String packageName,
                                                    @Field("investNumber") String investNumber,
                                                    @Field("reminderAmount") String reminderAmount,
                                                    @Field("reminderIType") String reminderIType,
                                                    @Field("reminderTime") String reminderTime,
                                                    @Field("startdate") String startdate);

        @GET(ASSETS_DOWNLOAD_URL)
        Observable<ResponseBody> downloadAsset(@Query("id") String id);


        @FormUrlEncoded
        @POST("FAQ/getAllFAQ")
        Observable<FaqResponse> getFaq(@Field("token") String token);


        @FormUrlEncoded
        @POST("customersApi/generalInfo")
        Observable<UserInfoResponse> userInfo(@Field("token") String token);

        @FormUrlEncoded
        @POST("customersApi/saveGeneralInfo")
        Observable<GenericResponse> saveUserInfo(@Field("answerNote") String answerNote,
                                                 @Field("email") String email,
                                                 @Field("firstName") String firstName,
                                                 @Field("homePhoneNumber") String homePhoneNumber,
                                                 @Field("imageKey") String imageKey,
                                                 @Field("lastName") String lastName,
                                                 @Field("middleName") String middleName,
                                                 @Field("mobileNumber") String mobileNumber,
                                                 @Field("question") String question,
                                                 @Field("token") String token);

        @Multipart
        @POST("attachFile/upload")
        Observable<UploadResponse> uploadPhoto(@Query("token") String token,
                                               @Part MultipartBody.Part file);
        @Multipart
        @POST("customerDocument/uploadByCustomer")
        Observable<GenericResponse> uploadByCustomerDoc1(@Query("token") String token,
                                                         @Query("type") String type,
                                                         @Part MultipartBody.Part body);


        @Multipart
        @POST("customerDocument/uploadByCustomerMobile")
        Observable<GenericResponse> uploadByCustomerVerDoc1(@Query("token") String token,
                                                         @Query("type") String type,
                                                         @Part MultipartBody.Part body);

        //new
        @Multipart
        @POST("customerDocument/uploadByCustomerMobile")
        Observable<GenericResponse> uploadByCustomerDoc5(@Query("token") String token,
                                                         @Query("type") String type,
                                                         @Part MultipartBody.Part body);

        @Multipart
        @POST("customerDocument/uploadByCustomerMobile")
        Observable<GenericResponse> uploadByCustomerVerDoc5(@Query("token") String token,
                                                         @Query("type") String type,
                                                         @Part MultipartBody.Part body);
        //end
        @Multipart
        @POST("customerDocument/uploadTransactionByCustomer")
        Observable<GenericResponse> uploadTransaction(@Query("orderno") String orderno,
                                                      @Query("token") String token,
                                                      @Query("type") String type,
                                                      @Part MultipartBody.Part body);



        @GET("customersApi/generatePin")
        Observable<GeneratePINResponse> generatePin(@Query("token") String token);


        @GET("userApi/signout")
        Observable<GenericResponse> logout(@Query("token") String token);

        @GET("redemption/rangeOfPartial")
        Observable<PartialRedemtionResponse> rangeOfPartialRedemtion(@Query("id") String idProduct,
                                                                     @Query("token") String token);

        @GET("redemption/getRedemptionFee")
        Observable<RedeemFeeResponse> redemptionFee(@Query("invNo") String invNo,
                                                    @Query("token") String token);

        @GET("redemption/partialRedemptionOrderDetails")
        Observable<RedemptionOrderResponse> partialRedemptionOrderDetails(@Query("investmentAccountNo") String investmentAccountNo,
                                                                          @Query("percentage") String percentage,
                                                                          @Query("token") String token);

        @GET("trxApi/createPartialRedemption")
        Observable<CreateRedemptionResponse> createPartialRedemption(@Query("index") String index,
                                                              @Query("investId") String investId,
                                                              @Query("percentage") String percentage,
                                                              @Query("pin") String pin,
                                                              @Query("token") String token);

        //new
//        @POST("trxApi/createPartialRedemption")
//        Observable<CreateRedemptionResponse> createPartialRedemption(@Query("token") String token,
//                                                                     @Body CreatePartialRedemptionRequest request);

        @GET("trxApi/confirmTransPartialRedemption")
        Observable<ConfirmationRedemptionResponse> confirmTransPartialRedemption(@Query("confirm") String confirm,
                                                                          @Query("pin") String pin,
                                                                          @Query("index") String index,
                                                                          @Query("investId") String investId,
                                                                          @Query("percentage") String percentage,
                                                                          @Query("token") String token);

        //new
//        @POST("trxApi/confirmTransPartialRedemption")
//        Observable<ConfirmationRedemptionResponse> confirmTransPartialRedemption(@Query("token") String token,
//                                                                                 @Body ConfirmationPartialRedemptionRequest request);


        @POST("version/android")
        Observable<AndroidVersionResponse> checkVersion(@Body AndroidVersionRequest request);

        @POST("promo/list")
        Observable<PromoListResponse> getPromoList(@Query("token") String token);

        @POST("promo/detail")
        Observable<PromoDetailResponse> getPromoDetail(@Body PromoDetailRequest request);

        @POST("promo/join_promo")
        Observable<GenericResponse> joinPromo(@Body JoinPromoRequest request);

        @GET("paymentTransaction/paymentmethod?")
        Observable<PaymentMethodResponse> getPaymentMethod(@Query("token") String token);

        @POST("paymentTransaction/retry_dompetku")
        Observable<AmountSummaryResponse> retryPaypro(@Body RetryPayProRequest request);

        @POST("customersApi/payment_method_update")
        Observable<GenericResponse> getPaymentPaypro(@Body PaymentPayProRequest request);

        @GET("customersApi/checkStatusCustomer")
        Observable<StatusCustomerResponse> getStatusCustomer(@Query ("token") String token);

        @POST("topUpTransaction/topUpViseePay")
        Observable<TopUpViseePayResponse> topUpViseePay(@Query ("token") String token,
                                                        @Body TopUpViseePayRequest request);

        @POST("topUpTransaction/topUpViseePay")
        Observable<TopUpFinPayResponse> topUpFinPay(@Query ("token") String token,
                                                    @Body TopUpViseePayRequest request);

        @POST("trxApi/listTopUp")
        Observable<List<TopUpTransaction>> getListTopUp(@Query ("token") String token,
                                                        @Query ("bank") String bank,
                                                        @Query ("statusBayar") String statusBayar);

        @POST("topUpTransaction/detailWaitingTopUp")
        Observable<TopUpViseePayResponse> detailWaitingTopUp(@Query ("token") String token,
                                                             @Body DetailWaitingTopUpRequest request);

        @POST("slideshow/customerlist")
        Observable<SliderResponse> getSlider();

        @GET("customersApi/getCustomerReferral")
        Observable<ReferralResponse> getCustomerReferral(@Query ("token") String token);

    }

}
