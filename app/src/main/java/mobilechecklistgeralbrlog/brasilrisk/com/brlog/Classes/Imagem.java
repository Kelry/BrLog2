package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import java.io.Serializable;

/**
 * Created by kelry on 27/03/18.
 */

public class Imagem implements Serializable {

    private String PrimeiraFoto;
    private String SegundaFoto;
    private String TerceiraFoto;
    private String QuartaFoto;
    private String QuintaFoto;

    public String getPrimeiraFoto() {
        return PrimeiraFoto;
    }

    public void setPrimeiraFoto(String primeiraFoto) {
        PrimeiraFoto = primeiraFoto;
    }

    public String getSegundaFoto() {
        return SegundaFoto;
    }

    public void setSegundaFoto(String segundaFoto) {
        SegundaFoto = segundaFoto;
    }

    public String getTerceiraFoto() {
        return TerceiraFoto;
    }

    public void setTerceiraFoto(String terceiraFoto) {
        TerceiraFoto = terceiraFoto;
    }

    public String getQuartaFoto() {
        return QuartaFoto;
    }

    public void setQuartaFoto(String quartaFoto) {
        QuartaFoto = quartaFoto;
    }

    public String getQuintaFoto() {
        return QuintaFoto;
    }

    public void setQuintaFoto(String quintaFoto) {
        QuintaFoto = quintaFoto;
    }
}
