package com.meetingmind.app.ui.meeting;

import androidx.lifecycle.SavedStateHandle;
import com.google.gson.Gson;
import com.meetingmind.app.data.remote.AiService;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.domain.repository.TranscriptRepository;
import com.meetingmind.app.util.AudioPlayerManager;
import com.meetingmind.app.util.DocumentExporter;
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
public final class MeetingDetailViewModel_Factory implements Factory<MeetingDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<MeetingRepository> meetingRepositoryProvider;

  private final Provider<TranscriptRepository> transcriptRepositoryProvider;

  private final Provider<AiService> aiServiceProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<DocumentExporter> documentExporterProvider;

  private final Provider<AudioPlayerManager> audioPlayerProvider;

  public MeetingDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<TranscriptRepository> transcriptRepositoryProvider,
      Provider<AiService> aiServiceProvider, Provider<Gson> gsonProvider,
      Provider<DocumentExporter> documentExporterProvider,
      Provider<AudioPlayerManager> audioPlayerProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.meetingRepositoryProvider = meetingRepositoryProvider;
    this.transcriptRepositoryProvider = transcriptRepositoryProvider;
    this.aiServiceProvider = aiServiceProvider;
    this.gsonProvider = gsonProvider;
    this.documentExporterProvider = documentExporterProvider;
    this.audioPlayerProvider = audioPlayerProvider;
  }

  @Override
  public MeetingDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), meetingRepositoryProvider.get(), transcriptRepositoryProvider.get(), aiServiceProvider.get(), gsonProvider.get(), documentExporterProvider.get(), audioPlayerProvider.get());
  }

  public static MeetingDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<TranscriptRepository> transcriptRepositoryProvider,
      Provider<AiService> aiServiceProvider, Provider<Gson> gsonProvider,
      Provider<DocumentExporter> documentExporterProvider,
      Provider<AudioPlayerManager> audioPlayerProvider) {
    return new MeetingDetailViewModel_Factory(savedStateHandleProvider, meetingRepositoryProvider, transcriptRepositoryProvider, aiServiceProvider, gsonProvider, documentExporterProvider, audioPlayerProvider);
  }

  public static MeetingDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      MeetingRepository meetingRepository, TranscriptRepository transcriptRepository,
      AiService aiService, Gson gson, DocumentExporter documentExporter,
      AudioPlayerManager audioPlayer) {
    return new MeetingDetailViewModel(savedStateHandle, meetingRepository, transcriptRepository, aiService, gson, documentExporter, audioPlayer);
  }
}
