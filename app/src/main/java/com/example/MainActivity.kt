package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FolderCopy
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.FolderCopy
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.DossierRepository
import com.example.ui.screens.CasoDetallePane
import com.example.ui.screens.CasosScreen
import com.example.ui.screens.EvidenciaScreen
import com.example.ui.screens.PatronScreen
import com.example.ui.screens.PsicologiaScreen
import com.example.ui.theme.DossierTheme
import com.example.viewmodel.DossierViewModel
import com.example.viewmodel.ThemeModePreference

object DossierRoutes {
    const val ROUTE_PATRON = "patron"
    const val ROUTE_CASOS = "casos"
    const val ROUTE_PSICOLOGIA = "psicologia"
    const val ROUTE_EVIDENCIA = "evidencia"
    const val ROUTE_CASO_DETALLE = "caso_detalle/{caseId}"

    fun caseDetail(caseId: String): String = "caso_detalle/$caseId"
}

private data class NavDestinationItem(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val primaryDestinations = listOf(
    NavDestinationItem(
        route = DossierRoutes.ROUTE_PATRON,
        labelRes = R.string.nav_patron,
        selectedIcon = Icons.Filled.AccountTree,
        unselectedIcon = Icons.Outlined.AccountTree,
        testTag = "nav_patron"
    ),
    NavDestinationItem(
        route = DossierRoutes.ROUTE_CASOS,
        labelRes = R.string.nav_casos,
        selectedIcon = Icons.Filled.FolderCopy,
        unselectedIcon = Icons.Outlined.FolderCopy,
        testTag = "nav_casos"
    ),
    NavDestinationItem(
        route = DossierRoutes.ROUTE_PSICOLOGIA,
        labelRes = R.string.nav_psicologia,
        selectedIcon = Icons.Filled.Psychology,
        unselectedIcon = Icons.Outlined.Psychology,
        testTag = "nav_psicologia"
    ),
    NavDestinationItem(
        route = DossierRoutes.ROUTE_EVIDENCIA,
        labelRes = R.string.nav_evidencia,
        selectedIcon = Icons.Filled.FormatQuote,
        unselectedIcon = Icons.Outlined.FormatQuote,
        testTag = "nav_evidencia"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: DossierViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val useDarkTheme = when (uiState.themePreference) {
                ThemeModePreference.DARK -> true
                ThemeModePreference.LIGHT -> false
                ThemeModePreference.SYSTEM -> isSystemInDarkTheme()
            }

            DossierTheme(darkTheme = useDarkTheme) {
                DossierAppShell(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DossierAppShell(
    viewModel: DossierViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isDetailRoute = currentDestination?.route == DossierRoutes.ROUTE_CASO_DETALLE

    val openCaseDetail: (String) -> Unit = { caseId ->
        viewModel.selectCase(caseId)
        navController.navigate(DossierRoutes.caseDetail(caseId))
    }

    val navigatePrimary: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    actions = {
                        val (themeIcon, themeDesc) = when (uiState.themePreference) {
                            ThemeModePreference.DARK -> Icons.Filled.DarkMode to "Modo archivo oscuro (tocar para papel claro)"
                            ThemeModePreference.LIGHT -> Icons.Filled.LightMode to "Modo papel claro (tocar para sistema)"
                            ThemeModePreference.SYSTEM -> Icons.Filled.BrightnessAuto to "Modo según sistema (tocar para archivo oscuro)"
                        }
                        IconButton(
                            onClick = viewModel::cycleThemePreference,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("theme_toggle_button")
                        ) {
                            Icon(
                                imageVector = themeIcon,
                                contentDescription = themeDesc
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
            bottomBar = {
                if (!useNavigationRail && !isDetailRoute) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp
                    ) {
                        primaryDestinations.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                            val label = stringResource(item.labelRes)
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigatePrimary(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = label
                                    )
                                },
                                label = { Text(label) },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        primaryDestinations.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true ||
                                (isDetailRoute && item.route == DossierRoutes.ROUTE_CASOS)
                            val label = stringResource(item.labelRes)
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigatePrimary(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = label
                                    )
                                },
                                label = { Text(label) },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = DossierRoutes.ROUTE_PATRON,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(DossierRoutes.ROUTE_PATRON) {
                        PatronScreen(
                            uiState = uiState,
                            onSelectPhase = viewModel::selectPhase,
                            onSelectComparison = viewModel::selectComparison,
                            onToggleMethodology = viewModel::toggleMethodology,
                            onOpenCase = openCaseDetail
                        )
                    }
                    composable(DossierRoutes.ROUTE_CASOS) {
                        CasosScreen(
                            uiState = uiState,
                            onSearchChange = viewModel::updateCaseSearch,
                            onSelectInterlocutor = viewModel::selectInterlocutorFilter,
                            onSelectTactic = viewModel::selectTacticFilter,
                            onClearFilters = viewModel::clearCaseFilters,
                            onOpenCase = openCaseDetail,
                            onSelectCaseInSplitPane = viewModel::selectCase,
                            onToggleBookmark = viewModel::toggleBookmark
                        )
                    }
                    composable(DossierRoutes.ROUTE_PSICOLOGIA) {
                        PsicologiaScreen(
                            uiState = uiState,
                            onToggleMechanism = viewModel::toggleMechanismExpanded,
                            onOpenCase = openCaseDetail
                        )
                    }
                    composable(DossierRoutes.ROUTE_EVIDENCIA) {
                        EvidenciaScreen(
                            uiState = uiState,
                            onSearchChange = viewModel::updateEvidenceSearch,
                            onSelectCategory = viewModel::selectEvidenceCategory,
                            onToggleBookmarkedFilter = viewModel::toggleShowOnlyBookmarked,
                            onClearFilters = viewModel::clearEvidenceFilters,
                            onToggleBookmark = viewModel::toggleBookmark,
                            onOpenCase = openCaseDetail
                        )
                    }
                    composable(
                        route = DossierRoutes.ROUTE_CASO_DETALLE,
                        arguments = listOf(navArgument("caseId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val caseId = backStackEntry.arguments?.getString("caseId") ?: "caso_1"
                        val documentedCase = DossierRepository.getCaseById(caseId)
                            ?: DossierRepository.documentedCases.first()
                        CasoDetallePane(
                            documentedCase = documentedCase,
                            bookmarkedIds = uiState.bookmarkedIds,
                            onToggleBookmark = viewModel::toggleBookmark,
                            showBackButton = true,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
