package com.meetingmind.app.ui.recording

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meetingmind.app.domain.model.TranscriptSegment
import com.meetingmind.app.service.RecordingService
import com.meetingmind.app.ui.theme.SpeakerColors
import com.meetingmind.app.util.TimeUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingScreen(
    meetingId: Long,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var service by remember { mutableStateOf<RecordingService?>(null) }
    var isBound by remember { mutableStateOf(false) }
    var showStopDialog by remember { mutableStateOf(false) }

    // Permission state
    var audioPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var permissionDenied by remember { mutableStateOf(false) }

    // Permission request launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        audioPermissionGranted = permissions[Manifest.permission.RECORD_AUDIO] == true
        if (!audioPermissionGranted) {
            permissionDenied = true
        }
    }

    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val recordingBinder = binder as RecordingService.RecordingBinder
                service = recordingBinder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                service = null
                isBound = false
            }
        }
    }

    // Request permission on first launch
    LaunchedEffect(Unit) {
        if (!audioPermissionGranted) {
            val permissionsToRequest = mutableListOf(Manifest.permission.RECORD_AUDIO)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    // Start and bind service only when permission is granted
    LaunchedEffect(audioPermissionGranted) {
        if (audioPermissionGranted) {
            val intent = Intent(context, RecordingService::class.java).apply {
                action = RecordingService.ACTION_START
                putExtra(RecordingService.EXTRA_MEETING_ID, meetingId)
            }
            context.startForegroundService(intent)
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isBound) {
                context.unbindService(connection)
            }
        }
    }

    val isRecording = service?.isRecording?.collectAsStateWithLifecycle()?.value ?: false
    val amplitude = service?.currentAmplitude?.collectAsStateWithLifecycle()?.value ?: 0f
    val liveTranscripts = service?.liveTranscripts?.collectAsStateWithLifecycle()?.value ?: emptyList()
    val duration = service?.recordingDuration?.collectAsStateWithLifecycle()?.value ?: 0L

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (audioPermissionGranted) "录音中" else "录音") },
                navigationIcon = {
                    IconButton(onClick = { showStopDialog = true }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        if (!audioPermissionGranted) {
            // Permission denied UI
            PermissionDeniedContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onRetry = {
                    val permissionsToRequest = mutableListOf(Manifest.permission.RECORD_AUDIO)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    permissionLauncher.launch(permissionsToRequest.toTypedArray())
                },
                onBack = onNavigateBack
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Recording status & waveform
                RecordingStatusBar(
                    isRecording = isRecording,
                    duration = duration,
                    amplitude = amplitude
                )

                HorizontalDivider()

                // Live transcript
                LiveTranscriptList(
                    transcripts = liveTranscripts,
                    modifier = Modifier.weight(1f)
                )

                // Control bar
                RecordingControlBar(
                    onStop = {
                        service?.stopRecording()
                        onNavigateBack()
                    }
                )
            }
        }
    }

    if (showStopDialog) {
        AlertDialog(
            onDismissRequest = { showStopDialog = false },
            title = { Text("停止录音") },
            text = { Text("确定要停止录音并返回吗？") },
            confirmButton = {
                TextButton(onClick = {
                    service?.stopRecording()
                    showStopDialog = false
                    onNavigateBack()
                }) {
                    Text("停止并返回")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStopDialog = false }) {
                    Text("继续录音")
                }
            }
        )
    }
}

@Composable
private fun RecordingStatusBar(
    isRecording: Boolean,
    duration: Long,
    amplitude: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Duration
        Text(
            text = TimeUtils.formatDuration(duration),
            style = MaterialTheme.typography.headlineLarge,
            color = if (isRecording) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Waveform visualization
        AudioWaveform(
            amplitude = amplitude,
            isRecording = isRecording,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Recording indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isRecording) {
                val infiniteTransition = rememberInfiniteTransition(label = "recording")
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "blinking"
                )
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(
                        color = Color.Red.copy(alpha = alpha),
                        radius = size.minDimension / 2
                    )
                }
                Text(
                    text = "正在录音",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun AudioWaveform(
    amplitude: Float,
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    val amplitudeHistory = remember { mutableStateListOf<Float>() }
    
    LaunchedEffect(amplitude) {
        if (isRecording) {
            amplitudeHistory.add(amplitude)
            if (amplitudeHistory.size > 50) {
                amplitudeHistory.removeAt(0)
            }
        }
    }

    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier) {
        val barWidth = size.width / 50f
        val centerY = size.height / 2

        amplitudeHistory.forEachIndexed { index, amp ->
            val barHeight = (amp * size.height * 0.8f).coerceAtLeast(2f)
            val x = index * barWidth + barWidth / 2
            
            drawLine(
                color = primaryColor.copy(alpha = 0.4f + amp * 0.6f),
                start = Offset(x, centerY - barHeight / 2),
                end = Offset(x, centerY + barHeight / 2),
                strokeWidth = barWidth * 0.6f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun LiveTranscriptList(
    transcripts: List<TranscriptSegment>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(transcripts.size) {
        if (transcripts.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(transcripts.size - 1)
            }
        }
    }

    if (transcripts.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "等待转写结果...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            state = listState,
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(transcripts) { segment ->
                LiveTranscriptItem(segment = segment)
            }
        }
    }
}

@Composable
private fun LiveTranscriptItem(segment: TranscriptSegment) {
    val isError = segment.speakerLabel == "system"
    val speakerColor = if (isError) Color.Red else getSpeakerColor(segment.speakerLabel)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = speakerColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = if (isError) "⚠ 系统" else segment.displaySpeaker,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = speakerColor
                )
            }
            if (!isError) {
                Text(
                    text = TimeUtils.formatDurationMs(segment.startTimeMs),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = segment.text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun RecordingControlBar(
    onStop: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            FilledTonalButton(
                onClick = onStop,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(Icons.Filled.Stop, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("停止录音")
            }
        }
    }
}

private fun getSpeakerColor(speakerLabel: String): Color {
    val index = Math.abs(speakerLabel.hashCode()) % SpeakerColors.size
    return SpeakerColors[index]
}

@Composable
private fun PermissionDeniedContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.Mic,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "需要录音权限",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "请授予录音权限以使用实时转写功能",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(onClick = onBack) {
                Text("返回")
            }
            Button(onClick = onRetry) {
                Text("授权")
            }
        }
    }
}
