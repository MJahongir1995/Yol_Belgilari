package uz.jahongir.yol_belgilari.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.jahongir.yol_belgilari.R
import uz.jahongir.yol_belgilari.adapters.StateAdapter
import uz.jahongir.yol_belgilari.databinding.FragmentHomeBinding
import uz.jahongir.yol_belgilari.databinding.TabItemBinding
import uz.jahongir.yol_belgilari.models.PagerItem

class HomeFragment : Fragment() {
    private lateinit var list: ArrayList<PagerItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = ArrayList()
        list.add(PagerItem("Ogohlantiruvchi"))
        list.add(PagerItem("Imtiyozli"))
        list.add(PagerItem("Taqiqlovchi"))
        list.add(PagerItem("Buyuruvchi"))
    }

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater)}
    private lateinit var stateAdapter: StateAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding.myNavView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                   findNavController().navigate(R.id.homeFragment)
                }
                R.id.nav_liked -> {
                    findNavController().navigate(R.id.likedFragment)
                }
                R.id.nav_info -> {
                    findNavController().navigate(R.id.aboutUsFragment)
                }
            }
        }

        //Set ViewPager's Adapter
        stateAdapter = StateAdapter(list, this)
        binding.myViewPager.adapter = stateAdapter

        //Tab Layout listener
        binding.myTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<TextView>(R.id.tab_item_tv)!!.setTextColor(Color.parseColor("#005CA1"))
                customView.findViewById<TextView>(R.id.tab_item_tv).setBackgroundColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<TextView>(R.id.tab_item_tv)!!.setBackgroundColor(Color.parseColor("#005CA1"))
                customView.findViewById<TextView>(R.id.tab_item_tv).setTextColor(Color.WHITE)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        //SetTabLayoutMediator
        TabLayoutMediator(binding.myTab, binding.myViewPager){tab,posititon->
            val tabItemView = TabItemBinding.inflate(layoutInflater)
            tabItemView.tabItemTv.text =list[posititon].type
            tab.customView =tabItemView.root
        }.attach()

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.addFragment)
        }
        return binding.root
    }
}