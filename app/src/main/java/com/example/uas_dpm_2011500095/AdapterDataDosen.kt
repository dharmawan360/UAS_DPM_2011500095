package com.example.uas_dpm_2011500095

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdapterDataDosen(
    private val getContext: Context,
    private val customListItem:ArrayList<DataDosen>
):ArrayAdapter<DataDosen>(getContext, 0 , customListItem){
    override  fun getView (position:Int,convertView: View?,parent: ViewGroup):View{
        var listLayout = convertView
        val holder : ViewHolder
        if (listLayout == null){
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_data_dosen,parent,false)
            holder = ViewHolder()
            with(holder){
                tvNmdosen = listLayout.findViewById(R.id.tvNmdosen)
                tvKdnidn = listLayout.findViewById(R.id.tvKdnidn)
                tvprogramstudi = listLayout.findViewById(R.id.tvprogramstudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmdosen!!.setText(listItem.nmdosen)
        holder.tvKdnidn!!.setText(listItem.kdnidn)
        holder.tvprogramstudi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context,EntriDataDosenActivity::class.java)
            i.putExtra("kode",listItem.kdnidn)
            i.putExtra("nama", listItem.nmdosen)
            i.putExtra("Jabatan",listItem.jabatan)
            i.putExtra("Golpangkat",listItem.golonganpangkat)
            i.putExtra("pendidikan",listItem.pendidikanterakhir)
            i.putExtra("keahlian",listItem.bidangkeahlian)
            i.putExtra("programstudi",listItem.programstudi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdnidn!!.text
            val nama = holder.tvNmdosen!!.text
            val programstudi = holder.tvprogramstudi!!.text
            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda Yakin akan menghapus data ini?
                    
                    $nama [$kode][$programstudi]
                """.trimIndent())
                setPositiveButton("Ya"){_,_->
                    if(db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data Dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak",null)
                create().show()
            }
        }

        return listLayout!!
    }
}

class ViewHolder{
    internal var tvNmdosen: TextView? = null
    internal var tvKdnidn : TextView? = null
    internal var tvprogramstudi : TextView? = null
    internal var btnEdit : ImageButton? = null
    internal var btnHapus : ImageButton? = null
}

