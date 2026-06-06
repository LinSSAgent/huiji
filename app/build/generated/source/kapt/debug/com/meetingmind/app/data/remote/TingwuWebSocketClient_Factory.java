package com.meetingmind.app.data.remote;

import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class TingwuWebSocketClient_Factory implements Factory<TingwuWebSocketClient> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<Gson> gsonProvider;

  public TingwuWebSocketClient_Factory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public TingwuWebSocketClient get() {
    return newInstance(okHttpClientProvider.get(), gsonProvider.get());
  }

  public static TingwuWebSocketClient_Factory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider) {
    return new TingwuWebSocketClient_Factory(okHttpClientProvider, gsonProvider);
  }

  public static TingwuWebSocketClient newInstance(OkHttpClient okHttpClient, Gson gson) {
    return new TingwuWebSocketClient(okHttpClient, gson);
  }
}
