package id.co.myproject.gozakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Zakat {
    @SerializedName("id_zakat")
    @Expose
    private String idZakat;

    @SerializedName("id_user")
    @Expose
    private String idUser;

    @SerializedName("id_masjid")
    @Expose
    private String idMasjid;

    @SerializedName("nominal")
    @Expose
    private String nominal;

    @SerializedName("jenis_zakat")
    @Expose
    private String jenisZakat;

    @SerializedName("jatuh_tempo")
    @Expose
    private Date jatuhTempo;

    @SerializedName("timestamp")
    @Expose
    private Date timestamp;

    @SerializedName("status")
    @Expose
    private String status;

    public Zakat(String idZakat, String idUser, String idMasjid, String nominal, String jenisZakat, Date jatuhTempo, Date timestamp, String status) {
        this.idZakat = idZakat;
        this.idUser = idUser;
        this.idMasjid = idMasjid;
        this.nominal = nominal;
        this.jenisZakat = jenisZakat;
        this.jatuhTempo = jatuhTempo;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getIdZakat() {
        return idZakat;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdMasjid() {
        return idMasjid;
    }

    public String getNominal() {
        return nominal;
    }

    public String getJenisZakat() {
        return jenisZakat;
    }

    public Date getJatuhTempo() {
        return jatuhTempo;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }
}
