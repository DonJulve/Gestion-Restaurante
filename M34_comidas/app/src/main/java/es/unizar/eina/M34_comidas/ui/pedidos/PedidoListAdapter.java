package es.unizar.eina.M34_comidas.ui.pedidos;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.M34_comidas.database.pedidos.Pedido;

public class PedidoListAdapter extends ListAdapter<Pedido, PedidoViewHolder> {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PedidoListAdapter(@NonNull DiffUtil.ItemCallback<Pedido> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PedidoViewHolder.create(parent);
    }

    public Pedido getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {

        Pedido current = getItem(position);
        holder.bind(current.getNombre());



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

    }


    static class PedidoDiff extends DiffUtil.ItemCallback<Pedido> {

        @Override
        public boolean areItemsTheSame(@NonNull Pedido oldItem, @NonNull Pedido newItem) {
            //android.util.Log.d ( "PedidoDiff" , "areItemsTheSame " + oldItem.getId() + " vs " + newItem.getId() + " " +  (oldItem.getId() == newItem.getId()));
            return oldItem.getPedidoId() == newItem.getPedidoId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pedido oldItem, @NonNull Pedido newItem) {
            //android.util.Log.d ( "NoteDiff" , "areContentsTheSame " + oldItem.getTitle() + " vs " + newItem.getTitle() + " " + oldItem.getTitle().equals(newItem.getTitle()));
            // We are just worried about differences in visual representation, i.e. changes in the title
            return oldItem.getNombre().equals(newItem.getNombre());
        }
    }
}
