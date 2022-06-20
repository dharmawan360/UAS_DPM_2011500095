package com.example.uas_dpm_2011500095

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adpdatadosen : AdapterDataDosen
    private lateinit var datadosen : ArrayList<DataDosen>
    private lateinit var lvDataDosen: ListView
    private lateinit var linTidakAda : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDataDosen = findViewById(R.id.lvDataDosen)
        linTidakAda = findViewById(R.id.linTidakada)

        datadosen = ArrayList()
        adpdatadosen = AdapterDataDosen(this@MainActivity, datadosen)

        lvDataDosen.adapter = adpdatadosen

        refresh()

        btnTambah.setOnClickListener{
            val i = Intent(this@MainActivity,EntriDataDosenActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume(){
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus)refresh()
    }
    private fun refresh(){
        val db = DbHelper(this@MainActivity)
        val data = db.tampil()
        repeat(datadosen.size){datadosen.removeFirst()}
        if(data.count>0){
            while (data.moveToNext()){
                val datadosen = DataDosen(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpdatadosen.add(datadosen)
                adpdatadosen.notifyDataSetChanged()
            }
            lvDataDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        }else{
            lvDataDosen.visibility = View.GONE
            linTidakAda.visibility= View.VISIBLE
        }
    }
}