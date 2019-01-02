package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

/**
 * Created by kelry on 29/03/18.
 */

public class Retorno {

    private int StatusCode;
    private String Status;
    private String Mensagem;
    private String MensagemProtocolo;


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

    public String getMensagemProtocolo() {
        return MensagemProtocolo;
    }

    public void setMensagemProtocolo(String mensagemProtocolo) {
        MensagemProtocolo = mensagemProtocolo;
    }
}
