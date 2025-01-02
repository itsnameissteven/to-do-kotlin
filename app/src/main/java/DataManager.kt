import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataManager(context: Context) {
    private val db: SQLiteDatabase

    init {
        val helper = CustomSQLiteOpenHelper(context)
        db = helper.writableDatabase
    }

    companion object {
        const val TABLE_ROW_ID = "_id"
        const val TABLE_ROW_NAME = "name"

        private const val DB_NAME = "to_do"
        private const val DB_VERSION = 1
        private const val TABLE_N = "names"
    }

    fun insert(name: String) {
        val query = "INSERT INTO " + TABLE_N + " (" +
                TABLE_ROW_NAME + ") " + "VALUES (" +
                "'" + name + "'" + ");"
        Log.i("insert", query)

        db.execSQL(query)
    }

    fun delete(id: String){
        val query = "DELETE FROM " + TABLE_N + " WHERE " +
                TABLE_ROW_ID + " = " +
                "'" + id + "'" + ";"
        Log.i("delete", query)
        db.execSQL(query)
    }

    fun selectAll(): Cursor {
        return db.rawQuery("SELECT * FROM " + TABLE_N + ";", null)
    }


    private inner class CustomSQLiteOpenHelper(
        context: Context
    ): SQLiteOpenHelper
        (context, DB_NAME, null, DB_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            val newTableQueryString = ("create table " +
                    TABLE_N + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_NAME
                    + " text not null);")
            db.execSQL(newTableQueryString)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }
}
