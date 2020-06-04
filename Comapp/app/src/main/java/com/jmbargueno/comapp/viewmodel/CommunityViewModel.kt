package com.jmbargueno.comapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jmbargueno.comapp.client.response.ResponseCommunity
import com.jmbargueno.comapp.repository.CommunityRepository
import javax.inject.Inject

class CommunityViewModel @Inject constructor(communityRepository: CommunityRepository) :
    ViewModel() {
    val respository = communityRepository

    fun getMyCommunity(): LiveData<ResponseCommunity> {
        return respository.getMyCommunity()
    }


}