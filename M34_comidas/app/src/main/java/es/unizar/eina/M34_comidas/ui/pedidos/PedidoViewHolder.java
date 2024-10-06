package es.unizar.eina.M34_comidas.ui.pedidos;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.M34_comidas.R;

class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private final TextView mPedidoItemView;



    private PedidoViewHolder(View itemView) {
        super(itemView);
        mPedidoItemView = itemView.findViewById(R.id.textView);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(String text) {
        mPedidoItemView.setText(text);
    }

    static PedidoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_pedido, parent, false);
        return new PedidoViewHolder(view);
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, Pedidos.DELETE_ID, Menu.NONE, R.string.pedido_delete);
        menu.add(Menu.NONE, Pedidos.EDIT_ID, Menu.NONE, R.string.pedido_edit);
        menu.add(Menu.NONE, Pedidos.NOTIFICAR_SMS_ID, Menu.NONE, "Notificar pedido por SMS");
        menu.add(Menu.NONE, Pedidos.NOTIFICAR_W_ID, Menu.NONE, "Notificar pedido por WhatsAPP");
    }

}
