package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.ui.screens.RepoForm
import ec.edu.uisek.githubclient.ui.screens.RepoList
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            GithubClientTheme {
                val listViewModel: RepoListViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("repoList") }
                var repoToEdit by remember { mutableStateOf<ec.edu.uisek.githubclient.models.Repository?>(null) }
                when (currentScreen) {
                    "repoList" -> RepoList(
                        viewModel = listViewModel,
                        onNavigateToForm = {
                            repoToEdit = null
                            currentScreen = "repoForm"
                        },
                        onEditRepo = { repo ->
                            repoToEdit = repo
                            currentScreen = "repoForm"
                        }
                    )
                    "repoForm" -> RepoForm(
                        onBackClick = { currentScreen = "repoList" },
                        onSaveSuccess = {
                            listViewModel.fetchRepos()
                            currentScreen = "repoList"
                        },
                        initialName = repoToEdit?.name ?: "",
                        initialDescription = repoToEdit?.description ?: "",
                        isEditMode = repoToEdit != null,
                        onUpdateRepo = { newName, newDescription ->
                            repoToEdit?.let { repo ->
                                listViewModel.updateRepository(
                                    owner = repo.owner.login,
                                    repo = repo.name,
                                    newName = newName,
                                    newDescription = newDescription
                                )
                                currentScreen = "repoList"
                            }
                        }
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    GithubClientTheme {
        RepoList()
    }
}