package com.example.pruebasjuegos.baseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class OperacionesBD {


    private static Context cont;

    public static void enviarContexto(Context context){
        cont = context;
    }

    public static void reiniciarBD(){
        String[] checkDB = null;
        try {
            checkDB = cont.databaseList();
            for (int i = 0; i<checkDB.length; i++){
                borrarBD(checkDB[i]);
                Log.w("BASE DE DATOS BORRADA", checkDB[i]);
            }
        } catch (SQLiteException e) {
            Log.e("Error", e.getMessage());
        }
    }

    public static boolean existenciaBD(String name){
        try {
            String[] checkDB = cont.databaseList();
            for (int i = 0; i < checkDB.length; i++){
                Log.w("BASE DE DATOS", checkDB[i]);
                if (checkDB[i].equalsIgnoreCase(name)){
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return false;
    }

    public static SQLiteDatabase openBD(){
        SQLiteDatabase db= cont.openOrCreateDatabase("N47", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS n47(id INTEGER PRIMARY KEY AUTOINCREMENT, nvlAlcanzado INTEGER,maxPuntuacion INTEGER);");
        return db;
    }

    public static void inicializarBaseDatos(){
        SQLiteDatabase db = openBD();
        String sql = "INSERT INTO n47 VALUES(0,1,0)";
        SQLiteStatement insert = db.compileStatement(sql);
        insert.executeInsert();
        insert.close();
        db.close();
    }


    public static void borrarBD(String name){
        cont.deleteDatabase(name);
    }

    public static void insertFilasBD(ArrayList<String> sql){
        SQLiteDatabase db = openBD();
        for (String sentence: sql){
            db.execSQL(sentence);
        }
        db.close();
    }

    public static void atualizarFilaBD(String sql){
        SQLiteDatabase db = openBD();
        db.execSQL(sql);
        db.close();
    }

    public static ArrayList consulArrayBD(String sql){
        SQLiteDatabase db = null;
        Cursor cr = null;
        ArrayList <String> lista = new ArrayList<String>();
        try{
            db = openBD();
            cr = db.rawQuery(sql,null);
            if (cr.getCount()!=0){
                while (cr.moveToNext()){
                    lista.add(cr.getString(0));
                }
            }
            cr.close();
            db.close();
            return lista;
        }catch (Exception e){
            Log.w("Error al lista", e.getMessage());
        }
        return null;
    }

    public static int consulBD(String sql){
        SQLiteDatabase db = null;
        Cursor cr = null;
        int lista = 0;
        try{
            db = openBD();
            cr = db.rawQuery(sql,null);
            if (cr.moveToFirst())
                lista =(int) cr.getFloat(0);
            cr.close();
            db.close();
            return lista;
        }catch (Exception e){
            Log.w("Error al lista", e.getMessage());
        }
        return 0;
    }

}
