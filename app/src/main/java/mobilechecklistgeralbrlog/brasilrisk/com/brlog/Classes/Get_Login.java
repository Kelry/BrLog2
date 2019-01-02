package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import android.support.annotation.Nullable;

/**
 * Created by kelry on 14/03/18.
 */

public class Get_Login {

    private int StatusCode;

    private String Status;

    private int CodEmpresaUsuario;

    private int CodEmpresa;

    private String Nome;

    private String Mensagem;

    private boolean SelecionarTipoCarreta;

    @Nullable
    private boolean EscolherTipoDoChecklist;

    private boolean SelecionarTipoOperacao;

    private boolean ProsseguirSemNumeroDeSm;

    private boolean PermitidoRealizarChecklistSemVeiculo;

    private boolean PermitidoRealizarChecklistSemMotorista;

    private boolean ExibirInformacaoMotoristaAptoInapto;

    private boolean ExibirInformacaoVeiculoAptoInapto;

    public boolean isExibirInformacaoMotoristaAptoInapto() {
        return ExibirInformacaoMotoristaAptoInapto;
    }

    public void setExibirInformacaoMotoristaAptoInapto(boolean exibirInformacaoMotoristaAptoInapto) {
        ExibirInformacaoMotoristaAptoInapto = exibirInformacaoMotoristaAptoInapto;
    }

    public boolean isExibirInformacaoVeiculoAptoInapto() {
        return ExibirInformacaoVeiculoAptoInapto;
    }

    public void setExibirInformacaoVeiculoAptoInapto(boolean exibirInformacaoVeiculoAptoInapto) {
        ExibirInformacaoVeiculoAptoInapto = exibirInformacaoVeiculoAptoInapto;
    }

    public boolean isProsseguirSemNumeroDeSm() {
        return ProsseguirSemNumeroDeSm;
    }

    public void setProsseguirSemNumeroDeSm(boolean prosseguirSemNumeroDeSm) {
        ProsseguirSemNumeroDeSm = prosseguirSemNumeroDeSm;
    }

    public boolean isSelecionarTipoOperacao() {
        return SelecionarTipoOperacao;
    }

    public void setSelecionarTipoOperacao(boolean selecionarTipoOperacao) {
        SelecionarTipoOperacao = selecionarTipoOperacao;
    }

    @Nullable
    public Boolean getEscolherTipoDoChecklist() {
        return EscolherTipoDoChecklist;
    }

    public void setEscolherTipoDoChecklist(@Nullable Boolean escolherTipoDoChecklist) {
        EscolherTipoDoChecklist = escolherTipoDoChecklist;
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

    public int getCodEmpresaUsuario() {
        return CodEmpresaUsuario;
    }

    public void setCodEmpresaUsuario(int codEmpresaUsuario) {
        CodEmpresaUsuario = codEmpresaUsuario;
    }

    public int getCodEmpresa() {
        return CodEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        CodEmpresa = codEmpresa;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public boolean isSelecionarTipoCarreta() {
        return SelecionarTipoCarreta;
    }

    public void setSelecionarTipoCarreta(boolean selecionarTipoCarreta) {
        SelecionarTipoCarreta = selecionarTipoCarreta;
    }

    @Nullable
    public boolean isEscolherTipoDoChecklist() {
        return EscolherTipoDoChecklist;
    }

    public void setEscolherTipoDoChecklist(@Nullable boolean escolherTipoDoChecklist) {
        EscolherTipoDoChecklist = escolherTipoDoChecklist;
    }

    public boolean isPermitidoRealizarChecklistSemVeiculo() {
        return PermitidoRealizarChecklistSemVeiculo;
    }

    public void setPermitidoRealizarChecklistSemVeiculo(boolean permitidoRealizarChecklistSemVeiculo) {
        PermitidoRealizarChecklistSemVeiculo = permitidoRealizarChecklistSemVeiculo;
    }

    public boolean isPermitidoRealizarChecklistSemMotorista() {
        return PermitidoRealizarChecklistSemMotorista;
    }

    public void setPermitidoRealizarChecklistSemMotorista(boolean permitidoRealizarChecklistSemMotorista) {
        PermitidoRealizarChecklistSemMotorista = permitidoRealizarChecklistSemMotorista;
    }

    @Override
    public String toString() {
        return "ClassPojo [StatusCode = " + StatusCode + ", Status = " + Status + ", CodEmpresaUsuario = " + CodEmpresaUsuario + ", CodEmpresa = " + CodEmpresa + ", Nome = " + Nome + ", Mensagem = " + Mensagem + "]";
    }

}

