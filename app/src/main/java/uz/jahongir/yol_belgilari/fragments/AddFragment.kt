package uz.jahongir.yol_belgilari.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import uz.jahongir.yol_belgilari.R
import uz.jahongir.yol_belgilari.adapters.SpinnerAdapter
import uz.jahongir.yol_belgilari.databinding.FragmentAddBinding
import uz.jahongir.yol_belgilari.db.MyDbHelper
import uz.jahongir.yol_belgilari.models.MyData
import uz.jahongir.yol_belgilari.models.MyObject
import uz.jahongir.yol_belgilari.models.PagerItem
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddFragment : Fragment() {

    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = ArrayList()
        list.add(PagerItem("Ogohlantiruvchi").type.toString())
        list.add(PagerItem("Imtiyozli").type.toString())
        list.add(PagerItem("Taqiqlovchi").type.toString())
        list.add(PagerItem("Buyuruvchi").type.toString())

    }

    private lateinit var binding: FragmentAddBinding
    private lateinit var uriPath: String
    private lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)

        uriPath = ""
        myDbHelper = MyDbHelper(binding.root.context)

        when (MyObject.edit) {
            true -> {
                binding.edtName.setText(MyObject.sign!!.name)
                binding.edtDescription.setText(MyObject.sign!!.description)
                binding.image.setImageURI(Uri.parse(MyObject.sign!!.image))

                binding.image.setOnClickListener {
                    getImageContent.launch("image/*")
                }

                spinnerAdapter = SpinnerAdapter(list)
                binding.mySpinner.adapter = spinnerAdapter

                binding.btnAdd.setOnClickListener {
                    if (uriPath == "") {
                        uriPath = MyObject.sign!!.image.toString()
                    }
                    MyObject.sign!!.name = binding.edtName.text.toString()
                    MyObject.sign!!.description = binding.edtDescription.text.toString()
                    MyObject.sign!!.image = uriPath
                    MyObject.sign!!.type = list[binding.mySpinner.selectedItemPosition]
                    myDbHelper.editSign(MyObject.sign!!)
                    findNavController().navigate(R.id.homeFragment)
                }

            }
            false -> {
                binding.image.setOnClickListener {
                    getImageContent.launch("image/*")
                }
                spinnerAdapter = SpinnerAdapter(list)
                binding.mySpinner.adapter = spinnerAdapter

                binding.btnAdd.setOnClickListener {
                    val myData = MyData(
                        name = binding.edtName.text.toString(),
                        description = binding.edtDescription.text.toString(),
                        image = uriPath,
                        like = "0",
                        type = list[binding.mySpinner.selectedItemPosition]
                    )
                    myDbHelper.addSign(myData)
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        }

        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            binding.image.setImageURI(it)
            val inputStream = requireActivity().contentResolver.openInputStream(it)
            val name = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())
            val file = File(requireActivity().filesDir, "$name.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream?.close()
            uriPath = file.absolutePath
        }
}