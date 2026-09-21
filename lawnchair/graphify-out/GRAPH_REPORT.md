# Graph Report - lawnchair  (2026-09-21)

## Corpus Check
- Large corpus: 503 files · ~217,523 words. Semantic extraction will be expensive (many Claude tokens). Consider running on a subfolder.

## Summary
- 4898 nodes · 11236 edges · 262 communities (230 shown, 32 thin omitted)
- Extraction: 97% EXTRACTED · 3% INFERRED · 0% AMBIGUOUS · INFERRED: 337 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Custom Font Cache
- App Usage Dates
- IDP Preferences
- Backup Creation UI
- Icon Provider
- Location Ping Service
- Launcher UI Canvas
- Hotseat Preferences
- Preference Bottom Sheets
- Retrofit Providers
- Smartspace SystemUI
- Nova Backup Import
- ui 2
- views
- icons 2
- smartspace
- components 2
- util
- preferences 4
- preferences 5
- animation
- nexuslauncher
- search
- preferences 6
- views 2
- libraries
- androidinternal
- icons 3
- colorpreference
- ActivityOptionsWrapper QuickstepLauncher
- allapps
- data 3
- engine
- components 3
- gestures
- data 4
- data 5
- util 2
- smartspace 2
- util 3
- allapps 2
- views 3
- preferences 7
- smartspace 3
- calculator
- preferences 8
- util 4
- data 6
- qsb
- views 4
- calculator 2
- qsb 2
- engine 2
- nexuslauncher 2
- smartspace 4
- color
- data 7
- calculator 3
- views 5
- libraries 2
- graphics
- util 5
- engine 3
- data 8
- algorithms
- components 4
- util 6
- gestures 2
- wallpaper
- gestures 3
- HeadlessWidgetsManager.kt HeadlessAppWid
- preferences 9
- smartspace 5
- flowerpot
- smartspace 6
- search 2
- util 7
- smartspace 7
- root
- flowerpot 2
- qsb 3
- preferences2
- qsb 4
- calculator 4
- theme
- components 5
- font 2
- preferences 10
- util 8
- preferences 11
- icons 4
- folder
- icons 5
- components 6
- theme 2
- systemui 2
- smartspace 8
- ui 3
- color 2
- theme 3
- smartspace 9
- folder 2
- gestures 4
- overview
- smartspace 10
- systemui 3
- systemui 4
- flowerpot 3
- ui 4
- theme 4
- search 3
- search 4
- .isSigned() FeedBridge.kt
- calculator 5
- graphics 2
- libraries 3
- util 9
- backup 2
- preferences 12
- wallpaper 2
- icons 6
- icons 7
- preferences 13
- monet
- gestures 5
- deck
- data 9
- data 10
- data 11
- nexuslauncher 3
- smartspace 11
- util 10
- search 5
- nexuslauncher 4
- search 6
- ui 5
- preferences 14
- ui 6
- util 11
- preferences 15
- icons 8
- backup 3
- bugreport
- folder 3
- LawnchairApp .checkRecentsComponent()
- util 12
- override
- provider 2
- search 7
- ui 7
- ui 8
- util 13
- allapps 3
- util 14
- factory
- data 12
- theme 5
- backup 4
- data 13
- gestures 6
- preferences 16
- smartspace 12
- preferences 17
- overview 2
- ui 9
- smartspace 13
- data 14
- gestures 7
- icons 9
- preferences2 2
- ui 10
- data 15
- AccessibilityEvent AccessibilityService
- allapps 4
- components 7
- views 6
- bugreport 2
- data 16
- data 17
- icons 10
- qsb 5
- smartspace 14
- components 8
- ui 11
- allapps 5
- animation 2
- allapps 6
- smartspace 15
- bugreport 3
- bugreport 4
- bugreport 5
- data 18
- wallpaper 3
- wallpaper 4
- deck 2
- SearchBarInsetsHandler.kt SearchBarInset
- theme 6
- components 9
- util 15
- util 16
- shared
- util 17
- about
- components 10
- calculator 6
- allapps 7
- allapps 8
- data 19
- data 20
- data 21
- data 22
- util 18
- views 7
- systemui 5
- graphics 3
- util 19
- util 20
- allapps 9
- data 23
- engine 4
- smartspace 16
- ui 12
- data 24
- data 25
- data 26
- data 27
- qsb 6
- search 8
- smartspace 17
- color 3
- ui 13
- views 8
- data 28
- preferences 18
- qsb 7
- qsb 8
- components 11
- util 21
- monet 2
- allapps 10
- ContextExtensions.kt Intent
- qsb 9
- qsb 10
- qsb 11
- qsb 12
- qsb 13
- preferences 19
- util 22
- util 23
- util 24
- allapps 11
- search 9
- util 25
- data 29
- icons 11
- overview 3
- search 10
- android

## God Nodes (most connected - your core abstractions)
1. `PreferenceTemplate()` - 83 edges
2. `PreferenceGroup()` - 79 edges
3. `LawnchairLauncher` - 70 edges
4. `SearchTargetCompat` - 62 edges
5. `PreferenceManager2` - 59 edges
6. `PreferenceLayout()` - 57 edges
7. `UsageSweepTest` - 55 edges
8. `PreferenceManager` - 51 edges
9. `SwitchPreference()` - 48 edges
10. `getAdapter()` - 45 edges

## Surprising Connections (you probably didn't know these)
- `UsageSweepTest` --calls--> `UsageDayWindow`  [INFERRED]
  tests/src/app/lawnchair/data/usage/UsageSweepTest.kt → src/app/lawnchair/data/usage/UsageEvent.kt
- `PreferenceManager` --calls--> `StringPref`  [INFERRED]
  src/app/lawnchair/preferences/PreferenceManager.kt → src/app/lawnchair/preferences/BasePreferenceManager.kt
- `PreferenceManager` --calls--> `BoolPref`  [INFERRED]
  src/app/lawnchair/preferences/PreferenceManager.kt → src/app/lawnchair/preferences/BasePreferenceManager.kt
- `PreferenceManager` --calls--> `IntPref`  [INFERRED]
  src/app/lawnchair/preferences/PreferenceManager.kt → src/app/lawnchair/preferences/BasePreferenceManager.kt
- `PreferenceManager` --calls--> `IdpIntPref`  [INFERRED]
  src/app/lawnchair/preferences/PreferenceManager.kt → src/app/lawnchair/preferences/BasePreferenceManager.kt

## Import Cycles
- None detected.

## Communities (262 total, 32 thin omitted)

### Community 0 - "Custom Font Cache"
Cohesion: 0.05
Nodes (28): FileObserver, FontFamily, AddFontException, AssetFont, DummyFont, Family, Font, FontCache (+20 more)

### Community 1 - "App Usage Dates"
Cohesion: 0.08
Nodes (17): UsageDates, UsageDaySweep, UsageDayWindow, UsageEvent, UsageEventType, ACTIVITY_PAUSED, ACTIVITY_RESUMED, ACTIVITY_STOPPED (+9 more)

### Community 2 - "IDP Preferences"
Cohesion: 0.05
Nodes (53): AnnouncementId, firstBlocking(), IdpPreference, InvariantDeviceProfile, state(), asState(), CoroutineScope, subscribeBlocking() (+45 more)

### Community 3 - "Backup Creation UI"
Cohesion: 0.06
Nodes (60): Configuration, CreateBackupScreen(), Modifier, Modifier, restoreBackupGraph(), restoreBackupOpener(), RestoreBackupOptions(), RestoreBackupScreen() (+52 more)

### Community 4 - "Icon Provider"
Cohesion: 0.06
Nodes (33): IconChangeListener, IconProvider, CalendarAndClockChangeReceiver, IconPackChangeReceiver, ActivityInfo, BroadcastReceiver, ComponentName, Context (+25 more)

### Community 5 - "Location Ping Service"
Cohesion: 0.07
Nodes (18): Context, IBinder, Intent, Job, Location, Service, LocationPingService, MotionDetector (+10 more)

### Community 6 - "Launcher UI Canvas"
Cohesion: 0.06
Nodes (29): ActivityResult, android.graphics.Canvas, androidx.annotation.IntDef, com.android.launcher3.util.EdgeEffectCompat, java.lang.annotation.Retention, BlankActivity, ResultReceiver, Activity (+21 more)

### Community 7 - "Hotseat Preferences"
Cohesion: 0.08
Nodes (46): HotseatMode, getAdapter(), P, ColorPreference(), Modifier, Preference, Modifier, PreferenceAdapter (+38 more)

### Community 8 - "Preference Bottom Sheets"
Cohesion: 0.08
Nodes (48): item, Composable, Modifier, ModalBottomSheetContent(), ClickablePreference(), ClickablePreferencePreview(), Modifier, PreferenceClickConfirmation() (+40 more)

### Community 9 - "Retrofit Providers"
Cohesion: 0.06
Nodes (21): Converter, Response, ResponseBody, Retrofit, DuckDuckGoService, DuckDuckGoWebSearchProvider, GoogleService, GoogleWebSearchProvider (+13 more)

### Community 10 - "Smartspace SystemUI"
Cohesion: 0.05
Nodes (24): android.app.PendingIntent, android.app.smartspace.SmartspaceAction, android.app.smartspace.SmartspaceTarget, android.app.smartspace.SmartspaceTargetEvent, android.app.smartspace.uitemplatedata.TapAction, android.content.Intent, android.graphics.drawable.Drawable, android.os.Parcelable (+16 more)

### Community 11 - "Nova Backup Import"
Cohesion: 0.06
Nodes (26): SQLiteDatabase, ImportedDeepShortcut, ItemCounts, android, ByteArray, Uri, NovaBackupConverter, NovaBackupInfo (+18 more)

### Community 12 - "ui 2"
Cohesion: 0.07
Nodes (41): BorderStroke, DrawScope, NestedScrollConnection, NestedScrollSource, Offset, color(), drawPlaceholder(), Color (+33 more)

### Community 13 - "views"
Cohesion: 0.07
Nodes (26): AppCompatButton, BitmapInfo, BubbleTextView, info, ItemInfoWithIcon, SearchActionItemInfo, ComponentKey, ComponentName (+18 more)

### Community 14 - "icons 2"
Cohesion: 0.08
Nodes (30): Arc, BaseBezierPath, BottomLeft, BottomRight, Cupertino, Cut, CutHex, IconCornerShape (+22 more)

### Community 15 - "smartspace"
Cohesion: 0.07
Nodes (17): android.media.MediaMetadata, android.media.session.MediaController, android.media.session.PlaybackState, android.os.Handler, android.service.notification.StatusBarNotification, android.view.KeyEvent, androidx.core.util.Consumer, app.lawnchair.NotificationManager (+9 more)

### Community 16 - "components 2"
Cohesion: 0.11
Nodes (36): ReorderableCollectionItemScope, ReorderableScope, Modifier, PreferenceAdapter, SwitchPreferencePreviewCard(), SwitchPreferenceWithPreview(), Color, Dp (+28 more)

### Community 17 - "util"
Cohesion: 0.12
Nodes (27): Button, RequiresApi, Modifier, PermissionDialog(), PermissionRow(), WallpaperAccessPermissionDialog(), FileAccessPermissionDialog(), FileSearchProvider() (+19 more)

### Community 18 - "preferences 4"
Cohesion: 0.12
Nodes (40): restoreNovaBackupOpener(), DevicePermissionCheck(), DeviceStatusValue(), Context, Modifier, openAppInfo(), PreferenceCategoryGroup(), PreferencesDashboard() (+32 more)

### Community 19 - "preferences 5"
Cohesion: 0.11
Nodes (34): Lifecycle, ColorContrastWarning(), Modifier, Modifier, WarningPreference(), WarningPreferencePreview(), FontPreference(), Modifier (+26 more)

### Community 20 - "animation"
Cohesion: 0.13
Nodes (12): DynamicAnimation, EndAction, EndListener, FlingAnimation, FloatPropertyCompat, EndListener, FlingConfig, T (+4 more)

### Community 21 - "nexuslauncher"
Cohesion: 0.07
Nodes (10): LauncherOverlay, LauncherOverlayCallbacks, Activity, Bundle, Context, LauncherOverlayManager, OverlayCallbackImpl, ISerializableScrollCallback (+2 more)

### Community 22 - "search"
Cohesion: 0.15
Nodes (9): SearchResultType, SearchTargetCompat, Bundle, Calculation, Intent, SearchTargetFactory, SettingsTarget, Context (+1 more)

### Community 23 - "preferences 6"
Cohesion: 0.11
Nodes (31): ColorMode, AUTO, DARK, LIGHT, Composable, Modifier, PreferenceAdapter, T (+23 more)

### Community 24 - "views 2"
Cohesion: 0.11
Nodes (13): AbstractFloatingView, Consumer, GestureNavContract, OnGlobalLayoutListener, SpringAnimation, Bitmap, Insettable, MotionEvent (+5 more)

### Community 25 - "libraries"
Cohesion: 0.09
Nodes (7): android.app.Activity, android.os.Bundle, android.view.WindowManager.LayoutParams, IScrollCallback, ILauncherOverlay, Intent, LauncherClient

### Community 26 - "androidinternal"
Cohesion: 0.10
Nodes (5): androidx.annotation.ColorInt, androidx.annotation.FloatRange, Shades, ColorUtils, ContrastCalculator

### Community 27 - "icons 3"
Cohesion: 0.06
Nodes (15): Circle, Corner, Cupertino, Cylinder, Diamond, Egg, Hexagon, IconShape (+7 more)

### Community 28 - "colorpreference"
Cohesion: 0.10
Nodes (25): ColorOption, CustomColor, Default, SystemAccent, WallpaperPrimary, ColorDot(), DefaultColorDot(), Color (+17 more)

### Community 29 - "ActivityOptionsWrapper QuickstepLauncher"
Cohesion: 0.09
Nodes (17): ActivityOptionsWrapper, QuickstepLauncher, ActivityContext, Bundle, ColorScheme, Intent, ItemInfo, LauncherOverlayManager (+9 more)

### Community 30 - "allapps"
Cohesion: 0.09
Nodes (13): AdapterItem, KeyEvent, SearchUiManager, AllAppsSearchInput, ActivityAllAppsContainerView, FrameLayout, ImageButton, Insettable (+5 more)

### Community 31 - "data 3"
Cohesion: 0.09
Nodes (8): RoomDatabase, DailyAppUsage, DailyDeviceUsage, DeviceIdentity, LocationPing, Flow, SupportSQLiteQuery, UsageDao

### Community 32 - "engine"
Cohesion: 0.07
Nodes (26): SearchRecentSuggestionsProvider, RecentKeyword, SettingInfo, HistorySearchProvider, Context, SearchResult, findSettingsByNameAndAction(), Context (+18 more)

### Community 33 - "components 3"
Cohesion: 0.13
Nodes (30): ClosedFloatingPointRange, ClosedRange, colorStringToIntColor(), hsvValuesToIntColor(), intColorToColorString(), intColorToHsvColorArray(), FloatArray, HsbColorSlider() (+22 more)

### Community 34 - "gestures"
Cohesion: 0.09
Nodes (15): OnTouchListener, SimpleOnGestureListener, DirectionalGestureListener, GestureListener, MotionEvent, View, IconGestureListener, GestureType (+7 more)

### Community 35 - "data 4"
Cohesion: 0.12
Nodes (13): FloatRange, UsagePackageUsage, DayBucket, SweepState, UsageSweep, Builder, AppWidgetProviderInfo, Bundle (+5 more)

### Community 36 - "data 5"
Cohesion: 0.10
Nodes (11): LauncherApi, LauncherAppUsageDto, LauncherDeviceSnapshot, LauncherDeviceUsageDto, LauncherLocationPingDto, LauncherRegisterRequest, LauncherRegisterResponse, LauncherSyncRequest (+3 more)

### Community 37 - "util 2"
Cohesion: 0.12
Nodes (31): createRoundedBitmap(), ensureOnMainThread(), getAllAppsScrimColor(), getDefaultLauncherPackageName(), getDefaultResolveInfo(), getDisplayName(), getFolderBackgroundAlpha(), getFolderPreviewAlpha() (+23 more)

### Community 38 - "smartspace 2"
Cohesion: 0.10
Nodes (15): SmartspaceAction, SmartspaceScores, SmartspaceTarget, BatteryStatusProvider, Activity, NowPlayingProvider, Activity, Flow (+7 more)

### Community 39 - "util 3"
Cohesion: 0.10
Nodes (11): android.content.Context, android.content.ContextWrapper, android.os.PowerManager, androidx.annotation.UiThread, androidx.annotation.VisibleForTesting, Override, RootHelperBackend, Override (+3 more)

### Community 40 - "allapps 2"
Cohesion: 0.10
Nodes (13): BubbleTextHolder, ImageView, LinearLayout, TextView, SearchResultEmptyState, LinearLayout, TextView, View (+5 more)

### Community 41 - "views 3"
Cohesion: 0.10
Nodes (14): CreateBackupViewModel, AndroidViewModel, Bitmap, InvariantDeviceProfile, RememberObserver, LauncherPreviewManager, AppWidgetProviderInfo, BgDataModel (+6 more)

### Community 42 - "preferences 7"
Cohesion: 0.13
Nodes (24): restoreNovaBackupGraph(), About(), Modifier, Acknowledgements(), Modifier, OssLibraryItem(), Alignment, Arrangement (+16 more)

### Community 43 - "smartspace 3"
Cohesion: 0.09
Nodes (11): DisabledHotseat, GoogleSearchHotseat, Context, LawnchairHotseat, GoogleSearchSmartspace, GoogleSmartspace, Context, LawnchairSmartspace (+3 more)

### Community 44 - "calculator"
Cohesion: 0.15
Nodes (9): AssignExpr, CallExpr, ExprVisitor, GroupingExpr, R, LiteralExpr, LogicalExpr, UnaryExpr (+1 more)

### Community 45 - "preferences 8"
Cohesion: 0.16
Nodes (23): AppFilter, AppItem(), AppItemLayout(), AppItemPlaceholder(), App, Bitmap, Composable, Modifier (+15 more)

### Community 46 - "util 4"
Cohesion: 0.14
Nodes (13): AppSearchProvider, AllAppsList, Context, SearchResult, filterHiddenApps(), Context, ShortcutInfo, SearchUtils (+5 more)

### Community 47 - "data 6"
Cohesion: 0.12
Nodes (6): UsageAuditUiState, Flow, Intent, SafeCloseable, UsageQuery, UsageService

### Community 48 - "qsb"
Cohesion: 0.08
Nodes (15): Brave, DuckDuckGo, Fennec, Intent, IronFox, Intent, Kagi, Intent (+7 more)

### Community 49 - "views 4"
Cohesion: 0.15
Nodes (15): AnimatorSet, FullScreenOverlayView, AnimatorListenerAdapter, AnimatorListenerAdapter, ViewOutlineProvider, AnimatorListenerAdapter, ViewOutlineProvider, Animator (+7 more)

### Community 50 - "calculator 2"
Cohesion: 0.27
Nodes (3): BinaryExpr, Expr, Parser

### Community 51 - "qsb 2"
Cohesion: 0.14
Nodes (9): Ecosia, GitHub, GoogleGo, Intent, Context, Intent, Launcher, QsbSearchProvider (+1 more)

### Community 52 - "engine 2"
Cohesion: 0.12
Nodes (15): ContactInfo, ContactsSearchProvider, Context, Flow, SearchResult, Context, Flow, SearchResult (+7 more)

### Community 53 - "nexuslauncher 2"
Cohesion: 0.15
Nodes (13): EventEnum, QsbWidgetHostView, Context, Intent, Launcher, MotionEvent, OnLongClickListener, Rect (+5 more)

### Community 54 - "smartspace 4"
Cohesion: 0.08
Nodes (25): FeatureType, FEATURE_ALARM, FEATURE_BEDTIME_ROUTINE, FEATURE_CALENDAR, FEATURE_COMMUTE_TIME, FEATURE_CONSENT, FEATURE_ETA_MONITORING, FEATURE_FITNESS_TRACKING (+17 more)

### Community 55 - "color"
Cohesion: 0.21
Nodes (14): DarkTextColorToken, DayNightColorToken, Color, ColorScheme, Context, SetAlphaColorToken, SetLStarColorToken, Shade (+6 more)

### Community 56 - "data 7"
Cohesion: 0.16
Nodes (16): AuditPermission, BACKGROUND_LOCATION, BATTERY, LOCATION, NOTIFICATIONS, USAGE_ACCESS, Context, auditPermissionLabel() (+8 more)

### Community 57 - "calculator 3"
Cohesion: 0.08
Nodes (23): TokenType, AMP_AMP, ASSIGN, BAR_BAR, COMMA, EOF, EQUAL_EQUAL, EXPONENT (+15 more)

### Community 58 - "views 5"
Cohesion: 0.16
Nodes (7): AbstractSlideInView, Interpolator, ComposeBottomSheet, Context, PaddingValues, PendingAnimation, T

### Community 59 - "libraries 2"
Cohesion: 0.20
Nodes (10): android.content.ComponentName, android.content.ServiceConnection, android.os.IBinder, BaseClientService, Override, Override, LauncherClientBridge, ILauncherOverlay (+2 more)

### Community 61 - "util 5"
Cohesion: 0.20
Nodes (11): CachedDisplayInfo, Display, DisplayCutout, Resources, Context, Rect, LawnchairWindowManagerProxy, VisibleForTesting (+3 more)

### Community 62 - "engine 3"
Cohesion: 0.23
Nodes (10): Cursor, FileInfo, IFileInfo, FileSearchProvider, Context, Flow, SearchResult, T (+2 more)

### Community 63 - "data 8"
Cohesion: 0.15
Nodes (9): MathContext, RoundingMode, Calculation, Expressions, CalculatorSearchProvider, Calculation, Context, Flow (+1 more)

### Community 64 - "algorithms"
Cohesion: 0.21
Nodes (16): ActionsSectionBuilder, AppsAndShortcutsSectionBuilder, CalculationSectionBuilder, ContactsSectionBuilder, EmptyStateSectionBuilder, FilesSectionBuilder, HistorySectionBuilder, SearchSettingsSectionBuilder (+8 more)

### Community 65 - "components 4"
Cohesion: 0.25
Nodes (16): Modifier, PaddingValues, T, PositionalListItem, PositionalMapper, PositionalOrderMenu(), PositionalReorderer(), ReorderableItemContainer() (+8 more)

### Community 66 - "util 6"
Cohesion: 0.17
Nodes (13): android.util.SparseIntArray, androidx.annotation.Keep, androidx.annotation.Nullable, androidx.annotation.RequiresApi, app.lawnchair.theme.color.AndroidColor, app.lawnchair.theme.ThemeProvider, com.android.launcher3.widget.LocalColorExtractor, dev.kdrag0n.colorkt.Color (+5 more)

### Community 67 - "gestures 2"
Cohesion: 0.15
Nodes (11): DeviceAdminReceiver, Context, Intent, Modifier, ServiceWarningDialog(), SleepDeviceAdmin, SleepGestureHandler, SleepMethod (+3 more)

### Community 68 - "wallpaper"
Cohesion: 0.14
Nodes (11): IntDef, ColorsHints, WallpaperColorsCompat, WallpaperManager, OnColorsChangedListener, WallpaperManagerCompat, WallpaperManagerCompatVO, WallpaperColors (+3 more)

### Community 69 - "gestures 3"
Cohesion: 0.13
Nodes (8): GestureHandler, NoOpGestureHandler, OpenAppDrawerGestureHandler, OpenAppGestureHandler, OpenAppSearchGestureHandler, OpenNotificationsHandler, OpenSearchGestureHandler, RecentsGestureHandler

### Community 70 - "HeadlessWidgetsManager.kt HeadlessAppWid"
Cohesion: 0.16
Nodes (14): HeadlessAppWidgetHost, HeadlessAppWidgetHostView, HeadlessWidgetsManager, AppWidgetHost, AppWidgetHostView, AppWidgetProviderInfo, Context, Flow (+6 more)

### Community 71 - "preferences 9"
Cohesion: 0.15
Nodes (7): BasePref, BasePreferenceManager, BoolPref, FloatPref, IntPref, SharedPreferences, StringSetPref

### Community 72 - "smartspace 5"
Cohesion: 0.16
Nodes (10): Activity, Bitmap, Flow, ImageView, PendingIntent, TextView, ViewGroup, SmartspaceWidgetReader (+2 more)

### Community 73 - "flowerpot"
Cohesion: 0.14
Nodes (13): BufferedReader, FlowerpotFormatException, RuntimeException, FlowerpotReader, LineParser, CodeRule, IntentAction, IntentCategory (+5 more)

### Community 74 - "smartspace 6"
Cohesion: 0.18
Nodes (10): LayerDrawable, BcSmartspaceCard, ImageView, LinearLayout, TextView, ViewGroup, DoubleShadowIconDrawable, Bitmap (+2 more)

### Community 75 - "search 2"
Cohesion: 0.16
Nodes (9): SearchAction, Builder, Bundle, Icon, Intent, Parcelable, PendingIntent, UserHandle (+1 more)

### Community 76 - "util 7"
Cohesion: 0.21
Nodes (12): Decoder, Encoder, KSerializer, App, OpenAppTarget, Shortcut, ComponentKeySerializer, IntentSerializer (+4 more)

### Community 77 - "smartspace 7"
Cohesion: 0.16
Nodes (6): FormatterFunction, DateTimeOptions, IcuDateTextView, Gregorian, Persian, SmartspaceCalendar

### Community 78 - "root"
Cohesion: 0.16
Nodes (11): IRootHelper, RootService, Intent, RootHelper, ComponentName, Deferred, IBinder, RootHelperManager (+3 more)

### Community 79 - "flowerpot 2"
Cohesion: 0.14
Nodes (5): Flowerpot, Context, Manager, Version, FlowerpotApps

### Community 80 - "qsb 3"
Cohesion: 0.17
Nodes (10): animateToAllApps(), AppSearch, Launcher, Launcher, Startpage, LauncherState, PendingAnimation, SearchBarStateHandler (+2 more)

### Community 81 - "preferences2"
Cohesion: 0.12
Nodes (9): CoroutineScope, PreferenceCollectorScope, Context, IconShape, SafeCloseable, T, PreferenceManager2, ColorPreferenceModel (+1 more)

### Community 82 - "qsb 4"
Cohesion: 0.21
Nodes (8): ActivityContext, AppWidgetHostView, Context, FrameLayout, ImageView, Intent, PendingIntent, LawnQsbLayout

### Community 83 - "calculator 4"
Cohesion: 0.23
Nodes (3): invalidToken(), Scanner, Token

### Community 84 - "theme"
Cohesion: 0.14
Nodes (9): ColorStyle, Content, Expressive, FruitSalad, Monochromatic, Rainbow, Spritz, TonalSpot (+1 more)

### Community 85 - "components 5"
Cohesion: 0.18
Nodes (18): Modifier, PreferenceAdapter, MainSwitchPreference(), ContactsSearchProvider(), GenericSearchProviderPreference(), getProviderName(), Modifier, PreferenceAdapter (+10 more)

### Community 86 - "font 2"
Cohesion: 0.19
Nodes (7): Comparable, DataProvider, GoogleFontInfo, GoogleFontsListing, JSONObject, SafeCloseable, MockDataProvider

### Community 87 - "preferences 10"
Cohesion: 0.19
Nodes (13): DisplayFeature, SharedPreferencesView, SharedPreferencesMigration, ComponentKey, SelectIconPreference(), IconPicker, Root, Modifier (+5 more)

### Community 88 - "util 8"
Cohesion: 0.20
Nodes (5): HandlerThread, Context, Handler, SharedPreferences, LawnchairLockedStateController

### Community 89 - "preferences 11"
Cohesion: 0.19
Nodes (16): index, Composable, Dp, Modifier, T, PreferenceGroupItem(), preferenceGroupItems(), ContentType (+8 more)

### Community 90 - "icons 4"
Cohesion: 0.14
Nodes (7): Matrix, Arch, FourSidedCookie, Context, SevenSidedCookie, T, unsafeLazy()

### Community 91 - "folder"
Cohesion: 0.22
Nodes (7): FolderService, Flow, SafeCloseable, FolderInfo, setLStar(), withContext(), withPreferences()

### Community 92 - "icons 5"
Cohesion: 0.18
Nodes (6): CustomIconPack, ComponentName, Drawable, Flow, Intent, XmlPullParser

### Community 93 - "components 6"
Cohesion: 0.19
Nodes (15): observeAsState(), Modifier, PreferenceCategory(), PreferenceCategoryPreview(), Modifier, PreferenceAdapter, TextPreference(), TextPreferenceDialog() (+7 more)

### Community 94 - "theme 2"
Cohesion: 0.28
Nodes (14): ColorToken, AttributeDrawableToken, DrawableToken, ColorScheme, Context, T, mutate(), MutatedDrawableToken (+6 more)

### Community 95 - "systemui 2"
Cohesion: 0.17
Nodes (10): Chroma, ChromaAdd, ChromaBound, ChromaConstant, ChromaMaxOut, ChromaMultiple, ChromaSource, CoreSpec (+2 more)

### Community 96 - "smartspace 8"
Cohesion: 0.21
Nodes (9): AppWidgetProvider, LauncherAppWidgetHostView, AppWidgetProviderInfo, Context, RemoteViews, View, ViewGroup, LawnchairAppWidgetHostView (+1 more)

### Community 97 - "ui 3"
Cohesion: 0.24
Nodes (4): CardView, Bitmap, LinearLayout, WallpaperCarouselView

### Community 98 - "color 2"
Cohesion: 0.20
Nodes (11): ColorStateList, ColorStateListToken, DayNightColorStateList, ColorScheme, Context, NewColorStateList, ColorStateListTokens, ColorScheme (+3 more)

### Community 99 - "theme 3"
Cohesion: 0.22
Nodes (10): ComposeColor, delinearized(), Color, ColorScheme, labInvf(), setLuminance(), toComposeColorScheme(), toAndroidColor() (+2 more)

### Community 100 - "smartspace 9"
Cohesion: 0.20
Nodes (6): PagerAdapter, CardPagerAdapter, View, ViewGroup, ViewHolder, ViewHolder

### Community 101 - "folder 2"
Cohesion: 0.19
Nodes (6): FolderInfoEntity, FolderItemEntity, FolderDao, FolderWithItems, Flow, SupportSQLiteQuery

### Community 102 - "gestures 4"
Cohesion: 0.20
Nodes (12): GestureHandlerConfig, Context, NoOp, OpenApp, OpenAppDrawer, OpenAppSearch, OpenAssistant, OpenNotifications (+4 more)

### Community 103 - "overview"
Cohesion: 0.20
Nodes (9): Context, Task, OverlayUICallbacks, OverlayUICallbacksImpl, TaskOverlay, TaskOverlayFactoryImpl, TaskContainer, TaskOverlayFactory (+1 more)

### Community 104 - "smartspace 10"
Cohesion: 0.18
Nodes (7): BcSmartspaceView, AnimatorListenerAdapter, ViewPager, Animator, AnimatorListenerAdapter, FrameLayout, repeatOnAttached()

### Community 105 - "systemui 3"
Cohesion: 0.12
Nodes (15): Color, ColorScheme, MonetColorSchemeCompat, ColorScheme, Style, CLOCK, CLOCK_VIBRANT, CONTENT (+7 more)

### Community 106 - "systemui 4"
Cohesion: 0.15
Nodes (8): Hue, HueAdd, HueExpressiveSecondary, HueExpressiveTertiary, HueSource, HueSubtract, HueVibrantSecondary, HueVibrantTertiary

### Community 107 - "flowerpot 3"
Cohesion: 0.21
Nodes (9): ApplicationInfo, Category, CodeRules, IsGame, addFlag(), hasFlag(), removeFlag(), setFlag() (+1 more)

### Community 108 - "ui 4"
Cohesion: 0.21
Nodes (11): BaseDraggingActivity, ModelAppInfo, Customize, ComponentName, Context, ItemInfo, SystemShortcut, View (+3 more)

### Community 109 - "theme 4"
Cohesion: 0.18
Nodes (9): IntentFilter, ColorSchemeChangeListener, BroadcastReceiver, Context, Intent, SafeCloseable, onColorsChanged(), ThemeProvider (+1 more)

### Community 110 - "search 3"
Cohesion: 0.24
Nodes (8): SearchAlgorithm, FloatArray, SearchItemBackground, BaseAllAppsAdapter, Context, SearchCallback, LawnchairSearchAlgorithm, removeDuplicateDividers()

### Community 111 - "search 4"
Cohesion: 0.23
Nodes (7): SearchSession, BaseAllAppsAdapter, Context, SearchCallback, SearchTarget, LawnchairASISearchAlgorithm, PendingQuery

### Community 112 - ".isSigned() FeedBridge.kt"
Cohesion: 0.25
Nodes (5): BridgeInfo, CustomBridgeInfo, FeedBridge, Context, PixelBridgeInfo

### Community 113 - "calculator 5"
Cohesion: 0.25
Nodes (3): ExpressionException, RuntimeException, Evaluator

### Community 115 - "libraries 3"
Cohesion: 0.17
Nodes (12): android.content.BroadcastReceiver, android.content.IntentFilter, android.content.pm.PackageManager, android.os.Handler.Callback, android.os.Message, android.view.Window, android.view.WindowManager, app.lawnchair.FeedBridge.BridgeInfo (+4 more)

### Community 116 - "util 9"
Cohesion: 0.20
Nodes (12): AppCompatImageView, Paint, RectF, generateColor(), Context, createTextBitmap(), getCornerRadiiCompat(), Bitmap (+4 more)

### Community 117 - "backup 2"
Cohesion: 0.28
Nodes (5): BackupInfo, Bitmap, Context, Uri, LawnchairBackup

### Community 118 - "preferences 12"
Cohesion: 0.17
Nodes (4): DefaultLifecycleObserver, PreferenceChangeListener, LifecycleOwner, PrefLifecycleObserver

### Community 119 - "wallpaper 2"
Cohesion: 0.23
Nodes (7): ByteArray, SafeCloseable, WallpaperManager, WallpaperService, Wallpaper, bitmapToByteArray(), ByteArray

### Community 120 - "icons 6"
Cohesion: 0.18
Nodes (8): IconEntry, IconType, Calendar, Normal, ClockMetadata, ComponentName, Drawable, SystemIconPack

### Community 121 - "icons 7"
Cohesion: 0.19
Nodes (6): IconPack, ClockMetadata, ComponentName, Deferred, Drawable, Flow

### Community 122 - "preferences 13"
Cohesion: 0.28
Nodes (7): KProperty, LifecycleOwner, SafeCloseable, T, View, PrefEntry, View

### Community 123 - "monet"
Cohesion: 0.19
Nodes (7): DynamicColorScheme, Color, ColorScheme, Color, ColorScheme, MaterialYouTargets, Zcam

### Community 124 - "gestures 5"
Cohesion: 0.25
Nodes (5): BothAxesSwipeDetector, MotionEvent, PointF, TouchController, VerticalSwipeTouchController

### Community 125 - "deck"
Cohesion: 0.28
Nodes (4): com, DatabaseFiles, android, LawndeckManager

### Community 126 - "data 9"
Cohesion: 0.21
Nodes (5): IconOverride, IconOverrideDao, ComponentKey, Flow, SupportSQLiteQuery

### Community 127 - "data 10"
Cohesion: 0.26
Nodes (7): DeviceSerial, Context, ResolvedSerial, SerialSource, DEBUG, KNOX, NONE

### Community 129 - "nexuslauncher 3"
Cohesion: 0.26
Nodes (7): Bitmap, ImageView, RemoteViews, TextView, View, ViewGroup, ThemedSmartSpaceHostView

### Community 130 - "smartspace 11"
Cohesion: 0.30
Nodes (7): createPopup(), Popup, Context, Intent, OptionsPopupView, View, SmartspacerView

### Community 131 - "util 10"
Cohesion: 0.30
Nodes (7): Dp, LayoutDirection, PaddingValues, max(), PaddingValues, minus(), PaddingValues

### Community 132 - "search 5"
Cohesion: 0.21
Nodes (6): DefaultSearchAdapterProvider, SparseIntArray, BaseAllAppsAdapter, LayoutInflater, ViewGroup, LawnchairSearchAdapterProvider

### Community 133 - "nexuslauncher 4"
Cohesion: 0.23
Nodes (8): QsbWidgetHost, AppWidgetProviderInfo, Bundle, QsbContainerView, View, ViewGroup, SmartSpaceFragment, SmartspaceQsb

### Community 134 - "search 6"
Cohesion: 0.25
Nodes (8): ContactsTarget, FilesTarget, ComponentKey, Context, Icon, ShortcutInfo, Uri, SearchLinksTarget

### Community 135 - "ui 5"
Cohesion: 0.22
Nodes (9): Bundle, ComponentActivity, SmartspacePreferencesShortcut, Bundle, ComponentActivity, Context, Intent, PreferenceActivity (+1 more)

### Community 136 - "preferences 14"
Cohesion: 0.24
Nodes (9): AndroidText(), Color, Modifier, T, toIntColor(), CustomFontTextView, AppCompatTextView, Job (+1 more)

### Community 137 - "ui 6"
Cohesion: 0.25
Nodes (7): RecyclerView, RememberObserver, T, Recyclable, rememberViewPool(), ViewHolder, ViewPool

### Community 138 - "util 11"
Cohesion: 0.23
Nodes (12): broadcastReceiverFlow(), BroadcastReceiver, collectAsStateBlocking(), dropWhileBusy(), firstBlocking(), BroadcastReceiver, Context, CoroutineScope (+4 more)

### Community 139 - "preferences 15"
Cohesion: 0.22
Nodes (10): BaseLogic, Keep, LauncherActivityInfo, LauncherActivityCachingLogic, Modifier, SearchBarPreference(), SearchPreferences(), SearchRoute (+2 more)

### Community 140 - "icons 8"
Cohesion: 0.27
Nodes (6): PackageManager, IconPackProvider, ClockMetadata, Drawable, SafeCloseable, UserHandle

### Community 141 - "backup 3"
Cohesion: 0.29
Nodes (12): BackupInfoGroup(), BackupInfoIntPreference(), BackupInfoOptions(), BackupInfoStringPreference(), CollectEvents(), Flow, State, RestoreNovaBackupContent() (+4 more)

### Community 142 - "bugreport"
Cohesion: 0.26
Nodes (6): IBinder, Intent, Job, Service, UploaderService, requireSystemService()

### Community 143 - "folder 3"
Cohesion: 0.19
Nodes (5): FolderOrderUtils, FolderViewModel, AndroidViewModel, LiveData, StateFlow

### Community 145 - "util 12"
Cohesion: 0.27
Nodes (7): getAppName(), Context, Flow, SafeCloseable, StatusBarNotification, NotificationManager, checkPackagePermission()

### Community 146 - "override"
Cohesion: 0.28
Nodes (11): CustomizeAppDialog(), CustomizeDialog(), ComponentKey, Composable, Drawable, Modifier, SelectIcon, addIf() (+3 more)

### Community 147 - "provider 2"
Cohesion: 0.17
Nodes (11): AppMatcher, MatchResult, MatchType, ALL_TOKENS_PRESENT, DIRECT_PREFIX, EXACT_MATCH, FUZZY, INITIALS (+3 more)

### Community 148 - "search 7"
Cohesion: 0.27
Nodes (7): AllAppsList, BaseAllAppsAdapter, BgDataModel, ModelTaskController, SearchCallback, LawnchairAppSearchAlgorithm, LauncherModel

### Community 149 - "ui 7"
Cohesion: 0.26
Nodes (9): Modifier, OverflowMenu(), OverflowMenuScope, OverflowMenuScopeImpl, ClickableIcon(), Color, ImageVector, Modifier (+1 more)

### Community 150 - "ui 8"
Cohesion: 0.27
Nodes (9): IconPickerGrid(), IconPickerPreference(), IconPreview(), Modifier, PaddingValues, State, LazyGridLayout, T (+1 more)

### Community 151 - "util 13"
Cohesion: 0.37
Nodes (4): ComponentName, Context, Task, TaskUtilLockState

### Community 152 - "allapps 3"
Cohesion: 0.23
Nodes (6): AllAppsStore, AlphabeticalAppsList, ItemInfo, OnIDPChangeListener, T, LawnchairAlphabeticalAppsList

### Community 153 - "util 14"
Cohesion: 0.36
Nodes (11): FileMetadata, FileSystem, file2Uri(), getMetadata(), isDirectory(), isFile(), isRegularFile(), Uri (+3 more)

### Community 154 - "factory"
Cohesion: 0.32
Nodes (8): HolderFactory, IntConsumer, AppWidgetHost, Context, RemoteViews, LawnchairHolderFactory, LauncherWidgetHolder, LawnchairWidgetHolder

### Community 155 - "data 12"
Cohesion: 0.23
Nodes (4): PowerManager, Context, UsageCollector, WatchedApp

### Community 156 - "theme 5"
Cohesion: 0.20
Nodes (4): ScrimView, getSystemAccent(), lightenColor(), LawnchairScrimView

### Community 157 - "backup 4"
Cohesion: 0.29
Nodes (8): Error, AndroidViewModel, Uri, Loading, RestoreBackupUiState, RestoreBackupViewModel, RestoreBackupViewModelState, Success

### Community 158 - "data 13"
Cohesion: 0.44
Nodes (3): Context, Location, LocationFix

### Community 159 - "gestures 6"
Cohesion: 0.41
Nodes (4): ComponentName, Context, Intent, OpenAssistantHandler

### Community 160 - "preferences 16"
Cohesion: 0.29
Nodes (4): T, ObjectPref, StringBasedPref, StringPref

### Community 161 - "smartspace 12"
Cohesion: 0.31
Nodes (5): CheckLongPressHelper, FrameLayout, MotionEvent, View, SmartspaceViewContainer

### Community 162 - "preferences 17"
Cohesion: 0.40
Nodes (3): K, MutableMapPref, V

### Community 163 - "overview 2"
Cohesion: 0.29
Nodes (5): OnClickListener, OverviewActionsView, LinearLayout, View, LawnchairOverviewActionsView

### Community 164 - "ui 9"
Cohesion: 0.29
Nodes (6): SpringRelativeLayout, Canvas, EdgeEffect, RecyclerView, StretchRelativeLayout, RecyclerView

### Community 165 - "smartspace 13"
Cohesion: 0.27
Nodes (6): BcSmartSpaceUtil, Context, Drawable, Icon, Intent, View

### Community 166 - "data 14"
Cohesion: 0.24
Nodes (5): FileProvider, Context, PingScheduler, Context, Uri

### Community 167 - "gestures 7"
Cohesion: 0.31
Nodes (3): GestureController, Flow, Preference

### Community 168 - "icons 9"
Cohesion: 0.25
Nodes (5): filter(), IconPickerCategory, IconPickerItem, Parcelable, Flow

### Community 169 - "preferences2 2"
Cohesion: 0.25
Nodes (3): InvariantDeviceProfile, ReloadHelper, TouchInteractionService

### Community 170 - "ui 10"
Cohesion: 0.35
Nodes (6): Canvas, EdgeEffect, RecyclerView, View, StretchRecyclerViewContainer, RecyclerView

### Community 172 - "AccessibilityEvent AccessibilityService"
Cohesion: 0.27
Nodes (4): AccessibilityEvent, AccessibilityService, Intent, LawnchairAccessibilityService

### Community 173 - "allapps 4"
Cohesion: 0.27
Nodes (5): ExtendedEditText, FallbackSearchInputView, ActivityAllAppsContainerView, colorToken(), Color

### Community 174 - "components 7"
Cohesion: 0.24
Nodes (7): GridItemScope, GridItemScope, Dp, Modifier, T, verticalGridItems(), GridItemScope

### Community 175 - "views 6"
Cohesion: 0.27
Nodes (6): LaunchDepthController, ObjectAnimator, Animator, AnimatorListenerAdapter, AnimatorListenerAdapter, AnimatorListenerAdapter

### Community 176 - "bugreport 2"
Cohesion: 0.27
Nodes (5): KatbinPaste, KatbinService, KatbinUploadBody, KatbinUploadResult, UploaderUtils

### Community 177 - "data 16"
Cohesion: 0.29
Nodes (3): IconOverrideRepository, ComponentKey, SafeCloseable

### Community 178 - "data 17"
Cohesion: 0.38
Nodes (4): Context, CoroutineWorker, Result, LauncherSyncWorker

### Community 179 - "icons 10"
Cohesion: 0.47
Nodes (6): ActivityInfo, ComponentName, Context, Drawable, skipToNextTag(), ThemedIconCompat

### Community 180 - "qsb 5"
Cohesion: 0.36
Nodes (6): Google, AppWidgetHostView, Context, Flow, Launcher, PendingIntent

### Community 181 - "smartspace 14"
Cohesion: 0.20
Nodes (4): FollowSystem, SmartspaceTimeFormat, TwelveHourFormat, TwentyFourHourFormat

### Community 182 - "components 8"
Cohesion: 0.29
Nodes (7): rememberReorderHapticFeedback(), ReorderHapticFeedback, ReorderHapticFeedback, ReorderHapticFeedbackType, END, MOVE, START

### Community 183 - "ui 11"
Cohesion: 0.33
Nodes (5): CombinePaddingValues, Dp, LayoutDirection, PaddingValues, rememberExtendPadding()

### Community 184 - "allapps 5"
Cohesion: 0.33
Nodes (5): AllAppsSearchUiDelegate, LauncherAllAppsContainerView, SearchAdapterProvider, SearchContainerView, LawnchairSearchUiDelegate

### Community 185 - "animation 2"
Cohesion: 0.36
Nodes (5): ArrayMap, AnimationUpdate, InternalListener, UpdateListener, UpdateMap

### Community 186 - "allapps 6"
Cohesion: 0.36
Nodes (5): DeviceProfile, ImageView, LinearLayout, TextView, SearchResultRightLeftIcon

### Community 187 - "smartspace 15"
Cohesion: 0.42
Nodes (3): EventProxy, InterceptingViewPager, MotionEvent

### Community 188 - "bugreport 3"
Cohesion: 0.42
Nodes (5): BugReport, Context, Intent, Parcelable, Uri

### Community 189 - "bugreport 4"
Cohesion: 0.56
Nodes (4): BugReportReceiver, BroadcastReceiver, Context, Intent

### Community 191 - "data 18"
Cohesion: 0.47
Nodes (3): Context, Location, PingCollector

### Community 192 - "wallpaper 3"
Cohesion: 0.39
Nodes (4): AndroidViewModel, LiveData, WallpaperManager, WallpaperViewModel

### Community 194 - "deck 2"
Cohesion: 0.42
Nodes (6): AddFoldersWithItemsTask, AllAppsList, BgDataModel, Intent, ModelTaskController, UserHandle

### Community 195 - "SearchBarInsetsHandler.kt SearchBarInset"
Cohesion: 0.36
Nodes (3): SearchBarInsetsHandler, WindowInsetsAnimationController, WindowInsetsAnimationControlListener

### Community 196 - "theme 6"
Cohesion: 0.31
Nodes (5): AndroidColor, Color, Color, ColorScheme, SystemColorScheme

### Community 197 - "components 9"
Cohesion: 0.44
Nodes (8): Alignment, Arrangement, LazyListState, Modifier, PaddingValues, ScrollState, PreferenceColumn(), PreferenceLazyColumn()

### Community 198 - "util 15"
Cohesion: 0.56
Nodes (8): checkGestureNavContract(), checkHuaweiHonorStock(), checkMeizuStock(), checkOnePlusStock(), checkOppoStock(), checkSamsungStock(), checkXiaomiStock(), getSystemProperty()

### Community 199 - "util 16"
Cohesion: 0.22
Nodes (9): Unit, Celsius, Delisle, Fahrenheit, Kelvin, Newton, Rakine, Reaumur (+1 more)

### Community 200 - "shared"
Cohesion: 0.36
Nodes (5): android.app.WallpaperColors, com.android.internal.colorextraction.types.Tonal, ExtractionInfo, TonalCompat, Tonal

### Community 201 - "util 17"
Cohesion: 0.46
Nodes (3): android.widget.OverScroller, OverScrollerCompat, SuppressWarnings

### Community 202 - "about"
Cohesion: 0.32
Nodes (6): Application, AcknowledgementsViewModel, AndroidViewModel, StateFlow, License, OssLibrary

### Community 203 - "components 10"
Cohesion: 0.61
Nodes (7): OnBackPressedDispatcher, Composable, Modifier, PreferenceSearchScaffold(), SearchBar(), SearchTextField(), SearchTextFieldPreview()

### Community 204 - "calculator 6"
Cohesion: 0.36
Nodes (4): QuickstepProcessInitializer, Context, LawnchairProcessInitializer, Function

### Community 205 - "allapps 7"
Cohesion: 0.46
Nodes (5): Canvas, Rect, RecyclerView, View, SearchItemDecorator

### Community 206 - "allapps 8"
Cohesion: 0.36
Nodes (4): ImageButton, LinearLayout, SearchResultSearchSettings, Search

### Community 207 - "data 19"
Cohesion: 0.43
Nodes (3): Context, Intent, LocationPermission

### Community 208 - "data 20"
Cohesion: 0.36
Nodes (4): Context, CoroutineWorker, Result, LocationPingWorker

### Community 209 - "data 21"
Cohesion: 0.39
Nodes (4): Context, CoroutineWorker, Result, UsageCollectWorker

### Community 211 - "util 18"
Cohesion: 0.43
Nodes (5): A, Context, T, LawnchairSingletonHolder, SingletonHolder

### Community 212 - "views 7"
Cohesion: 0.36
Nodes (4): IconFrame, Context, FrameLayout, ImageView

### Community 215 - "util 19"
Cohesion: 0.38
Nodes (5): ContextWrapper, LifecycleOwner, lookupLifecycleOwner(), observeLifecycleState(), ProvideLifecycleState()

### Community 216 - "util 20"
Cohesion: 0.38
Nodes (6): S, OnResult(), createSideEffect(), P, PropsContainer, SideEffect

### Community 217 - "allapps 9"
Cohesion: 0.38
Nodes (3): LinearLayout, TextView, SearchResultText

### Community 219 - "engine 4"
Cohesion: 0.48
Nodes (4): Context, SearchResult, ShortcutInfo, ShortcutSearchProvider

### Community 220 - "smartspace 16"
Cohesion: 0.43
Nodes (4): DoubleShadowTextView, Canvas, CustomTextView, AppCompatTextView

### Community 221 - "ui 12"
Cohesion: 0.38
Nodes (5): OptionsPopupView, T, View, LawnchairOptionsPopUp, LawnchairPopupDialog

### Community 222 - "data 24"
Cohesion: 0.60
Nodes (3): Converters, ComponentKey, toEntity()

### Community 223 - "data 25"
Cohesion: 0.53
Nodes (3): BatteryOptimization, Context, Intent

### Community 224 - "data 26"
Cohesion: 0.47
Nodes (3): Context, Intent, UsagePermission

### Community 225 - "data 27"
Cohesion: 0.53
Nodes (4): BroadcastReceiver, Context, Intent, UsageRestrictionsReceiver

### Community 226 - "qsb 6"
Cohesion: 0.60
Nodes (4): AssistantIconView, Context, ImageButton, Intent

### Community 227 - "search 8"
Cohesion: 0.47
Nodes (3): BaseAllAppsAdapter, View, SearchAdapterItem

### Community 229 - "color 3"
Cohesion: 0.33
Nodes (6): Swatch, Accent1, Accent2, Accent3, Neutral1, Neutral2

### Community 230 - "ui 13"
Cohesion: 0.47
Nodes (4): BottomSheetContent, BottomSheetHandler, ProvideBottomSheetHandler(), WindowInsets

### Community 231 - "views 8"
Cohesion: 0.33
Nodes (4): FullScreenOverlayMode, FADE_IN, NONE, SUCK_IN

### Community 234 - "qsb 7"
Cohesion: 0.60
Nodes (3): GoogleQsbContainerView, QsbContainerView, QsbFragment

### Community 235 - "qsb 8"
Cohesion: 0.40
Nodes (5): QsbSearchProviderType, APP, APP_AND_WEBSITE, LOCAL, WEBSITE

### Community 236 - "components 11"
Cohesion: 0.70
Nodes (4): Composable, Modifier, TopBar(), TopAppBarScrollBehavior

### Community 240 - "ContextExtensions.kt Intent"
Cohesion: 0.83
Nodes (3): Intent, ResolveInfo, resolveIntent()

### Community 246 - "preferences 19"
Cohesion: 0.50
Nodes (4): Color Preference Components, Preferences Components Directory, Preference Controls, Preference Layout Components

### Community 248 - "util 23"
Cohesion: 1.00
Nodes (3): formatShortElapsedTime(), formatShortElapsedTimeRoundingUpToMinutes(), Context

## Knowledge Gaps
- **189 isolated node(s):** `Loading`, `Error`, `RestoreSuccess`, `RestoreError`, `Loading` (+184 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **32 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PreferenceManager` connect `Backup Creation UI` to `Custom Font Cache`, `IDP Preferences`, `Icon Provider`, `Hotseat Preferences`, `Nova Backup Import`, `preferences 15`, `views`, `allapps 3`, `theme 5`, `ActivityOptionsWrapper QuickstepLauncher`, `allapps`, `preferences 16`, `engine`, `overview 2`, `util 2`, `data 14`, `preferences 7`, `engine 2`, `engine 3`, `data 8`, `algorithms`, `preferences 9`, `root`, `qsb 3`, `preferences2`, `qsb 4`, `folder`, `preferences 18`, `search 4`, `.isSigned() FeedBridge.kt`?**
  _High betweenness centrality (0.128) - this node is a cross-community bridge._
- **Why does `PreferenceManager2` connect `preferences2` to `IDP Preferences`, `Backup Creation UI`, `Icon Provider`, `smartspace 11`, `Hotseat Preferences`, `Retrofit Providers`, `Nova Backup Import`, `search 7`, `nexuslauncher`, `allapps 3`, `ActivityOptionsWrapper QuickstepLauncher`, `allapps`, `engine`, `gestures`, `util 2`, `smartspace 2`, `gestures 7`, `preferences2 2`, `preferences 7`, `util 4`, `views 4`, `qsb 2`, `engine 2`, `engine 3`, `algorithms`, `gestures 3`, `smartspace 7`, `qsb 3`, `qsb 4`, `font 2`, `preferences 10`, `qsb 6`, `ui 4`, `theme 4`, `search 3`, `gestures 5`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **Why does `LawnchairLauncher` connect `ActivityOptionsWrapper QuickstepLauncher` to `smartspace 11`, `Backup Creation UI`, `preferences 4`, `nexuslauncher`, `views 2`, `factory`, `gestures 6`, `smartspace 12`, `data 14`, `gestures 7`, `preferences2 2`, `util 4`, `nexuslauncher 2`, `gestures 2`, `gestures 3`, `util 7`, `icons 4`, `ui 3`, `ui 4`, `util 21`, `gestures 5`?**
  _High betweenness centrality (0.058) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `PreferenceManager2` (e.g. with `ReloadHelper` and `SharedPreferencesMigration`) actually correct?**
  _`PreferenceManager2` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `Loading`, `Error`, `RestoreSuccess` to the rest of the system?**
  _189 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Custom Font Cache` be split into smaller, more focused modules?**
  _Cohesion score 0.051061388410786 - nodes in this community are weakly interconnected._
- **Should `App Usage Dates` be split into smaller, more focused modules?**
  _Cohesion score 0.07678410117434507 - nodes in this community are weakly interconnected._