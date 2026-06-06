package com.meetingmind.app.ui.home;

import com.meetingmind.app.domain.repository.MeetingRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<MeetingRepository> meetingRepositoryProvider;

  public HomeViewModel_Factory(Provider<MeetingRepository> meetingRepositoryProvider) {
    this.meetingRepositoryProvider = meetingRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(meetingRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<MeetingRepository> meetingRepositoryProvider) {
    return new HomeViewModel_Factory(meetingRepositoryProvider);
  }

  public static HomeViewModel newInstance(MeetingRepository meetingRepository) {
    return new HomeViewModel(meetingRepository);
  }
}
