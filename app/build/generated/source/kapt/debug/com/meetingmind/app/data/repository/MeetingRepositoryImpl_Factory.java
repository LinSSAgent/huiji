package com.meetingmind.app.data.repository;

import com.google.gson.Gson;
import com.meetingmind.app.data.local.dao.MeetingDao;
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
public final class MeetingRepositoryImpl_Factory implements Factory<MeetingRepositoryImpl> {
  private final Provider<MeetingDao> meetingDaoProvider;

  private final Provider<Gson> gsonProvider;

  public MeetingRepositoryImpl_Factory(Provider<MeetingDao> meetingDaoProvider,
      Provider<Gson> gsonProvider) {
    this.meetingDaoProvider = meetingDaoProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public MeetingRepositoryImpl get() {
    return newInstance(meetingDaoProvider.get(), gsonProvider.get());
  }

  public static MeetingRepositoryImpl_Factory create(Provider<MeetingDao> meetingDaoProvider,
      Provider<Gson> gsonProvider) {
    return new MeetingRepositoryImpl_Factory(meetingDaoProvider, gsonProvider);
  }

  public static MeetingRepositoryImpl newInstance(MeetingDao meetingDao, Gson gson) {
    return new MeetingRepositoryImpl(meetingDao, gson);
  }
}
