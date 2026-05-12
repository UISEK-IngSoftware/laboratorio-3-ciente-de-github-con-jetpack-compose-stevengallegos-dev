package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ec.edu.uisek.githubclient.ui.components.RepoItem

@Composable
fun RepoList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
         RepoItem(
             name = "Proyecto de Django",
             description = "Un proyecto realizado en Django para la meteria de Desarrollo web",
             avatarImg = "https://i.pinimg.com/236x/6c/dc/9d/6cdc9ddacd8ecd32d17c29b494278b20.jpg",
             language = "Python"
         )
         RepoItem(
             name = "Proyecto de React",
             description = "Un proyecto realizado en React para la meteria de Desarrollo web",
             avatarImg = "https://png.pngtree.com/png-vector/20230420/ourmid/pngtree-dc-batman-logo-symbol-of-superhero-comic-cartoon-symbol-vector-png-image_51620966.jpg",
             language = "Typescript"
         )
         RepoItem(
             name = "Proyecto de Android",
             description = "Un proyecto realizado Kotlin para la meteria de Desarrollo Móvil",
             avatarImg = "https://deadline.com/wp-content/uploads/2024/07/superman-2025-logo-james-gunn.jpg?w=1024",
             language = "Kotlin"
         )
         RepoItem(
             name = "Proyecto de iOS",
             description = "Un proyecto realizado en Swift para la meteria de Desarrollo Móvil",
             avatarImg = "https://wallpaper.forfun.com/fetch/ec/ec3f313529c328c0103ea8a4bd786f19.jpeg",
             language = "Swift"
         )
     }
}