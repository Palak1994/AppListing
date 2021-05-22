package com.jio.app

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.tesseract.PhoneList
import com.jio.tesseract.model.PhoneData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var searchView:SearchView
    var list: List<PhoneData> = ArrayList()
    lateinit var adapter: PhoneDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        var menu1 = menu.findItem(R.id.action_search)
         searchView = menu1.actionView as SearchView
        searching(searchView)


        return true
    }

    override fun onResume() {
        super.onResume()
        list = PhoneList.getInstalledApps(false, this).sortedBy { it.appName?.toUpperCase() }
        adapter = PhoneDataAdapter(this, list)

        phone_list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        phone_list.adapter = adapter
    }
    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var filteredDataList = filter(list, newText);
                adapter.setFilter(filteredDataList as List<PhoneData>)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun filter(dataList: List<PhoneData>, newText: String): List<PhoneData?>? {
        var newText = newText
        newText = newText.toLowerCase()
        var text: String
        var filteredDataList: MutableList<PhoneData?> = ArrayList()
        for (dataFromDataList in dataList) {
            text = dataFromDataList?.appName.toString().toLowerCase()
            if (text.contains(newText)) {
                filteredDataList.add(dataFromDataList)
            }
        }
        return filteredDataList
    }


}