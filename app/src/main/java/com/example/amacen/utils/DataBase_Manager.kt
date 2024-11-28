package com.example.amacen.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.amacen.data.ArticuloClass
import com.example.amacen.data.ArticuloClass.Dimensions
import com.example.amacen.data.ArticuloClass.Meta
import com.example.amacen.data.ArticuloClass.Review

class DataBase_Manager () {

  /* (
}context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    COMENTADO PARA QUE SEA FUNCIONAL


    companion object {
        const val DATABASE_VERSION = 1                        // Si cambias el esquema de la BBDD, debes incrementar la version de la BBDD
        const val DATABASE_NAME = "Almacen.db"

        private const val SQL_CREATE_TABLE_ARTICULOS =
//            "create table ${ArticuloClass.Companion.TABLE_NAME} (" +
            "create table ARTICULOS (" +
                "${ArticuloClass.COLUMN_ID}                     INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${ArticuloClass.COLUMN_CATEGORY}               STRING," +
                "${ArticuloClass.COLUMN_BRAND}                  STRING," +
                "${ArticuloClass.COLUMN_TITLE}                  STRING," +
                "${ArticuloClass.COLUMN_DESCRIPCION}            STRING," +
                "${ArticuloClass.COLUMN_PRICE}                  DOUBLE," +
                "${ArticuloClass.COLUMN_DISCOUNTPERCENTAGE}     DOUBLE," +
                "${ArticuloClass.COLUMN_MINIMUMORDERQUANTITY}   INT," +
                "${ArticuloClass.COLUMN_RETURNPOLICY}           STRING," +
                "${ArticuloClass.COLUMN_SHIPPINGINFORMATION}    STRING," +
                "${ArticuloClass.COLUMN_RATING}                 DOUBLE," +
                "${ArticuloClass.COLUMN_SKU}                    STRING," +
                "${ArticuloClass.COLUMN_STOCK}                  INT," +
                "${ArticuloClass.COLUMN_THUMBNAIL}              STRING," +
                "${ArticuloClass.COLUMN_WARRANTYINFORMATION}    STRING," +
                "${ArticuloClass.COLUMN_WEIGHT}                 INT," +
                "${ArticuloClass.COLUMN_META}                   STRING)"

        private const val SQL_CREATE_TABLE_DIMENSIONS =
            "create table DIMENSIONS (" +
                    "${Dimensions.COLUMN_ID}           INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Dimensions.COLUMN_DEPTH}        DOUBLE," +
                    "${Dimensions.COLUMN_HEIGHT}       DOUBLE," +
                    "${Dimensions.COLUMN_WIDTH}        DOUBLE)"

        private const val SQL_CREATE_TABLE_META =
            "create table META (" +
                    "${Meta.COLUMN_ID}           INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Meta.COLUMN_BARCODE}      STRING," +
                    "${Meta.COLUMN_CREATEAT}     STRING," +
                    "${Meta.COLUMN_QRCODE}       STRING)," +
                    "${Meta.COLUMN_UPDATEAT}     STRING)"

        private const val SQL_CREATE_TABLE_REVIEW =  "create table REVIEW (" +
                "(${Review.COLUMN_ID}           INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "${Review.COLUMN_COMENT}       STRING," +
                 "${Review.COLUMN_DATE}         STRING," +
                 "${Review.COLUMN_RATING}       INT," +
                 "${Review.COLUMN_REVIEWERMAIL} STRING)," +
                 "${Review.COLUMN_REVIEWNAME}   STRING)"

        private const val SQL_DELETE_TABLE_ARTICULOS = "DROP TABLE IF EXISTS Articulos"
        private const val SQL_DELETE_TABLE_DIMENSIONS = "DROP TABLE IF EXISTS DIMENSIONS"
        private const val SQL_DELETE_TABLE_META = "DROP TABLE IF EXISTS META"
        private const val SQL_DELETE_TABLE_REVIEW = "DROP TABLE IF EXISTS REVIEW"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_ARTICULOS)
        db.execSQL(SQL_CREATE_TABLE_DIMENSIONS)
        db.execSQL(SQL_CREATE_TABLE_META)
        db.execSQL(SQL_CREATE_TABLE_REVIEW)
    }

    private fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE_ARTICULOS)
        db.execSQL(SQL_DELETE_TABLE_DIMENSIONS)
        db.execSQL(SQL_DELETE_TABLE_META)
        db.execSQL(SQL_DELETE_TABLE_REVIEW)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

 */
}