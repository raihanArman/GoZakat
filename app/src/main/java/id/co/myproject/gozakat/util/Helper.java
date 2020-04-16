package id.co.myproject.gozakat.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Helper {
    public static int KODE_ZAKAT_PROFESI = 22;
    public static int KODE_ZAKAT_MAL = 21;
    public static int KODE_INFAQ = 23;
    public static int KODE_ZAKAT_EMAS = 24;
    public static int KODE_ZAKAT_PERAK = 25;
    public static int KODE_ZAKAT_LENGKAP = 26;
    public static String ZAKAT_PROFESI = "zakat_profesi";
    public static String ZAKAT_MAL = "zakat_mal";

    public static String rupiahFormat(int harga){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }
}
