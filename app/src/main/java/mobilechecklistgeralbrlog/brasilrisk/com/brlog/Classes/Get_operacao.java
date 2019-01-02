package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import android.support.annotation.Nullable;

/**
 * Created by kelry on 10/04/18.
 */

public class Get_operacao {

    @Nullable
    private int CodTipoOperacao;

    private String  Descricao;

    private String Mensagem;

    private int StatusCode;

    private String Status;

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

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    @Nullable
    public int getCodTipoOperacao() {
        return CodTipoOperacao;
    }

    public void setCodTipoOperacao(@Nullable int codTipoOperacao) {
        CodTipoOperacao = codTipoOperacao;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    @Override
    public String toString()
    {
        return  Descricao;}
}


