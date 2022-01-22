package com.example.symbolkt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.symbolkt.adapter.StockListAdapter
import com.example.symbolkt.databinding.FragmentStockBinding
import com.example.symbolkt.model.StockResult
import com.example.symbolkt.viewmodel.StockViewModel


class StockFragment : Fragment() {

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(this)[StockViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentStockBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = StockListAdapter()
        binding.rvStock.adapter = adapter

        viewModel.stockResults.observe(viewLifecycleOwner, { stockResults ->
            stockResults?.let {
                adapter.data = stockResults
            }
        })

        // Observer values from view model
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.pbStock.isVisible = isLoading
        })

        viewModel.fragmentTitle.observe(viewLifecycleOwner, {
            it?.let {
                activity?.title = it
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}