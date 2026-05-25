package ec.edu.uisek.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.models.RepositoryPayload
import ec.edu.uisek.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel () {
    private  val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos (){
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null

            try {
                _repos.value = RetrofitClient.apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value =  false

            }
        }
    }

    fun updateRepository(
        owner: String,
        repo: String,
        newName: String,
        newDescription: String
    ){
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.updateRepository(
                    owner = owner,
                    repo = repo,
                    repository= RepositoryPayload(
                        name = newName,
                        description = newDescription
                    )
                )
                fetchRepos()
            } catch (e: Exception) {
                _errorMsg.value = "Error al editar repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }

    fun deleteRepository(
        owner: String,
        repo: String
    ){
        viewModelScope.launch {
            try {
                android.util.Log.d("GITHUB_DELETE", "owner=$owner repo=$repo")
                RetrofitClient.apiService.deleteRepository(
                    owner = owner,
                    repo = repo
                )
                fetchRepos()
            } catch (e: Exception) {
                _errorMsg.value = "Error al elimianr repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }
}