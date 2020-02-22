package mx.edu.ittepic.ladm_u1_practica_braylosky_ramirez_fletes_20xx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    var vall = ""

    var posicion = 0

    val vecsito : Array<Int> = Array(10,{0})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            insertarVector()
        }
        button2.setOnClickListener {
            ver()
        }

        button3.setOnClickListener {
            guardarArchivoSD()
        }
        button3.setOnClickListener {
            leerArchivoSD()
        }
    }


    fun noSD() : Boolean{
        var estado = Environment.getExternalStorageState()
        if(estado != Environment.MEDIA_MOUNTED){
            return true
        }
        return false
    }


    fun mensaje(m:String){
        AlertDialog.Builder(this).setTitle("ATENCIÓN").setMessage((m)).setPositiveButton("OK"){d,i->}.show()

    }

    fun ver() {
        vall = ""
        (0..9).forEach { vall = vall + vecsito[it]
            if(it<9)
                vall = vall + ","
        }

        mensaje(vall)
    }


    fun guardarArchivoSD() {
        if (noSD()) {
            mensaje("No Existe Memoria SD")
            return
        }
        try {
            var rutaSD = Environment.getExternalStorageDirectory()
            var data = editText3.text.toString()

            var flujo = File(rutaSD.absolutePath, data)
            var flujoSalida = OutputStreamWriter(FileOutputStream(flujo))

            flujoSalida.write(vall)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("Exito al Guardar en Memoria SD")
            limpiarCampos()

        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }


    fun leerArchivoSD() {
        var nombreleer = editText4.text.toString()

        if (noSD()) {
            mensaje("NO TIENE MEMORIA EXTERNA")
            return
        }

        try
        {
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath, nombreleer)
            var flujoEntrada = BufferedReader(InputStreamReader(FileInputStream(datosArchivo)))
            var data = flujoEntrada.readLine()

            mensaje(data)

            var valoresSD = data.split(",")

            (0..9).forEach {
                vecsito[it] = valoresSD[it].toInt()
            }


            flujoEntrada.close()

        }catch (error:IOException) {
            mensaje(error.message.toString())
        }
    }

    private fun limpiarCampos() {
        editText.setText("")
        editText2.setText("")
        editText3.setText("")
        editText4.setText("")
    }



    private fun insertarVector() {
        if (editText2.text.isEmpty() || editText.text.isEmpty() ) {
            mensaje("Se Necesita informacion en cada campo")
            return

        }
        var value = editText.text.toString().toInt()
        var posicion = editText2.text.toString().toInt()
        vecsito[posicion] = value
        limpiarCampos()
        mensaje("Se Inserto Correctamente el valor")
    }

}
