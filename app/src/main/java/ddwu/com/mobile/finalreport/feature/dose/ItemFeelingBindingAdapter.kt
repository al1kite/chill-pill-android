package ddwu.com.mobile.finalreport.feature.dose
import android.graphics.Color
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ddwu.com.mobile.finalreport.R

object ItemFeelingBindingAdapter {

    private val colors = mapOf(
        "awesome" to "#F4CE14", // Yellow
        "downcast" to "#088395", // Blue
        "confounded" to "#FF7F3E", // Orange
        "mask" to "#5C2FC2", // Purple
        "explode" to "#007F73", // Green
        "angry" to "#C40C0C"  // Red
    )

    private var isButtonClickedIcon : Int = -1

    @JvmStatic
    @BindingAdapter("app:onButtonClick", "app:iconTypeColor")
    fun setLastClickedColorClear(button: Button, lastClickedLiveData: LiveData<Int>?, iconType: LiveData<String>?){
        button.setOnClickListener {
            // viewModel의 onAddBtnClick 또는 onCancelBtnClick 호출
            val context = button.context
            if (context is ViewModelStoreOwner) {
                val viewModel = ViewModelProvider(context)[DoseViewModel::class.java]
                when (button.id) {
                    R.id.btnAdd -> viewModel.onAddBtnClick()
                    R.id.btnCancel -> viewModel.onCancelBtnClick()
                }
            }
        }

        lastClickedLiveData?.value?.let { lastClicked ->
            if(!getIconTypeByButtonId(lastClicked).equals(iconType)) {
                val lastClickedButton = button.rootView.findViewById<ImageButton>(lastClicked)
                lastClickedButton?.setColorFilter(Color.parseColor("#D9D9D9"))
            }
        }

        if (iconType != null) {
            isButtonClickedIcon = iconType.value?.let { it1 -> getButtonIdByIconType(it1) }!!
        }

    }

    @JvmStatic
    @BindingAdapter("app:onIconClick", "app:lastClicked", "app:iconTypeColor")
    fun setIconTypeAndColor(button: ImageButton, viewModel: DoseViewModel, lastClickedLiveData: LiveData<Int>?, iconType: LiveData<String>?) {

        // 클릭 이벤트 설정
        button.setOnClickListener {
            // 이전에 클릭된 버튼의 색상 초기화
            if(isButtonClickedIcon != -1){
                val lastClickedButton = button.rootView.findViewById<ImageButton>(isButtonClickedIcon)
                lastClickedButton?.setColorFilter(Color.parseColor("#D9D9D9"))
            }

            lastClickedLiveData?.value?.let { lastClicked ->
                val lastClickedButton = button.rootView.findViewById<ImageButton>(lastClicked)
                lastClickedButton?.setColorFilter(Color.parseColor("#D9D9D9"))
            }

            // 현재 클릭된 버튼의 색상 변경
            val color = colors[iconType?.value]
            if (color != null) {
                button.setColorFilter(Color.parseColor(color))
            } else {
                button.clearColorFilter()
            }

            // 클릭 이벤트 처리
            val buttonId = button.id
            val clickedIconType = getIconTypeByButtonId(buttonId)
            viewModel.onIconClick(clickedIconType)
            viewModel.updateLastClicked(buttonId)
        }

        // 아이콘 타입에 따른 초기 색상 설정
            val color = colors[iconType?.value]
            if (color != null) {
                val buttonId = iconType?.value?.let { getButtonIdByIconType(it) }
                if (buttonId != null) {
                    button.rootView.findViewById<ImageButton>(buttonId).setColorFilter(Color.parseColor(color))
                }
            } else {
                button.clearColorFilter()
            }
    }

    private fun getIconTypeByButtonId(buttonId: Int): String {
        return when (buttonId) {
            R.id.imageButton1 -> "awesome"
            R.id.imageButton2 -> "downcast"
            R.id.imageButton3 -> "confounded"
            R.id.imageButton4 -> "mask"
            R.id.imageButton5 -> "explode"
            R.id.imageButton6 -> "angry"
            else -> ""
        }
    }

    @JvmStatic
    fun getButtonIdByIconType(iconType: String): Int {
        return when (iconType) {
            "awesome" ->  R.id.imageButton1
            "downcast" -> R.id.imageButton2
            "confounded" -> R.id.imageButton3
            "mask" -> R.id.imageButton4
            "explode" -> R.id.imageButton5
            "angry" -> R.id.imageButton6
            else -> -1
        }
    }

}
