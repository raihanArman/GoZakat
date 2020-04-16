package id.co.myproject.gozakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muzakki {
    @SerializedName("id_user")
    @Expose
    private String idUser;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Muzakki(String idUser, String email, String nama, String username, String alamat, String avatar) {
        this.idUser = idUser;
        this.email = email;
        this.nama = nama;
        this.username = username;
        this.alamat = alamat;
        this.avatar = avatar;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getAvatar() {
        return avatar;
    }
}
