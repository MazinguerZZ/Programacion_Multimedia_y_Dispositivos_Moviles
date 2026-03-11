/**
 * FrasesDBHelper
 *
 * Esta clase se encarga de crear y gestionar la base de datos SQLite
 * de la aplicación.
 *
 * Aquí se define:
 *  - El nombre de la base de datos
 *  - La versión
 *  - La estructura de la tabla de frases
 *
 * Android llama automáticamente a onCreate() y onUpgrade()
 * cuando es necesario.
 */
package com.pmm.a23.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FrasesDBHelper extends SQLiteOpenHelper {

    // ==============================
    // DATOS DE LA BASE DE DATOS
    // ==============================

    // Nombre del archivo de la base de datos
    private static final String DATABASE_NAME = "frases_motivadoras.db";

    // Versión de la base de datos
    // Si se cambia, se ejecutará onUpgrade()
    private static final int DATABASE_VERSION = 1;

    // ==============================
    // TABLA Y COLUMNAS
    // ==============================

    // Nombre de la tabla
    public static final String TABLE_FRASES = "frases";

    // Columnas de la tabla
    public static final String COLUMN_ID = "_id";       // Identificador único
    public static final String COLUMN_TEXTO = "texto";   // Texto de la frase
    public static final String COLUMN_AUTOR = "autor";   // Autor de la frase
    public static final String COLUMN_TITULO = "titulo"; // Título opcional

    // ==============================
    // SENTENCIAS SQL
    // ==============================

    // Sentencia SQL para crear la tabla de frases
    // No se usan valores por defecto para evitar problemas
    private static final String CREATE_TABLE_FRASES =
            "CREATE TABLE " + TABLE_FRASES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEXTO + " TEXT NOT NULL, " +
                    COLUMN_AUTOR + " TEXT, " +
                    COLUMN_TITULO + " TEXT" +
                    ")";

    // Sentencia SQL para eliminar la tabla si existe
    private static final String DROP_TABLE_FRASES =
            "DROP TABLE IF EXISTS " + TABLE_FRASES;

    /**
     * Constructor del helper.
     * Se llama una sola vez cuando se crea el objeto.
     *
     * @param context contexto de la aplicación
     */
    public FrasesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Se ejecuta automáticamente la primera vez que se crea la base de datos.
     * Aquí se crean las tablas necesarias.
     *
     * @param db base de datos recién creada
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecutamos la sentencia SQL de creación de la tabla
        db.execSQL(CREATE_TABLE_FRASES);
    }

    /**
     * Se ejecuta cuando se incrementa la versión de la base de datos.
     * En este caso se elimina la tabla antigua y se vuelve a crear.
     *
     * @param db base de datos
     * @param oldVersion versión anterior
     * @param newVersion versión nueva
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borramos la tabla antigua
        db.execSQL(DROP_TABLE_FRASES);

        // Creamos de nuevo la base de datos
        onCreate(db);
    }
}
