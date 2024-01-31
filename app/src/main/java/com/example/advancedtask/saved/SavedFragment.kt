package com.example.advancedtask.saved

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.advancedtask.R
import com.example.advancedtask.adapter.SearchAdapter
import com.example.advancedtask.data.SelectedList
import com.example.advancedtask.data.SelectedList.selectedItems
import com.example.advancedtask.databinding.FragmentSavedBinding
import com.example.advancedtask.databinding.FragmentSearchBinding
import com.example.advancedtask.retrofit.ApiRepositoryImpl
import com.example.advancedtask.search.SearchViewModel

class SavedFragment : Fragment() {

    companion object {
        fun newInstance() = SavedFragment()
    }
    private lateinit var binding: FragmentSavedBinding

    private var _adapter: SearchAdapter? = null
    private val adapter get() = _adapter

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModel.SearchViewModelFactory(
            ApiRepositoryImpl()
        ))[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setAdapter()
    }

    private fun setAdapter() {
        _adapter = SearchAdapter()
        binding.rvSave.adapter = adapter
        binding.rvSave.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        Log.d("SpecialThanks","데이터베이스에 저장 잘 되었남? ${selectedItems}")
        if(selectedItems.isEmpty().not()) {
            Log.d("SpecialThanks","북마크 값 잘 저장 됨? ${selectedItems[0].bookMark}")
        }
        viewModel.selectedList.observe(viewLifecycleOwner) {
            adapter?.submitList(selectedItems)
        }

    }

}