package com.example.asimalank.exchangerates.presentation.exchangerates.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.asimalank.exchangerates.R
import com.example.asimalank.exchangerates.databinding.FragmentExchangeRatesBinding
import com.example.asimalank.exchangerates.presentation.exchangerates.adapter.CurrencyAdapter
import com.example.asimalank.exchangerates.presentation.exchangerates.viewModel.ExchangeRatesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExchangeRatesFragment : Fragment() {

    private val viewModel: ExchangeRatesViewModel by viewModels()

    @Inject
    lateinit var adapter: CurrencyAdapter

    private var _binding: FragmentExchangeRatesBinding? = null
    private val binding
        get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        binding.apply {
            listExchangeRates.adapter = adapter
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.fetchCurrency()
                swipeRefreshLayout.postDelayed({
                    swipeRefreshLayout.isRefreshing = false
                }, 500)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.apply {
                        textError.isGone = !(state.listCurrency.isEmpty() && state.isErrorViewText)
                    }
                    if (state.isErrorViewToast) toast(R.string.error_toast)
                    adapter.submitList(state.listCurrency.sortedBy { it.curName })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun toast(text: Int) {
        Toast.makeText(
            requireContext(),
            requireContext().getString(text),
            Toast.LENGTH_SHORT
        ).show()
    }
}