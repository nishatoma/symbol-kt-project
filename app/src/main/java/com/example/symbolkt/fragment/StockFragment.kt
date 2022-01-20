package com.example.symbolkt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.symbolkt.R
import com.example.symbolkt.databinding.FragmentStockBinding
import com.example.symbolkt.viewmodel.StockViewModel


class StockFragment : Fragment() {

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(this)[StockViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentStockBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }
}