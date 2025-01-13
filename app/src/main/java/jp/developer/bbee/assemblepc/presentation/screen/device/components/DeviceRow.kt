package jp.developer.bbee.assemblepc.presentation.screen.device.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.domain.model.Device

@Composable
fun DeviceRow(
    device: Device,
    onClick: (Device) -> Unit
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.heightIn(min = 120.dp).padding(vertical = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick(device) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = device.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h6,
                    //fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Row (verticalAlignment = Alignment.CenterVertically){
                    AsyncImage(
                        model = device.imgurl,
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp),
                        contentDescription = "製品画像"
                    )
                    Text(
                        modifier = Modifier.weight(1f).padding(horizontal = 5.dp),
                        text = device.detail,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = device.price.yenOrUnknown(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
    }
}