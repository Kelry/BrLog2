package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;




public class Checklist {


    private String Mensagem;

    private boolean isChecked;

    private boolean isHeader;

    private int StatusCode;

    private String Status;

    private int CodCheckListCategoria;

    private String NomeCategoria;

    private String CodEmpresaChecklist;

    private int CodCheckListItem;

    private boolean ItemDeReprova;

    private String NomeParaExibicao;


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

    public Checklist(boolean isHeader, String nomeCategoria) {
        this.isHeader = isHeader;
        NomeCategoria = nomeCategoria;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public int getCodCheckListCategoria() {
        return CodCheckListCategoria;
    }

    public void setCodCheckListCategoria(int codCheckListCategoria) {
        CodCheckListCategoria = codCheckListCategoria;
    }

    public String getNomeCategoria ()
    {
        return NomeCategoria;
    }

    public void setNomeCategoria (String NomeCategoria)
    {
        this.NomeCategoria = NomeCategoria;
    }

    public String getCodEmpresaChecklist ()
    {
        return CodEmpresaChecklist;
    }

    public void setCodEmpresaChecklist (String CodEmpresaChecklist)
    {
        this.CodEmpresaChecklist = CodEmpresaChecklist;
    }

    public int getCodCheckListItem ()
    {
        return CodCheckListItem;
    }

    public void setCodCheckListItem (int CodCheckListItem)
    {
        this.CodCheckListItem = CodCheckListItem;
    }

    public boolean isItemDeReprova() {
        return ItemDeReprova;
    }

    public void setItemDeReprova(boolean itemDeReprova) {
        ItemDeReprova = itemDeReprova;
    }

    public String getNomeParaExibicao ()
    {
        return NomeParaExibicao;
    }

    public void setNomeParaExibicao (String NomeParaExibicao)
    {
        this.NomeParaExibicao = NomeParaExibicao;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [CodCheckListCategoria = "+CodCheckListCategoria+", NomeCategoria = "+NomeCategoria+", CodEmpresaChecklist = "+CodEmpresaChecklist+", CodCheckListItem = "+CodCheckListItem+", ItemDeReprova = "+ItemDeReprova+", NomeParaExibicao = "+NomeParaExibicao+"]";
    }
}

