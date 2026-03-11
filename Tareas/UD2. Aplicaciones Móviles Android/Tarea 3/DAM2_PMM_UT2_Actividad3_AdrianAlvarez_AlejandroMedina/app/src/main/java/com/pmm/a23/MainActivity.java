/**
 * MainActivity
 *
 * Esta es la actividad principal de la aplicación.
 *
 * Desde aquí se controla casi todo lo que ve y hace el usuario:
 *  - Descargar frases desde Internet
 *  - Guardarlas en una base de datos
 *  - Mostrarlas en una lista (RecyclerView)
 *  - Mostrar u ocultar las frases con un botón
 *  - Borrar todas las frases guardadas
 *
 * El objetivo del código es ser claro, sencillo y fácil de mantener.
 *
 */
package com.pmm.a23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pmm.a23.ui.FrasesAdapter;
import com.pmm.a23.data.FrasesDAO;
import com.pmm.a23.data.Frase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ==============================
    // ATRIBUTOS DE LA ACTIVIDAD
    // ==============================

    /** RecyclerView donde se muestran las frases */
    private RecyclerView recyclerView;

    /** Lista de frases en formato texto para mostrar */
    private ArrayList<String> frasesList;

    /** Adaptador del RecyclerView */
    private FrasesAdapter adapter;

    /** Botón para mostrar u ocultar las frases */
    private Button btnMostrarOcultar;

    /** DAO para el acceso a la base de datos */
    private FrasesDAO frasesDAO;

    /** Indica si las frases están visibles o no */
    private boolean mostrarFrases = false;

    /**
     * Método que se ejecuta al iniciar la actividad.
     * Inicializa la interfaz, la base de datos y el RecyclerView.
     *
     * @param savedInstanceState estado previo de la actividad
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Este método se ejecuta cuando se abre la app
        // Aquí se inicializa absolutamente todo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la lista de frases
        // Creamos la lista donde se guardarán las frases que se muestran
        frasesList = new ArrayList<>();

        // Inicializar y abrir la base de datos
        // Creamos el DAO para trabajar con la base de datos
        frasesDAO = new FrasesDAO(this);
        // Abrimos la base de datos
        frasesDAO.open();

        // Obtener referencia al botón Mostrar/Ocultar
        btnMostrarOcultar = findViewById(R.id.btn2);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.frases);
        // Indicamos que el RecyclerView será una lista vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear y asignar el adaptador
        // Creamos el adaptador que une la lista de datos con la vista
        adapter = new FrasesAdapter(frasesList);
        recyclerView.setAdapter(adapter);

        // Cargar frases guardadas en la base de datos
        cargarFrasesDeBD();

        // Ocultar la lista al inicio
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * Se ejecuta cuando la actividad se destruye.
     * Cierra la conexión con la base de datos.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        frasesDAO.close();
    }

    /**
     * Carga todas las frases almacenadas en la base de datos
     * y las muestra en el RecyclerView.
     */
    /**
     * Carga todas las frases guardadas en la base de datos
     * y las prepara para mostrarlas por pantalla
     */
    private void cargarFrasesDeBD() {
        // Limpiar la lista actual
        // Vaciar la lista para no duplicar datos
        frasesList.clear();

        // Obtener todas las frases de la base de datos
        List<Frase> frasesBD = frasesDAO.getAllFrases();

        // Convertir cada frase a texto para mostrar
        // Recorremos todas las frases obtenidas de la BD
        for (Frase frase : frasesBD) {
            String textoMostrar = "\"" + frase.getTexto() + "\"\n- " + frase.getAutor();
            frasesList.add(textoMostrar);
        }

        // Notificar cambios al adaptador
        adapter.notifyDataSetChanged();
    }

    /**
     * Método asociado al botón "Descargar Nueva Frase".
     * Descarga una frase desde Internet en un hilo secundario
     * y la guarda en la base de datos.
     *
     * @param view vista que invoca el método
     */
    /**
     * Se ejecuta al pulsar el botón de descargar frase
     * Usa un hilo secundario para no bloquear la aplicación
     */
    public void descargarFrase(View view) {
        // Creamos un nuevo hilo para la descarga
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Descargar frase desde Internet
                Frase nuevaFrase = descargarFraseDeInternet();

                // Volver al hilo principal para actualizar la UI
                // Volvemos al hilo principal para tocar la interfaz
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (nuevaFrase != null) {
                            // Insertar frase en la base de datos
                            long id = frasesDAO.insertFrase(nuevaFrase);

                            if (id != -1) {
                                // Añadir frase a la lista
                                String textoMostrar = "\"" + nuevaFrase.getTexto() + "\"\n- " + nuevaFrase.getAutor();
                                frasesList.add(textoMostrar);
                                adapter.notifyItemInserted(frasesList.size() - 1);

                                Toast.makeText(MainActivity.this,
                                        "Frase añadida",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Descarga una frase desde la API ZenQuotes.
     * Si ocurre un error, devuelve una frase de ejemplo.
     *
     * @return objeto {@link Frase}
     */
    /**
     * Conecta con la API de ZenQuotes y obtiene una frase aleatoria
     * Si algo falla, se devuelve una frase de ejemplo
     */
    private Frase descargarFraseDeInternet() {
        try {
            String jsonResponse = com.pmm.a23.net.HttpUtils
                    .getRequest("https://zenquotes.io/api/random");

            if (jsonResponse != null && !jsonResponse.trim().isEmpty()) {
                try {
                    org.json.JSONArray jsonArray = new org.json.JSONArray(jsonResponse);
                    if (jsonArray.length() > 0) {
                        org.json.JSONObject jsonObject = jsonArray.getJSONObject(0);

                        String texto = "";
                        String autor = "";

                        if (jsonObject.has("q")) {
                            texto = jsonObject.getString("q");
                        }
                        if (jsonObject.has("a")) {
                            autor = jsonObject.getString("a");
                        }

                        if (!texto.isEmpty() && !autor.isEmpty()) {
                            texto = texto.replace("\"", "'");
                            Frase frase = new Frase();
                            frase.setTexto(texto);
                            frase.setAutor(autor);
                            return frase;
                        }
                    }
                } catch (org.json.JSONException e) {
                    return crearFraseEjemplo();
                }
            }
            return crearFraseEjemplo();
        } catch (Exception e) {
            return crearFraseEjemplo();
        }
    }

    /**
     * Crea una frase de ejemplo cuando no hay conexión
     * o ocurre un error.
     *
     * @return objeto {@link Frase}
     */
    private Frase crearFraseEjemplo() {
        String[] frasesEjemplo = {
                "La vida es bella",
                "El conocimiento es poder",
                "Más vale tarde que nunca"
        };

        String[] autoresEjemplo = {
                "Anónimo",
                "Francis Bacon",
                "Refrán popular"
        };

        int indice = (int) (Math.random() * frasesEjemplo.length);
        Frase frase = new Frase();
        frase.setTexto(frasesEjemplo[indice]);
        frase.setAutor(autoresEjemplo[indice]);
        return frase;
    }

    /**
     * Muestra u oculta el RecyclerView con las frases.
     *
     * @param view vista que invoca el método
     */
    /**
     * Muestra u oculta la lista de frases según su estado actual
     */
    public void ocultarFrases(View view) {
        if (mostrarFrases) {
            recyclerView.setVisibility(View.GONE);
            btnMostrarOcultar.setText("Mostrar Frases");
            mostrarFrases = false;
        } else {
            if (frasesList.isEmpty()) {
                Toast.makeText(this,
                        "No hay frases para mostrar",
                        Toast.LENGTH_SHORT).show();
            } else {
                cargarFrasesDeBD();
                recyclerView.setVisibility(View.VISIBLE);
                btnMostrarOcultar.setText("Ocultar Frases");
                mostrarFrases = true;
            }
        }
    }

    /**
     * Elimina todas las frases almacenadas en la base de datos
     * y actualiza la interfaz.
     *
     * @param view vista que invoca el método
     */
    /**
     * Borra todas las frases de la base de datos
     * y limpia la pantalla
     */
    public void eliminarFrase(View view) {
        int frasesEliminadas = frasesDAO.deleteAllFrases();

        frasesList.clear();
        adapter.notifyDataSetChanged();

        recyclerView.setVisibility(View.GONE);
        btnMostrarOcultar.setText("Mostrar Frases");
        mostrarFrases = false;

        Toast.makeText(this,
                "Se eliminaron " + frasesEliminadas + " frases",
                Toast.LENGTH_SHORT).show();
    }
}
