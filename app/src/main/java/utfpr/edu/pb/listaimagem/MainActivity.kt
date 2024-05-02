package utfpr.edu.pb.listaimagem

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import utfpr.edu.pb.listaimagem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var uriArrayList: ArrayList<Uri>
    private lateinit var recyclerView: RecyclerView
    val Read_Permission: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerViewGalleryImages
        uriArrayList = ArrayList<Uri>()
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Read_Permission
            )
        }
        binding.pitch.setOnClickListener(){
            var intent = Intent()
            intent.setType("image/*")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            }
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent,"Sekect Picture"),1)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            if(data?.clipData != null){
                var x = data.clipData!!.itemCount
                for (i in 0 until x){
                    uriArrayList.add(data.clipData!!.getItemAt(i).uri)
                }
            }
            recyclerView.adapter?.notifyDataSetChanged()
            binding.text.setText("PHOTOS("+ uriArrayList.size + ")")
        }else  if(data?.clipData != null){
            var imageURI = data.data?.path
            uriArrayList.add(Uri.parse(imageURI))
        }
        println(uriArrayList.get(0))
        recyclerView.adapter = RecyclerAdapter(this, uriArrayList)
    }
}