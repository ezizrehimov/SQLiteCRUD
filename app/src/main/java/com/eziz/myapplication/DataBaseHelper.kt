package com.eziz.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val databaseName = "Value"
val tableName = "Users"
val colName = "adSoyad"
val colAge = "age"
val colId = "id"

class DataBaseHelper(var context: Context) : SQLiteOpenHelper(context, databaseName, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
//        Dtabase i yaradanda 1 defe isleyir
        var createTable =
            " CREATE TABLE " + tableName + " (" + colId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colName + " VARCHAR(256)," + colAge + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//    Database i qaldirmaq ucun istifade olunur

    }


    fun insertData(user: User) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(colName, user.adSoyad)
        cv.put(colAge, user.age)
        var result = db.insert(tableName, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Inserted", Toast.LENGTH_LONG).show()
        }
    }

    fun readData(): MutableList<User> {
        var list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        var sql = " SELECT *  FROM " + tableName
        var result = db.rawQuery(sql, null)
        if (result.moveToFirst()) {
            do {
                var user = User()
                user.id = result.getString(result.getColumnIndex(colId)).toInt()
                user.adSoyad = result.getString(result.getColumnIndex(colName))
                user.age = result.getString(result.getColumnIndex(colAge)).toInt()
                list.add(user)

            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun updateData() {
        val db = this.readableDatabase
        var sql = "SELECT * FROM $tableName"
        var result = db.rawQuery(sql, null)
        if (result.moveToFirst()) {
            do {
                var cv = ContentValues()
                cv.put(colAge, (result.getInt(result.getColumnIndex(colAge))) + 1)
                cv.put(colName, (result.getString(result.getColumnIndex(colName))) + " " + "++")
                db.update(
                    tableName, cv, "$colId=? AND $colName=?",
                    arrayOf(
                        result.getString(result.getColumnIndex(colId)), result.getString(
                            result.getColumnIndex(
                                colName
                            )
                        )
                    )
                )
            } while (result.moveToNext())
            result.close()
            db.close()
        }

    }

    fun deleteData() {
        val db = this.writableDatabase
        db.delete(tableName, null, null)
        db.close()
    }
}