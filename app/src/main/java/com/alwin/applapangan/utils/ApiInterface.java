package com.alwin.applapangan.utils;


import com.alwin.applapangan.models.booking.BodyBooking;
import com.alwin.applapangan.models.booking.ResponseBooking;
import com.alwin.applapangan.models.jadwal.ResponseJadwal;
import com.alwin.applapangan.models.lapangan.ResponseLapangan;
import com.alwin.applapangan.models.login.BodyLogin;
import com.alwin.applapangan.models.login.ResponseProfile;
import com.alwin.applapangan.models.register.BodyRegister;
import com.driver.nyaku.models.BaseResponse;
import com.driver.nyaku.models.BaseResponseOther;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiInterface {
    @POST("login")
    Call<BaseResponse<String>> postLogin(@Body BodyLogin data);

    @POST("register")
    Call<BaseResponseOther> postRegister(@Body BodyRegister data);

    @POST("booking")
    Call<BaseResponseOther> postBooking(@Body BodyBooking data);

    @GET("profile")
    Call<BaseResponse<ResponseProfile>> getProfile();

    @GET("lapangan")
    Call<BaseResponse<List<ResponseLapangan>>> getLapangan();

    @GET("jadwal")
    Call<BaseResponse<List<ResponseJadwal>>> getJadwal();

    @GET("booking")
    Call<BaseResponse<List<ResponseBooking>>> getbooking();

    @GET("pembayaran")
    Call<BaseResponse<List<ResponseBooking>>> getPembayaran();
    //
//    @FormUrlEncoded
//    @PUT("users/logout")
//    Call<BaseResponseOther> postLogout(@Field("id_user") String id
//    );
//
//    @POST("penduduk/register")
//    Call<BaseResponseOther> postRegister(@Body RegisterDto data);
//
//

//
//    @GET("konten/tentang")
//    Call<BaseResponse<TentangDto>> getTentang();
//
//    @GET("profil-desa")
//    Call<BaseResponse<TentangDto>> getProfileDesa();
//
//    @GET("master/pekerjaan")
//    Call<BaseResponse<List<AgamaDto>>> getPekerjaan();
//
//    @GET("anggaran")
//    Call<BaseResponse<AnggaranDto>> getAnggaran(@Query("akun") String akun,
//
//                                                @Query("tahun") String tahun);
//    @FormUrlEncoded
//    @POST("penduduk-fcm-token")
//    Call<BaseResponseOther> postToken(@Field("penduduk_id") String id,
//                                                @Field("token") String token);
//   @FormUrlEncoded
//    @POST("aspirasi")
//    Call<BaseResponseOther> postAspirasi(@Field("penduduk_id") String id,
//                                                @Field("keterangan") String msg);
//
//    @GET("aspirasi/{id}")
//    Call<BaseResponse<List<AspirasiDto>>> getAspirasi(@Path("id")String idpenduduk);
//
//    @GET("permohonan-surat/penduduk/{id}")
//    Call<BaseResponse<ParentStatusSuratDto>> getStatusSurat(@Path("id")String idpenduduk);
//
//    @DELETE("penduduk-fcm-token/{id}")
//    Call<BaseResponseOther> logout(@Path("id") String id);
//
//    @GET("statistik/umur-kategori")
//    Call<BaseResponse<List<StatistikDto>>> getStatistik();
//
//    @GET("berita-all")
//    Call<BaseResponse<List<BeritaDto>>> getBerita();
//
//    @GET("bantuan/{id}")
//    Call<BaseResponse<List<BantuanDto>>> getBantuan(@Path("id") String id);
//
//    @GET("master/agama")
//    Call<BaseResponse<List<AgamaDto>>> getAgama();
//
//    @GET("master/goldarah")
//    Call<BaseResponse<List<AgamaDto>>> getGolonganDarah();
//
//    @GET("master/hubungan")
//    Call<BaseResponse<List<AgamaDto>>> getHubungan();
//
//    @GET("master/surat")
//    Call<BaseResponse<List<SuratDto>>> getSurat(@Header("Authorization") String authHeader);
//
//    @GET("penduduk/{id}")
//    Call<BaseResponse<UserLoginDto>> getProfile(@Header("Authorization") String authHeader,
//                                                @Path("id") String id);
//
//    @POST("permohonan-surat")
//    Call<BaseResponseOther> postSurat(@Header("Authorization") String authHeader, @Body PostSuratDto data);
//
//
//    @Multipart
//    @POST("penduduk/avatar/{id}")
//    Call<BaseResponseOther> uploadFIle(@Path("id") String id, @Part MultipartBody.Part file);
//

}