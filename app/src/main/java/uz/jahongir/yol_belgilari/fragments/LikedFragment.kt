package uz.jahongir.yol_belgilari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.jahongir.yol_belgilari.R
import uz.jahongir.yol_belgilari.adapters.MyRvAdapter
import uz.jahongir.yol_belgilari.databinding.FragmentLikedBinding
import uz.jahongir.yol_belgilari.db.MyDbHelper
import uz.jahongir.yol_belgilari.models.MyData
import uz.jahongir.yol_belgilari.models.MyObject

class LikedFragment : Fragment(), MyRvAdapter.RvClick {

    private val binding by lazy { FragmentLikedBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var list: ArrayList<MyData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        list = ArrayList()
        myDbHelper = MyDbHelper(binding.root.context)
        myDbHelper.getAllSign().forEach {
            if (it.like == "1"){
                list.add(it)
            }
        }

        myRvAdapter = MyRvAdapter(list, this)
        binding.myRv.adapter = myRvAdapter
        return binding.root
    }

    override fun editCLick(myData: MyData) {
        MyObject.edit = true
        MyObject.sign = myData
        findNavController().navigate(R.id.addFragment, bundleOf("key" to myData))
    }

    override fun deleteClick(myData: MyData) {
        myDbHelper.deleteSign(myData)
        list.remove(myData)
        myRvAdapter.notifyDataSetChanged()
    }

    override fun itemClick(myData: MyData) {
        findNavController().navigate(R.id.infoFragment, bundleOf("key" to myData))
    }

    override fun likeClick(myData: MyData) {
        if (myData.like=="1"){
            myData.like ="0"
            myDbHelper.editSign(myData)
            myRvAdapter.notifyDataSetChanged()
        }else if (myData.like=="0"){
            myData.like= "1"
            myDbHelper.editSign(myData)
            myRvAdapter.notifyDataSetChanged()
        }
    }
}