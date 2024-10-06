package es.unizar.eina.M34_comidas.ui.platos;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.M34_comidas.R;

class PlatoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private final TextView mPlatoItemView;



    private PlatoViewHolder(View itemView) {
        super(itemView);
        mPlatoItemView = itemView.findViewById(R.id.textView);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(String text) {
        mPlatoItemView.setText(text);
    }

    static PlatoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new PlatoViewHolder(view);
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, Platos.DELETE_ID, Menu.NONE, R.string.plato_delete);
        menu.add(Menu.NONE, Platos.EDIT_ID, Menu.NONE, R.string.plato_edit);
    }


}
