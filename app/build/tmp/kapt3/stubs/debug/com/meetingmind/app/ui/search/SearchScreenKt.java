package com.meetingmind.app.ui.search;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextOverflow;
import com.meetingmind.app.domain.model.Meeting;
import com.meetingmind.app.domain.model.TranscriptSegment;
import com.meetingmind.app.util.TimeUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a4\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001a\u001e\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u0010"}, d2 = {"MeetingSearchResult", "", "meeting", "Lcom/meetingmind/app/domain/model/Meeting;", "onClick", "Lkotlin/Function0;", "SearchScreen", "onNavigateBack", "onMeetingClick", "Lkotlin/Function1;", "", "viewModel", "Lcom/meetingmind/app/ui/search/SearchViewModel;", "TranscriptSearchResult", "segment", "Lcom/meetingmind/app/domain/model/TranscriptSegment;", "app_debug"})
public final class SearchScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SearchScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onMeetingClick, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.ui.search.SearchViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MeetingSearchResult(com.meetingmind.app.domain.model.Meeting meeting, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TranscriptSearchResult(com.meetingmind.app.domain.model.TranscriptSegment segment, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}