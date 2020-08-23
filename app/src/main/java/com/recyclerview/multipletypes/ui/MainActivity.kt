package com.recyclerview.multipletypes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProviders
import com.recyclerview.multipletypes.R
import com.recyclerview.multipletypes.common.Constant
import com.recyclerview.multipletypes.common.DisposableOnNextObserver
import com.recyclerview.multipletypes.databinding.ActivityMainBinding
import com.recyclerview.multipletypes.model.ListData
import com.recyclerview.multipletypes.model.Result
import com.recyclerview.multipletypes.network.APIHelper
import com.recyclerview.multipletypes.network.APIServiceImpl
import com.recyclerview.multipletypes.ui.adapter.MainListAdapter
import com.recyclerview.multipletypes.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private val composite: CompositeDisposable = CompositeDisposable()
    private val adapter = MainListAdapter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            adapter = this@MainActivity.adapter
        }
        initData()
    }

    private fun initData() {
        initViewModel()
        loadFirstResponse()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(APIHelper(APIServiceImpl(this)))
        ).get(MainViewModel::class.java)
        binding.viewModel = mainViewModel
    }

    private fun loadFirstResponse() {
        val disposableOnNextObserver =
            object : DisposableOnNextObserver<Result<ListData>>() {
                override fun onNext(result: Result<ListData>) {
                    if (result.success)
                        onFirstResponse(result.data!!)
                    else
                        onDataFetchFail(result.exception)
                }
            }
        mainViewModel.fetchListData(Constant.DATA_URL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableOnNextObserver)

        composite.add(disposableOnNextObserver)
    }

    private fun onFirstResponse(data: ListData) {
        hideProgressBarAndShowList()
        setUpRecyclerViewAdapter(data)
    }

    private fun hideProgressBarAndShowList() {
        mainViewModel.progressBarVisibility.set(false)
        mainViewModel.listVisibility.set(true)
    }

    private fun setUpRecyclerViewAdapter(data: ListData) {
        adapter.addListItems(data.items)
    }

    private fun onDataFetchFail(exception: Exception?) {
        mainViewModel.progressBarVisibility.set(false)
        Toast.makeText(this, "Error while fetching: ${exception?.message}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.dispose()
    }
}