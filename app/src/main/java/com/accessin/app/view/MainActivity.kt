package com.accessin.app.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.accessin.app.R
import com.accessin.app.databinding.ActivityMainBinding
import com.accessin.app.model.util.LocationTypeConstants
import com.accessin.app.model.util.UiState
import com.accessin.app.view.adapters.SavedLocationsAdapter
import com.accessin.app.view.adapters.ScrollableSectionAdapter
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.accessin.app.viewmodel.AccessInViewModel
import com.accessin.app.viewmodel.AccessInViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: AccessInViewModel

    @Inject
    lateinit var factory: AccessInViewModelFactory

    @Inject
    lateinit var scrollableSectionsAdapter: ScrollableSectionsAdapter

    @Inject
    lateinit var savedLocationsAdapter: SavedLocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting up binding and inflating
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting up Bottom Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        // Initializing ViewModel
        viewModel = ViewModelProvider(this, factory)[AccessInViewModel::class.java]

    }
}
