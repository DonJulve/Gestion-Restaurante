package es.unizar.eina.M34_comidas.database.Platos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "plato")
public class Plato {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "platoId")
    private int platoId;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @NonNull
    @ColumnInfo(name = "precio")
    private Double precio;

    @NonNull
    @ColumnInfo(name = "categoria")
    private String categoria;

    public Plato(@NonNull String nombre, @NonNull String descripcion, @NonNull Double precio, @NonNull String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    // ID
    /** Devuelve el identificador de la nota */
    public int getPlatoId(){
        return this.platoId;
    }

    /** Permite actualizar el identificador de una nota */
    public void setPlatoId(@NonNull Integer id) {
        this.platoId = id;
    }

    // NOMBRE
    /** Devuelve el título de la nota */
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre (@NonNull String nombre ) { this.nombre = nombre; }

    /** Devuelve el cuerpo de la nota */
    public String getDescripcion(){
        return this.descripcion;
    }
    public void setDescripcion(@NonNull String descripcion) { this.descripcion = descripcion; }

    // PRECIO
    public void setPrecio(@NonNull Double precio) { this.precio = precio; }
    public Double getPrecio() { return  this.precio; }

    // CATEGORIA
    public String getCategoria() { return this.categoria; }

    public void setCategoria (@NonNull String categoria) { this.categoria = categoria; }


}
