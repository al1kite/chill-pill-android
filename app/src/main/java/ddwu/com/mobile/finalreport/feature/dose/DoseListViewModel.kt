package ddwu.com.mobile.finalreport.feature.dose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.finalreport.data.model.community.CommunityResponse
import ddwu.com.mobile.finalreport.data.model.community.CommunitySearchRequest
import ddwu.com.mobile.finalreport.data.remote.RetrofitClient
import ddwu.com.mobile.finalreport.data.remote.dose.DiaryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoseListViewModel : ViewModel() {

    private val diaryService = RetrofitClient.createService<DiaryService>()

    fun getMyDairyList(searchRequest: CommunitySearchRequest?): LiveData<ArrayList<CommunityResponse>?> {
        val mutableLiveData = MutableLiveData<ArrayList<CommunityResponse>?>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = diaryService.getMyDairyList(searchRequest?.keyword).execute()
                if (response.isSuccessful) {
                    val communityList = response.body()
                    mutableLiveData.postValue(communityList)
                } else {
                    mutableLiveData.postValue(null)
                }
            } catch (e: Exception) {
                mutableLiveData.postValue(null)
            }
        }

        return mutableLiveData
    }

    fun deleteItem(item: CommunityResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = diaryService.deleteDiaryItem(item.diarySeq).execute()
            if (!response.isSuccessful)
                getMyDairyList(null)
        }
    }
}