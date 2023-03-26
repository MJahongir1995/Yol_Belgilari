package uz.jahongir.yol_belgilari.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.jahongir.yol_belgilari.db.MyConst.DB_NAME
import uz.jahongir.yol_belgilari.db.MyConst.DESCRIPTION
import uz.jahongir.yol_belgilari.db.MyConst.ID
import uz.jahongir.yol_belgilari.db.MyConst.IMAGE
import uz.jahongir.yol_belgilari.db.MyConst.LIKE
import uz.jahongir.yol_belgilari.db.MyConst.NAME
import uz.jahongir.yol_belgilari.db.MyConst.TABLE_NAME
import uz.jahongir.yol_belgilari.db.MyConst.TYPE
import uz.jahongir.yol_belgilari.models.MyData

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1), MyInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        var query = "create table " + TABLE_NAME + " (" + ID + " integer not null primary key autoincrement unique, " + NAME + " text not null, " + DESCRIPTION + " text not null, " + IMAGE + " text not null,  " + LIKE + " text not null, " + TYPE + " text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    override fun addSign(myData: MyData) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, myData.name)
        contentValues.put(DESCRIPTION, myData.description)
        contentValues.put(IMAGE, myData.image)
        contentValues.put(LIKE, myData.like)
        contentValues.put(TYPE, myData.type)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun getAllSign(): ArrayList<MyData> {
        val list = ArrayList<MyData>()
        val database = readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val myData = MyData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5))
                list.add(myData)

            } while (cursor.moveToNext())
        }
        return list
    }

    override fun deleteSign(myData: MyData) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID =?", arrayOf(myData.id.toString()))
        database.close()
    }

    override fun editSign(myData: MyData) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, myData.id)
        contentValues.put(NAME, myData.name)
        contentValues.put(DESCRIPTION, myData.description)
        contentValues.put(IMAGE, myData.image)
        contentValues.put(LIKE, myData.like)
        contentValues.put(TYPE, myData.type)
        database.update(TABLE_NAME, contentValues, "$ID = ?", arrayOf(myData.id.toString()))
    }
}