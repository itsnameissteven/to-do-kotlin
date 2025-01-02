import com.example.todothree.Item
import com.example.todothree.R


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CustomAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : ArrayAdapter<Item>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        val dm = DataManager(context)
        // Find views in the layout
        val itemText = view.findViewById<TextView>(R.id.itemText)
        val deleteIcon = view.findViewById<ImageView>(R.id.deleteIcon)

        // Bind data to views
        itemText.text = items[position].name

        // Handle delete button click
        deleteIcon.setOnClickListener {
            dm.delete(items[position].id.toString())
            items.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
        }

        return view
    }
    override fun getItem(position: Int) : Item {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }
}
