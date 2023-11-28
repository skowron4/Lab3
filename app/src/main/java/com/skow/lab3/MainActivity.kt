package com.skow.lab3

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var sizeItemId = R.id.mediumText
    private var fontItemId = R.id.normalFont
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Menu"

        registerForContextMenu(findViewById(R.id.rightActivityButton))
        registerForContextMenu(findViewById(R.id.leftActivityButton))
    }

    private fun setPrefs(themeNum: Int): Boolean {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("theme_num", themeNum)
        editor.apply()
        recreate()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuTheme1 -> setPrefs(4)
            R.id.menuTheme2 -> setPrefs(2)
            R.id.menuTheme3 -> setPrefs(3)
            R.id.menuTheme4 -> setPrefs(1)
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyTheme() {
        val data = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        when (data.getInt("theme_num", 0)) {
            1 -> setTheme(R.style.AppTheme1)
            2 -> setTheme(R.style.AppTheme2)
            3 -> setTheme(R.style.AppTheme3)
            4 -> setTheme(R.style.Theme_Lab3)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v == findViewById(R.id.leftActivityButton)) {
            menuInflater.inflate(R.menu.fonttype, menu)
            menu?.findItem(fontItemId)?.isChecked = true
        } else if(v == findViewById(R.id.rightActivityButton)) {
            menuInflater.inflate(R.menu.fontsize, menu)
            menu?.findItem(sizeItemId)?.isChecked = true
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.smallText,
            R.id.mediumText,
            R.id.largeText -> {
                item.isChecked = !item.isChecked
                when (item.itemId) {
                    R.id.smallText -> {
                        findViewById<Button>(R.id.rightActivityButton).textSize = 10F
                    }
                    R.id.mediumText -> {
                        findViewById<Button>(R.id.rightActivityButton).textSize = 14F
                    }
                    R.id.largeText -> {
                        findViewById<Button>(R.id.rightActivityButton).textSize = 20F
                    }
                }
                sizeItemId = item.itemId
                return true
            }
            R.id.normalFont,
            R.id.boldFont,
            R.id.italicFont -> {
                item.isChecked = !item.isChecked
                when (item.itemId) {
                    R.id.normalFont -> {
                        findViewById<Button>(R.id.leftActivityButton).setTypeface(null, Typeface.NORMAL)
                    }
                    R.id.boldFont -> {
                        findViewById<Button>(R.id.leftActivityButton).setTypeface(null, Typeface.BOLD)
                    }
                    R.id.italicFont -> {
                        findViewById<Button>(R.id.leftActivityButton).setTypeface(null, Typeface.ITALIC)
                    }
                }
                fontItemId = item.itemId
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}