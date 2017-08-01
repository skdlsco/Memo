package com.example.eka.memo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        val MY_ADD_CODE: Int = 1000
        val MY_SET_CODE: Int = 2000
    }

    lateinit var pref: SharedPreferences
    lateinit var pref_edit: SharedPreferences.Editor
    val gson: Gson = Gson()

    var items: ArrayList<item> = ArrayList()
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getPreferences(Context.MODE_PRIVATE)
        pref_edit = pref.edit()

        if (pref.getString("items", "") != ("")) {
            items = gson.fromJson<ArrayList<item>>(pref.getString("items", ""), object : TypeToken<ArrayList<item>>() {}.type)
        }

        toolBar?.run {
            toolBar.setTitleTextColor(resources.getColor(R.color.colorWhite))
            setSupportActionBar(toolBar)
            supportActionBar!!.title = "MEMO"
        }
        adapter = MyAdapter(this@MainActivity, items)
        textListView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        textListView.adapter = adapter
        val myCallback: MyItemTouchHelperCallBack = MyItemTouchHelperCallBack(textListView, adapter)
        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(myCallback)
        itemTouchHelper.attachToRecyclerView(textListView)
        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onItemClick(view: View?, position: Int) {
                val intent: Intent = Intent(this@MainActivity, AddActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("content", items.get(position).content)
                startActivityForResult(intent, MY_SET_CODE)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): kotlin.Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.add -> {
                val intent: Intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivityForResult(intent, MY_ADD_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        when (requestCode) {
            MY_ADD_CODE -> {
                val content: String = data.getStringExtra("content")
                val date: Date = Date()
                items.add(0, item(date, content))
                adapter.notifyItemInserted(0)
            }
            MY_SET_CODE -> {
                val position: Int = data.getIntExtra("position", -1)
                val content: String = data.getStringExtra("content")
                if (position == -1)
                    return
                items.set(position, item(items.get(position).date, content))
                adapter.notifyItemChanged(position)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        pref_edit.putString("items", gson.toJson(items))
                .apply()
        super.onDestroy()
    }
}
