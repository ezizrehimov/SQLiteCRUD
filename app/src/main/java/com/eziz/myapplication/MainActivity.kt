package com.eziz.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        var db = DataBaseHelper(context)
        btnSave.setOnClickListener {
            var name = editTextName.text.toString()
            var age = editTextNumber.text.toString()
            if (name.isNotEmpty() && age.isNotEmpty()) {
                val user = User(name, age.toInt())
                db.insertData(user)
            } else {
                Toast.makeText(applicationContext, "Melumatlari tam doldurun", Toast.LENGTH_LONG)
                    .show()
            }
        }

        btnRead.setOnClickListener {
            var data = db.readData()
            textResult.text = ""
            for (i in 0 until data.size) {
                textResult.append(
                    data.get(i).id.toString() + " " + data.get(i).adSoyad + " " + data.get(
                        i
                    ).age + "\n"
                )
            }
        }

        btnUpdate.setOnClickListener {
            db.updateData()
            btnRead.performClick()
        }

        btnDel.setOnClickListener {
            db.deleteData()
            btnRead.performClick()
        }
    }
}