package com.meetingmind.app.di;

import com.meetingmind.app.data.local.MeetingDatabase;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideTranscriptSegmentDaoFactory implements Factory<TranscriptSegmentDao> {
  private final Provider<MeetingDatabase> databaseProvider;

  public DatabaseModule_ProvideTranscriptSegmentDaoFactory(
      Provider<MeetingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TranscriptSegmentDao get() {
    return provideTranscriptSegmentDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTranscriptSegmentDaoFactory create(
      Provider<MeetingDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTranscriptSegmentDaoFactory(databaseProvider);
  }

  public static TranscriptSegmentDao provideTranscriptSegmentDao(MeetingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTranscriptSegmentDao(database));
  }
}
