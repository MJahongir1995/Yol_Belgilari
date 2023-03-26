package uz.jahongir.yol_belgilari.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.jahongir.yol_belgilari.R
import uz.jahongir.yol_belgilari.databinding.FragmentInfoBinding
import uz.jahongir.yol_belgilari.models.MyData

class InfoFragment : Fragment() {

    private val binding by lazy { FragmentInfoBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sign = arguments?.getSerializable("key") as MyData

        binding.title.text = sign.name
        binding.nameOfSign.text = sign.name
        binding.txtDescription.text = sign.description
        binding.infoImage.setImageURI(Uri.parse(sign.image))

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root

    }
}