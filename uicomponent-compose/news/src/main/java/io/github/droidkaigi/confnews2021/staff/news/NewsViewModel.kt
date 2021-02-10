package io.github.droidkaigi.confnews2021.staff.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confnews2021.AppError
import io.github.droidkaigi.confnews2021.Filters
import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.NewsContents
import io.github.droidkaigi.confnews2021.staff.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel :
    UnidirectionalViewModel<NewsViewModel.Event, NewsViewModel.Effect, NewsViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
        val filters: Filters = Filters(),
        val filteredNewsContents: NewsContents = NewsContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ChangeFavoriteFilter(val filters: Filters) : Event()
        class ToggleFavorite(val news: News) : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val AmbientNewsViewModel = ambientOf<NewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: NewsViewModel, block: @Composable () -> Unit) {
    Providers(AmbientNewsViewModel provides viewModel, content = block)
}

@Composable
fun newsViewModel() = AmbientNewsViewModel.current