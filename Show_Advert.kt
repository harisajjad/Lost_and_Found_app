package com.example.task71

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Show_Advert : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var databaseClass: db_Class
    private lateinit var noData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_advert)

        databaseClass = db_Class(this)
        recyclerView = findViewById(R.id.lostAndFound)
        noData = findViewById(R.id.noData)

        adapter = Adapter(object: SetOnItemClickListener{
            override fun onItemClickListener(lostAndFound: LostAndFound) {
                val intent = Intent(this@Show_Advert,RemoveAdvert::class.java)
                intent.putExtra("id",lostAndFound.id)
                startActivity(intent)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }
    // This method is called when the activity is resumed
    override fun onResume() {
        super.onResume()

        // Retrieve from the database
        val list = databaseClass.getData()

        // Show or hide
        if (list.isEmpty())
            noData.visibility = View.VISIBLE
        else noData.visibility = View.GONE

        // Update the adapter with the new data
        adapter.submit(list)
    }
}