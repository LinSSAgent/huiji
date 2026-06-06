package com.meetingmind.app.data.remote;

import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AiService_Factory implements Factory<AiService> {
  private final Provider<QwenApiService> qwenApiServiceProvider;

  private final Provider<Gson> gsonProvider;

  public AiService_Factory(Provider<QwenApiService> qwenApiServiceProvider,
      Provider<Gson> gsonProvider) {
    this.qwenApiServiceProvider = qwenApiServiceProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public AiService get() {
    return newInstance(qwenApiServiceProvider.get(), gsonProvider.get());
  }

  public static AiService_Factory create(Provider<QwenApiService> qwenApiServiceProvider,
      Provider<Gson> gsonProvider) {
    return new AiService_Factory(qwenApiServiceProvider, gsonProvider);
  }

  public static AiService newInstance(QwenApiService qwenApiService, Gson gson) {
    return new AiService(qwenApiService, gson);
  }
}
