package org.example.project.presentation.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import network.chaintech.sdpcomposemultiplatform.sdp
import org.example.project.domain.model.BlogData
import org.example.project.domain.model.BlogUiState
import org.example.project.presentation.components.CommonToolbar
import org.example.project.presentation.components.GeneralTextView
import org.example.project.presentation.viewModel.BlogViewModel
import org.example.project.utils.decodeHtml
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import vridblogapp.composeapp.generated.resources.Res
import vridblogapp.composeapp.generated.resources.image_not_found_icon

@Composable
fun BlogListScreen(
    viewModel: BlogViewModel,
    onBlogClick: (BlogData) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                lastVisibleItemIndex?.let {
                    if (uiState is BlogUiState.Success &&
                        it >= listState.layoutInfo.totalItemsCount - 2 &&
                        !(uiState as BlogUiState.Success).isLoadingMore &&
                        !(uiState as BlogUiState.Success).isLastPage
                    ) {
                        viewModel.loadNextPage()
                    }
                }
            }
    }

    Scaffold(
        topBar = {
            CommonToolbar(title = "Vrid Blog",showBackButton = false){

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.refreshBlogs()
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = ""
                    )
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is BlogUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is BlogUiState.Success -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize().weight(1f),
                        contentPadding = PaddingValues(16.sdp),
                        verticalArrangement = Arrangement.spacedBy(16.sdp)
                    ) {
                        items(state.blogs) { blog ->
                            BlogItem(
                                blog = blog,
                                onClick = {
                                    viewModel.webUrl.value = ""
                                    viewModel.webUrl.value = blog.link
                                    onBlogClick(blog)
                                }
                            )
                        }

                        item {
                            if (state.isLoadingMore) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(16.sdp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }


                }
            }
            is BlogUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.refreshBlogs() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}





@Composable
fun ErrorView(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Something Went Wrong Please Try Again",
        )

        Spacer(modifier = Modifier.height(16.sdp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// Helper function to format the date
fun formatDate(dateString: String): String {
    return dateString.split("T").firstOrNull() ?: dateString
}






@Composable
fun BlogItem(blog: BlogData, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick,
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                CoilImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(size = 12.dp)),
                    imageModel = {
                        blog.featuredMediaUrl
                    },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillBounds,
                        alignment = Alignment.Center
                    ),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    failure = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                                Image(
                                    modifier = Modifier,
                                    painter = painterResource(Res.drawable.image_not_found_icon),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds
                                )
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Date with calendar icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Outlined.DateRange,
                            contentDescription = "Date",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formatDate(blog.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = blog.title.text,
                    style = MaterialTheme.typography.titleLarge.copy(
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Excerpt
                Text(
                    text = decodeHtml(blog.excerpt.text),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Read More button
                Button(
                    onClick = onClick,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Read More")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Outlined.ArrowForward,
                        contentDescription = "Read more",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}


