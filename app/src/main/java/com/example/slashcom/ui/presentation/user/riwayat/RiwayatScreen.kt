package com.example.slashcom.ui.presentation.user.riwayat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.component.BottomNavBar
import com.example.slashcom.ui.presentation.component.RiwayatEmosiCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class RiwayatEmosi(
    val emosi: String,
    val tanggal: LocalDate,
    val tingkatStres: Int,
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RiwayatScreen(
    navController: NavController,
    viewModel: RiwayatViewModel = remember { RiwayatViewModel() },
) {
    val moodList by viewModel.moodList.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val uid = UserData.uid
    
    val lazyListState = rememberLazyListState()
    
    val topBarHeightDp by remember {
        derivedStateOf {
            val offset = lazyListState.firstVisibleItemScrollOffset
            val collapseRange = 300f
            
            val collapseFraction = (offset / collapseRange).coerceIn(0f, 1f)
            val maxHeight = 88.dp
            val minHeight = 0.dp
            
            maxHeight - (maxHeight - minHeight) * collapseFraction
        }
    }
    
    val cornerRadiusDp by remember {
        derivedStateOf {
            val offset = lazyListState.firstVisibleItemScrollOffset
            val collapseRange = 300f
            
            val collapseFraction = (offset / collapseRange).coerceIn(0f, 1f)
            val maxRadius = 30.dp
            val minRadius = 0.dp
            
            maxRadius - (maxRadius - minRadius) * collapseFraction
        }
    }
    
    LaunchedEffect(uid) {
        viewModel.loadMoods(uid)
    }
    
    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(topBarHeightDp)
                    .padding(top = 24.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Riwayat", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = Color(0xFF121212),
                        textAlign = TextAlign.Center
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }, bottomBar = {
            BottomNavBar(
                navController = navController, selected = "riwayat"
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = cornerRadiusDp, topEnd = cornerRadiusDp)
                )
                .padding(horizontal = 20.dp)
        ) {
            LazyColumn(
                state = lazyListState, verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Text(
                        text = "Timeline Emosi", style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            color = Color(0xFF121212),
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                items(moodList.reversed()) { item ->
                    RiwayatEmosiCard(
                        emosi = item.emosi,
                        tanggal = LocalDate.parse(item.date, formatter),
                        tingkatStres = item.stress,
                        onClick = { })
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}