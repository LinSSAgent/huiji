package com.meetingmind.app.data.repository;

import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
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
public final class TranscriptRepositoryImpl_Factory implements Factory<TranscriptRepositoryImpl> {
  private final Provider<TranscriptSegmentDao> transcriptSegmentDaoProvider;

  public TranscriptRepositoryImpl_Factory(
      Provider<TranscriptSegmentDao> transcriptSegmentDaoProvider) {
    this.transcriptSegmentDaoProvider = transcriptSegmentDaoProvider;
  }

  @Override
  public TranscriptRepositoryImpl get() {
    return newInstance(transcriptSegmentDaoProvider.get());
  }

  public static TranscriptRepositoryImpl_Factory create(
      Provider<TranscriptSegmentDao> transcriptSegmentDaoProvider) {
    return new TranscriptRepositoryImpl_Factory(transcriptSegmentDaoProvider);
  }

  public static TranscriptRepositoryImpl newInstance(TranscriptSegmentDao transcriptSegmentDao) {
    return new TranscriptRepositoryImpl(transcriptSegmentDao);
  }
}
