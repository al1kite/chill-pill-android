package ddwu.com.mobile.finalreport.feature.setting

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ddwu.com.mobile.finalreport.R
import ddwu.com.mobile.finalreport.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateFragment()
        showPopupDialog()
    }

    private fun showPopupDialog() {
        binding.tvGuideTerms2.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_alert) // 다이얼로그 레이아웃 설정 (custom_dialog_layout.xml 필요)
            dialog.findViewById<TextView>(R.id.confirmTextView).text = "종료하시겠습니까?"

            // 다이얼로그 내에서 닫기 버튼을 찾아서 클릭 리스너 설정
            val closeButton = dialog.findViewById<Button>(R.id.btnCancel)
            val btnConfirm = dialog.findViewById<Button>(R.id.btnAdd)

            closeButton.setOnClickListener {
                dialog.dismiss() // 다이얼로그 닫기
            }

            btnConfirm.setOnClickListener {
                dialog.dismiss() // 다이얼로그 닫기
                requireActivity().finishAffinity() // 액티비티 종료
            }

            dialog.show() // 다이얼로그 보이기
        }
    }

    private fun navigateFragment() {
        binding.tvGuideAppVersion.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_profile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
