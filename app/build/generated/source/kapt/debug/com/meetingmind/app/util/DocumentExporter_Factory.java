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
public final class DocumentExporter_Factory implements Factory<DocumentExporter> {
  private final Provider<Context> contextProvider;

  public DocumentExporter_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DocumentExporter get() {
    return newInstance(contextProvider.get());
  }

  public static DocumentExporter_Factory create(Provider<Context> contextProvider) {
    return new DocumentExporter_Factory(contextProvider);
  }

  public static DocumentExporter newInstance(Context context) {
    return new DocumentExporter(context);
  }
}
