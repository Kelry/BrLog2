package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;


public class Result implements Serializable{

    private int CodChecklist;

    private String CodVeiculo;

    private String CodMotorista;

    private int CodEmpresaUsuario;

    private int CodStatusCheckList;

    private String DhInicioChecklist;

    private String DhFimChecklist;

    private double Latitude;

    private double Longitude;

    private String CpfMotorista;

    private String Placa;

    private String Foto1;

    private String Foto2;

    private String Foto3;

    private String Observacao;

    private String PlacaCarreta;

    private String PlacaCarreta2;

    private int CodTipoChecklist;

    @Nullable
    private String SolicitacaoMonitoramentoId;

    private boolean Aprovado;

    @Nullable
    private String CodSegmentoTransporte;

    @Nullable
    private String CodTransportadora;

    private String Protocolo;

    private ArrayList<mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.CheckListResultado> CheckListResultado;

    private mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Imagem Imagem;

    private String CartaoPedagio;

    @Nullable
    private String PosicaoPaletesNaCarreta;

    @Nullable
    private float AlturaBauSider;

    @Nullable
    private String QtdeLacres ;

    @Nullable
    private String QtdeEixos ;

    @Nullable
    private String NrMinuta;

    @Nullable
    private String NrMoop;

    @Nullable
    private String ValidadeMoop;

    @Nullable
    public  String CodVeiculoTipo;

    @Nullable
    private String NumeroDaDoca;

    @Nullable
    private String NumeroDaRampa;

    @Nullable
    private float ComprimentoVeiculo;

    @Nullable
    private float LarguraVeiculo;

    @Nullable
    private float AlturaVeiculo;

    @Nullable
    private String NumeroLacrePortaTraseira;

    @Nullable
    private String NumeroLacrePortaLateral;

    @Nullable
    private int QtdeNotasFiscais;

    @Nullable
    private int QtdeDevolucao;

    private String ResponsavelAmarracao;

    private boolean MotoristaApto;

    private  boolean VeiculoApto;

    public boolean isMotoristaApto() {
        return MotoristaApto;
    }

    public void setMotoristaApto(boolean motoristaApto) {
        MotoristaApto = motoristaApto;
    }

    public boolean isVeiculoApto() {
        return VeiculoApto;
    }

    public void setVeiculoApto(boolean veiculoApto) {
        VeiculoApto = veiculoApto;
    }

    public String getResponsavelAmarracao() {
        return ResponsavelAmarracao;
    }

    public void setResponsavelAmarracao(String responsavelAmarracao) {
        ResponsavelAmarracao = responsavelAmarracao;
    }

    public String getCartaoPedagio() {
        return CartaoPedagio;
    }

    public void setCartaoPedagio(String cartaoPedagio) {
        CartaoPedagio = cartaoPedagio;
    }

    @Nullable
    public String getPosicaoPaletesNaCarreta() {
        return PosicaoPaletesNaCarreta;
    }

    public void setPosicaoPaletesNaCarreta(@Nullable String posicaoPaletesNaCarreta) {
        PosicaoPaletesNaCarreta = posicaoPaletesNaCarreta;
    }

    @Nullable
    public float getAlturaBauSider() {
        return AlturaBauSider;
    }

    public void setAlturaBauSider(@Nullable float alturaBauSider) {
        AlturaBauSider = alturaBauSider;
    }

    @Nullable
    public String getQtdeLacres() {
        return QtdeLacres;
    }

    public void setQtdeLacres(@Nullable String qtdeLacres) {
        QtdeLacres = qtdeLacres;
    }

    @Nullable
    public String getQtdeEixos() {
        return QtdeEixos;
    }

    public void setQtdeEixos(@Nullable String qtdeEixos) {
        QtdeEixos = qtdeEixos;
    }

    @Nullable
    public String getNrMinuta() {
        return NrMinuta;
    }

    public void setNrMinuta(@Nullable String nrMinuta) {
        NrMinuta = nrMinuta;
    }

    @Nullable
    public String getNrMoop() {
        return NrMoop;
    }

    public void setNrMoop(@Nullable String nrMoop) {
        NrMoop = nrMoop;
    }

    @Nullable
    public String getValidadeMoop() {
        return ValidadeMoop;
    }

    public void setValidadeMoop(@Nullable String validadeMoop) {
        ValidadeMoop = validadeMoop;
    }

    public int getCodChecklist() {
        return CodChecklist;
    }

    public void setCodChecklist(int codChecklist) {
        CodChecklist = codChecklist;
    }

    public String getCodVeiculo() {
        return CodVeiculo;
    }

    public void setCodVeiculo(String codVeiculo) {
        CodVeiculo = codVeiculo;
    }

    public String getCodMotorista() {
        return CodMotorista;
    }

    public void setCodMotorista(String codMotorista) {
        CodMotorista = codMotorista;
    }

    public int getCodEmpresaUsuario() {
        return CodEmpresaUsuario;
    }

    public void setCodEmpresaUsuario(int codEmpresaUsuario) {
        CodEmpresaUsuario = codEmpresaUsuario;
    }

    public int getCodStatusCheckList() {
        return CodStatusCheckList;
    }

    public void setCodStatusCheckList(int codStatusCheckList) {
        CodStatusCheckList = codStatusCheckList;
    }

    public String getDhInicioChecklist() {
        return DhInicioChecklist;
    }

    public void setDhInicioChecklist(String dhInicioChecklist) {
        DhInicioChecklist = dhInicioChecklist;
    }

    public String getDhFimChecklist() {
        return DhFimChecklist;
    }

    public void setDhFimChecklist(String dhFimChecklist) {
        DhFimChecklist = dhFimChecklist;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCpfMotorista() {
        return CpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        CpfMotorista = cpfMotorista;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public boolean isAprovado() {
        return Aprovado;
    }

    public void setAprovado(boolean aprovado) {
        Aprovado = aprovado;
    }

    public String getCodSegmentoTransporte() {
        return CodSegmentoTransporte;
    }

    public void setCodSegmentoTransporte(String codSegmentoTransporte) {
        CodSegmentoTransporte = codSegmentoTransporte;
    }

    @Nullable
    public String getCodTransportadora() {
        return CodTransportadora;
    }

    public void setCodTransportadora(@Nullable String codTransportadora) {
        CodTransportadora = codTransportadora;
    }

    public String getProtocolo() {
        return Protocolo;
    }

    public void setProtocolo(String protocolo) {
        Protocolo = protocolo;
    }

    public String getFoto1() {
        return Foto1;
    }

    public void setFoto1(String foto1) {
        Foto1 = foto1;
    }

    public String getFoto2() {
        return Foto2;
    }

    public void setFoto2(String foto2) {
        Foto2 = foto2;
    }

    public String getFoto3() {
        return Foto3;
    }

    public void setFoto3(String foto3) {
        Foto3 = foto3;
    }

    @Nullable
    public String getCodVeiculoTipo() {
        return CodVeiculoTipo;
    }

    public void setCodVeiculoTipo(@Nullable String codVeiculoTipo) {
        CodVeiculoTipo = codVeiculoTipo;
    }

    public ArrayList<mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.CheckListResultado> getCheckListResultado() {
        return CheckListResultado;
    }

    public void setCheckListResultado(ArrayList<mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.CheckListResultado> checkListResultado) {
        CheckListResultado = checkListResultado;
    }


    public mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Imagem getImagem() {
        return Imagem;
    }

    public void setImagem(mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Imagem imagem) {
        Imagem = imagem;

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

    public int getCodTipoChecklist() {
        return CodTipoChecklist;
    }


    @Nullable
    public String getNumeroDaDoca() {
        return NumeroDaDoca;
    }

    public void setNumeroDaDoca(@Nullable String numeroDaDoca) {
        NumeroDaDoca = numeroDaDoca;
    }

    @Nullable
    public String getNumeroDaRampa() {
        return NumeroDaRampa;
    }

    public void setNumeroDaRampa(@Nullable String numeroDaRampa) {
        NumeroDaRampa = numeroDaRampa;
    }

    @Nullable
    public float getComprimentoVeiculo() {
        return ComprimentoVeiculo;
    }

    public void setComprimentoVeiculo(@Nullable float comprimentoVeiculo) {
        ComprimentoVeiculo = comprimentoVeiculo;
    }

    @Nullable
    public float getLarguraVeiculo() {
        return LarguraVeiculo;
    }

    public void setLarguraVeiculo(@Nullable float larguraVeiculo) {
        LarguraVeiculo = larguraVeiculo;
    }

    @Nullable
    public float getAlturaVeiculo() {
        return AlturaVeiculo;
    }

    public void setAlturaVeiculo(@Nullable float alturaVeiculo) {
        AlturaVeiculo = alturaVeiculo;
    }

    @Nullable
    public String getNumeroLacrePortaTraseira() {
        return NumeroLacrePortaTraseira;
    }

    public void setNumeroLacrePortaTraseira(@Nullable String numeroLacrePortaTraseira) {
        NumeroLacrePortaTraseira = numeroLacrePortaTraseira;
    }

    @Nullable
    public String getNumeroLacrePortaLateral() {
        return NumeroLacrePortaLateral;
    }

    public void setNumeroLacrePortaLateral(@Nullable String numeroLacrePortaLateral) {
        NumeroLacrePortaLateral = numeroLacrePortaLateral;
    }

    @Nullable
    public int getQtdeNotasFiscais() {
        return QtdeNotasFiscais;
    }

    public void setQtdeNotasFiscais(@Nullable int qtdeNotasFiscais) {
        QtdeNotasFiscais = qtdeNotasFiscais;
    }

    @Nullable
    public int getQtdeDevolucao() {
        return QtdeDevolucao;
    }

    public void setQtdeDevolucao(@Nullable int qtdeDevolucao) {
        QtdeDevolucao = qtdeDevolucao;
    }

    @Nullable
    public String getSolicitacaoMonitoramentoId() {
        return SolicitacaoMonitoramentoId;
    }

    public void setSolicitacaoMonitoramentoId(@Nullable String solicitacaoMonitoramentoId) {
        SolicitacaoMonitoramentoId = solicitacaoMonitoramentoId;
    }

    public void setCodTipoChecklist(int codTipoChecklist) {
        CodTipoChecklist = codTipoChecklist;
    }
}

