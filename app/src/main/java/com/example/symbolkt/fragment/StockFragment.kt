package com.example.symbolkt.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        // Change activity title
        activity?.title = getString(R.string.stock_fragment_title)

        binding.lifecycleOwner = this
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
            this.findNavController()
                .navigate(StockFragmentDirections.actionStockFragmentToStockDetailsFragment(it))
        })

        binding.btnSearchQuery.setOnClickListener {
            viewModel.updateQuery(binding.svSearchQuery.query.toString())
            viewModel.onSearchClicked()
            resetSearchView(binding)
        }
        Log.d("StockFragment", activity?.supportFragmentManager?.backStackEntryCount.toString())
        // Inflate the layout for this fragment
        return binding.root
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