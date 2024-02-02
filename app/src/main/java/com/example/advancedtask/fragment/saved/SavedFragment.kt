package com.example.advancedtask.fragment.saved

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.advancedtask.databinding.FragmentSavedBinding
import com.example.advancedtask.fragment.shared.SharedViewModel
import com.example.advancedtask.model.SearchItemModel

class SavedFragment : Fragment() {

    // Context와 ViewModel
    private lateinit var mContext: Context

    val sharedViewModel by activityViewModels<SharedViewModel>()

    private val viewModel: SavedViewModel by viewModels()

    // 바인딩과 어댑터
    private var binding: FragmentSavedBinding? = null
    private lateinit var adapter: SavedAdapter

    // 프래그먼트가 액티비티에 붙을 때 호출
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    // 프래그먼트 뷰 생성 시 호출
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 어댑터 초기화
        adapter = SavedAdapter(mContext)

        // 바인딩 설정
        binding = FragmentSavedBinding.inflate(inflater, container, false).apply {
            rvSave.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvSave.adapter = adapter
            pbBookmark.visibility = View.GONE
        }

        // 북마크된 아이템 로딩
        viewModel.getBookmarkedItems(mContext)

        // 북마크 리스트 관찰하여 UI 업데이트
        viewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarks ->
            adapter.items = bookmarks.toMutableList()
            adapter.notifyDataSetChanged()
        }

        // 항목 클릭 시 동작 정의
        adapter.setOnItemClickListener(object : SavedAdapter.OnItemClickListener {
            override fun onItemClick(item: SearchItemModel, position: Int) {
                viewModel.deleteItem(mContext, item, position)
                Log.d("BookmarkFragment", "#jblee onItemClick deleteItem position = $position")
                sharedViewModel.addDeletedItemUrls(item.url)

            }
        })

        return binding?.root
    }

    // 프래그먼트 뷰 종료 시 호출
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null  // 바인딩 리소스 해제
    }
}
