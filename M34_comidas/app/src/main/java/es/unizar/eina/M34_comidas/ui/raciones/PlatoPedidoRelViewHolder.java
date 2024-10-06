package es.unizar.eina.M34_comidas.ui.raciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.M34_comidas.R;

class PlatoPedidoRelViewHolder extends RecyclerView.ViewHolder {
    private final TextView mRacionItemView;
    private final TextView mCantidadItemView;

    ImageButton mBotonMas;
    ImageButton mBotonMenos;


    private PlatoPedidoRelViewHolder(View itemView) {
        super(itemView);
        mRacionItemView = itemView.findViewById(R.id.textView);
        mCantidadItemView = itemView.findViewById(R.id.texto_cantidad);
        mBotonMas = itemView.findViewById(R.id.boton_mas);
        mBotonMenos = itemView.findViewById(R.id.boton_menos);
    }

    public void bind(int id, int raciones) {
        mRacionItemView.setText(Raciones.encontrarNombrePlato(id));
        mCantidadItemView.setText(Integer.toString(raciones));
    }

    static PlatoPedidoRelViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_raciones, parent, false);

        return new PlatoPedidoRelViewHolder(view);
    }

}
