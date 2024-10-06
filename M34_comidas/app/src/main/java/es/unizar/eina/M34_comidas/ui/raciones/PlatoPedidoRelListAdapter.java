package es.unizar.eina.M34_comidas.ui.raciones;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.concurrent.CountDownLatch;

import es.unizar.eina.M34_comidas.database.ComidasRoomDatabase;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRel;
import es.unizar.eina.M34_comidas.ui.raciones.Raciones;

public class PlatoPedidoRelListAdapter extends ListAdapter<PlatosPedidosRel, PlatoPedidoRelViewHolder> {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PlatoPedidoRelListAdapter(@NonNull DiffUtil.ItemCallback<PlatosPedidosRel> diffCallback)  {
        super(diffCallback);
    }

    @Override
    public PlatoPedidoRelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PlatoPedidoRelViewHolder.create(parent);
    }

    public PlatosPedidosRel getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(PlatoPedidoRelViewHolder holder, int position) {

        PlatosPedidosRel current = getItem(position);
        holder.bind(current.getPlatoId(), current.getCantidad());

        holder.mBotonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Raciones.aumentarRacion(current);
            }
        });

        holder.mBotonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Raciones.disminuirRacion(current);
            }
        });

    }

    static class PlatoPedidoRelDiff extends DiffUtil.ItemCallback<PlatosPedidosRel> {

        @Override
        public boolean areItemsTheSame(@NonNull PlatosPedidosRel oldItem, @NonNull PlatosPedidosRel newItem) {
            //android.util.Log.d ( "PedidoDiff" , "areItemsTheSame " + oldItem.getId() + " vs " + newItem.getId() + " " +  (oldItem.getId() == newItem.getId()));
            return oldItem.getPedidoId() == newItem.getPedidoId() && oldItem.getPlatoId() == newItem.getPlatoId() && oldItem.getCantidad() == newItem.getCantidad();
        }


        @Override
        public boolean areContentsTheSame(@NonNull PlatosPedidosRel oldItem, @NonNull PlatosPedidosRel newItem) {
            //android.util.Log.d ( "NoteDiff" , "areContentsTheSame " + oldItem.getTitle() + " vs " + newItem.getTitle() + " " + oldItem.getTitle().equals(newItem.getTitle()));
            // We are just worried about differences in visual representation, i.e. changes in the title
            return oldItem.getPedidoId() == newItem.getPedidoId() && oldItem.getPlatoId() == newItem.getPlatoId() && oldItem.getCantidad() == newItem.getCantidad();
        }

    }
}
