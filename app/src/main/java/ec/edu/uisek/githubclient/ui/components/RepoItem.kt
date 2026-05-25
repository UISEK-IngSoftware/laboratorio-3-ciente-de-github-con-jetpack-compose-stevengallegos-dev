package ec.edu.uisek.githubclient.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.uisek.githubclient.models.GithubUser
import ec.edu.uisek.githubclient.models.Repository

@Composable
fun RepoItem(
    repository: Repository,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = repository.owner.avatarUrl,
                    contentDescription = "Imagen de ${repository.name}",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )

                Row {

                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
                    }

                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                repository.description?.let {

                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                repository.language?.let {

                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {

    val repository = Repository(
        id = "1",
        name = "Repo Name",
        owner = GithubUser(
            id = "1",
            login = "OwnerName",
            avatarUrl = "https://i.pinimg.com/236x/6c/dc/9d/6cdc9ddacd8ecd32d17c29b494278b20.jpg"
        ),
        description = "Repo Description",
        language = "Kotlin"
    )

    RepoItem(repository)
}