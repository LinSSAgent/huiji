package com.meetingmind.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.meetingmind.app.data.local.MeetingDatabase;
import com.meetingmind.app.data.local.dao.MeetingDao;
import com.meetingmind.app.data.local.dao.TranscriptSegmentDao;
import com.meetingmind.app.data.remote.AiService;
import com.meetingmind.app.data.remote.AudioRecorder;
import com.meetingmind.app.data.remote.QwenApiService;
import com.meetingmind.app.data.remote.TingwuWebSocketClient;
import com.meetingmind.app.data.repository.MeetingRepositoryImpl;
import com.meetingmind.app.data.repository.TranscriptRepositoryImpl;
import com.meetingmind.app.di.DatabaseModule_ProvideDatabaseFactory;
import com.meetingmind.app.di.DatabaseModule_ProvideMeetingDaoFactory;
import com.meetingmind.app.di.DatabaseModule_ProvideTranscriptSegmentDaoFactory;
import com.meetingmind.app.di.NetworkModule_ProvideGsonFactory;
import com.meetingmind.app.di.NetworkModule_ProvideOkHttpClientFactory;
import com.meetingmind.app.di.NetworkModule_ProvideQwenApiServiceFactory;
import com.meetingmind.app.di.NetworkModule_ProvideWebSocketClientFactory;
import com.meetingmind.app.domain.repository.MeetingRepository;
import com.meetingmind.app.domain.repository.TranscriptRepository;
import com.meetingmind.app.receiver.BootReceiver;
import com.meetingmind.app.receiver.BootReceiver_MembersInjector;
import com.meetingmind.app.service.RecordingService;
import com.meetingmind.app.service.RecordingService_MembersInjector;
import com.meetingmind.app.ui.home.HomeViewModel;
import com.meetingmind.app.ui.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.meetingmind.app.ui.meeting.CreateMeetingViewModel;
import com.meetingmind.app.ui.meeting.CreateMeetingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.meetingmind.app.ui.meeting.MeetingDetailViewModel;
import com.meetingmind.app.ui.meeting.MeetingDetailViewModel_HiltModules_KeyModule_ProvideFactory;
import com.meetingmind.app.ui.search.SearchViewModel;
import com.meetingmind.app.ui.search.SearchViewModel_HiltModules_KeyModule_ProvideFactory;
import com.meetingmind.app.util.AudioFileManager;
import com.meetingmind.app.util.AudioPlayerManager;
import com.meetingmind.app.util.DocumentExporter;
import com.meetingmind.app.util.MeetingScheduler;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SetBuilder;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class DaggerMeetingMindApp_HiltComponents_SingletonC {
  private DaggerMeetingMindApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public MeetingMindApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements MeetingMindApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements MeetingMindApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements MeetingMindApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements MeetingMindApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements MeetingMindApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements MeetingMindApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements MeetingMindApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public MeetingMindApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends MeetingMindApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends MeetingMindApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends MeetingMindApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends MeetingMindApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(4).add(CreateMeetingViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(MeetingDetailViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SearchViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends MeetingMindApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CreateMeetingViewModel> createMeetingViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<MeetingDetailViewModel> meetingDetailViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.createMeetingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.meetingDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(4).put("com.meetingmind.app.ui.meeting.CreateMeetingViewModel", ((Provider) createMeetingViewModelProvider)).put("com.meetingmind.app.ui.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.meetingmind.app.ui.meeting.MeetingDetailViewModel", ((Provider) meetingDetailViewModelProvider)).put("com.meetingmind.app.ui.search.SearchViewModel", ((Provider) searchViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return Collections.<String, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.meetingmind.app.ui.meeting.CreateMeetingViewModel 
          return (T) new CreateMeetingViewModel(singletonCImpl.bindMeetingRepositoryProvider.get(), singletonCImpl.meetingSchedulerProvider.get());

          case 1: // com.meetingmind.app.ui.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.bindMeetingRepositoryProvider.get());

          case 2: // com.meetingmind.app.ui.meeting.MeetingDetailViewModel 
          return (T) new MeetingDetailViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.bindMeetingRepositoryProvider.get(), singletonCImpl.bindTranscriptRepositoryProvider.get(), singletonCImpl.aiServiceProvider.get(), singletonCImpl.provideGsonProvider.get(), singletonCImpl.documentExporterProvider.get(), singletonCImpl.audioPlayerManagerProvider.get());

          case 3: // com.meetingmind.app.ui.search.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.bindMeetingRepositoryProvider.get(), singletonCImpl.bindTranscriptRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends MeetingMindApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends MeetingMindApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectRecordingService(RecordingService recordingService) {
      injectRecordingService2(recordingService);
    }

    private RecordingService injectRecordingService2(RecordingService instance) {
      RecordingService_MembersInjector.injectAudioRecorder(instance, singletonCImpl.audioRecorderProvider.get());
      RecordingService_MembersInjector.injectTingwuClient(instance, singletonCImpl.tingwuWebSocketClientProvider.get());
      RecordingService_MembersInjector.injectAudioFileManager(instance, singletonCImpl.audioFileManagerProvider.get());
      RecordingService_MembersInjector.injectMeetingRepository(instance, singletonCImpl.bindMeetingRepositoryProvider.get());
      RecordingService_MembersInjector.injectTranscriptRepository(instance, singletonCImpl.bindTranscriptRepositoryProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends MeetingMindApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<MeetingDatabase> provideDatabaseProvider;

    private Provider<Gson> provideGsonProvider;

    private Provider<MeetingRepositoryImpl> meetingRepositoryImplProvider;

    private Provider<MeetingRepository> bindMeetingRepositoryProvider;

    private Provider<MeetingScheduler> meetingSchedulerProvider;

    private Provider<TranscriptRepositoryImpl> transcriptRepositoryImplProvider;

    private Provider<TranscriptRepository> bindTranscriptRepositoryProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<QwenApiService> provideQwenApiServiceProvider;

    private Provider<AiService> aiServiceProvider;

    private Provider<DocumentExporter> documentExporterProvider;

    private Provider<AudioPlayerManager> audioPlayerManagerProvider;

    private Provider<AudioRecorder> audioRecorderProvider;

    private Provider<OkHttpClient> provideWebSocketClientProvider;

    private Provider<TingwuWebSocketClient> tingwuWebSocketClientProvider;

    private Provider<AudioFileManager> audioFileManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private MeetingDao meetingDao() {
      return DatabaseModule_ProvideMeetingDaoFactory.provideMeetingDao(provideDatabaseProvider.get());
    }

    private TranscriptSegmentDao transcriptSegmentDao() {
      return DatabaseModule_ProvideTranscriptSegmentDaoFactory.provideTranscriptSegmentDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<MeetingDatabase>(singletonCImpl, 1));
      this.provideGsonProvider = DoubleCheck.provider(new SwitchingProvider<Gson>(singletonCImpl, 2));
      this.meetingRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 0);
      this.bindMeetingRepositoryProvider = DoubleCheck.provider((Provider) meetingRepositoryImplProvider);
      this.meetingSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<MeetingScheduler>(singletonCImpl, 3));
      this.transcriptRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 4);
      this.bindTranscriptRepositoryProvider = DoubleCheck.provider((Provider) transcriptRepositoryImplProvider);
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 7));
      this.provideQwenApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<QwenApiService>(singletonCImpl, 6));
      this.aiServiceProvider = DoubleCheck.provider(new SwitchingProvider<AiService>(singletonCImpl, 5));
      this.documentExporterProvider = DoubleCheck.provider(new SwitchingProvider<DocumentExporter>(singletonCImpl, 8));
      this.audioPlayerManagerProvider = DoubleCheck.provider(new SwitchingProvider<AudioPlayerManager>(singletonCImpl, 9));
      this.audioRecorderProvider = DoubleCheck.provider(new SwitchingProvider<AudioRecorder>(singletonCImpl, 10));
      this.provideWebSocketClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 12));
      this.tingwuWebSocketClientProvider = DoubleCheck.provider(new SwitchingProvider<TingwuWebSocketClient>(singletonCImpl, 11));
      this.audioFileManagerProvider = DoubleCheck.provider(new SwitchingProvider<AudioFileManager>(singletonCImpl, 13));
    }

    @Override
    public void injectMeetingMindApp(MeetingMindApp meetingMindApp) {
    }

    @Override
    public void injectBootReceiver(BootReceiver bootReceiver) {
      injectBootReceiver2(bootReceiver);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private BootReceiver injectBootReceiver2(BootReceiver instance) {
      BootReceiver_MembersInjector.injectMeetingRepository(instance, bindMeetingRepositoryProvider.get());
      BootReceiver_MembersInjector.injectMeetingScheduler(instance, meetingSchedulerProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.meetingmind.app.data.repository.MeetingRepositoryImpl 
          return (T) new MeetingRepositoryImpl(singletonCImpl.meetingDao(), singletonCImpl.provideGsonProvider.get());

          case 1: // com.meetingmind.app.data.local.MeetingDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.google.gson.Gson 
          return (T) NetworkModule_ProvideGsonFactory.provideGson();

          case 3: // com.meetingmind.app.util.MeetingScheduler 
          return (T) new MeetingScheduler(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.meetingmind.app.data.repository.TranscriptRepositoryImpl 
          return (T) new TranscriptRepositoryImpl(singletonCImpl.transcriptSegmentDao());

          case 5: // com.meetingmind.app.data.remote.AiService 
          return (T) new AiService(singletonCImpl.provideQwenApiServiceProvider.get(), singletonCImpl.provideGsonProvider.get());

          case 6: // com.meetingmind.app.data.remote.QwenApiService 
          return (T) NetworkModule_ProvideQwenApiServiceFactory.provideQwenApiService(singletonCImpl.provideOkHttpClientProvider.get());

          case 7: // @javax.inject.Named("api") okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 8: // com.meetingmind.app.util.DocumentExporter 
          return (T) new DocumentExporter(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.meetingmind.app.util.AudioPlayerManager 
          return (T) new AudioPlayerManager();

          case 10: // com.meetingmind.app.data.remote.AudioRecorder 
          return (T) new AudioRecorder();

          case 11: // com.meetingmind.app.data.remote.TingwuWebSocketClient 
          return (T) new TingwuWebSocketClient(singletonCImpl.provideWebSocketClientProvider.get(), singletonCImpl.provideGsonProvider.get());

          case 12: // @javax.inject.Named("websocket") okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideWebSocketClientFactory.provideWebSocketClient();

          case 13: // com.meetingmind.app.util.AudioFileManager 
          return (T) new AudioFileManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
