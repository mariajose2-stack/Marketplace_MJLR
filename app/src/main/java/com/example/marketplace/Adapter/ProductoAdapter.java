package com.example.marketplace.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.example.marketplace.DetalleProducto;
import com.example.marketplace.R;
import com.example.marketplace.Model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



//Adaptador para el RecyclerView de la pantalla Home.
//Se encarga de conectar la lista de objetos Producto con el diseño de la tarjeta item_producto.xml.
public class ProductoAdapter extends
        RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private static final String TAG = "ProductoAdapter";
    private final Context context;

    // Lista visible para el RecyclerView (filtrada o completa)
    private List<Producto> listaProductos;

    // Copia inmutable de la lista original (para restaurar después del filtro)
    private final List<Producto> listaOriginal;

    public ProductoAdapter(Context context, List<Producto> lista) {
        this.context = context;
        this.listaProductos = new ArrayList<>(lista);
        this.listaOriginal = new ArrayList<>(lista);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        // Asignar datos
        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText(producto.getPrecio());

        // Al hacer clic, abrir Detalle_Producto
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleProducto.class);
            intent.putExtra(DetalleProducto.EXTRA_PRODUCTO_ID, producto.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    /**
     * Filtro de productos (por nombre)
     */
    public void filtrar(String texto) {
        String textoBusqueda = texto.toLowerCase(Locale.getDefault()).trim();
        List<Producto> listaFiltrada = new ArrayList<>();

        if (textoBusqueda.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
            Log.d(TAG, "Búsqueda vacía. Mostrando todos los productos: " + listaFiltrada.size());
        } else {
            for (Producto producto : listaOriginal) {
                if (producto.getNombre().toLowerCase(Locale.getDefault()).contains(textoBusqueda)) {
                    listaFiltrada.add(producto);
                }
            }
            Log.d(TAG, "Productos encontrados para '" + texto + "': " + listaFiltrada.size());
        }

        this.listaProductos = listaFiltrada;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder que contiene las vistas del ítem
     */
    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_producto_titulo);
            tvPrecio = itemView.findViewById(R.id.tv_producto_precio);
        }
    }
}
