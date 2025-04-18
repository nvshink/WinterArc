package com.nvshink.winterarc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.nvshink.winterarc.data.repository.ExerciseRepository
import com.nvshink.winterarc.data.room.WinterArcDatabase
import com.nvshink.winterarc.di.AppModule
import com.nvshink.winterarc.ui.WinterArcApp
import com.nvshink.winterarc.ui.theme.WinterArcTheme
import com.nvshink.winterarc.ui.viewModel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val db = AppModule.provideLocalDatabase(applicationContext)
            WinterArcTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(modifier = Modifier
                    .padding(
                        start = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(layoutDirection)
                    )
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    WinterArcApp(windowSize = windowSize.widthSizeClass, db = db)
                }
            }
        }
    }
}
