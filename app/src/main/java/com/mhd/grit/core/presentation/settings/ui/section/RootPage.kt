package com.mhd.grit.core.presentation.settings.ui.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mhd.grit.core.domain.Pages
import com.mhd.grit.core.presentation.getRandomLine
import com.mhd.grit.core.presentation.settings.SettingsAction
import com.mhd.grit.core.presentation.settings.SettingsState
import com.mhd.grit.core.presentation.settings.ui.component.endItemShape
import com.mhd.grit.core.presentation.settings.ui.component.leadingItemShape
import com.mhd.grit.core.presentation.settings.ui.component.listItemColors
import com.mhd.grit.core.presentation.settings.ui.component.middleItemShape
import grit.shared.core.generated.resources.Res
import grit.shared.core.generated.resources.backup
import grit.shared.core.generated.resources.backup_desc
import grit.shared.core.generated.resources.biometric_lock
import grit.shared.core.generated.resources.biometric_lock_desc
import grit.shared.core.generated.resources.grit_plus
import grit.shared.core.generated.resources.look_and_feel
import grit.shared.core.generated.resources.look_and_feel_desc
import grit.shared.core.generated.resources.pause_notifications
import grit.shared.core.generated.resources.pause_notifications_desc
import grit.shared.core.generated.resources.reorder_tasks
import grit.shared.core.generated.resources.reorder_tasks_desc
import grit.shared.core.generated.resources.settings
import grit.shared.core.generated.resources.show_habits
import grit.shared.core.generated.resources.show_habits_desc
import grit.shared.core.generated.resources.staring_day
import grit.shared.core.generated.resources.use_24Hr
import grit.shared.core.generated.resources.use_24Hr_desc
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RootPage(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onNavigateToLookAndFeel: () -> Unit,
    onNavigateToBackup: () -> Unit,
    onNavigateToPaywall: () -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onAction(SettingsAction.OnCheckBiometric(context))
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
    ) {
        LargeFlexibleTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(text = stringResource(Res.string.settings))
            },
            subtitle = {
                Text(text = getRandomLine())
            },
            colors = TopAppBarDefaults.topAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.surface
            )
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 60.dp)
        ) {
            // ✅ كود الإعلان النهائي (يظهر فقط لغير المشتركين)
            item {
                if (!state.isUserSubscribed) { // 👈 أعدنا هذا الشرط المهم
                    androidx.compose.ui.viewinterop.AndroidView(
                        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                        factory = { context ->
                            com.google.android.gms.ads.AdView(context).apply {
                                setAdSize(com.google.android.gms.ads.AdSize.BANNER)
                                adUnitId =  "TEST_AD_UNIT" // تذكر تغيير هذا الرقم عند النشر
                                loadAd(com.google.android.gms.ads.AdRequest.Builder().build())
                            }
                        }
                    )
                    // مسافة صغيرة تحت الإعلان
                    androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Column {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.pause_notifications)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(Res.string.pause_notifications_desc)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.pauseNotifications,
                                onCheckedChange = {
                                    onAction(SettingsAction.ChangePauseNotifications(it))
                                }
                            )
                        },
                        colors = listItemColors(),
                        modifier = Modifier.clip(leadingItemShape())
                    )

                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.reorder_tasks)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(Res.string.reorder_tasks_desc)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.reorderTasks,
                                onCheckedChange = {
                                    onAction(SettingsAction.ChangeReorderTasks(it))
                                }
                            )
                        },
                        colors = listItemColors(),
                        modifier = Modifier.clip(middleItemShape())
                    )

                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.show_habits)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(Res.string.show_habits_desc)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.startingPage == Pages.Habits,
                                onCheckedChange = {
                                    onAction(
                                        SettingsAction.ChangeStartingPage(
                                            if (it) Pages.Habits else Pages.Tasks
                                        )
                                    )
                                }
                            )
                        },
                        colors = listItemColors(),
                        modifier = Modifier.clip(middleItemShape())
                    )

                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.staring_day)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.startOfTheWeek == DayOfWeek.SUNDAY,
                                onCheckedChange = {
                                    onAction(
                                        SettingsAction.ChangeStartOfTheWeek(if (it) DayOfWeek.SUNDAY else DayOfWeek.MONDAY)
                                    )
                                }
                            )
                        },
                        colors = listItemColors(),
                        modifier = Modifier.clip(middleItemShape())
                    )

                    if (state.biometricAvailable) {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = stringResource(Res.string.biometric_lock)
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = stringResource(Res.string.biometric_lock_desc)
                                )
                            },
                            trailingContent = {
                                Switch(
                                    checked = state.biometric == true,
                                    onCheckedChange = {
                                        onAction(SettingsAction.ChangeBiometricLock(it))
                                    }
                                )
                            },
                            colors = listItemColors(),
                            modifier = Modifier.clip(middleItemShape())
                        )
                    }

                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.use_24Hr)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(Res.string.use_24Hr_desc)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.is24Hr,
                                onCheckedChange = {
                                    onAction(SettingsAction.ChangeIs24Hr(it))
                                }
                            )
                        },
                        colors = listItemColors(),
                        modifier = Modifier.clip(endItemShape())
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    ListItem(
                        modifier = Modifier
                            .clip(leadingItemShape())
                            .clickable { onNavigateToLookAndFeel() },
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.look_and_feel)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(Res.string.look_and_feel_desc)
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Navigate"
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Rounded.Palette,
                                contentDescription = "Navigate",
                            )
                        },
                        colors = listItemColors()
                    )

//                    ListItem(
//                        modifier = Modifier
//                            .clip(middleItemShape())
//                            .clickable { onNavigateToServer() },
//                        colors = listItemColors(),
//                        headlineContent = {
//                            Text(
//                                text = stringResource(Res.string.server)
//                            )
//                        },
//                        leadingContent = {
//                            Icon(
//                                imageVector = Icons.Rounded.DesktopWindows,
//                                contentDescription = null
//                            )
//                        },
//                        supportingContent = {
//                            Text(
//                                text = stringResource(Res.string.server_desc)
//                            )
//                        },
//                        trailingContent = {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
//                                contentDescription = "Navigate"
//                            )
//                        },
//                    )

                    ListItem(
                        modifier = Modifier
                            .clip(endItemShape())
                            .clickable { onNavigateToBackup() },
                        colors = listItemColors(),
                        headlineContent = {
                            Text(
                                text = stringResource(Res.string.backup)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(Res.string.backup_desc)
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Navigate"
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Rounded.Download,
                                contentDescription = "Backup",
                            )
                        }
                    )
                }
            }
        }
    }}