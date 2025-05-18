package com.example.todo_odev7


import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var dbHelper: ToDoDBHelper
    private var todoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        editText = findViewById(R.id.todoEditText)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)
        dbHelper = ToDoDBHelper(this)

        todoId = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("name")
        editText.setText(name)

        updateButton.setOnClickListener {
            val newName = editText.text.toString()
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("name", newName)
            db.update("todos", values, "id = ?", arrayOf(todoId.toString()))
            finish()
        }

        deleteButton.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("todos", "id = ?", arrayOf(todoId.toString()))
            finish()
        }
    }
}
