package com.example.pruebasjuegos.motor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

import com.example.pruebasjuegos.logica.Logica;
import com.example.pruebasjuegos.motor.interfaces.comunicacion.Contador;
import com.example.pruebasjuegos.motor.interfaces.sprites.Chara;

import java.io.InputStream;

public class ClasesAuxiliares {

    //Obtiene un BitMap de un tamaño determinado, y escalado en función de la asignación de la cuadrícula
    public static Bitmap obtenerBitMap(Context context, int [] datosPantalla,float y, float x,String direccionRelativa){
        try {
            AssetManager mngr = context.getAssets();
            InputStream is = mngr.open(direccionRelativa);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            int ancho =(int) (datosPantalla[2]*x);
            int alto =(int) (datosPantalla[3]*y);
            bitmap = bitmap.createScaledBitmap(bitmap,ancho,alto,true);
            return  bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Obtiene un BitMap "Virgen"
    public static Bitmap obtenerBitmapSinFormato(Context context, String direccionRelativa){
        try {
            AssetManager mngr = context.getAssets();
            InputStream is = mngr.open(direccionRelativa);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return  bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Array de Bitmap a partir de direcciones, tamaños, etc.
    public static Bitmap [] obtenerArrayBitMap(Context context, int [] datosPantalla,float y, float x,String [] direccionRelativa){
        Bitmap [] arrayBitmap = new Bitmap[direccionRelativa.length];
        for (int vuelta = 0; vuelta < direccionRelativa.length; vuelta++){
            arrayBitmap [vuelta] = obtenerBitMap(context,datosPantalla,y,x,direccionRelativa[vuelta]);
        }
        return arrayBitmap;
    }

    //Sección de transiciones
    private static int alpha = 0;
    private static int incremento = 25;

    //Degradados
    public static  Paint pintarAlpha(){
        Paint paint = new Paint();
        if (alpha < 255){
            paint.setAlpha(alpha);
            alpha += incremento;
        }
        return  paint;
    }

    public static  Paint pintarAlpha(int incremento){
        Paint paint = new Paint();
        if (alpha < 255){
            paint.setAlpha(alpha);
            alpha += incremento;
        }
        return  paint;
    }

    public static Paint degradadoAplpha(){
        Paint paint = new Paint();
        if (alpha < 255){
            paint.setAlpha(alpha);
            alpha -= incremento;
        }
        return paint;
    }

    public static void reiniciarDegradadoAlpha(){
        alpha = 255;
    }

    public static void reiniciarPintarAlpha(){
        alpha = 0;
    }

    public static void cambiarIncremento(int valor){
        incremento = valor;
    }

    //Texto
    public static String quitarArticulo(String palabra){
        int contador = 0;
        while (true){
            if (palabra.substring(contador,contador+1).equalsIgnoreCase(" "))
                return palabra.substring(contador+1);
            else
                contador++;
        }
    }

    private final static int [][] lvl_1 = new int[][]{
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,3,0,1,1,1,0},
            {0,0,1,1,1,1,1,1,1,1,0,0},
            {0,0,1,1,1,0,-10,1,1,1,0,0},
            {0,0,1,1,1,0,1,-10,1,1,0,0},
            {0,0,1,1,1,1,1,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
    };

    private final static int [][] lvl_2_1 = new int[][]{
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,-10,0,3,0,0,0,0,0},
            {0,0,1,1,1,1,1,1,1,1,0,0},
            {0,0,1,1,1,0,-10,1,1,1,0,0},
            {0,-10,1,1,1,0,1,0,1,1,0,0},
            {0,0,1,1,1,1,1,1,1,1,-10,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
    };

    private final static int [][] lvl_2_2 = new int[][]{
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,-10,-10,1,3,0,0},
            {0,0,0,0,0,1,1,1,1,1,1,0},
            {0,0,1,-10,0,0,0,1,0,0,0,0},
            {0,0,1,0,0,0,0,1,0,0,0,0},
            {0,-10,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
    };

    private final static int [][] lvl_3_1 = new int[][]{
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,-10,0,0,1,3,0,0,0},
            {0,0,0,-10,0,0,0,1,1,-10,1,1,1,1,1,0,0,0},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
            {0,0,-10,1,0,0,1,1,1,1,0,-10,0,1,1,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,-10,0,0,0,1,1,-10,0},
            {0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,1,-10,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };

    private final static int [][] lvl_3_2 = new int[][]{
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,-10,3,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,0,-10,0,1,-10,0,0,0,0},
            {0,0,0,0,0,1,1,0,1,1,1,0,0,0,0,0},
            {0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,-10,1,1,1,1,1,-10,0,0,1,-10,0},
            {0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,0},
            {0,0,0,0,1,-10,0,0,1,1,0,0,1,0,0,0},
            {0,0,0,0,1,0,0,-10,1,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };


    public static Object [] mapLogicaNivel(Context context, int nivel, Display display, double proporcion){
        Logica logica = null;
        TileMap tileMap = null;
        int [][] matriz = null;
        NavegacionPorMatriz nMatriz = null;
        int [] desviaciónMatriz = new int[]{0,0};
        int [] datosPantalla = tamañoPantalla(display,proporcion);
        Chara chara = null;
        int azar = Logica.generarAleatorios(2,1);
        //azar = 1;
        switch (nivel){
            case 1:
                Log.e("Error1","Llego hasta aqui");
                logica = new Logica(2,2,1,50,1);
                matriz = logica.reasignarMatriz(lvl_1);
                nMatriz = new NavegacionPorMatriz(matriz,new int[] {6,6},3);
                tileMap = new TileMap(context,datosPantalla,nMatriz,"TitlesMaps/LVL_2_1.jpg");
                chara = new Chara(context,datosPantalla, new int[]{
                        ((datosPantalla[0]-tileMap.obtenerAncho())/2)+(datosPantalla[2]*(tileMap.getMatriz().getMatriz()[0].length/2)),
                        ((datosPantalla[1]-tileMap.obtenerAlto())/2)+(datosPantalla[3]*(tileMap.getMatriz().getMatriz().length/2))},
                        new int[]{1,7},"TitlesMaps/Charas.png",12,8, true);
                break;
            case 2:
                logica = new Logica(4,5,1,100,azar);
                if (azar == 1){
                    matriz = logica.reasignarMatriz(lvl_2_1);
                    nMatriz = new NavegacionPorMatriz(matriz,new int[] {3,6},3);
                    desviaciónMatriz = new int[]{0,3};
                    tileMap = new TileMap(context,datosPantalla,nMatriz,"TitlesMaps/LVL_2_1.jpg");
                    chara = new Chara(context,datosPantalla, new int[]{
                            ((datosPantalla[0]-tileMap.obtenerAncho())/2)+(datosPantalla[2]*(tileMap.getMatriz().getMatriz()[0].length/2)),
                            ((datosPantalla[1]-tileMap.obtenerAlto())/2)+(datosPantalla[3]*(tileMap.getMatriz().getMatriz().length/2))},
                            new int[]{4,4},"TitlesMaps/Charas.png",12,8,true);
                }else{
                    matriz = logica.reasignarMatriz(lvl_2_2);
                    nMatriz = new NavegacionPorMatriz(matriz,new int[] {3,9},3);
                    desviaciónMatriz = new int[]{-3,3};
                    tileMap = new TileMap(context,datosPantalla,nMatriz,"TitlesMaps/LVL_2_2.jpg");
                    chara = new Chara(context,datosPantalla, new int[]{
                            ((datosPantalla[0]-tileMap.obtenerAncho())/2)+(datosPantalla[2]*(tileMap.getMatriz().getMatriz()[0].length/2)),
                            ((datosPantalla[1]-tileMap.obtenerAlto())/2)+(datosPantalla[3]*(tileMap.getMatriz().getMatriz().length/2))},
                            new int[]{7,0},"TitlesMaps/Charas.png",12,8);
                }
                break;
            case 3:
                logica = new Logica(8,8,1,220,azar);
                if (azar == 1){
                    matriz = logica.reasignarMatriz(lvl_3_1);
                    nMatriz = new NavegacionPorMatriz(matriz,new int[] {3,14},3);
                    desviaciónMatriz = new int[]{-5,6};
                    tileMap = new TileMap(context,datosPantalla,nMatriz,"TitlesMaps/LVL_3_1.jpg");
                    chara = new Chara(context,datosPantalla, new int[]{
                            ((datosPantalla[0]-tileMap.obtenerAncho())/2)+(datosPantalla[2]*(tileMap.getMatriz().getMatriz()[0].length/2)),
                            ((datosPantalla[1]-tileMap.obtenerAlto())/2)+(datosPantalla[3]*(tileMap.getMatriz().getMatriz().length/2))},
                            new int[]{1,4},"TitlesMaps/Charas.png",12,8,true);
                }else{
                    matriz = logica.reasignarMatriz(lvl_3_2);
                    nMatriz = new NavegacionPorMatriz(matriz,new int[] {3,5},3);
                    desviaciónMatriz = new int[]{3,6};
                    tileMap = new TileMap(context,datosPantalla,nMatriz,"TitlesMaps/LVL_3_2.jpg");
                    chara = new Chara(context,datosPantalla, new int[]{
                            ((datosPantalla[0]-tileMap.obtenerAncho())/2)+(datosPantalla[2]*(tileMap.getMatriz().getMatriz()[0].length/2)),
                            ((datosPantalla[1]-tileMap.obtenerAlto())/2)+(datosPantalla[3]*(tileMap.getMatriz().getMatriz().length/2))},
                            new int[]{10,0},"TitlesMaps/Charas.png",12,8);
                }
                break;
        }
        return new Object[]{tileMap,logica,desviaciónMatriz,chara};
    }

    public static int[] tamañoPantalla(Display display,double proporcion){
        int [] tamaño = new int[4];
        Point size = new Point();
        display.getRealSize(size);
        tamaño [0] = size.x;
        tamaño [1]= size.y;
        tamaño [2] = (int) (tamaño [0]*proporcion);
        tamaño [3] = (int) (tamaño [2]*0.917);
        return tamaño;
    }

    //Parte de Textos

    static private String [] lineaDialogoBuscar = new String[]{"\"Espera, se me olvidan %s.\"",
            "\"...%s. Me he dejado %1$s.\",",
            "\"¿Podré irme algún día?\"",
            "\"No lo puedo creer... %s\"",
            "\"¿Dónde he puesto %s?\""};

    static private String [] lineaDialogoEncontrado = new String[]{"\"¡Te tengo!\"",
            "\"Vamonos de una vez.\"",
            "\"¡Al fín!\"",
            "\"Espero no dejarme nada más.\"",
            "\"¡Voy a llegar tarde!\""};

    static private String [] lineaDialogoBuscarActual;
    static private String [] lineaDialogoEncontradoActual;

    static private boolean controlDialogo = true;

    public static String obtenerDialogo(){
        String retorno = "Esto no es lo que busco.";
        int n = 0;
        if (lineaDialogoBuscarActual == null || lineaDialogoBuscarActual.length == 0){
            lineaDialogoBuscarActual = lineaDialogoBuscar;
            lineaDialogoEncontradoActual = lineaDialogoEncontrado;
        }
        if(controlDialogo){
            n = Logica.generarAleatorios(lineaDialogoBuscarActual.length-1,0);
            retorno = lineaDialogoBuscarActual[n];
            lineaDialogoBuscarActual = actualizarArreglo(lineaDialogoBuscarActual,n);
        }else{
            n = Logica.generarAleatorios(lineaDialogoEncontradoActual.length-1,0);
            retorno = lineaDialogoEncontradoActual[n];
            lineaDialogoEncontradoActual = actualizarArreglo(lineaDialogoEncontradoActual,n);
        }
        return retorno;
    }

    static String[] actualizarArreglo(String[] arreglo, int n){
        String [] nuevoArreglo = new String[arreglo.length-1];
        for (int x = 0; x < nuevoArreglo.length; x++){
            if (x < n)
                nuevoArreglo[x] = arreglo[x];
            else
                nuevoArreglo[x] = arreglo[x+1];
        }
        return nuevoArreglo;
    }

    static public void actualizarControlDialogo(){
        controlDialogo = !controlDialogo;
    }

    static public void actualizarControlDialogo(Boolean valor){
        controlDialogo = valor;
    }
}
