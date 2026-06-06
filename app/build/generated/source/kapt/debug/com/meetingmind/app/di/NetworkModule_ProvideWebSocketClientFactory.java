package com.meetingmind.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
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
public final class NetworkModule_ProvideWebSocketClientFactory implements Factory<OkHttpClient> {
  @Override
  public OkHttpClient get() {
    return provideWebSocketClient();
  }

  public static NetworkModule_ProvideWebSocketClientFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OkHttpClient provideWebSocketClient() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWebSocketClient());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideWebSocketClientFactory INSTANCE = new NetworkModule_ProvideWebSocketClientFactory();
  }
}
