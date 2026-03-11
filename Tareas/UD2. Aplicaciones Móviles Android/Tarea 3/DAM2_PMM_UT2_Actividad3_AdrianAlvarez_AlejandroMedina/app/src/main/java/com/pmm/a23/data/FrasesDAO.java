/**
 * FrasesDAO
 *
 * Esta clase es el DAO (Data Access Object) de la aplicación.
 * Se encarga de todas las operaciones relacionadas con la base de datos:
 * guardar frases, obtenerlas y eliminarlas.
 *
 * La actividad nunca accede directamente a SQLite,
 * siempre pasa por esta clase.
 */
package com.pmm.a23.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FrasesDAO {

    // Helper que crea y gestiona la base de datos
    private FrasesDBHelper dbHelper;

    // Objeto para trabajar directamente con SQLite
    private SQLiteDatabase database;

    // Formato de fecha para guardar la fecha como texto
    private SimpleDateFormat dateFormat;

    /**
     * Constructor del DAO.
     * Prepara el helper y el formato de fecha.
     *
     * @param context contexto de la aplicación
     */
    public FrasesDAO(Context context) {
        // Creamos el helper de la base de datos
        dbHelper = new FrasesDBHelper(context);

        // Definimos el formato de fecha que se guardará en la BD
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
    }

    /**
     * Abre la conexión con la base de datos.
     * Debe llamarse antes de realizar cualquier operación.
     */
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexión con la base de datos.
     * Se recomienda llamarlo cuando la actividad se destruye.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Inserta una nueva frase en la base de datos.
     *
     * Además del texto y el autor, se guarda la fecha actual
     * en la columna título para que no quede vacía.
     *
     * @param frase objeto Frase a insertar
     * @return id de la fila insertada o -1 si hay error
     */
    public long insertFrase(Frase frase) {
        // Usamos ContentValues para pasar los datos
        ContentValues values = new ContentValues();

        // Guardamos el texto y el autor
        values.put(FrasesDBHelper.COLUMN_TEXTO, frase.getTexto());
        values.put(FrasesDBHelper.COLUMN_AUTOR, frase.getAutor());

        // Guardamos la fecha actual como título
        String fechaActual = dateFormat.format(new Date());
        values.put(FrasesDBHelper.COLUMN_TITULO, fechaActual);

        // Insertamos la frase en la tabla
        return database.insert(FrasesDBHelper.TABLE_FRASES, null, values);
    }

    /**
     * Obtiene todas las frases guardadas en la base de datos.
     * Las devuelve ordenadas por ID descendente (las más nuevas primero).
     *
     * @return lista de frases
     */
    public List<Frase> getAllFrases() {
        List<Frase> frases = new ArrayList<>();

        // Columnas que queremos obtener
        String[] columns = {
                FrasesDBHelper.COLUMN_ID,
                FrasesDBHelper.COLUMN_TEXTO,
                FrasesDBHelper.COLUMN_AUTOR,
                FrasesDBHelper.COLUMN_TITULO
        };

        // Ordenar por ID descendente
        String orderBy = FrasesDBHelper.COLUMN_ID + " DESC";

        // Realizamos la consulta
        Cursor cursor = database.query(
                FrasesDBHelper.TABLE_FRASES,
                columns,
                null, null, null, null,
                orderBy
        );

        // Recorremos el cursor y creamos los objetos Frase
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Frase frase = new Frase();

                frase.setId(cursor.getInt(
                        cursor.getColumnIndexOrThrow(FrasesDBHelper.COLUMN_ID)));
                frase.setTexto(cursor.getString(
                        cursor.getColumnIndexOrThrow(FrasesDBHelper.COLUMN_TEXTO)));
                frase.setAutor(cursor.getString(
                        cursor.getColumnIndexOrThrow(FrasesDBHelper.COLUMN_AUTOR)));
                frase.setTitulo(cursor.getString(
                        cursor.getColumnIndexOrThrow(FrasesDBHelper.COLUMN_TITULO)));

                frases.add(frase);
            } while (cursor.moveToNext());

            // Cerramos el cursor cuando terminamos
            cursor.close();
        }

        return frases;
    }

    /**
     * Elimina todas las frases de la base de datos.
     *
     * @return número de filas eliminadas
     */
    public int deleteAllFrases() {
        return database.delete(FrasesDBHelper.TABLE_FRASES, null, null);
    }

    /**
     * Devuelve el número total de frases almacenadas.
     *
     * @return cantidad de frases
     */
    public int getCount() {
        Cursor cursor = database.rawQuery(
                "SELECT COUNT(*) FROM " + FrasesDBHelper.TABLE_FRASES,
                null
        );

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }

        return count;
    }
}
