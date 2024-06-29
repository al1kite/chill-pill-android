package ddwu.com.mobile.finalreport.feature.dose

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.data.model.community.CommunitySearchRequest
import ddwu.com.mobile.finalreport.databinding.FragmentCommunityBinding
import ddwu.com.mobile.finalreport.feature.community.CommunityAdapter
import ddwu.com.mobile.finalreport.feature.community.CommunityViewModel

class DoseListFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private lateinit var doseListAdapter: DoseListAdapter
    private val viewModel: DoseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchListener()
        observeCommunityList(binding.search.text.toString())  // 초기 호출 추가
    }

    private fun setupRecyclerView() {
        doseListAdapter = DoseListAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = doseListAdapter
        }

        doseListAdapter.setOnItemLongClickListener { item ->
            viewModel.deleteItem(item)
            doseListAdapter.currentList.toMutableList().apply {
                remove(item)
                doseListAdapter.submitList(this)
            }
        }
    }

    private fun setupSearchListener() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                observeCommunityList(s.toString())
            }
        })
    }

    private fun observeCommunityList(keyword: String) {
        val searchRequest = CommunitySearchRequest(keyword = keyword.trim())
        viewModel.getMyDairyList(searchRequest).observe(viewLifecycleOwner, Observer { communityList ->
            doseListAdapter.submitList(communityList)
        })
    }
}



