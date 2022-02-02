package com.example.symbolkt.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.symbolkt.R
import com.example.symbolkt.adapter.StockListAdapter
import com.example.symbolkt.databinding.FragmentStockBinding
import com.example.symbolkt.viewmodel.StockViewModel


class StockFragment : Fragment(R.layout.fragment_stock) {

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(this)[StockViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DataBindingUtil.bind<FragmentStockBinding>(view)!!

        // Change activity title
        activity?.title = getString(R.string.stock_fragment_title)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = StockListAdapter(StockListAdapter.ClickListener{
            viewModel.setStockResult(it)
        })
        binding.rvStock.adapter = adapter

        viewModel.stockResults.observe(viewLifecycleOwner, { stockResults ->
            stockResults?.let {
                adapter.data = stockResults
                setStockResultText(stockResults.isEmpty(), binding)
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

        viewModel.stockResult.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(StockFragmentDirections.actionStockFragmentToStockDetailsFragment(it))
                viewModel.onResultClicked()
            }
        })

        binding.btnSearchQuery.setOnClickListener {
            viewModel.updateQuery(binding.svSearchQuery.query.toString())
            viewModel.onSearchClicked()
            resetSearchView(binding)
        }
        Log.d("StockFragment", activity?.supportFragmentManager?.backStackEntryCount.toString())
        // Inflate the layout for this fragment
    }

    private fun resetSearchView(binding: FragmentStockBinding) {
        binding.svSearchQuery.setQuery("", false)
        binding.svSearchQuery.isIconified = true
        binding.svSearchQuery.clearFocus()
    }

    private fun setStockResultText(condition: Boolean,
                                   binding: FragmentStockBinding) {
        if (condition) {
            binding.tvStockResult.text =
                String.format(getString(R.string.tv_result_empty), viewModel.searchQuery.value!!)
        }
    }
}