package my.first.github.utils

import android.app.AlertDialog
import android.content.Context

class DialogHelper {
    companion object{
        fun buildDialog(context: Context, message: String){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder
            .setTitle("Error")
            .setMessage(message)

            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}