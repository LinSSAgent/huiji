package com.meetingmind.app.ui.meeting;

import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.util.MeetingScheduler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CreateMeetingViewModel_Factory implements Factory<CreateMeetingViewModel> {
  private final Provider<MeetingRepository> meetingRepositoryProvider;

  private final Provider<MeetingScheduler> meetingSchedulerProvider;

  public CreateMeetingViewModel_Factory(Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<MeetingScheduler> meetingSchedulerProvider) {
    this.meetingRepositoryProvider = meetingRepositoryProvider;
    this.meetingSchedulerProvider = meetingSchedulerProvider;
  }

  @Override
  public CreateMeetingViewModel get() {
    return newInstance(meetingRepositoryProvider.get(), meetingSchedulerProvider.get());
  }

  public static CreateMeetingViewModel_Factory create(
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<MeetingScheduler> meetingSchedulerProvider) {
    return new CreateMeetingViewModel_Factory(meetingRepositoryProvider, meetingSchedulerProvider);
  }

  public static CreateMeetingViewModel newInstance(MeetingRepository meetingRepository,
      MeetingScheduler meetingScheduler) {
    return new CreateMeetingViewModel(meetingRepository, meetingScheduler);
  }
}
