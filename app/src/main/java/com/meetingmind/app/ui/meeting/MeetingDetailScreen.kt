package com.meetingmind.app.ui.meeting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meetingmind.app.domain.model.MeetingStatus
import com.meetingmind.app.domain.model.TranscriptSegment
import com.meetingmind.app.ui.components.MarkdownContent
import com.meetingmind.app.ui.theme.SpeakerColors
import com.meetingmind.app.util.AudioPlayerManager
import com.meetingmind.app.util.TimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailScreen(
    meetingId: Long,
    onNavigateBack: () -> Unit,
    onStartRecording: () -> Unit,
    viewModel: MeetingDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("转写", "摘要", "待办", "关键词")
    var showExportMenu by remember { mutableStateOf(false) }

    // Auto-start recording: navigate to recording screen when shouldAutoStartRecording becomes true
    LaunchedEffect(uiState.shouldAutoStartRecording) {
        if (uiState.shouldAutoStartRecording) {
            viewModel.onAutoStartConsumed()
            onStartRecording()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.meeting?.title ?: "会议详情",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    // Export menu
                    Box {
                        IconButton(onClick = { showExportMenu = true }) {
                            Icon(Icons.Outlined.Share, contentDescription = "导出")
                        }
                        DropdownMenu(
                            expanded = showExportMenu,
                            onDismissRequest = { showExportMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("导出为 Markdown") },
                                onClick = {
                                    viewModel.exportAs("md")
                                    showExportMenu = false
                                },
                                leadingIcon = { Icon(Icons.Outlined.Description, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("导出为 Word") },
                                onClick = {
                                    viewModel.exportAs("docx")
                                    showExportMenu = false
                                },
                                leadingIcon = { Icon(Icons.Outlined.Article, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("导出为 PDF") },
                                onClick = {
                                    viewModel.exportAs("pdf")
                                    showExportMenu = false
                                },
                                leadingIcon = { Icon(Icons.Outlined.PictureAsPdf, null) }
                            )
                        }
                    }
                    // AI generate button
                    IconButton(
                        onClick = { viewModel.generateAiContent() },
                        enabled = !uiState.isAiProcessing && uiState.segments.isNotEmpty()
                    ) {
                        if (uiState.isAiProcessing) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Outlined.AutoAwesome, contentDescription = "AI分析")
                        }
                    }
                    if (uiState.meeting?.status == MeetingStatus.SCHEDULED ||
                        uiState.meeting?.status == MeetingStatus.COMPLETED) {
                        IconButton(onClick = onStartRecording) {
                            Icon(Icons.Filled.Mic, contentDescription = "开始录音")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val meeting = uiState.meeting ?: return@Scaffold

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Meeting Info Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    if (!meeting.description.isNullOrBlank()) {
                        Text(
                            text = meeting.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        meeting.startTime?.let {
                            Text(
                                text = "开始: ${TimeUtils.formatDateTime(it)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        meeting.duration?.let {
                            Text(
                                text = "时长: ${TimeUtils.formatDuration(it)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Auto-start recording toggle (only show for scheduled/completed meetings)
                    if (meeting.status == MeetingStatus.SCHEDULED ||
                        meeting.status == MeetingStatus.COMPLETED
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "自动开始录音",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "进入会议后自动开始录音",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = meeting.autoStartRecording,
                                onCheckedChange = { viewModel.setAutoStartRecording(it) },
                                thumbContent = {
                                    if (meeting.autoStartRecording) {
                                        Icon(
                                            Icons.Filled.Mic,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Audio Player Bar (shown only when recording file exists)
            meeting.audioFilePath?.let { audioPath ->
                AudioPlayerBar(
                    audioFilePath = audioPath,
                    audioPlayer = viewModel.audioPlayer
                )
            }

            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> TranscriptTab(
                    segments = uiState.filteredSegments,
                    speakers = uiState.speakers,
                    selectedSpeaker = uiState.selectedSpeakerFilter,
                    onSpeakerFilter = viewModel::filterBySpeaker,
                    onEditSegment = viewModel::updateSegmentText,
                    onRenameSpeaker = viewModel::updateSpeakerName
                )
                1 -> SummaryTab(
                    summary = meeting.summary,
                    isAiProcessing = uiState.isAiProcessing
                )
                2 -> TodoTab(
                    todos = meeting.todos,
                    isAiProcessing = uiState.isAiProcessing
                )
                3 -> KeywordTab(
                    keywords = meeting.keywords,
                    isAiProcessing = uiState.isAiProcessing
                )
            }
        }
    }
}

@Composable
private fun TranscriptTab(
    segments: List<TranscriptSegment>,
    speakers: List<com.meetingmind.app.data.local.dao.SpeakerInfo>,
    selectedSpeaker: String?,
    onSpeakerFilter: (String?) -> Unit,
    onEditSegment: (Long, String) -> Unit,
    onRenameSpeaker: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Speaker filter chips
        if (speakers.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedSpeaker == null,
                        onClick = { onSpeakerFilter(null) },
                        label = { Text("全部") }
                    )
                }
                items(speakers) { speaker ->
                    val displayName = speaker.speakerName ?: speaker.speakerLabel
                    FilterChip(
                        selected = selectedSpeaker == displayName,
                        onClick = { onSpeakerFilter(displayName) },
                        label = { Text(displayName) }
                    )
                }
            }
        }

        if (segments.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Outlined.SpeakerNotes,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "暂无转写内容",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(segments, key = { it.id }) { segment ->
                    TranscriptSegmentItem(
                        segment = segment,
                        speakerColor = getSpeakerColor(segment.speakerLabel),
                        onEdit = onEditSegment,
                        onRenameSpeaker = onRenameSpeaker
                    )
                }
            }
        }
    }
}

@Composable
private fun TranscriptSegmentItem(
    segment: TranscriptSegment,
    speakerColor: androidx.compose.ui.graphics.Color,
    onEdit: (Long, String) -> Unit,
    onRenameSpeaker: (String, String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editText by remember(segment.text) { mutableStateOf(segment.text) }
    var showRenameDialog by remember { mutableStateOf(false) }

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
                    text = segment.displaySpeaker,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = speakerColor
                )
            }
            Text(
                text = TimeUtils.formatDurationMs(segment.startTimeMs),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { showRenameDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "编辑说话人",
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        if (isEditing) {
            OutlinedTextField(
                value = editText,
                onValueChange = { editText = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = {
                    Row {
                        IconButton(onClick = {
                            onEdit(segment.id, editText)
                            isEditing = false
                        }) {
                            Icon(Icons.Filled.Check, "确认")
                        }
                        IconButton(onClick = {
                            editText = segment.text
                            isEditing = false
                        }) {
                            Icon(Icons.Filled.Close, "取消")
                        }
                    }
                }
            )
        } else {
            Text(
                text = segment.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }

    if (showRenameDialog) {
        var newName by remember { mutableStateOf(segment.speakerName ?: "") }
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("命名说话人") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("说话人名称") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newName.isNotBlank()) {
                        onRenameSpeaker(segment.speakerLabel, newName.trim())
                    }
                    showRenameDialog = false
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun SummaryTab(summary: String?, isAiProcessing: Boolean = false) {
    if (isAiProcessing && summary.isNullOrBlank()) {
        AiProcessingPlaceholder(
            icon = Icons.Outlined.AutoAwesome,
            title = "AI 正在分析会议内容…",
            subtitle = "正在生成摘要，请稍候"
        )
    } else if (summary.isNullOrBlank()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.Summarize,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "暂无摘要",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "录音结束后将自动生成，或点击右上角 AI 按钮",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            if (isAiProcessing) {
                item { AiProcessingBanner("AI 正在重新分析…") }
            }
            item {
                MarkdownContent(
                    markdown = summary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TodoTab(todos: List<com.meetingmind.app.domain.model.TodoItem>, isAiProcessing: Boolean = false) {
    if (isAiProcessing && todos.isEmpty()) {
        AiProcessingPlaceholder(
            icon = Icons.Outlined.Checklist,
            title = "AI 正在提取待办事项…",
            subtitle = "录音结束后自动分析"
        )
    } else if (todos.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.Checklist,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "暂无待办事项",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (todo.isCompleted) Icons.Filled.CheckCircle
                            else Icons.Outlined.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (todo.isCompleted) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = todo.content,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            todo.assignee?.let {
                                Text(
                                    text = "负责人: $it",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun KeywordTab(keywords: List<com.meetingmind.app.domain.model.Keyword>, isAiProcessing: Boolean = false) {
    if (isAiProcessing && keywords.isEmpty()) {
        AiProcessingPlaceholder(
            icon = Icons.Outlined.Label,
            title = "AI 正在提取关键词…",
            subtitle = "录音结束后自动分析"
        )
    } else if (keywords.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.Label,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "暂无关键词",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(keywords) { keyword ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Tag,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = keyword.word,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        keyword.description?.let {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getSpeakerColor(speakerLabel: String): androidx.compose.ui.graphics.Color {
    val index = speakerLabel.hashCode().let { Math.abs(it) } % SpeakerColors.size
    return SpeakerColors[index]
}

@Composable
private fun AiProcessingPlaceholder(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                strokeWidth = 2.5.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AiProcessingBanner(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun AudioPlayerBar(
    audioFilePath: String,
    audioPlayer: AudioPlayerManager
) {
    val isPlaying by audioPlayer.isPlaying.collectAsStateWithLifecycle()
    val currentPosition by audioPlayer.currentPosition.collectAsStateWithLifecycle()
    val duration by audioPlayer.duration.collectAsStateWithLifecycle()
    val currentFile by audioPlayer.currentFilePath.collectAsStateWithLifecycle()

    // Determine if THIS meeting's audio is currently loaded
    val isThisFileLoaded = currentFile == audioFilePath
    val isThisPlaying = isThisFileLoaded && isPlaying

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Play / Pause button
            IconButton(
                onClick = { audioPlayer.play(audioFilePath) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isThisPlaying) Icons.Filled.Pause
                                  else Icons.Filled.PlayArrow,
                    contentDescription = if (isThisPlaying) "暂停" else "播放",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Current time
            Text(
                text = TimeUtils.formatDurationMs(currentPosition),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(42.dp)
            )

            // Seek bar
            Slider(
                value = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                onValueChange = { ratio ->
                    audioPlayer.seekTo((ratio * duration).toLong())
                },
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary
                )
            )

            // Total duration
            Text(
                text = TimeUtils.formatDurationMs(duration),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(42.dp)
            )
        }
    }
}
