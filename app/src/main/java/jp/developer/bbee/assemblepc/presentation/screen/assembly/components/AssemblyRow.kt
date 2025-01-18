package jp.developer.bbee.assemblepc.presentation.screen.assembly.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.domain.model.CompositionItem
import jp.developer.bbee.assemblepc.presentation.common.BaseBGPreview
import jp.developer.bbee.assemblepc.presentation.components.MultipleTotalPrice

private val DEVICE_TEXT_TABLE = mapOf(
    "pccase" to "PCケース",
    "motherboard" to "マザーボード",
    "powersupply" to "電源",
    "cpu" to "CPU",
    "cpucooler" to "CPUクーラー",
    "pcmemory" to "メモリ",
    "ssd" to "SSD",
    "hdd35inch" to "HDD",
    "videocard" to "グラフィックボード",
    "ossoft" to "OS",
    "lcdmonitor" to "ディスプレイ",
    "keyboard" to "キーボード",
    "mouse" to "マウス",
    "dvddrive" to "DVDドライブ",
    "bluraydrive" to "BDドライブ",
    "soundcard" to "サウンドカード",
    "pcspeaker" to "スピーカー",
    "fancontroller" to "ファンコントローラー",
    "casefan" to "ファン"
)

@Composable
fun AssemblyRow(
    modifier: Modifier = Modifier,
    item: CompositionItem,
    onAssemblyClick: () -> Unit,
) {

    Box (modifier = modifier) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .heightIn(min = 120.dp)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAssemblyClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = item.deviceImgUrl,
                    modifier = Modifier
                        .padding(5.dp)
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = "製品画像"
                )

                // 製品名
                Text(
                    text = item.deviceName,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    fontSize = 18.sp,
                    softWrap = true,
                )

                // 単価
                Text(
                    text = item.devicePriceRecent.yenOrUnknown(),
                    modifier = Modifier
                        .padding(10.dp)
                        .offset(y = (-20).dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Card(
            modifier = Modifier
                .offset(x = (-2).dp, y = (-2).dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Text(
                text = DEVICE_TEXT_TABLE.getOrDefault(item.deviceType, "???"),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 2.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        MultipleTotalPrice(quantity = item.quantity, price = item.devicePriceRecent)
    }
}

@Preview
@Composable
private fun AssemblyRowPreview(modifier: Modifier = Modifier) {
    BaseBGPreview {
        AssemblyRow(
            modifier = modifier,
            item = Constants.COMPOSITION_SAMPLE.items.first(),
            onAssemblyClick = {}
        )
    }
}

@Preview
@Composable
private fun MultiAssemblyRowPreview(modifier: Modifier = Modifier) {
    BaseBGPreview {
        AssemblyRow(
            modifier = modifier,
            item = Constants.COMPOSITION_SAMPLE.items.first().copy(quantity = 3),
            onAssemblyClick = {}
        )
    }
}
