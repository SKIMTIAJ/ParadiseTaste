package com.paradisetaste.fragments.categories_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.paradisetaste.R
import com.paradisetaste.adapter.CategoryDetailsAdapter
import com.paradisetaste.databinding.FragmentMottonCategoryBinding
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.viewmodel.HomeMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MottonCategoryFragment : Fragment() {

    private var _binding:FragmentMottonCategoryBinding?=null
    private val binding get() = _binding!!

    val homeViewmodel by viewModels<HomeMainViewModel>()
    @Inject
    lateinit var adapter : CategoryDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMottonCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*homeViewmodel.getData().observe(requireActivity()){
            //foodName = it.toString()
            initializationRecycclerView(it.toString())
        }*/

        initializationRecycclerView("Dessert")

    }

    private fun initializationRecycclerView(foodName: String?) {
        binding.mottonRecyclerView.layoutManager = GridLayoutManager(activity,2,
            GridLayoutManager.VERTICAL,false)
        binding.mottonRecyclerView.hasFixedSize()
        homeViewmodel.getCategoryDetails(foodName!!)

        homeViewmodel.categoryDetailsLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Success->{
                    adapter.submitList(it.data?.meals)
                    binding.mottonRecyclerView.adapter = adapter
                    binding.mottonRecyclerView.visibility = View.GONE
                    //selectTabOnRun(foodName)
                }
                is NetworkResult.Error->{
                    Toast.makeText(requireActivity(),it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressBarInMottonPage.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.progressBarInMottonPage.visibility = View.VISIBLE
                }
            }
        }


    }
}