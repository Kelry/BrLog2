package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

public class Get_TipoCarreta {


    private int CodVeiculoTipo;

    private String Descricao;

    private String  Mensagem;

    private int StatusCode;

    private String Status;

    public int getCodVeiculoTipo() {
        return CodVeiculoTipo;
    }

    public void setCodVeiculoTipo(int codVeiculoTipo) {
        CodVeiculoTipo = codVeiculoTipo;
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
