package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import android.support.annotation.Nullable;

/**
 * Created by kelry on 16/03/18.
 */

public class Get_Transportadora {

    private String NomeTransportadora;

    private int codEmpresa;

    private int StatusCode;

    private String Status;

    private String Mensagem;

    @Nullable
    private int codTransportadora;

    public String getNomeTransportadora() {
        return NomeTransportadora;
    }


    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setNomeTransportadora(String nomeTransportadora) {
        NomeTransportadora = nomeTransportadora;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public int getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodTransportadora() {
        return codTransportadora;
    }

    public void setCodTransportadora(int codTransportadora) {
        this.codTransportadora = codTransportadora;
    }

    @Override
    public String toString()
    {
        return  NomeTransportadora;}
}
