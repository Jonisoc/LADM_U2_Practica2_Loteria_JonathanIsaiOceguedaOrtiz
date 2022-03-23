package mx.tecnm.tepic.ladm_u2_practica2_loteria_jonathanisaioceguedaortiz

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import mx.tecnm.tepic.ladm_u2_practica2_loteria_jonathanisaioceguedaortiz.databinding.ActivityMainBinding
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    var cartas = arrayOf(R.drawable.carta1, R.drawable.carta2, R.drawable.carta3,R.drawable.carta4, R.drawable.carta5,R.drawable.carta6
        ,R.drawable.carta7,R.drawable.carta8, R.drawable.carta9,R.drawable.carta10, R.drawable.carta11,R.drawable.carta12, R.drawable.carta13,R.drawable.carta14
        ,R.drawable.carta15,R.drawable.carta16, R.drawable.carta17,R.drawable.carta18, R.drawable.carta19,R.drawable.carta20, R.drawable.carta21,R.drawable.carta22
        ,R.drawable.carta23,R.drawable.carta24, R.drawable.carta25,R.drawable.carta26, R.drawable.carta27,R.drawable.carta28, R.drawable.carta29,R.drawable.carta30
        ,R.drawable.carta31,R.drawable.carta32, R.drawable.carta33,R.drawable.carta34, R.drawable.carta35,R.drawable.carta36, R.drawable.carta37,R.drawable.carta38
        ,R.drawable.carta39,R.drawable.carta40, R.drawable.carta41,R.drawable.carta42, R.drawable.carta43,R.drawable.carta44, R.drawable.carta45,R.drawable.carta46
        ,R.drawable.carta47,R.drawable.carta48, R.drawable.carta49,R.drawable.carta50, R.drawable.carta51,R.drawable.carta52, R.drawable.carta53,R.drawable.carta54)

    var nombres = arrayOf("El gallo", "El diablo", "La dama", "El catrin", "El paraguas", "La sirena", "La escalera", "La botella", "El barril", "El arbol", "El melon", "El valiente", "El gorrito", "La muerte", "La pera", "La bandera", "El bandolon", "El violoncello", "La garza", "El pajaro", "La mano", "La bota", "La luna", "El cotorro", "El borracho", "El negrito", "El corazon", "La sandia", "El tambor", "El camarón", "Las jaras", "El músico", "La araña", "El soldado", "La estrella", "El cazo", "El mundo", "El apache", "El nopal", "El alacran", "La rosa", "La calavera", "La campana", "El cantarito", "El venado", "El sol", "La corona", "La chalupa", "El pino", "El pescado", "La palma", "La maceta", "El arpa", "La rana")
    var audios = arrayOf(R.raw.audio1, R.raw.audio2, R.raw.audio3,R.raw.audio4, R.raw.audio5,R.raw.audio6
        ,R.raw.audio7,R.raw.audio8, R.raw.audio9,R.raw.audio10, R.raw.audio11,R.raw.audio12, R.raw.audio13,R.raw.audio14
        ,R.raw.audio15,R.raw.audio16, R.raw.audio17,R.raw.audio18, R.raw.audio19,R.raw.audio20, R.raw.audio21,R.raw.audio22
        ,R.raw.audio23,R.raw.audio24, R.raw.audio25,R.raw.audio26, R.raw.audio27,R.raw.audio28, R.raw.audio29,R.raw.audio30
        ,R.raw.audio31,R.raw.audio32, R.raw.audio33,R.raw.audio34, R.raw.audio35,R.raw.audio36, R.raw.audio37,R.raw.audio38
        ,R.raw.audio39,R.raw.audio40, R.raw.audio41,R.raw.audio42, R.raw.audio43,R.raw.audio44, R.raw.audio45,R.raw.audio46
        ,R.raw.audio47,R.raw.audio48, R.raw.audio49,R.raw.audio50, R.raw.audio51,R.raw.audio52, R.raw.audio53,R.raw.audio54)

    var numeros = ArrayList<Int>(54)
    var restantes = ""

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    var darCartas = scope.launch(EmptyCoroutineContext, CoroutineStart.LAZY){ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        for (i in 0..53){
            numeros.add(i)
        }

        //Collections.shuffle(numeros)
        //numeros.shuffle()

        /*for (i in 0..53){
            System.out.println(numeros[i])
        }*/

        binding.imagen.setImageResource(R.drawable.portada)

        //Correrloteria
        binding.iniciar.setOnClickListener {
            for (i in 0..53){
                numeros[i] = i
            }
            numeros.shuffle()

            restantes = ""
            binding.iniciar.isEnabled = false
            binding.iniciar.setBackgroundColor(Color.rgb(255, 255, 255))
            binding.parar.isEnabled = true
            binding.parar.setBackgroundColor(Color.rgb(4, 135, 25))
            binding.restantes.isEnabled = false
            binding.restantes.setBackgroundColor(Color.rgb(255, 255, 255))

            darCartas = scope.launch(EmptyCoroutineContext, CoroutineStart.LAZY){
                runOnUiThread {
                    binding.imagen.setImageResource(R.drawable.portada)
                }
                val inicio = MediaPlayer.create(this@MainActivity, R.raw.inicio)
                inicio.start()
                delay(2000)
                for(i in 0..53){
                    runOnUiThread {
                        binding.imagen.setImageResource(cartas[numeros[i]])
                    }
                    val sonido = MediaPlayer.create(this@MainActivity, audios[numeros[i]])
                    sonido.start()
                    numeros[i] = -1
                    delay(1800L)
                    sonido.release()
                }
            }
            darCartas.start()
        }

        binding.parar.setOnClickListener {
            darCartas.cancel()
            val inicio = MediaPlayer.create(this@MainActivity, R.raw.loteria)
            inicio.start()
            cartasRestantes(this).start()
            binding.iniciar.isEnabled = true
            binding.iniciar.setBackgroundColor(Color.rgb(4, 135, 25))
            binding.parar.isEnabled = false
            binding.parar.setBackgroundColor(Color.rgb(255, 255, 255))
            binding.restantes.isEnabled = true
            binding.restantes.setBackgroundColor(Color.rgb(222, 0, 0))
        }

        binding.restantes.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Cartas Restantes")
                .setMessage(restantes)
                .setPositiveButton("Regresar",{ d, i -> d.dismiss()})
                .show()
        }
    }
}

class cartasRestantes(activity:MainActivity) : Thread() {
    var activity = activity
    override fun run() {
        super.run()
        for(i in 0..53){
            if (activity.numeros[i]!=-1) activity.restantes += "${activity.nombres[activity.numeros[i]]}\n"
        }
    }
}