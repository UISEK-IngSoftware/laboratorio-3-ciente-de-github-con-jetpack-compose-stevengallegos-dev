package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.ui.components.RepoItem
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel

@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: () -> Unit = {},
    onEditRepo: (ec.edu.uisek.githubclient.models.Repository) -> Unit = {}
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    var repoToDelete by remember {
        mutableStateOf<ec.edu.uisek.githubclient.models.Repository?>(null)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToForm,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMsg?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            if (!isLoading && errorMsg == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(repos.size) { index ->
                        val swipeState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                when (value) {
                                    SwipeToDismissBoxValue.StartToEnd -> {
                                        onEditRepo(repos[index])
                                        false
                                    }

                                    SwipeToDismissBoxValue.EndToStart -> {
                                        repoToDelete = repos[index]
                                        false
                                    }

                                    else -> false
                                }
                            }
                        )
                        SwipeToDismissBox(
                            state = swipeState,
                            backgroundContent = {
                                val color = when (swipeState.dismissDirection) {
                                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
                                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                                    else -> MaterialTheme.colorScheme.surface
                                }

                                val text = when (swipeState.dismissDirection) {
                                    SwipeToDismissBoxValue.StartToEnd -> "Editar"
                                    SwipeToDismissBoxValue.EndToStart -> "Eliminar"
                                    else -> ""
                                }

                                val alignment = when (swipeState.dismissDirection) {
                                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                    SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                                    else -> Alignment.Center
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(16.dp),
                                    contentAlignment = alignment
                                ) {
                                    Text(text)
                                }
                            }
                        ) {
                            RepoItem(
                                repository = repos[index],
                                onEditClick = {
                                    onEditRepo(repos[index])
                                },
                                onDeleteClick = {
                                    repoToDelete = repos[index]
                                }
                            )
                        }
                    }
                }
                repoToDelete?.let { repo ->
                    AlertDialog(
                        onDismissRequest = {
                            repoToDelete = null
                        },
                        title = {
                            Text("Confirmar eliminacion")
                        },
                        text = {
                            Text("Seguro que deseas eliminar ${repo.name}?")
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.deleteRepository(

                                        owner = repo.owner.login,
                                        repo = repo.name
                                    )
                                    repoToDelete = null
                                }
                            ) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {

                                    repoToDelete = null
                                }
                            ) {
                                Text("Cancelar")
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
fun RepoListPreview(){
    GithubClientTheme {
        RepoList()
    }
}