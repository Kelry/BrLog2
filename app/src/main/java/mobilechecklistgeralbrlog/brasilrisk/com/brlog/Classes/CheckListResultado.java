package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes;

import java.io.Serializable;

/**
 * Created by kelry on 28/03/18.
 */

public class  CheckListResultado implements Serializable  {


    private int CodCheckListResultado;
    private int CodChecklist;
    private int CodChecklistItem;
    private boolean Valido;

    public int getCodCheckListResultado() {
        return CodCheckListResultado;
    }

    public void setCodCheckListResultado(int codCheckListResultado) {
        CodCheckListResultado = codCheckListResultado;
    }

    public int getCodChecklist() {
        return CodChecklist;
    }

    public void setCodChecklist(int codChecklist) {
        CodChecklist = codChecklist;
    }

    public int getCodChecklistItem() {
        return CodChecklistItem;
    }

    public void setCodChecklistItem(int codChecklistItem) {
        CodChecklistItem = codChecklistItem;
    }

    public boolean isValido() {
        return Valido;
    }

    public void setValido(boolean valido) {
        Valido = valido;
    }
}
