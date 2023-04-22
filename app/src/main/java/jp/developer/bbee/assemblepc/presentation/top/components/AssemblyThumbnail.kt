package jp.developer.bbee.assemblepc.presentation.top.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.domain.model.Assembly

@Composable
fun AssemblyThumbnail(
    assemblies: List<Assembly> = emptyList(),
    onClick: () -> Unit
) {
    val max = assemblies.size
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .border(3.dp, Color.LightGray)
            .background(Color.White)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                AsyncImage(
                    model = if (max > 0) assemblies[0].deviceImgUrl else null,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    contentDescription = "左上の画像"
                )
                AsyncImage(
                    model = if (max > 1) assemblies[1].deviceImgUrl else null,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    contentDescription = "中上の画像"
                )
                AsyncImage(
                    model = if (max > 2) assemblies[2].deviceImgUrl else null,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    contentDescription = "右上の画像"
                )
                /*
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "左上の画像",
                    modifier = Modifier.weight(1f).fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "中上の画像",
                    modifier = Modifier.weight(1f).fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "右上の画像",
                    modifier = Modifier.weight(1f).fillMaxSize()
                )
                */
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                AsyncImage(
                    model = if (max > 3) assemblies[3].deviceImgUrl else null,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    contentDescription = "左下の画像"
                )
                AsyncImage(
                    model = if (max > 4) assemblies[4].deviceImgUrl else null,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    contentDescription = "中下の画像"
                )
                AsyncImage(
                    model = if (max > 5) assemblies[5].deviceImgUrl else null,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    contentDescription = "右下の画像"
                )
                /*
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "左下の画像",
                    modifier = Modifier.weight(1f).fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "中下の画像",
                    modifier = Modifier.weight(1f).fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "右下の画像",
                    modifier = Modifier.weight(1f).fillMaxSize()
                )
                */
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (max > 0) assemblies[0].assemblyName else "",
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Gray,
                    maxLines = 3,
                    modifier = Modifier.align(Alignment.Center)
                )
                Text(
                    text = "総額 ¥ ${"%,d".format(assemblies.sumOf { it.devicePriceRecent })}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(30.dp)
                )
            }
        }
    }
}
