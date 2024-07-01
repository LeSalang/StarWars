import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lesa.uikit.R

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    val loadingLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading_animation
        )
    )
    val loadingProgress by animateLottieCompositionAsState(
        loadingLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 2f
    )
    LottieAnimation(
        composition = loadingLottieComposition,
        progress = loadingProgress,
        modifier = modifier.sizeIn(10.dp, 10.dp, 100.dp, 100.dp)
    )
}
