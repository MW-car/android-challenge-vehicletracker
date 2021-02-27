package com.vimcar.vehicletracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.vimcar.vehicletracker.R
import com.vimcar.vehicletracker.databinding.FragmentVehicleOverviewBinding
import com.vimcar.vehicletracker.di.component.DaggerVehicleOverViewComponent
import com.vimcar.vehicletracker.ui.adapter.VehiclesAdapter
import com.vimcar.vehicletracker.viewmodel.VehicleOverviewViewModel
import com.vimcar.vehicletracker.viewmodel.VehiclesViewState
import javax.inject.Inject

class VehicleOverviewFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: VehicleOverviewViewModel by viewModels { viewModelProviderFactory }
    private var viewBinding: FragmentVehicleOverviewBinding? = null
    private val adapter = VehiclesAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerVehicleOverViewComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =
            FragmentVehicleOverviewBinding.inflate(LayoutInflater.from(context), container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            vehiclesRecyclerView.adapter = adapter
            swipeRefresh.setOnRefreshListener(this@VehicleOverviewFragment)
        }

        viewModel.vehiclesOverViewViewState.observe(this.viewLifecycleOwner) { viewState ->
            when (viewState) {
                is VehiclesViewState.Success -> {
                    handleSuccess(viewState)
                }
                is VehiclesViewState.Loading -> {
                    handleLoading()
                }
                is VehiclesViewState.Error -> {
                    handlerError()
                }
            }
        }

        viewModel.loadVehicles()
    }

    private fun handleSuccess(success: VehiclesViewState.Success) {
        viewBinding?.swipeRefresh?.isRefreshing = false
        adapter.submitList(success.vehicles)
    }

    private fun handleLoading() {
        viewBinding?.swipeRefresh?.isRefreshing = true
    }

    private fun handlerError() {
        viewBinding?.swipeRefresh?.isRefreshing = false
        viewBinding?.apply {
            val snackbar =
                Snackbar.make(root, R.string.error_message, Snackbar.LENGTH_LONG)
            snackbar.setAction(R.string.error_snackbar_action) {
                viewModel.refreshVehicles()
            }
            snackbar.show()
        }
    }

    override fun onRefresh() {
        adapter.submitList(emptyList())
        viewModel.refreshVehicles()
    }
}
