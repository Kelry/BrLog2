package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import java.io.Serializable;

/**
 * Created by kelry on 16/03/18.
 */

public class Get_Veiculo implements Serializable {

    private int StatusCode;

    private int TecnologiaId;

    private String Modelo;

    private String StatusEquipamento;

    private String Renavam;

    private String Placa;

    private String CarretaPlaca;

    private String Chassi;

    private int VeiculoId;

    private String CapacidadeVolumetrica;

    private String Mensagem;

    private String Estado;

    private String AnoFabricacao;

    private String Status;

    private String Tecnologia;

    private String Marca;

    private String Frota;

    private String Cidade;

    private String Cor;

    private String NumeroRastreador;

    private String NumeroRNTRC;

    private String EmpresaId;

    private String Proprietario;

    private String CorStatusSinal;

    private String DataUltimaPosicao;

    private String Localizacao;

    private String SituacaoVeiculo;

    private boolean VeiculoApto;

    public boolean isVeiculoApto() {
        return VeiculoApto;
    }

    public void setVeiculoApto(boolean veiculoApto) {
        VeiculoApto = veiculoApto;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public int getTecnologiaId() {
        return TecnologiaId;
    }

    public void setTecnologiaId(int tecnologiaId) {
        TecnologiaId = tecnologiaId;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getStatusEquipamento() {
        return StatusEquipamento;
    }

    public void setStatusEquipamento(String statusEquipamento) {
        StatusEquipamento = statusEquipamento;
    }

    public String getRenavam() {
        return Renavam;
    }

    public void setRenavam(String renavam) {
        Renavam = renavam;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getCarretaPlaca() {
        return CarretaPlaca;
    }

    public void setCarretaPlaca(String carretaPlaca) {
        CarretaPlaca = carretaPlaca;
    }

    public String getChassi() {
        return Chassi;
    }

    public void setChassi(String chassi) {
        Chassi = chassi;
    }

    public int getVeiculoId() {
        return VeiculoId;
    }

    public void setVeiculoId(int veiculoId) {
        VeiculoId = veiculoId;
    }

    public String getCapacidadeVolumetrica() {
        return CapacidadeVolumetrica;
    }

    public void setCapacidadeVolumetrica(String capacidadeVolumetrica) {
        CapacidadeVolumetrica = capacidadeVolumetrica;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getAnoFabricacao() {
        return AnoFabricacao;
    }

    public void setAnoFabricacao(String anoFabricacao) {
        AnoFabricacao = anoFabricacao;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTecnologia() {
        return Tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        Tecnologia = tecnologia;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getFrota() {
        return Frota;
    }

    public void setFrota(String frota) {
        Frota = frota;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String cor) {
        Cor = cor;
    }

    public String getNumeroRastreador() {
        return NumeroRastreador;
    }

    public void setNumeroRastreador(String numeroRastreador) {
        NumeroRastreador = numeroRastreador;
    }

    public String getNumeroRNTRC() {
        return NumeroRNTRC;
    }

    public void setNumeroRNTRC(String numeroRNTRC) {
        NumeroRNTRC = numeroRNTRC;
    }

    public String getEmpresaId() {
        return EmpresaId;
    }

    public void setEmpresaId(String empresaId) {
        EmpresaId = empresaId;
    }

    public String getProprietario() {
        return Proprietario;
    }

    public void setProprietario(String proprietario) {
        Proprietario = proprietario;
    }

    public String getCorStatusSinal() {
        return CorStatusSinal;
    }

    public void setCorStatusSinal(String corStatusSinal) {
        CorStatusSinal = corStatusSinal;
    }

    public String getDataUltimaPosicao() {
        return DataUltimaPosicao;
    }

    public void setDataUltimaPosicao(String dataUltimaPosicao) {
        DataUltimaPosicao = dataUltimaPosicao;
    }

    public String getLocalizacao() {
        return Localizacao;
    }

    public void setLocalizacao(String localizacao) {
        Localizacao = localizacao;
    }

    public String getSituacaoVeiculo() {
        return SituacaoVeiculo;
    }

    public void setSituacaoVeiculo(String situacaoVeiculo) {
        SituacaoVeiculo = situacaoVeiculo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", TecnologiaId = "+TecnologiaId+", Modelo = "+Modelo+", StatusEquipamento = "+StatusEquipamento+", Renavam = "+Renavam+", Placa = "+Placa+", CarretaPlaca = "+CarretaPlaca+", Chassi = "+Chassi+", VeiculoId = "+VeiculoId+", CapacidadeVolumetrica = "+CapacidadeVolumetrica+", Mensagem = "+Mensagem+", Estado = "+Estado+", AnoFabricacao = "+AnoFabricacao+", Status = "+Status+", Tecnologia = "+Tecnologia+", Marca = "+Marca+", Frota = "+Frota+", Cidade = "+Cidade+", Cor = "+Cor+", NumeroRastreador = "+NumeroRastreador+", NumeroRNTRC = "+NumeroRNTRC+", EmpresaId = "+EmpresaId+", Proprietario = "+Proprietario+"]";
    }
}
