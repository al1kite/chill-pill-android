package ddwu.com.mobile.finalreport.feature.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ddwu.com.mobile.finalreport.R
import ddwu.com.mobile.finalreport.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    data class LinkInfo(val viewId: Int, val url: String)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private val linkInfo = arrayOf(
        LinkInfo(R.id.constraintLayout1, "https://github.com/al1kite"),
        LinkInfo(R.id.constraintLayout2, "https://al1kite.github.io"),
        LinkInfo(R.id.constraintLayout3, "mailto:widekdus62@gmail.com")
    )

    private fun setupClickListeners() {
        linkInfo.forEach { info ->
            binding.root.findViewById<View>(info.viewId).setOnClickListener {
                openUrlInBrowser(info.url)
            }
        }
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}