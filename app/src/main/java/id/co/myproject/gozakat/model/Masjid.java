package id.co.myproject.gozakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Masjid {
    @SerializedName("id_masjid")
    @Expose
    private String idMasjid;

    @SerializedName("email")
    @Expose
    private String emailMasjid;

    @SerializedName("nama")
    @Expose
    private String namaMasjid;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("no_telp")
    @Expose
    private String noTelp;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    public Masjid(String idMasjid, String emailMasjid, String namaMasjid, String alamat, String noTelp, String gambar) {
        this.idMasjid = idMasjid;
        this.emailMasjid = emailMasjid;
        this.namaMasjid = namaMasjid;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.gambar = gambar;
    }

    public String getIdMasjid() {
        return idMasjid;
    }

    public String getEmailMasjid() {
        return emailMasjid;
    }

    public String getNamaMasjid() {
        return namaMasjid;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getGambar() {
        return gambar;
    }
}
