package es.unizar.eina.M34_comidas.database.platos_pedidos;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import es.unizar.eina.M34_comidas.database.Platos.Plato;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;

@Entity(
        tableName = "PlatosPedidosRel",
        primaryKeys = {"platoId", "pedidoId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Plato.class,
                        parentColumns = "platoId",
                        childColumns = "platoId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                ),
                @ForeignKey(
                        entity = Pedido.class,
                        parentColumns = "pedidoId",
                        childColumns = "pedidoId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                )
        }
)
public class PlatosPedidosRel {

    @NonNull
    @ColumnInfo(name = "platoId")
    private int platoId;

    @NonNull
    @ColumnInfo(name = "pedidoId")
    private int pedidoId;

    @NonNull
    @ColumnInfo(name = "cantidad")
    private int cantidad;

    @NonNull
    @ColumnInfo(name = "precioCrear")   // Aqui se guarda el precio al crear el pedido
    private Double precioCrear;

    public PlatosPedidosRel(@NonNull int platoId, @NonNull int pedidoId, @NonNull int cantidad, @NonNull Double precioCrear){
        this.platoId = platoId;
        this.pedidoId = pedidoId;
        this.cantidad = cantidad;
        this.precioCrear = precioCrear;
    }

    // PlatosID
    public int getPlatoId() { return platoId; }
    public void setPlatoId(@NonNull int platosID) { this.platoId = platosID; }


    // PedidosId
    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(@NonNull int pedidosID) { this.pedidoId = pedidosID; }



    // Cantidad
    public int getCantidad() { return  cantidad; }
    public  void setCantidad(@NonNull int cantidad) {this.cantidad = cantidad; }


    //Precio
    public Double getPrecioCrear() {return this.precioCrear; }
    public void setPrecioCrear(@NonNull Double precioCrear) { this.precioCrear = precioCrear; }

}

