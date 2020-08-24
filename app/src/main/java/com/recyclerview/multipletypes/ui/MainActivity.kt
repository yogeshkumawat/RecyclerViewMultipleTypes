package com.recyclerview.multipletypes.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProviders
import com.recyclerview.multipletypes.R
import com.recyclerview.multipletypes.common.Constant
import com.recyclerview.multipletypes.common.DisposableOnNextObserver
import com.recyclerview.multipletypes.databinding.ActivityMainBinding
import com.recyclerview.multipletypes.model.BaseItem
import com.recyclerview.multipletypes.model.ListData
import com.recyclerview.multipletypes.model.Result
import com.recyclerview.multipletypes.network.APIHelper
import com.recyclerview.multipletypes.network.APIServiceImpl
import com.recyclerview.multipletypes.ui.adapter.MainListAdapter
import com.recyclerview.multipletypes.ui.adapter.OnHandleCallback
import com.recyclerview.multipletypes.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity(), OnHandleCallback {
    private lateinit var mainViewModel: MainViewModel
    private val composite: CompositeDisposable = CompositeDisposable()
    private val adapter = MainListAdapter(this)
    private lateinit var binding: ActivityMainBinding
    private var baseItem: BaseItem? = null
    private var position = -1

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_submit)
            submitData()
        return super.onOptionsItemSelected(item)
    }

    private fun submitData() {
        for(item in adapter.listItems) {
            Log.v("MainActivity", "Id is ${item.id}")
            if(item.type == "PHOTO")
                Log.v("MainActivity", "${item.title} Bitmap is ${item.bitmap}")
            else if(item.type == "SINGLE_CHOICE")
                Log.v("MainActivity", "${item.title} Selected option is ${item.singleChoiceSelected}")
            else if(item.type == "COMMENT")
                Log.v("MainActivity", "${item.title} Provide comment?  ${item.provideComment} and Comment is ${item.comment}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 7 && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            baseItem?.bitmap = bitmap
            adapter.notifyItemChanged(position)
        }
    }

    override fun onCallback(position: Int, baseItem: BaseItem) {
        MainActivity@ this.baseItem = baseItem
        MainActivity@ this.position = position
        if(hasCameraPermission())
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 7)
        else
            requestCameraPermission()
    }

    private fun requestCameraPermission() {
        if(greaterThan23API())
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 10)
    }

    private fun greaterThan23API(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun hasCameraPermission(): Boolean {
        if(greaterThan23API())
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        return true
    }

    override fun onRemove(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 10 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 7)
    }
}