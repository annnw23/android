package com.spaceexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.spaceexplorer.navigation.NavGraph
import com.spaceexplorer.ui.theme.SpaceExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceExplorerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
