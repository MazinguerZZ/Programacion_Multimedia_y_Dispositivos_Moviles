/**
 * FrasesAdapter
 *
 * Adaptador del RecyclerView encargado de mostrar la lista de frases.
 * Su función principal es conectar los datos (las frases en texto)
 * con la vista que se muestra en pantalla.
 *
 * Cada elemento de la lista es simplemente un TextView con una frase.
 */
package com.pmm.a23.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FrasesAdapter extends RecyclerView.Adapter<FrasesAdapter.FraseViewHolder> {

    // Lista de frases que se van a mostrar en el RecyclerView
    private ArrayList<String> frasesList;

    /**
     * Constructor del adaptador.
     * Recibe la lista de frases que se mostrarán.
     *
     * @param frasesList lista de frases en formato texto
     */
    public FrasesAdapter(ArrayList<String> frasesList) {
        // Guardamos la lista recibida
        this.frasesList = frasesList;
    }

    /**
     * Se ejecuta cuando el RecyclerView necesita crear una nueva fila.
     * Aquí se infla el layout que tendrá cada elemento de la lista.
     */
    @NonNull
    @Override
    public FraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usamos un layout simple de Android con un solo TextView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        // Devolvemos el ViewHolder con la vista creada
        return new FraseViewHolder(view);
    }

    /**
     * Se ejecuta para mostrar los datos en una fila concreta.
     * Aquí se asigna el texto de la frase al TextView.
     */
    @Override
    public void onBindViewHolder(@NonNull FraseViewHolder holder, int position) {
        // Obtenemos la frase correspondiente a la posición
        String frase = frasesList.get(position);

        // Mostramos la frase en el TextView
        holder.textView.setText(frase);

        // Ajustes visuales para que el texto se vea mejor
        holder.textView.setTextSize(16);
        holder.textView.setPadding(20, 20, 20, 20);
        holder.textView.setTextColor(
                holder.itemView.getResources().getColor(android.R.color.black)
        );
    }

    /**
     * Devuelve el número total de frases que hay en la lista.
     */
    @Override
    public int getItemCount() {
        return frasesList.size();
    }

    /**
     * ViewHolder que representa una fila del RecyclerView.
     * Contiene las vistas que se reutilizan al hacer scroll.
     */
    public static class FraseViewHolder extends RecyclerView.ViewHolder {

        // TextView donde se muestra la frase
        TextView textView;

        /**
         * Constructor del ViewHolder.
         * Asocia la vista con el TextView del layout.
         */
        public FraseViewHolder(@NonNull View itemView) {
            super(itemView);

            // Enlazamos el TextView del layout
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
