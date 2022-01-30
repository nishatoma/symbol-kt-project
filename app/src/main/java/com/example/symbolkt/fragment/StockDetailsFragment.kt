package com.example.symbolkt.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.symbolkt.R
import com.example.symbolkt.databinding.FragmentStockDetailsBinding

class StockDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Stock Details Fragment Binding
        val binding = FragmentStockDetailsBinding.inflate(inflater)

        // set Lifecycle owner
        binding.lifecycleOwner = viewLifecycleOwner
        // change title of activity
        activity?.title = getString(R.string.stock_fragment_details_title)

        Log.d("StockDetailsFragment", activity?.supportFragmentManager?.backStackEntryCount.toString())

        return binding.root
    }
}