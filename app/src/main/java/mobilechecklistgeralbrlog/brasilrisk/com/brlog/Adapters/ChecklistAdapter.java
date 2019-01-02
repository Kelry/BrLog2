package mobilechecklistgeralbrlog.brasilrisk.com.brlog.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.brasilrisk.mobilechecklistgeralbrlog.R;

import java.util.ArrayList;

import mobilechecklistgeralbrlog.brasilrisk.com.brlog.Classes.Checklist;

/**
 * Created by celsoribeiro on 05/02/15.
 */

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ItemViewHolder> {
    private ArrayList<Checklist> Itens;
    private mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnCheckChangeListener OnCheckChangeListener;
    private Context context;
    private static final int HEADER = 1;
    private static final int ITEM = 2;

    public ChecklistAdapter(ArrayList<Checklist> itens, Context cont) {
        this.Itens = itens;
        this.context = cont;
    }

    public void setOnCheckChangeListener(mobilechecklistgeralbrlog.brasilrisk.com.brlog.Interface.OnCheckChangeListener onCheckChangeListener) {
        OnCheckChangeListener = onCheckChangeListener;
    }

    @Override
    public int getItemCount() {
        return Itens.size();

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder ItemViewHolder, final int i) {
        final Checklist item = Itens.get(i);

        if(item.isHeader()){
            ItemViewHolder.Titulo.setText(item.getNomeCategoria());
        }else{
            if (item.getNomeParaExibicao() != null)
                ItemViewHolder.LinhaTexto.setText(item.getNomeParaExibicao());

            ItemViewHolder.Linha_Switch.setOnCheckedChangeListener(null);
            ItemViewHolder.Linha_Switch.setChecked(item.isChecked());


            ItemViewHolder.Linha_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if (OnCheckChangeListener != null) {
                       View v = (View) buttonView.getParent();
                       OnCheckChangeListener.OnCheckChange(v,isChecked, i);
                   }
               }
           });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Checklist item = Itens.get(position);
        if (item.isHeader())
            return HEADER;
        return ITEM;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        ItemViewHolder itemViewHolderObject;
        View itemView;
        int res = 0;
        if(viewType ==HEADER)
        res = R.layout.checklist_header;
        else
            res = R.layout.linha_checklist;

        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(res, viewGroup, false);
        itemViewHolderObject = new ItemViewHolder(itemView);
        return itemViewHolderObject;
    }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView Titulo,LinhaTexto;
        protected Switch Linha_Switch;

        public ItemViewHolder(View v) {
            super(v);
            Titulo = (TextView) v.findViewById(R.id.titulo);
            LinhaTexto = (TextView) v.findViewById(R.id.linha_texto);
            Linha_Switch = (Switch) v.findViewById(R.id.linha_switch);
        }
    }
}




