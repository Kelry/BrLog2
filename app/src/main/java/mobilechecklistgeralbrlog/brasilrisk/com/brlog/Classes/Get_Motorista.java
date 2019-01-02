package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import java.io.Serializable;

/**
 * Created by kelry on 15/03/18.
 */

public class Get_Motorista implements Serializable {

    private String NomeMotorista;

    private int StatusCode;

    private int CodMotorista;

    private String Status;

    private String CPF;

    private String NumeroCNH;

    private String Mensagem;

    private String DataNascimento;

    private String PerfilMotorista;

    private String SituacaoMotorista;

    private boolean MotoristaApto;

    public boolean isMotoristaApto() {
        return MotoristaApto;
    }

    public void setMotoristaApto(boolean motoristaApto) {
        MotoristaApto = motoristaApto;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public int getCodMotorista() {
        return CodMotorista;
    }

    public void setCodMotorista(int codMotorista) {
        CodMotorista = codMotorista;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNumeroCNH() {
        return NumeroCNH;
    }

    public void setNumeroCNH(String numeroCNH) {
        NumeroCNH = numeroCNH;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public String getNomeMotorista() {
        return NomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        NomeMotorista = nomeMotorista;
    }

    public String getPerfilMotorista() {
        return PerfilMotorista;
    }

    public void setPerfilMotorista(String perfilMotorista) {
        PerfilMotorista = perfilMotorista;
    }

    public String getSituacaoMotorista() {
        return SituacaoMotorista;
    }

    public void setSituacaoMotorista(String situacaoMotorista) {
        SituacaoMotorista = situacaoMotorista;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", CodMotorista = "+CodMotorista+", Status = "+Status+", CPF = "+CPF+", NumeroCNH = "+NumeroCNH+", Mensagem = "+Mensagem+", DataNascimento = "+DataNascimento+"]";
    }
}
