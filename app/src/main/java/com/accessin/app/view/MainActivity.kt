package com.accessin.app.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.accessin.app.model.util.LocationTypeConstants
import com.accessin.app.model.util.UiState
import com.accessin.app.ui.theme.AccessInTheme
import com.accessin.app.viewmodel.AccessInViewModel
import com.accessin.app.viewmodel.AccessInViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    lateinit var viewModel: AccessInViewModel

    @Inject
    lateinit var factory: AccessInViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[AccessInViewModel::class.java]

        //viewModel.getLocationAccessibilityData("eI7Ye94vNnvwxX0KZBSb")
        //viewModel.searchForSpecificLocationType(LocationTypeConstants.COFFEE_SHOP)
        //viewModel.searchAccordingToQuery("Starb")
        //viewModel.getScrollableSections()

        /*
        viewModel.getSavedLocations().observe(this) { locations ->
            locations.forEach { location ->

            }
        }
         */


        /*
        viewModel.specificLocationTypeList.observe(this) { state ->
            when(state){
                is UiState.Loading -> {

                }

                is UiState.Error -> {

                }

                is UiState.Success -> {
                    state.data.forEach { location ->
                        viewModel.saveLocation(location)
                    }
                }
            }
        }

         */


        setContent {
            AccessInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccessInTheme {
        Greeting("Android")
    }
}