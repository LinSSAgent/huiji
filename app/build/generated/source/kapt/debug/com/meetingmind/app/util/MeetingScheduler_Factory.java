package com.meetingmind.app.util;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class MeetingScheduler_Factory implements Factory<MeetingScheduler> {
  private final Provider<Context> contextProvider;

  public MeetingScheduler_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MeetingScheduler get() {
    return newInstance(contextProvider.get());
  }

  public static MeetingScheduler_Factory create(Provider<Context> contextProvider) {
    return new MeetingScheduler_Factory(contextProvider);
  }

  public static MeetingScheduler newInstance(Context context) {
    return new MeetingScheduler(context);
  }
}
