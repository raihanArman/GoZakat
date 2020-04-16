package id.co.myproject.gozakat.request;

import java.util.List;

import id.co.myproject.gozakat.model.Masjid;
import id.co.myproject.gozakat.model.Mustahiq;
import id.co.myproject.gozakat.model.Muzakki;
import id.co.myproject.gozakat.model.Value;
import id.co.myproject.gozakat.model.Zakat;
import id.co.myproject.gozakat.model.ZakatHistory;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {

    @FormUrlEncoded
    @POST("input_user.php")
    Call<Value> inpuUserRequest(
            @Field("username") String username,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("password") String password,
            @Field("avatar") String avatar
    );

    @FormUrlEncoded
    @POST("cek_user.php")
    Call<Value> cekUserCallback(
            @Field("email") String email
    );

    @GET("tampil_masjid.php")
    Call<Masjid> getMasjidItemRequest(
            @Query("id_masjid") String idMasjid
    );

    @GET("tampil_mustahiq.php")
    Call<Mustahiq> getMustahiqItemRequest(
            @Query("id_mustahiq") String idMustahiq
    );

    @GET("tampil_user.php")
    Call<Muzakki> getMuzakkiItemRequest(
            @Query("id_user") int idUser
    );

    @GET("tampil_zakat.php")
    Call<List<Zakat>> getZakatRequest(
            @Query("id_user") int idUser
    );

    @GET("tampil_histori.php")
    Call<List<ZakatHistory>> getHistoryRequest(
            @Query("id_user") int idUser
    );

    @FormUrlEncoded
    @POST("edit_user.php")
    Call<Value> editProfilRequest(
            @Field("id_user") int idUser,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("alamat") String alamat,
            @Field("avatar") String avatar
    );


    @GET("tampil_masjid_amil.php")
    Call<List<Masjid>> getMasjidRequest();

    @GET("tampil_masjid.php")
    Call<List<Masjid>> getMasjidCariRequest(
            @Query("cari") String cari
    );

    @FormUrlEncoded
    @POST("input_zakat.php")
    Call<Value> inputZakatRequest(
            @Field("id_user") int idUser,
            @Field("id_masjid") String idMasjid,
            @Field("nominal") String nominal,
            @Field("jenis_zakat") String jenisZakat,
            @Field("jatuh_tempo") String jatuhTempo
    );
}
