package com.osmangaziyildiz.kutayindogamacerasi.util

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import java.io.InputStream

@Composable
fun LoadImageFromAssets(
    imagePath: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val bitmap = remember(imagePath) {
        try {
            val inputStream: InputStream = context.assets.open(imagePath)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.FillHeight
        )
    } ?: run {
        Box(
            modifier = modifier, contentAlignment = Alignment.Center
        ) {
            Text("Görsel yüklenemedi")

        }
    }
}
