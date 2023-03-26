package uz.jahongir.yol_belgilari.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.jahongir.yol_belgilari.R
import uz.jahongir.yol_belgilari.adapters.MyRvAdapter
import uz.jahongir.yol_belgilari.databinding.FragmentViewPagerItemBinding
import uz.jahongir.yol_belgilari.db.MyDbHelper
import uz.jahongir.yol_belgilari.models.MyData
import uz.jahongir.yol_belgilari.models.MyObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPagerItemFragment : Fragment(), MyRvAdapter.RvClick {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val binding by lazy { FragmentViewPagerItemBinding.inflate(layoutInflater) }
    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var list: ArrayList<MyData>
    private lateinit var myDbHelper: MyDbHelper

    private val TAG = "ViewPagerItemFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myDbHelper = MyDbHelper(binding.root.context)

        list = ArrayList()

        if (param1 == "Ogohlantiruvchi") {
            myDbHelper.getAllSign().forEach {
                Log.d(TAG,"onCreateView: ${myDbHelper.getAllSign()}")
                if (it.type == param1) {
                    list.add(it)
                }
            }
            myRvAdapter = MyRvAdapter(list, this)
            binding.rv.adapter = myRvAdapter
        }
        if (param1 == "Imtiyozli") {
            myDbHelper.getAllSign().forEach {
                if (it.type == param1) {
                    list.add(it)
                }
            }
            myRvAdapter = MyRvAdapter(list, this)
            binding.rv.adapter = myRvAdapter
        }

        if (param1 == "Taqiqlovchi") {
            myDbHelper.getAllSign().forEach {
                if (it.type == param1) {
                    list.add(it)
                }
            }
            myRvAdapter = MyRvAdapter(list, this)
            binding.rv.adapter = myRvAdapter
        }

        if (param1 == "Buyuruvchi") {
            myDbHelper.getAllSign().forEach {
                if (it.type == param1) {
                    list.add(it)
                }
            }
            myRvAdapter = MyRvAdapter(list, this)
            binding.rv.adapter = myRvAdapter
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewPagerItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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