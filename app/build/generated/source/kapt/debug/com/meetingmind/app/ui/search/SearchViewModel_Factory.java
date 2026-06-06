package com.meetingmind.app.ui.search;

import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.domain.repository.TranscriptRepository;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<MeetingRepository> meetingRepositoryProvider;

  private final Provider<TranscriptRepository> transcriptRepositoryProvider;

  public SearchViewModel_Factory(Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<TranscriptRepository> transcriptRepositoryProvider) {
    this.meetingRepositoryProvider = meetingRepositoryProvider;
    this.transcriptRepositoryProvider = transcriptRepositoryProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(meetingRepositoryProvider.get(), transcriptRepositoryProvider.get());
  }

  public static SearchViewModel_Factory create(
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<TranscriptRepository> transcriptRepositoryProvider) {
    return new SearchViewModel_Factory(meetingRepositoryProvider, transcriptRepositoryProvider);
  }

  public static SearchViewModel newInstance(MeetingRepository meetingRepository,
      TranscriptRepository transcriptRepository) {
    return new SearchViewModel(meetingRepository, transcriptRepository);
  }
}
