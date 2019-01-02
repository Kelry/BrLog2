package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by kelry on 15/03/18.
 */

public class Get_sm  implements Serializable {

    private int StatusCode;

    private int CodMotorista;

    private String Status;

    @Nullable
    private int CodTransportadora;

    private String NomeTransportadora;

    private int CodEmpresa;

    private int CodPedido;
    @Nullable
    private int CodTipoTrajeto;

    private String Mensagem;

    private int CodVeiculo;

    private String PlacaCarreta;

    private String PlacaCarreta2;

    private String Minuta;

    private String OrdemColeta;




    //Motorista

    private Get_Motorista Motorista;

    public Get_Motorista getMotorista() {
        return Motorista;
    }

    public void setMotorista(Get_Motorista motorista) {
        Motorista = motorista;
    }

    //Veiculo

    private Get_Veiculo Veiculo;

    public Get_Veiculo getVeiculo() {
        return Veiculo;
    }

    public void setVeiculo(Get_Veiculo veiculo) {
        Veiculo = veiculo;
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

    public String getNomeTransportadora() {
        return NomeTransportadora;
    }

    public void setNomeTransportadora(String nomeTransportadora) {
        NomeTransportadora = nomeTransportadora;
    }

    @Nullable
    public int getCodTransportadora() {
        return CodTransportadora;
    }

    public void setCodTransportadora(@Nullable int codTransportadora) {
        CodTransportadora = codTransportadora;
    }

    public int getCodEmpresa() {
        return CodEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        CodEmpresa = codEmpresa;
    }

    public int getCodPedido() {
        return CodPedido;
    }

    public void setCodPedido(int codPedido) {
        CodPedido = codPedido;
    }

    @Nullable
    public int getCodTipoTrajeto() {
        return CodTipoTrajeto;
    }

    public void setCodTipoTrajeto(@Nullable int codTipoTrajeto) {
        CodTipoTrajeto = codTipoTrajeto;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public int getCodVeiculo() {
        return CodVeiculo;
    }

    public void setCodVeiculo(int codVeiculo) {
        CodVeiculo = codVeiculo;
    }

    public String getPlacaCarreta() {
        return PlacaCarreta;
    }

    public void setPlacaCarreta(String placaCarreta) {
        PlacaCarreta = placaCarreta;
    }

    public String getPlacaCarreta2() {
        return PlacaCarreta2;
    }

    public void setPlacaCarreta2(String placaCarreta2) {
        PlacaCarreta2 = placaCarreta2;
    }

    public String getOrdemColeta() {
        return OrdemColeta;
    }

    public void setOrdemColeta(String ordemColeta) {
        OrdemColeta = ordemColeta;
    }

    public String getMinuta() {
        return Minuta;
    }

    public void setMinuta(String minuta) {
        Minuta = minuta;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", CodMotorista = "+CodMotorista+", CodTransportadora = "+CodTransportadora+", CodEmpresa = "+CodEmpresa+", OrdemColeta = "+OrdemColeta+", Mensagem = "+Mensagem+", PlacaCarreta = "+PlacaCarreta+", CodVeiculo = "+CodVeiculo+", Status = "+Status+", Veiculo = "+Veiculo+", PlacaCarreta2 = "+PlacaCarreta2+", Motorista = "+Motorista+", NomeTransportadora = "+NomeTransportadora+", CodPedido = "+CodPedido+", Minuta = "+Minuta+", CodTipoTrajeto = "+CodTipoTrajeto+"]";
    }
}
