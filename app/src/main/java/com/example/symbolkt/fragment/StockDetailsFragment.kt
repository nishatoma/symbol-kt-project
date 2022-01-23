package com.example.symbolkt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.symbolkt.databinding.FragmentStockDetailsBinding

class StockDetailsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Stock Details Fragment Binding
        val binding = FragmentStockDetailsBinding.inflate(inflater)

        return binding.root
    }
}