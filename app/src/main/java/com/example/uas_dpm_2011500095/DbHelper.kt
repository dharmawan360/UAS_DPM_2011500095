package com.example.uas_dpm_2011500095

import android.content.*
import android.database.Cursor
import android.database.sqlite.*

class DbHelper (context: Context):SQLiteOpenHelper(context,"campuss",null,1){
        var kdnidn =""
        var nmdosen= ""
        var jabatan =""
        var golonganpangkat = ""
        var pendidikanterakhir =""
        var bidangkeahlian = ""
        var programstudi =""

    private val lecturer = "datadosen"
    private var sql = ""

    override fun onCreate(db:SQLiteDatabase?){
        sql = """create table $lecturer(
            nidn char(10) primary key,
            nama_dosen varchar(50) not null,
            jabatan varchar(15) not null,
            golongan_pangkat varchar(30) not null,
            pendidikan char(2) not null,
            keahlian varchar(30) not null,
            program_studi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion:Int,newVersion:Int){
        sql = "drop table if exist $lecturer"
        db?.execSQL(sql)
    }
    fun simpan():Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv){
            put("nidn",kdnidn)
            put("nama_dosen",nmdosen)
            put("jabatan",jabatan)
            put("golongan_pangkat",golonganpangkat)
            put("pendidikan",pendidikanterakhir)
            put("keahlian",bidangkeahlian)
            put("program_studi",programstudi)
        }
        val cmd = db.insert(lecturer,null,cv)
        db.close()
        return cmd != -1L
    }

    fun ubah(kode:String):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with (cv){
            put ("nama_dosen",nmdosen)
            put ("jabatan",jabatan)
            put ("golongan_pangkat",golonganpangkat)
            put ("pendidikan",pendidikanterakhir)
            put ("keahlian",bidangkeahlian)
            put ("program_studi",programstudi)
        }
        val cmd = db.update(lecturer,cv,"nidn = ?", arrayOf(kode))
        db.close()
        return cmd !=-1
    }

    fun hapus(kode: String):Boolean{
        val db = writableDatabase
        val cmd = db.delete(lecturer,"nidn = ?", arrayOf(kode))
        return cmd !=-1
    }
    fun tampil(): Cursor{
        val db = writableDatabase
        val reader = db.rawQuery("select * from $lecturer",null)
        return reader

    }
}