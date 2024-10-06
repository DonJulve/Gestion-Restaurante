package es.unizar.eina.M34_comidas.ui.platos;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.unizar.eina.M34_comidas.database.Platos.Plato;

public class PlatoListAdapter extends ListAdapter<Plato, PlatoViewHolder> {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PlatoListAdapter(@NonNull DiffUtil.ItemCallback<Plato> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PlatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PlatoViewHolder.create(parent);
    }

    public Plato getCurrent() {
        return getItem(getPosition());
    }

    @Override
    public void onBindViewHolder(PlatoViewHolder holder, int position) {

        Plato current = getItem(position);
        holder.bind(current.getNombre());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }


    static class PlatoDiff extends DiffUtil.ItemCallback<Plato> {

        @Override
        public boolean areItemsTheSame(@NonNull Plato oldItem, @NonNull Plato newItem) {
            //android.util.Log.d ( "NoteDiff" , "areItemsTheSame " + oldItem.getId() + " vs " + newItem.getId() + " " +  (oldItem.getId() == newItem.getId()));
            return oldItem.getPlatoId() == newItem.getPlatoId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plato oldItem, @NonNull Plato newItem) {
            //android.util.Log.d ( "NoteDiff" , "areContentsTheSame " + oldItem.getTitle() + " vs " + newItem.getTitle() + " " + oldItem.getTitle().equals(newItem.getTitle()));
            // We are just worried about differences in visual representation, i.e. changes in the title
            return oldItem.getNombre().equals(newItem.getNombre());
        }
    }
}
