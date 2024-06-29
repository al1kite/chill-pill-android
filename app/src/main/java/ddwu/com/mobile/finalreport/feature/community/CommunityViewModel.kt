package ddwu.com.mobile.finalreport.feature.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.finalreport.data.model.community.CommunityResponse
import ddwu.com.mobile.finalreport.data.model.community.CommunitySearchRequest
import ddwu.com.mobile.finalreport.data.remote.RetrofitClient
import ddwu.com.mobile.finalreport.data.remote.community.CommunityService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityViewModel : ViewModel() {

    private val communityService = RetrofitClient.createService<CommunityService>()

    fun getCommunityList(searchRequest: CommunitySearchRequest): LiveData<ArrayList<CommunityResponse>?> {
        val mutableLiveData = MutableLiveData<ArrayList<CommunityResponse>?>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = communityService.getCommunityList(searchRequest.keyword).execute()
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
}


