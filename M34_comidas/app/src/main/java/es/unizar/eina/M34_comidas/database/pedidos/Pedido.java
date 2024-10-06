package es.unizar.eina.M34_comidas.database.pedidos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "pedido")
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pedidoId")
    private int pedidoId;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "telefono")
    private String telefono;

    @NonNull
    @ColumnInfo(name = "fecha")
    private String fecha;

    @NonNull
    @ColumnInfo(name = "hora")
    private String hora;

    @NonNull
    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "precioTotal")
    private Double precioTotal;

    public Pedido(@NonNull String nombre,@NonNull String telefono,@NonNull String fecha,@NonNull String hora,@NonNull String estado) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.precioTotal = 0.0;
    }

    // ID
    /** Devuelve el identificador de la nota */
    public int getPedidoId(){
        return this.pedidoId;
    }

    /** Permite actualizar el identificador de una nota */
    public void setPedidoId(int id) {
        this.pedidoId = id;
    }

    // NOMBRE
    /** Devuelve el título de la nota */
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre (String nombre ) { this.nombre = nombre; }

    // TELEGONO
    /** Devuelve el cuerpo de la nota */
    public String getTelefono(){
        return this.telefono;
    }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    // FECHA
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getFecha() { return  this.fecha; }

    //HORA
    public void setHora(String hora) {this.hora = hora; }

    public String getHora() { return this.hora; }

    // ESTADO
    public String getEstado() { return this.estado; }

    public void setEstado (String estado) { this.estado = estado; }

    // PRECIO
    public Double getPrecioTotal() { return this.precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }
}



