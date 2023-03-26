package uz.jahongir.yol_belgilari.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.jahongir.yol_belgilari.R
import uz.jahongir.yol_belgilari.databinding.MyItemBinding
import uz.jahongir.yol_belgilari.models.MyData

class MyRvAdapter(var list: List<MyData>, val rvClick: RvClick):RecyclerView.Adapter<MyRvAdapter.VH>() {
    inner class VH(private var myItem: MyItemBinding) : RecyclerView.ViewHolder(myItem.root) {
        fun onBind(myData: MyData, position: Int) {
            myItem.txtName.text = myData.name
            myItem.image.setImageURI(Uri.parse(myData.image))

            list.forEach {
                if (it.like == "1") {
                    myItem.imageLike.setImageResource(R.drawable.selectedheart)
                } else if (it.like == "0") {
                    myItem.imageLike.setImageResource(R.drawable.unselectedheart)
                }
            }
            myItem.txtDelete.setOnClickListener {
                rvClick.deleteClick((myData))
            }
            myItem.txtChange.setOnClickListener {
                rvClick.editCLick(myData)
            }
            myItem.rectangle2.setOnClickListener {
                rvClick.itemClick(myData)
            }
            myItem.imageLike.setOnClickListener {
                rvClick.likeClick(myData)
                if (myData.like == "1") {
                    myItem.imageLike.setImageResource(R.drawable.selectedheart)
                }else{
                    myItem.imageLike.setImageResource(R.drawable.unselectedheart)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(MyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun editCLick(myData: MyData)
        fun deleteClick(myData: MyData)
        fun itemClick(myData: MyData)
        fun likeClick(myData: MyData)
    }
}