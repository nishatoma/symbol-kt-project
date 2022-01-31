package com.example.symbolkt.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.symbolkt.R
import com.example.symbolkt.databinding.FragmentStockDetailsBinding
import com.example.symbolkt.factory.StockDetailsViewModelFactory
import com.example.symbolkt.model.StockResult
import com.example.symbolkt.viewmodel.StockDetailsViewModel

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

        // Pass the stock result
        val stockResult = StockDetailsFragmentArgs.fromBundle(arguments!!).stockResult
        // Init view model factory
        val factory = StockDetailsViewModelFactory(stockResult)
        // ViewModel provider
        val provider = ViewModelProvider(this, factory)
        // Get the viewmodel and assign it to the binding
        val viewModel = provider[StockDetailsViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.isDetailCardShown.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    binding.btnShowMore.text = getString(R.string.show_less)
                } else {
                    binding.btnShowMore.text = getString(R.string.show_more)
                }
            }
        })

        return binding.root
    }
}