package utfpr.edu.pb.listaimagem

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import utfpr.edu.pb.listaimagem.databinding.CustomSingleImageBinding

class RecyclerAdapter(val context: Context,  imagens : ArrayList<Uri>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolderImage>(){

    private var imagens1  =  imagens.toMutableList()

    inner class ViewHolderImage( val binding: CustomSingleImageBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {

        }
        fun vincula (uri: Uri){
            binding.image.setImageURI(uri)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ViewHolderImage {
        val layout = LayoutInflater.from(context)
        println(imagens1.get(0))
        val binding = CustomSingleImageBinding.inflate(layout, parent, false)
        return ViewHolderImage(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolderImage, position: Int) {
        holder.vincula(imagens1[position])
    }

    override fun getItemCount(): Int {
      return imagens1.size
    }

}