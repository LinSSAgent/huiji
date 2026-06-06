package com.meetingmind.app.di;

import com.meetingmind.app.data.local.MeetingDatabase;
import com.meetingmind.app.data.local.dao.MeetingDao;
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
public final class DatabaseModule_ProvideMeetingDaoFactory implements Factory<MeetingDao> {
  private final Provider<MeetingDatabase> databaseProvider;

  public DatabaseModule_ProvideMeetingDaoFactory(Provider<MeetingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MeetingDao get() {
    return provideMeetingDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideMeetingDaoFactory create(
      Provider<MeetingDatabase> databaseProvider) {
    return new DatabaseModule_ProvideMeetingDaoFactory(databaseProvider);
  }

  public static MeetingDao provideMeetingDao(MeetingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMeetingDao(database));
  }
}
