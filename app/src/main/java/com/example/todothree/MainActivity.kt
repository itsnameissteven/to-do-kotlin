package com.example.todothree

import CustomAdapter
import DataManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todothree.databinding.ActivityMainBinding
data class Item(val name: String, val id: Number)

class MainActivity  : AppCompatActivity(), NewTaskDialogFragment.NewTaskDialogListener {
    private lateinit var binding: ActivityMainBinding
    private var todoListItems = ArrayList<Item>()
    private var listView: ListView? = null
    private var listAdapter: CustomAdapter? = null
    private var dm: DataManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dm = DataManager(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        fun showNewTaskUI() {
            val newFragment = NewTaskDialogFragment.newInstance(R.string.add_new_task_dialog_title)
            newFragment.show(supportFragmentManager, "newtask")
        }
        binding.fab.setOnClickListener {showNewTaskUI()}
        listView = findViewById(R.id.list_view)
        populateListView()
    }
    override fun onDialogPositiveClick(dialog: DialogFragment, task:String) {
        dm?.insert(task)
        todoListItems.add(Item(task, todoListItems.size))
        listAdapter?.notifyDataSetChanged()
        Snackbar.make(binding.fab, "Task Added Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }
    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }
    private fun populateListView() {
        val cursor = dm?.selectAll()
        while (cursor!!.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            todoListItems.add(Item(name, id))
        }
        listAdapter = CustomAdapter(this, todoListItems)
        listView?.adapter = listAdapter
    }
}
