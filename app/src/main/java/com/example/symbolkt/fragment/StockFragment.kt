package com.example.symbolkt.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.symbolkt.R
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
            binding.tvStockResult.isVisible = !isLoading
            binding.rvStock.isVisible = !isLoading
        })

        // Observer values from view model
        viewModel.isCallSuccessful.observe(viewLifecycleOwner, { isSuccessful ->
            binding.rvStock.isVisible = isSuccessful
            binding.tvStockResult.isVisible = !isSuccessful
        })

        viewModel.fragmentTitle.observe(viewLifecycleOwner, {
            it?.let {
                activity?.title = it
            }
        })

        binding.btnSearchQuery.setOnClickListener {
            Log.d("StockFragment", binding.svSearchQuery.query.toString())
            viewModel.updateQuery(binding.svSearchQuery.query.toString())
            viewModel.onSearchClicked()
            resetSearchView(binding)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun resetSearchView(binding: FragmentStockBinding) {
        binding.svSearchQuery.setQuery("", false)
        binding.svSearchQuery.isIconified = true
        binding.svSearchQuery.clearFocus()
    }
}