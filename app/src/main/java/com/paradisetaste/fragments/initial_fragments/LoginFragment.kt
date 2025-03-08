package com.paradisetaste.fragments.initial_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.paradisetaste.R
import com.paradisetaste.activities.HomeActivity
import com.paradisetaste.databinding.FragmentLoginBinding
import com.paradisetaste.databinding.FragmentSplashBinding
import com.paradisetaste.utils.verifyMobilenumber
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    val TAG = "LoginFragment"

    private var _binding : FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private lateinit var navigator: NavDirections

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            verifyButton.setOnClickListener {
                val mobileNumber = mobileVerification.text.trim().toString()
                if(verifyMobilenumber(mobileNumber)){
                    Log.d(TAG,verifyMobilenumber(mobileNumber).toString())
                    startActivity(Intent(requireActivity(),HomeActivity::class.java))
                }else{
                    // just for now
                    Log.d(TAG,verifyMobilenumber(mobileNumber).toString())
                    mobileVerification.requestFocus()
                    mobileVerification.error = "Please enter correct Mobile Number"
                }
            }
        }

    }

    fun maincheck(num:String){
        println(verifyMobilenumber("8250839319"))
    }


}