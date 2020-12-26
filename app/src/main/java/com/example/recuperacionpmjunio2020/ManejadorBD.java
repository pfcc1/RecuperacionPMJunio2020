package com.example.recuperacionpmjunio2020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManejadorBD extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tareas.db";

    private static final String TABLE_NAME = "tareas";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FECHA";
    private static final String COL_3 = "TAREA";
    private static final String COL_4="ESTADO";


    public ManejadorBD(Context context){
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME +
                "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_2+ " TEXT, " +COL_3+ " TEXT,"+ COL_4 + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertar(String fecha, String tarea, String estado){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fecha);
        contentValues.put(COL_3, tarea);
        contentValues.put(COL_4,estado);


        long resultado = db.insert(TABLE_NAME, null, contentValues);


        if (resultado ==-1) {
            return false;
        }else{
            return true;
        }



        //return (resultado==-1?false:false);

    }

    public boolean actualizarTareaARealizada(String id, String estado){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,estado);

        long resultado = db.update(TABLE_NAME,  contentValues, COL_1+"=?", new String[]{id} );

        db.close();

        return (resultado>0);

    }

    Cursor obtenerNombreTareaRealizada(String id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor estado=db.rawQuery("SELECT * FROM tareas WHERE ID=?",new String[]{id});

        return estado;
    }

   Cursor obtenerNumerodeTareasNoRealizadas(){
        SQLiteDatabase db=this.getReadableDatabase();

        String estado="No realizada";
        Cursor tareasnorealizadas=db.rawQuery("SELECT COUNT(ESTADO) from tareas WHERE ESTADO=?",new String[]{estado});
        return tareasnorealizadas;
    }

    boolean borrar(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int filas_borradas = db.delete(TABLE_NAME, COL_1+"=? AND "+COL_4+"='Realizada'",new String[]{id});
        db.close();

        return (filas_borradas>0);

    }

    Cursor listarTareasRealizadas(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor datos = db.rawQuery("SELECT * FROM tareas WHERE "+COL_4+"='Realizada'",null);
        return datos;

    }

    Cursor listarTareasNoRealizadas(){

        SQLiteDatabase db = this.getReadableDatabase();

        String norealizado="No realizada";
        //Cursor datos = db.rawQuery("SELECT * FROM tareas WHERE "+COL_4+"='No realizado'",null);
        Cursor datos = db.rawQuery("SELECT * FROM tareas WHERE ESTADO=?",new String[]{norealizado});
        return datos;

    }


}
