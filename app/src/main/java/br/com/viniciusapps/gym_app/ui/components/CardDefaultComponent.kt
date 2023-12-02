package br.com.viniciusapps.gym_app.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import br.com.viniciusapps.gym_app.ui.theme.OrangeStrong
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCard(vararg textos: String, onClick: () -> Unit, image: String,onDelete: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = OrangeStrong
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    for (texto in textos) {
                        Text(
                            text = texto,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }


            Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                IconButton(
                    modifier = Modifier.padding(start = 12.dp,top = 8.dp),
                    onClick = onDelete) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }

            }
        }
    }
}



