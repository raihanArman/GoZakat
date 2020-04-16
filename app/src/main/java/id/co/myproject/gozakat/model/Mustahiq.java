package id.co.myproject.gozakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mustahiq {
    @SerializedName("id_mustahiq")
    @Expose
    private String idMustahiq;

    @SerializedName("nama_mustahiq")
    @Expose
    private String namaMustahiq;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("jenis_mustahiq")
    @Expose
    private String jenis_mustahiq;

    @SerializedName("no_telp")
    @Expose
    private String noTelp;

    @SerializedName("foto")
    @Expose
    private String foto;

    public Mustahiq(String idMustahiq, String namaMustahiq, String alamat, String jenis_mustahiq, String noTelp, String foto) {
        this.idMustahiq = idMustahiq;
        this.namaMustahiq = namaMustahiq;
        this.alamat = alamat;
        this.jenis_mustahiq = jenis_mustahiq;
        this.noTelp = noTelp;
        this.foto = foto;
    }

    public String getIdMustahiq() {
        return idMustahiq;
    }

    public String getNamaMustahiq() {
        return namaMustahiq;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJenis_mustahiq() {
        return jenis_mustahiq;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getFoto() {
        return foto;
    }
}
