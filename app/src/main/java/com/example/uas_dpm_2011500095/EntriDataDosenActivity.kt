package com.example.uas_dpm_2011500095

import android.icu.text.RelativeDateTimeFormatter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntriDataDosenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_data_dosen)

        val modeEdit = intent.hasExtra("kode")&& intent.hasExtra("nama")&&
                intent.hasExtra("Jabatan")&& intent.hasExtra("Golpangkat")&&
                intent.hasExtra("pendidikan")&& intent.hasExtra("keahlian")&&
                intent.hasExtra("programstudi")
        title = if (modeEdit)"Edit Data Dosen " else "Entri Data Dosen"

        val etkdnidn = findViewById<EditText>(R.id.etkdnidn)
        val etNmdosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolonganPangkat = findViewById<Spinner>(R.id.spnGolonganPangkat)
        val rds2 = findViewById<RadioButton>(R.id.rds2)
        val rds3 = findViewById<RadioButton>(R.id.rds3)
        val btnSimpan =findViewById<Button>(R.id.btnSimpan)
        val etbidangkeahlian = findViewById<EditText>(R.id.etbidangkeahlian)
        val etprogramstudi = findViewById<EditText>(R.id.etprgoramstudi)
        val jabatan = arrayOf("TenagaPengajar","AsistenAhli","Lektor","LektorKepala","GuruBesar")
        val adpjabatan = ArrayAdapter(
            this@EntriDataDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
            jabatan
        )
        spnJabatan.adapter = adpjabatan

        val golonganpangkat = arrayOf("III/a-Penata Muda","III/b-Penata Muda Tingkat I","III/c-Penata",
            "III/d-Penata tingkat I","IV/a-Pembina","IV/b-Pembina Tingkat I","IV/c-Pembina Utama Muda",
            "IV/d-Pembina Utama Madya","IV/e-Pembina Utama")
        val adpgolonganpangkat = ArrayAdapter(
            this@EntriDataDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
            golonganpangkat
        )
        spnGolonganPangkat.adapter = adpgolonganpangkat

        if(modeEdit){
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val nilaijabatan = intent.getStringExtra("Jabatan")
            val nilaigolpangkat = intent.getStringArrayExtra("GolPangkat")
            val pendidikanterakhir = intent.getStringArrayExtra("pendidikan")
            val bidangkeahlian = intent.getStringArrayExtra("keahlian")
            val programstudi = intent.getStringArrayExtra("programstudi")

            etkdnidn.setText(kode)
            etNmdosen.setText(nama)
            spnJabatan.setSelection(jabatan.indexOf(nilaijabatan))
            if(pendidikanterakhir == rds2) rds2.isChecked = true else rds3.isChecked = true


        }
        etkdnidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener{
            if("${etkdnidn.text}".isNotEmpty()&& "$etNmdosen.text".isNotEmpty()&&
                    (rds2.isChecked || rds3.isChecked)){
                        val db = DbHelper(this@EntriDataDosenActivity)
                db.kdnidn =  "${etkdnidn.text}"
                db.nmdosen = "${etNmdosen.text}"
                db.bidangkeahlian = "${etbidangkeahlian.text}"
                db.programstudi = "${etprogramstudi.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golonganpangkat = spnGolonganPangkat.selectedItem as String
                db.pendidikanterakhir = if(rds2.isChecked)"S2" else "S3"
                if(if(!modeEdit)db.simpan()else db.ubah("$etkdnidn.text")){
                    Toast.makeText(
                        this@EntriDataDosenActivity,
                                "Data Dosen berhasi disimpan",
                                Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@EntriDataDosenActivity,
                        "Data Dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@EntriDataDosenActivity,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
            finish()
        }


    }
}