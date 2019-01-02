package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

/**
 * Created by kelry on 19/04/18.
 */

public class Get_Documentos {

    private int StatusCode;

    private int CodTipoConsulta;

    private String Status;

    private String Descricao;

    private String Mensagem;

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

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public int getCodTipoConsulta() {
        return CodTipoConsulta;
    }

    public void setCodTipoConsulta(int codTipoConsulta) {
        CodTipoConsulta = codTipoConsulta;
    }

    @Override
    public String toString()
    {
        return  Descricao;}
}
