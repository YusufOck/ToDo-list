package com.example.todo_odev7


import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var searchEditText: EditText
    private lateinit var addButton: Button
    private lateinit var dbHelper: ToDoDBHelper
    private lateinit var adapter: ArrayAdapter<String>
    private var todoList = mutableListOf<ToDo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        searchEditText = findViewById(R.id.searchEditText)
        addButton = findViewById(R.id.addButton)
        dbHelper = ToDoDBHelper(this)

        loadToDos()

        addButton.setOnClickListener {
            startActivity(Intent(this, AddToDoActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", todoList[position].id)
            intent.putExtra("name", todoList[position].name)
            startActivity(intent)
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterToDos(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadToDos() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM todos", null)
        todoList.clear()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                todoList.add(ToDo(id, name))
            } while (cursor.moveToNext())
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todoList.map { it.name })
        listView.adapter = adapter
        cursor.close()
    }

    private fun filterToDos(query: String) {
        val filtered = todoList.filter { it.name.contains(query, ignoreCase = true) }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filtered.map { it.name })
        listView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadToDos()
    }
}

