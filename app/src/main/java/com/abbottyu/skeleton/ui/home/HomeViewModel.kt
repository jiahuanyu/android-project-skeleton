package com.abbottyu.skeleton.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbottyu.skeleton.base.SingleLiveEvent
import com.abbottyu.skeleton.data.repository.ArticleRepository
import com.abbottyu.skeleton.data.source.local.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(
        HomePageUIState(
            loading = true
        )
    )
    val uiState: LiveData<HomePageUIState> = _uiState

    private val _uiEffect = SingleLiveEvent<HomePageUIEffect>()
    val uiEffect: LiveData<HomePageUIEffect> = _uiEffect

    fun fetchData() {
        viewModelScope.launch {
            repository.queryArticles()
                .zip(repository.queryBanners()) { articles, banners ->
                    Pair(articles, banners)
                }
                .onStart {
                    _uiState.value = _uiState.value?.copy(loading = true)
                }
                .catch {
                    // catch
                    _uiEffect.value = HomePageUIEffect.ShowToastEffect(it.toString())
                }
                .onCompletion {
                    _uiState.value = _uiState.value?.copy(loading = false)
                }
                .collect {
                    _uiState.value = _uiState.value?.copy(items = it.first)
                    _uiEffect.value = HomePageUIEffect.ShowToastEffect("数据获取成功")
                }
        }
    }
}

data class HomePageUIState(
    // 正在加载中
    val loading: Boolean = true,
    // 文章列表
    val items: List<Article> = emptyList()
)


sealed class HomePageUIEffect {
    data class ShowToastEffect(val message: String) : HomePageUIEffect()
}