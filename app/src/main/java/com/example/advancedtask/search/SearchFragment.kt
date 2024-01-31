package com.example.advancedtask.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.advancedtask.adapter.SearchAdapter
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.databinding.FragmentSearchBinding
import com.example.advancedtask.databinding.SearchListGridBinding
import com.example.advancedtask.main.MainActivity
import com.example.advancedtask.retrofit.ApiRepositoryImpl

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }
//    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val data: Intent? = result.data
//            // 결과를 처리하는 코드를 여기에 작성합니다.
//        }
//    }
    private lateinit var binding: FragmentSearchBinding

    private lateinit var binding2 : SearchListGridBinding

    private var _adapter: SearchAdapter? = null
    private val adapter get() = _adapter

    private lateinit var item: MutableList<SearchModel>

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 결과 처리
        }
    }
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModel.SearchViewModelFactory(ApiRepositoryImpl()))[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding2 = SearchListGridBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()


    }


    private fun initView() {
        setSearch()
        setAdapter()
        setSearchButton()

        loadData()

    }


    private fun setSearchButton() {
        binding.btnSearch.setOnClickListener {

            viewModel.searchPosts(1)

        }
    }

    private fun setAdapter() {
        _adapter = SearchAdapter()
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        viewModel.myCustomPosts.observe(viewLifecycleOwner){models ->
            item = models.map { it.copy() }.toMutableList()

            Log.d("myCustomPosts","$models")
            adapter?.submitList(item)

        }
        _adapter!!.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, item: SearchModel) {
                Log.d("SpecialThanks","오예 GotFragmentFromAdapter : $item")
                viewModel.selectItem(position, item)
                hideKeyboard()
                saveData()

            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Special Thanks", "으앙 onDestroyView")
    }

    override fun onPause() {
        super.onPause()
        binding.rvSearch.layoutManager?.onSaveInstanceState()
            ?.let { viewModel.saveRecyclerViewState(it) }

    }

    override fun onResume() {
        super.onResume()
        binding.rvSearch.layoutManager?.onRestoreInstanceState(viewModel.recyclerViewState.value)

    }

    private fun setSearch() = with(binding){
        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchPosts(it, 1) }
                hideKeyboard()
                saveData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchTextLive(it)}
                return false // 검색어 추천등 추가 하면 좋을 듯

            }

        })
    }
    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.window?.decorView?.applicationWindowToken, 0)
    }

    private fun saveData() {
        val pref = this.activity?.getSharedPreferences("pref",0)
        val edit = pref?.edit() // 수정 모드
        viewModel.search.observe(viewLifecycleOwner) {
            edit?.putString("text", it)
        }
        edit?.apply() // 저장완료
    }

    private fun loadData() {
        val pref = this.activity?.getSharedPreferences("pref",0)
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값
        if (pref != null) {
            binding.etSearch.setQuery(pref.getString("text",""), true)

        }
    }
}