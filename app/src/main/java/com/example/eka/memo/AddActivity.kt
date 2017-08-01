package com.example.eka.memo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*

/**
 * Created by eka on 2017. 7. 31..
 */
class AddActivity : AppCompatActivity() {

    var position: Int? = null
    var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val metrics: DisplayMetrics = getResources().getDisplayMetrics()
        val screenWidth: Int = (metrics.widthPixels * 0.95).toInt()
        getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        toolBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolBar.title = "Add"
        toolBar.setTitleTextColor(resources.getColor(R.color.colorWhite))
        setSupportActionBar(toolBar)

        position = intent.getIntExtra("position", -1)
        content = intent.getStringExtra("content") ?: ""

        editText!!.setText(content)

        add.setOnClickListener {

            intent.putExtra("position", position!!)
            intent.putExtra("content", editText.text.toString())
            setResult(MainActivity.MY_ADD_CODE, intent)
            finish()
        }
        cancel.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        intent.putExtra("position", position!!)
        intent.putExtra("content", editText.text.toString())
        setResult(MainActivity.MY_ADD_CODE, intent)
        finish()
    }
}