package com.meetingmind.app.ui.meeting;

import androidx.lifecycle.ViewModel;
import com.meetingmind.app.domain.model.Meeting;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.util.MeetingScheduler;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\'\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\u0014R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/meetingmind/app/ui/meeting/CreateMeetingViewModel;", "Landroidx/lifecycle/ViewModel;", "meetingRepository", "Lcom/meetingmind/app/domain/repository/MeetingRepository;", "meetingScheduler", "Lcom/meetingmind/app/util/MeetingScheduler;", "(Lcom/meetingmind/app/domain/repository/MeetingRepository;Lcom/meetingmind/app/util/MeetingScheduler;)V", "_createdMeetingId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "createdMeetingId", "Lkotlinx/coroutines/flow/StateFlow;", "getCreatedMeetingId", "()Lkotlinx/coroutines/flow/StateFlow;", "createMeeting", "", "title", "", "description", "scheduledTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CreateMeetingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.domain.repository.MeetingRepository meetingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.meetingmind.app.util.MeetingScheduler meetingScheduler = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _createdMeetingId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> createdMeetingId = null;
    
    @javax.inject.Inject()
    public CreateMeetingViewModel(@org.jetbrains.annotations.NotNull()
    com.meetingmind.app.domain.repository.MeetingRepository meetingRepository, @org.jetbrains.annotations.NotNull()
    com.meetingmind.app.util.MeetingScheduler meetingScheduler) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getCreatedMeetingId() {
        return null;
    }
    
    public final void createMeeting(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    java.lang.String description, @org.jetbrains.annotations.Nullable()
    java.lang.Long scheduledTime) {
    }
}