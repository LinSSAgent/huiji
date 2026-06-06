package com.meetingmind.app.service;

import com.meetingmind.app.data.remote.AudioRecorder;
import com.meetingmind.app.data.remote.TingwuWebSocketClient;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.domain.repository.TranscriptRepository;
import com.meetingmind.app.util.AudioFileManager;
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
public final class RecordingService_MembersInjector implements MembersInjector<RecordingService> {
  private final Provider<AudioRecorder> audioRecorderProvider;

  private final Provider<TingwuWebSocketClient> tingwuClientProvider;

  private final Provider<AudioFileManager> audioFileManagerProvider;

  private final Provider<MeetingRepository> meetingRepositoryProvider;

  private final Provider<TranscriptRepository> transcriptRepositoryProvider;

  public RecordingService_MembersInjector(Provider<AudioRecorder> audioRecorderProvider,
      Provider<TingwuWebSocketClient> tingwuClientProvider,
      Provider<AudioFileManager> audioFileManagerProvider,
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<TranscriptRepository> transcriptRepositoryProvider) {
    this.audioRecorderProvider = audioRecorderProvider;
    this.tingwuClientProvider = tingwuClientProvider;
    this.audioFileManagerProvider = audioFileManagerProvider;
    this.meetingRepositoryProvider = meetingRepositoryProvider;
    this.transcriptRepositoryProvider = transcriptRepositoryProvider;
  }

  public static MembersInjector<RecordingService> create(
      Provider<AudioRecorder> audioRecorderProvider,
      Provider<TingwuWebSocketClient> tingwuClientProvider,
      Provider<AudioFileManager> audioFileManagerProvider,
      Provider<MeetingRepository> meetingRepositoryProvider,
      Provider<TranscriptRepository> transcriptRepositoryProvider) {
    return new RecordingService_MembersInjector(audioRecorderProvider, tingwuClientProvider, audioFileManagerProvider, meetingRepositoryProvider, transcriptRepositoryProvider);
  }

  @Override
  public void injectMembers(RecordingService instance) {
    injectAudioRecorder(instance, audioRecorderProvider.get());
    injectTingwuClient(instance, tingwuClientProvider.get());
    injectAudioFileManager(instance, audioFileManagerProvider.get());
    injectMeetingRepository(instance, meetingRepositoryProvider.get());
    injectTranscriptRepository(instance, transcriptRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.meetingmind.app.service.RecordingService.audioRecorder")
  public static void injectAudioRecorder(RecordingService instance, AudioRecorder audioRecorder) {
    instance.audioRecorder = audioRecorder;
  }

  @InjectedFieldSignature("com.meetingmind.app.service.RecordingService.tingwuClient")
  public static void injectTingwuClient(RecordingService instance,
      TingwuWebSocketClient tingwuClient) {
    instance.tingwuClient = tingwuClient;
  }

  @InjectedFieldSignature("com.meetingmind.app.service.RecordingService.audioFileManager")
  public static void injectAudioFileManager(RecordingService instance,
      AudioFileManager audioFileManager) {
    instance.audioFileManager = audioFileManager;
  }

  @InjectedFieldSignature("com.meetingmind.app.service.RecordingService.meetingRepository")
  public static void injectMeetingRepository(RecordingService instance,
      MeetingRepository meetingRepository) {
    instance.meetingRepository = meetingRepository;
  }

  @InjectedFieldSignature("com.meetingmind.app.service.RecordingService.transcriptRepository")
  public static void injectTranscriptRepository(RecordingService instance,
      TranscriptRepository transcriptRepository) {
    instance.transcriptRepository = transcriptRepository;
  }
}
