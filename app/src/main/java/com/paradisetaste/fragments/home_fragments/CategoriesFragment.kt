package com.paradisetaste.fragments.home_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.paradisetaste.adapter.CategoryDetailsAdapter
import com.paradisetaste.adapter.HomeCategoriesAdapter
import com.paradisetaste.adapter.viewpager_adapter.ViewPagerAdapter
import com.paradisetaste.databinding.FragmentCategoriesBinding
import com.paradisetaste.models.Category
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.viewmodel.HomeMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding:FragmentCategoriesBinding?=null
    private val binding get() = _binding!!

    val homeViewmodel by viewModels<HomeMainViewModel>()
    private var listOfItem:List<String>? = null
  //  lateinit var foodName:String

    private lateinit var fragmentPageAdapter: ViewPagerAdapter
    private lateinit var  sizeOfList:List<Category>

    @Inject
    lateinit var adapter : CategoryDetailsAdapter
    @Inject
    lateinit var homeCategoryAdapter: HomeCategoriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewmodel.getCategoryFromHome()
        fragmentPageAdapter = ViewPagerAdapter(childFragmentManager,lifecycle)
        //initializationRecycclerView(foodName)
        inflateTab()
       // setViewPager()
        //onClickTabItem()
    }

    /*private fun setViewPager() {
        binding.categoryViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.categoryTab.selectTab(binding.categoryTab.getTabAt(position))
            }
        })
    }*/


    /*private fun initializationRecycclerView(foodName: String?) {
        binding.categoryDetailsRecyclerWithTab.layoutManager = GridLayoutManager(activity,2,
            GridLayoutManager.VERTICAL,false)
        binding.categoryDetailsRecyclerWithTab.hasFixedSize()
        homeViewmodel.getCategoryDetails(foodName!!)

        homeViewmodel.categoryDetailsLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Success->{
                    adapter.submitList(it.data?.meals)
                    binding.categoryDetailsRecyclerWithTab.adapter = adapter
                    binding.progressBarinCategory.visibility = View.GONE
                    //selectTabOnRun(foodName)
                }
                is NetworkResult.Error->{
                    Toast.makeText(requireActivity(),it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressBarinCategory.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.progressBarinCategory.visibility = View.VISIBLE
                }
            }
        }


    }*/

    /*private fun onClickTabItem() {

        binding.categoryViewPager.adapter = fragmentPageAdapter
        binding.categoryTab.addOnTabSelectedListener(object:OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedTabTitle = tab?.text.toString()
                binding.categoryViewPager.currentItem = tab!!.position
                homeViewmodel.setData(selectedTabTitle)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }*/

   /* private fun selectTabOnRun(foodName: String) {
        val tabIndex = listOfItem?.indexOf(foodName)
        if (tabIndex != -1) {
            val tab = binding.categoryTab.getTabAt(tabIndex!!)
            tab?.select()
        } else {
            Toast.makeText(context, "Food item not found in tabs", Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun inflateTab() {
        binding.categoryDetailsRecyclerWithTab.layoutManager = GridLayoutManager(activity,3,
            GridLayoutManager.VERTICAL,false)
        binding.categoryDetailsRecyclerWithTab.hasFixedSize()
        homeViewmodel.getCategoryFromHome()
        homeViewmodel.categoryLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Success ->{
                    listOfItem = it.data?.categories!!.map { it.strCategory }
                    /*it.data?.categories?.forEach{it->
                        binding.categoryTab.addTab(binding.categoryTab.newTab().setText(it.strCategory.toString()))
                    }*/
                    //binding.homeCategoryProgressBar.visibility = View.GONE
                    homeCategoryAdapter.submitList(it.data?.categories)
                    binding.categoryDetailsRecyclerWithTab.adapter = homeCategoryAdapter
                    binding.progressBarinCategory.visibility = View.GONE
                }
                is NetworkResult.Error->{
                    Toast.makeText(requireActivity(),it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressBarinCategory.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.progressBarinCategory.visibility = View.VISIBLE
                }
            }
        }
    }

}