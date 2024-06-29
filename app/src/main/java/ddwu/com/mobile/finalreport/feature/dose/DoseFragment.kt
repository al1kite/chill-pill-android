package ddwu.com.mobile.finalreport.feature.dose

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ddwu.com.mobile.finalreport.R
import ddwu.com.mobile.finalreport.databinding.FragmentDoseBinding
import ddwu.com.mobile.finalreport.feature.login.LoginActivity

class DoseFragment : Fragment() {

    private lateinit var binding: FragmentDoseBinding
    private val viewModel: DoseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoseBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupObservers()

        binding.iconBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        return binding.root
    }

    private fun setupObservers() {
        viewModel.diaryData.observe(viewLifecycleOwner, Observer { diary ->
            diary?.let {
                binding.title.setText(it.title)
                binding.content.setText(it.content)
                viewModel.onIconClick(it.iconType)
                binding.btnAdd.text = "수정"
            }
        })
    }
}

