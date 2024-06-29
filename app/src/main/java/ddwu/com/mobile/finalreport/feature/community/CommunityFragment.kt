package ddwu.com.mobile.finalreport.feature.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.data.model.community.CommunitySearchRequest
import ddwu.com.mobile.finalreport.databinding.FragmentCommunityBinding
import ddwu.com.mobile.finalreport.feature.dose.DoseListViewModel

class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private lateinit var communityAdapter: CommunityAdapter
    private val viewModel: CommunityViewModel by viewModels()

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
        observeCommunityList()
    }

    private fun setupRecyclerView() {
        communityAdapter = CommunityAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = communityAdapter
        }
    }

    private fun observeCommunityList() {
        val searchRequest = CommunitySearchRequest(keyword = binding.search.text.toString().trim())
        viewModel.getCommunityList(searchRequest).observe(viewLifecycleOwner, Observer { communityList ->
            communityAdapter.submitList(communityList)
        })
    }
}
