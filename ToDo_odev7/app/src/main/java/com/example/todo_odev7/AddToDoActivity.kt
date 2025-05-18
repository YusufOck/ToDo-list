package com.example.todo_odev7



import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddToDoActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: ToDoDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editText = findViewById(R.id.todoEditText)
        saveButton = findViewById(R.id.saveButton)
        dbHelper = ToDoDBHelper(this)

        saveButton.setOnClickListener {
            val name = editText.text.toString()
            if (name.isNotEmpty()) {
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                values.put("name", name)
                db.insert("todos", null, values)
                finish()
            } else {
                Toast.makeText(this, "Lütfen görev giriniz", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
