package com.meetingmind.app.receiver;

import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.util.MeetingScheduler;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<MeetingRepository> meetingRepositoryProvider;

  private final Provider<MeetingScheduler> meetingSchedulerProvider;

  public BootReceiver_MembersInjector(Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<MeetingScheduler> meetingSchedulerProvider) {
    this.meetingRepositoryProvider = meetingRepositoryProvider;
    this.meetingSchedulerProvider = meetingSchedulerProvider;
  }

  public static MembersInjector<BootReceiver> create(
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<MeetingScheduler> meetingSchedulerProvider) {
    return new BootReceiver_MembersInjector(meetingRepositoryProvider, meetingSchedulerProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectMeetingRepository(instance, meetingRepositoryProvider.get());
    injectMeetingScheduler(instance, meetingSchedulerProvider.get());
  }

  @InjectedFieldSignature("com.meetingmind.app.receiver.BootReceiver.meetingRepository")
  public static void injectMeetingRepository(BootReceiver instance,
      MeetingRepository meetingRepository) {
    instance.meetingRepository = meetingRepository;
  }

  @InjectedFieldSignature("com.meetingmind.app.receiver.BootReceiver.meetingScheduler")
  public static void injectMeetingScheduler(BootReceiver instance,
      MeetingScheduler meetingScheduler) {
    instance.meetingScheduler = meetingScheduler;
  }
}
