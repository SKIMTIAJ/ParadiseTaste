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
import com.paradisetaste.databinding.FragmentBeefCategoryBinding
import com.paradisetaste.databinding.FragmentChikenCategoryBinding
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.viewmodel.HomeMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BeefCategoryFragment : Fragment() {
    private var _binding: FragmentBeefCategoryBinding?=null
    private val binding get() = _binding!!

    val homeViewmodel by viewModels<HomeMainViewModel>()
    @Inject
    lateinit var adapter : CategoryDetailsAdapter
    lateinit var foodName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeefCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationRecyclerView("Beef")
    }

    private fun initializationRecyclerView(foodName: String?) {
        binding.beefRecyclerView.layoutManager = GridLayoutManager(activity,2,
            GridLayoutManager.VERTICAL,false)
        binding.beefRecyclerView.hasFixedSize()
        homeViewmodel.getCategoryDetails(foodName!!)
        homeViewmodel.categoryDetailsLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Success->{
                    adapter.submitList(it.data?.meals)
                    binding.beefRecyclerView.adapter = adapter
                    binding.progressBarInBeefPage.visibility = View.GONE
                    //selectTabOnRun(foodName)
                }
                is NetworkResult.Error->{
                    Toast.makeText(requireActivity(),it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressBarInBeefPage.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.progressBarInBeefPage.visibility = View.VISIBLE
                }
            }
        }
    }



}