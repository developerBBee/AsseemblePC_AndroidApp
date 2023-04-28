package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.domain.model.Assembly

val deviceTextConvert = mapOf(
    "pccase" to "PCケース",
    "motherboard" to "マザーボード",
    "powersupply" to "電源",
    "cpu" to "CPU",
    "cpucooler" to "CPUクーラー",
    "pcmemory" to "メモリ",
    "ssd" to "SSD",
    "hdd35inch" to "HDD",
    "videocard" to "グラフィックボード",
)

@Composable
fun AssemblyRow(
    assembly: Assembly
) {
    var isShowDialog by remember { mutableStateOf(false) }
    Box (Modifier.padding(10.dp)) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .heightIn(min = 120.dp)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable {
                        isShowDialog = true
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = assembly.deviceImgUrl,
                    modifier = Modifier
                        .padding(5.dp)
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = "製品画像"
                )
                Text(
                    text = assembly.deviceName,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    color = Color.Black,
                    fontSize = 18.sp,
                    softWrap = true,
                )
                Text(
                    text = "¥ ${assembly.devicePriceRecent}",
                    modifier = Modifier.padding(10.dp),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Card(
            modifier = Modifier
                .offset(x = (-2).dp, y = (-2).dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            backgroundColor = Color(0xFFE0E0FF),
        ) {
            Text(
                text = deviceTextConvert.getOrDefault(assembly.deviceType, "???"),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 2.dp),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if (isShowDialog) {
        // 仮置き
        Dialog(onDismissRequest = { isShowDialog = false }) {
            Column (
                modifier = Modifier
                    .width(300.dp).height(300.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TODO 名称、画像、金額、削除ボタンを追加する
                Text(text = assembly.deviceDetail, modifier = Modifier.padding(10.dp))
            }
        }
    }
}