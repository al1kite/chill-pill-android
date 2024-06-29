package ddwu.com.mobile.finalreport.feature.dose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.finalreport.data.model.dose.DiaryRequest
import ddwu.com.mobile.finalreport.data.model.dose.DiaryResponse
import ddwu.com.mobile.finalreport.data.remote.RetrofitClient
import ddwu.com.mobile.finalreport.data.remote.dose.DiaryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class DoseViewModel(application: Application) : AndroidViewModel(application) {

    private val diaryService = RetrofitClient.createService<DiaryService>()

    private val _diaryData = MutableLiveData<DiaryResponse?>()
    val diaryData: LiveData<DiaryResponse?> get() = _diaryData

    private val _lastClicked = MutableLiveData<Int>().apply { value = -1 }
    val lastClicked: LiveData<Int> get() = _lastClicked

    // iconType을 MutableLiveData로 선언하고 초기 값을 빈 문자열로 설정
    private val _iconType = MutableLiveData<String>().apply { value = "" }
    val iconType: LiveData<String> get() = _iconType

    var title: String = ""
    var content: String = ""

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = diaryService.getTodayDiary().execute()
                if (response.isSuccessful) {
                    val diary = response.body()
                    withContext(Dispatchers.Main) {
                        diary?.let {
                            updateFields(it)
                            val buttonId = ItemFeelingBindingAdapter.getButtonIdByIconType(_iconType.value ?: "")
                            updateLastClicked(buttonId)
                        }
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    _diaryData.postValue(null)
                }
            }
        }
    }

    fun onAddBtnClick() {
        val diaryRequest = DiaryRequest(title, _iconType.value ?: "", content)
        viewModelScope.launch(Dispatchers.IO) {
            val diaryResponse = _diaryData.value
            if (diaryResponse?.diarySeq != null) {
                // 이미 다이어리가 있는 경우 updateDiary 호출
                val updateResponse = diaryService.updateDiary(diaryResponse.diarySeq, diaryRequest).execute()
                if (updateResponse.isSuccessful) {
                    fetchData()
                }
            } else {
                // 다이어리가 없는 경우 createDiary 호출
                val createResponse = diaryService.createDiary(diaryRequest).execute()
                if (createResponse.isSuccessful) {
                    fetchData()
                }
            }
        }
    }

    fun onCancelBtnClick() {
        if (_diaryData.value == null) {
            // 데이터가 없는 경우 빈칸으로 초기화
            updateFields(null)
        } else {
            // 데이터가 있는 경우 초기 데이터로 수정
            _diaryData.value?.let { updateFields(it) }
        }
    }

    fun onIconClick(iconType: String?) {
        _iconType.value = iconType
    }

    fun updateLastClicked(buttonId: Int) {
        _lastClicked.value = buttonId
    }

    private fun updateFields(diary: DiaryResponse?) {
        title = diary?.title ?: ""
        _iconType.value = diary?.iconType ?: ""
        content = diary?.content ?: ""

        _diaryData.postValue(diary)
    }
}
