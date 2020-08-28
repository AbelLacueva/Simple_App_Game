package com.example.pruebasjuegos.motor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.pruebasjuegos.MainActivity;
import com.example.pruebasjuegos.logica.Logica;
import com.example.pruebasjuegos.motor.interfaces.comunicacion.Contador;
import com.example.pruebasjuegos.motor.interfaces.comunicacion.ObjetosEncontrados;
import com.example.pruebasjuegos.motor.interfaces.inicioFinJuego.FinPartida;
import com.example.pruebasjuegos.motor.interfaces.sprites.Chara;
import com.example.pruebasjuegos.motor.interfaces.sprites.CharaBallon;
import com.example.pruebasjuegos.motor.interfaces.controles.Cruceta;
import com.example.pruebasjuegos.motor.interfaces.controles.Evento;


public class Juego extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

    private SurfaceHolder holder;
    private BucleJuego bucle;
    private Activity activity;
    private Context context;
    private TileMap tileMap;
    private Logica logica;
    private Chara personaje;
    private Cruceta cruceta;
    private Evento evento;
    private Contador contador;
    private ObjetosEncontrados objetosEncontrados;
    private FinPartida finPartida;

    private int [] datosPantalla;
    public static int [] cMo;

    private static final String TAG = Juego.class.getSimpleName();

    public Juego(Activity activity, Context context, TileMap tileMap, Logica logica, int [] desviacion, Chara chara) {
        super(context);

        this.activity = activity;
        this.context = context;
        this.datosPantalla = tileMap.getDatosPantalla();
        this.tileMap = tileMap;
        this.logica = logica;

        //Inicializacion de la posición
        cMo = new int[]{((datosPantalla[0]-tileMap.obtenerAncho())/2)+(desviacion[0]*datosPantalla[2]),
                ((datosPantalla[1]-tileMap.obtenerAlto())/2)-datosPantalla[3]+(desviacion[1]*datosPantalla[3])};

        //Asignación del personaje
        personaje = chara;

        //Controles
        this.cruceta = new Cruceta(cMo,new int []{
                datosPantalla[2]*2,datosPantalla[2]*3,datosPantalla[1]-datosPantalla[3]*2,datosPantalla[1]-datosPantalla[3],
                datosPantalla[2],datosPantalla[2]*2,datosPantalla[1]-datosPantalla[3],datosPantalla[1],
                0,datosPantalla[2],datosPantalla[1]-datosPantalla[3]*2,datosPantalla[1]-datosPantalla[3],
                datosPantalla[2],datosPantalla[2]*2,datosPantalla[1]-datosPantalla[3]*3,datosPantalla[1]-datosPantalla[3]*2
        },ClasesAuxiliares.obtenerBitMap(context,datosPantalla,1,1,"TitlesMaps/flecha.ico"),
                personaje,
                datosPantalla,
                tileMap.getMatriz());

        //Creación del control de eventos
        evento = new Evento(new int[]{
                ((datosPantalla[0]-tileMap.obtenerAncho())/2)+(datosPantalla[2]*(tileMap.getMatriz().getMatriz()[0].length/2)),
                ((datosPantalla[1]-tileMap.obtenerAlto())/2)+(datosPantalla[3]*(tileMap.getMatriz().getMatriz().length/2))},
                ClasesAuxiliares.obtenerBitMap(context,datosPantalla,1,1,"TitlesMaps/lupaReducida.png"),
                tileMap.getMatriz(), datosPantalla,
                new CharaBallon(context,datosPantalla, new int[]{
                        personaje.getCentroMatriz()[0],
                        personaje.getCentroMatriz()[1] - (int) (datosPantalla[3]*1.75f) },
                new int[]{0,0},"TitlesMaps/Balloon.png",8,16),
                logica);

        //Contador de tiempo y objetos
        this.contador = new Contador(new int []{datosPantalla[0]-(datosPantalla[3]*11/5),
                datosPantalla[3]*4/5}, datosPantalla[3]*4/5, logica);
        this.objetosEncontrados = new ObjetosEncontrados(new int []{datosPantalla[2]*3/4,datosPantalla[3]*3/4},
                ClasesAuxiliares.obtenerArrayBitMap(context,datosPantalla,0.75f,0.75f,logica.getRutas()));

        //otras interfaces

        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);

        /*
        logica.setFinJuego(true);
        logica.setVictoria(true);*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */

    public void actualizar() {
        if (!logica.isFinJuego()){
            evento.actualizarEvento();
            cruceta.actualizarTiempo(logica.actualizarLogicaTemporal());
            cruceta.movimiento();
            objetosEncontrados.actulizarEncontrados(logica.getEncontrados());
        }else{
            if(finPartida == null){
                Bitmap bm = null;
                if (logica.isVictoria())
                    bm = ClasesAuxiliares.obtenerBitmapSinFormato(context,"Cuadros/titulo_victoria.png");
                else
                    bm = ClasesAuxiliares.obtenerBitmapSinFormato(context,"Cuadros/titulo_derrota.png");
                finPartida = new FinPartida(datosPantalla,
                        bm, ClasesAuxiliares.obtenerBitMap(context,datosPantalla,1,1,"TitlesMaps/flecha.ico"),
                        logica.getEncontrados().size(),logica.getSegundos(),logica.isVictoria());
            }
            //Logica de fin de juego
        }

    }

    public void renderizar(Canvas canvas){
        if (canvas != null)
            try{
                //Parte del Mapa y el fondo
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(tileMap.getBitmap(),cMo[0],cMo[1],null);

                //Parte del personajes
                personaje.dibujarChara(canvas);


                if (logica.isFinJuego()){
                    finPartida.dibujarFinPartida(canvas);
                    //Pintado de elementos.
                }else{
                    //Parte de los Controles
                    cruceta.pintarCruceta(canvas);

                    //Parte de los eventos
                    evento.pintarEvento(canvas);

                    //Parte de la informacion
                    contador.dibujarCronometro(canvas);
                    objetosEncontrados.dibujarEncontrados(canvas);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
    }

    public void fin(){
        MainActivity.mediaPlayer.stop();
        bucle.fin();
        activity.finish();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (logica.pulsadoFinJuego()){
            if (event.getX() > datosPantalla[0] - (datosPantalla[2]*1.1f) && event.getX() < datosPantalla[0] -(datosPantalla[2]*0.1f) &&
                    event.getY() > datosPantalla[1] - (datosPantalla[3]*2.2f) && event.getY() < datosPantalla[1] -(datosPantalla[3]*1.2f))
                this.fin();
        }else
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    evento.ejecutarEvento((int) event.getX(),(int) event.getY());
                    cruceta.realizarMovimiento((int) event.getX(),(int) event.getY());
                    break;
            case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    cruceta.setControlPulsado(0);
                    break;
            case MotionEvent.ACTION_MOVE:
                boolean bdp = cruceta.botonDiferentePulsado((int) event.getX(),(int) event.getY());
                if (!cruceta.botonPulsado((int) event.getX(),(int) event.getY()) ||
                        bdp){
                    cruceta.setControlPulsado(0);
                    if (bdp)
                        cruceta.realizarMovimiento((int) event.getX(),(int) event.getY());
                }
                break; }
        return true;
    }

}
