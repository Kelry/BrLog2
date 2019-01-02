package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

/**
 * Created by kelry on 13/04/18.
 */

public class Tipo_Checklist
{
    private int CodTipoChecklist;

    private String Descricao;

    private String  Mensagem;

    private int StatusCode;

    private String Status;

    public int getCodTipoChecklist() {
        return CodTipoChecklist;
    }

    public void setCodTipoChecklist(int codTipoChecklist) {
        CodTipoChecklist = codTipoChecklist;
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


    @Override
    public String toString()
    {
        return  Descricao;}
}
