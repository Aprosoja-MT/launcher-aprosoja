# Graph Report - lawnchair  (2026-09-21)

## Corpus Check
- 501 files · ~158,249 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 4923 nodes · 11280 edges · 273 communities (234 shown, 39 thin omitted)
- Extraction: 97% EXTRACTED · 3% INFERRED · 0% AMBIGUOUS · INFERRED: 335 edges (avg confidence: 0.84)
- Token cost: 27,000 input · 3,800 output

## Community Hubs (Navigation)
- Community 0
- Community 1
- Community 2
- Community 3
- Community 4
- Community 5
- Community 6
- Community 7
- Community 8
- Community 9
- Community 10
- Community 11
- Community 12
- Community 13
- Community 14
- Community 15
- Community 16
- Community 17
- Community 18
- Community 19
- Community 20
- Community 21
- Community 22
- Community 23
- Community 24
- Community 25
- Community 26
- Community 27
- Community 28
- Community 29
- Community 30
- Community 31
- Community 32
- Community 33
- Community 34
- Community 35
- Community 36
- Community 37
- Community 38
- Community 39
- Community 40
- Community 41
- Community 42
- Community 43
- Community 44
- Community 45
- Community 46
- Community 47
- Community 48
- Community 49
- Community 50
- Community 51
- Community 52
- Community 53
- Community 54
- Community 55
- Community 56
- Community 57
- Community 58
- Community 59
- Community 60
- Community 61
- Community 62
- Community 63
- Community 64
- Community 65
- Community 66
- Community 67
- Community 68
- Community 69
- Community 70
- Community 71
- Community 72
- Community 73
- Community 74
- Community 75
- Community 76
- Community 77
- Community 78
- Community 79
- Community 80
- Community 81
- Community 82
- Community 83
- Community 84
- Community 85
- Community 86
- Community 87
- Community 88
- Community 89
- Community 90
- Community 91
- Community 92
- Community 93
- Community 94
- Community 95
- Community 96
- Community 97
- Community 98
- Community 99
- Community 100
- Community 101
- Community 102
- Community 103
- Community 104
- Community 105
- Community 106
- Community 107
- Community 108
- Community 109
- Community 110
- Community 111
- Community 112
- Community 113
- Community 114
- Community 115
- Community 116
- Community 117
- Community 118
- Community 119
- Community 120
- Community 121
- Community 122
- Community 123
- Community 124
- Community 125
- Community 126
- Community 127
- Community 128
- Community 129
- Community 130
- Community 131
- Community 132
- Community 133
- Community 134
- Community 135
- Community 136
- Community 137
- Community 138
- Community 139
- Community 140
- Community 141
- Community 142
- Community 143
- Community 144
- Community 145
- Community 146
- Community 147
- Community 148
- Community 149
- Community 150
- Community 151
- Community 152
- Community 153
- Community 154
- Community 155
- Community 156
- Community 157
- Community 158
- Community 159
- Community 160
- Community 161
- Community 162
- Community 163
- Community 164
- Community 165
- Community 166
- Community 167
- Community 168
- Community 169
- Community 170
- Community 171
- Community 172
- Community 173
- Community 174
- Community 175
- Community 176
- Community 177
- Community 178
- Community 179
- Community 180
- Community 181
- Community 182
- Community 183
- Community 184
- Community 185
- Community 186
- Community 187
- Community 188
- Community 189
- Community 190
- Community 191
- Community 192
- Community 193
- Community 194
- Community 195
- Community 196
- Community 197
- Community 198
- Community 199
- Community 200
- Community 201
- Community 202
- Community 203
- Community 204
- Community 205
- Community 206
- Community 207
- Community 208
- Community 209
- Community 210
- Community 211
- Community 212
- Community 213
- Community 214
- Community 215
- Community 216
- Community 217
- Community 218
- Community 219
- Community 220
- Community 221
- Community 222
- Community 223
- Community 224
- Community 225
- Community 226
- Community 227
- Community 228
- Community 229
- Community 230
- Community 231
- Community 232
- Community 233
- Community 234
- Community 235
- Community 236
- Community 237
- Community 238
- Community 239
- Community 240
- Community 241
- Community 242
- Community 243
- Community 244
- Community 245
- Community 246
- Community 247
- Community 248
- Community 250
- Community 251
- Community 252
- Community 253
- Community 254
- Community 255
- Community 256
- Community 257
- Community 258
- Community 259
- Community 260
- Community 261
- Community 262
- Community 263
- Community 264
- Community 265
- Community 266
- Community 267
- Community 269

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

## Communities (273 total, 39 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.06
Nodes (54): ColorStateList, ExtendedEditText, FallbackSearchInputView, ActivityAllAppsContainerView, AndroidColor, Color, ColorStateListToken, DayNightColorStateList (+46 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (28): FileObserver, FontFamily, AddFontException, AssetFont, DummyFont, Family, Font, FontCache (+20 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (41): CachedDisplayInfo, Display, DisplayCutout, QsbWidgetHost, Resources, ActivityInfo, ComponentName, Context (+33 more)

### Community 3 - "Community 3"
Cohesion: 0.07
Nodes (18): Context, IBinder, Intent, Job, Location, Service, LocationPingService, MotionDetector (+10 more)

### Community 4 - "Community 4"
Cohesion: 0.08
Nodes (43): SafeCloseable, ColorPreference(), Modifier, Preference, ExpandAndShrink(), Modifier, Alignment, Arrangement (+35 more)

### Community 5 - "Community 5"
Cohesion: 0.06
Nodes (23): android.app.PendingIntent, android.app.smartspace.SmartspaceAction, android.app.smartspace.SmartspaceTarget, android.app.smartspace.SmartspaceTargetEvent, android.app.smartspace.uitemplatedata.TapAction, android.graphics.drawable.Drawable, android.os.Parcelable, android.view.View (+15 more)

### Community 6 - "Community 6"
Cohesion: 0.07
Nodes (41): BorderStroke, DrawScope, NestedScrollConnection, NestedScrollSource, Offset, color(), drawPlaceholder(), Color (+33 more)

### Community 7 - "Community 7"
Cohesion: 0.07
Nodes (24): IconChangeListener, IconProvider, CalendarAndClockChangeReceiver, IconPackChangeReceiver, ActivityInfo, BroadcastReceiver, ComponentName, Context (+16 more)

### Community 8 - "Community 8"
Cohesion: 0.06
Nodes (26): FormatterFunction, DoubleShadowTextView, Canvas, DateTimeOptions, IcuDateTextView, Gregorian, Persian, SmartspaceCalendar (+18 more)

### Community 9 - "Community 9"
Cohesion: 0.09
Nodes (41): Composable, Modifier, ModalBottomSheetContent(), AppGesturePreference(), GestureHandlerPreference(), ComponentKey, Modifier, PreferenceAdapter (+33 more)

### Community 10 - "Community 10"
Cohesion: 0.07
Nodes (17): android.media.MediaMetadata, android.media.session.MediaController, android.media.session.PlaybackState, android.os.Handler, android.service.notification.StatusBarNotification, android.view.KeyEvent, androidx.core.util.Consumer, app.lawnchair.NotificationManager (+9 more)

### Community 11 - "Community 11"
Cohesion: 0.10
Nodes (15): ActivityResult, android.graphics.Canvas, androidx.annotation.IntDef, com.android.launcher3.util.EdgeEffectCompat, java.lang.annotation.Retention, BlankActivity, ResultReceiver, Activity (+7 more)

### Community 12 - "Community 12"
Cohesion: 0.09
Nodes (28): AnnouncementId, PackageManager, Context, IconShape, SafeCloseable, PreferenceManager2, Context, PreferenceManager (+20 more)

### Community 14 - "Community 14"
Cohesion: 0.12
Nodes (13): DynamicAnimation, EndAction, EndListener, FlingAnimation, FloatPropertyCompat, SpringAnimation, EndListener, FlingConfig (+5 more)

### Community 15 - "Community 15"
Cohesion: 0.08
Nodes (18): ActivityOptionsWrapper, QuickstepLauncher, OpenAppDrawerGestureHandler, OpenAppSearchGestureHandler, ActivityContext, Bundle, ColorScheme, Intent (+10 more)

### Community 16 - "Community 16"
Cohesion: 0.12
Nodes (26): firstBlocking(), IdpPreference, InvariantDeviceProfile, state(), asState(), CoroutineScope, subscribeBlocking(), asPreferenceAdapter() (+18 more)

### Community 17 - "Community 17"
Cohesion: 0.11
Nodes (34): ClickablePreference(), ClickablePreferencePreview(), Modifier, PreferenceClickConfirmation(), Modifier, T, LoadingScreen(), Color (+26 more)

### Community 18 - "Community 18"
Cohesion: 0.07
Nodes (10): LauncherOverlay, LauncherOverlayCallbacks, Activity, Bundle, Context, LauncherOverlayManager, OverlayCallbackImpl, ISerializableScrollCallback (+2 more)

### Community 19 - "Community 19"
Cohesion: 0.08
Nodes (12): DeviceIdentity, LauncherApi, LauncherAppUsageDto, LauncherDeviceSnapshot, LauncherDeviceUsageDto, LauncherLocationPingDto, LauncherRegisterRequest, LauncherRegisterResponse (+4 more)

### Community 20 - "Community 20"
Cohesion: 0.15
Nodes (9): SearchResultType, SearchTargetCompat, Bundle, Calculation, Intent, SearchTargetFactory, SettingsTarget, Context (+1 more)

### Community 21 - "Community 21"
Cohesion: 0.11
Nodes (31): ColorMode, AUTO, DARK, LIGHT, Composable, Modifier, PreferenceAdapter, T (+23 more)

### Community 22 - "Community 22"
Cohesion: 0.10
Nodes (5): androidx.annotation.ColorInt, androidx.annotation.FloatRange, Shades, ColorUtils, ContrastCalculator

### Community 23 - "Community 23"
Cohesion: 0.11
Nodes (31): ClosedFloatingPointRange, ClosedRange, HsbColorSlider(), Color, Modifier, RgbColorSlider(), Modifier, PreferenceCategory() (+23 more)

### Community 24 - "Community 24"
Cohesion: 0.12
Nodes (29): ReorderableCollectionItemScope, ReorderableScope, Launcher, OptionItem, LauncherOptionMetadata, LauncherOptionPopupItem, LauncherOptionsPopup, toLauncherOptions() (+21 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (15): Circle, Corner, Cupertino, Cylinder, Diamond, Egg, Hexagon, IconShape (+7 more)

### Community 26 - "Community 26"
Cohesion: 0.09
Nodes (14): AdapterItem, AllAppsStore, KeyEvent, SearchUiManager, AllAppsSearchInput, ActivityAllAppsContainerView, FrameLayout, ImageButton (+6 more)

### Community 27 - "Community 27"
Cohesion: 0.09
Nodes (8): android.app.Activity, android.content.Intent, android.os.Bundle, android.view.WindowManager.LayoutParams, IScrollCallback, ILauncherOverlay, Intent, LauncherClient

### Community 28 - "Community 28"
Cohesion: 0.10
Nodes (17): SQLiteDatabase, ImportedDeepShortcut, ItemCounts, android, ByteArray, Uri, NovaBackupConverter, NovaBackupInfo (+9 more)

### Community 29 - "Community 29"
Cohesion: 0.10
Nodes (17): Arc, BaseBezierPath, BottomLeft, BottomRight, Cupertino, Cut, CutHex, IconCornerShape (+9 more)

### Community 30 - "Community 30"
Cohesion: 0.09
Nodes (18): SmartspaceAction, SmartspaceScores, SmartspaceTarget, BatteryStatusProvider, Activity, NowPlayingProvider, Activity, Flow (+10 more)

### Community 31 - "Community 31"
Cohesion: 0.09
Nodes (16): BubbleTextHolder, ImageView, LinearLayout, TextView, SearchResultEmptyState, LinearLayout, TextView, View (+8 more)

### Community 32 - "Community 32"
Cohesion: 0.11
Nodes (32): Modifier, PreferenceAdapter, MainSwitchPreference(), Modifier, PreferenceAdapter, PreviewParameterProvider, SwitchPreference(), SwitchPreferencePreview() (+24 more)

### Community 33 - "Community 33"
Cohesion: 0.12
Nodes (10): GestureNavContract, OnGlobalLayoutListener, Bitmap, Insettable, MotionEvent, Rect, View, LawnchairFloatingSurfaceView (+2 more)

### Community 34 - "Community 34"
Cohesion: 0.12
Nodes (13): FloatRange, UsageDaySweep, DayBucket, SweepState, UsageSweep, Builder, AppWidgetProviderInfo, Bundle (+5 more)

### Community 35 - "Community 35"
Cohesion: 0.09
Nodes (15): OnTouchListener, SimpleOnGestureListener, DirectionalGestureListener, GestureListener, MotionEvent, View, IconGestureListener, GestureType (+7 more)

### Community 36 - "Community 36"
Cohesion: 0.14
Nodes (31): restoreBackupOpener(), restoreNovaBackupOpener(), Context, openAppInfo(), PreferencesOverflowMenu(), About, AppDrawer, AppDrawerFolder (+23 more)

### Community 37 - "Community 37"
Cohesion: 0.09
Nodes (12): DisabledHotseat, GoogleSearchHotseat, HotseatMode, Context, LawnchairHotseat, GoogleSearchSmartspace, GoogleSmartspace, Context (+4 more)

### Community 38 - "Community 38"
Cohesion: 0.10
Nodes (11): android.content.Context, android.content.ContextWrapper, android.os.PowerManager, androidx.annotation.UiThread, androidx.annotation.VisibleForTesting, Override, RootHelperBackend, Override (+3 more)

### Community 39 - "Community 39"
Cohesion: 0.10
Nodes (6): DailyAppUsage, DailyDeviceUsage, LocationPing, Flow, SupportSQLiteQuery, UsageDao

### Community 40 - "Community 40"
Cohesion: 0.15
Nodes (9): AssignExpr, CallExpr, ExprVisitor, GroupingExpr, R, LiteralExpr, LogicalExpr, UnaryExpr (+1 more)

### Community 41 - "Community 41"
Cohesion: 0.13
Nodes (18): ColorOption, CustomColor, Default, SystemAccent, WallpaperPrimary, ColorPreferenceEntry, ColorSelection(), forCustomPicker() (+10 more)

### Community 42 - "Community 42"
Cohesion: 0.16
Nodes (23): AppFilter, AppItem(), AppItemLayout(), AppItemPlaceholder(), App, Bitmap, Composable, Modifier (+15 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (21): SearchRecentSuggestionsProvider, ContactInfo, RecentKeyword, HistorySearchProvider, Context, SearchResult, Action, App (+13 more)

### Community 44 - "Community 44"
Cohesion: 0.12
Nodes (6): UsageAuditUiState, Flow, Intent, SafeCloseable, UsageQuery, UsageService

### Community 45 - "Community 45"
Cohesion: 0.15
Nodes (15): AnimatorSet, FullScreenOverlayView, AnimatorListenerAdapter, AnimatorListenerAdapter, ViewOutlineProvider, AnimatorListenerAdapter, ViewOutlineProvider, Animator (+7 more)

### Community 46 - "Community 46"
Cohesion: 0.14
Nodes (13): SearchAlgorithm, Canvas, FloatArray, View, SearchItemBackground, BaseAllAppsAdapter, View, SearchAdapterItem (+5 more)

### Community 47 - "Community 47"
Cohesion: 0.27
Nodes (3): BinaryExpr, Expr, Parser

### Community 48 - "Community 48"
Cohesion: 0.13
Nodes (21): invariantDeviceProfile(), getIconPackItemWidth(), IconPackGrid(), IconPackInfo, IconPackItem(), IconPackPreferences(), Modifier, PreferenceAdapter (+13 more)

### Community 49 - "Community 49"
Cohesion: 0.17
Nodes (10): android.content.ComponentName, android.content.ServiceConnection, android.os.IBinder, BaseClientService, Override, Override, LauncherClientBridge, ILauncherOverlay (+2 more)

### Community 50 - "Community 50"
Cohesion: 0.11
Nodes (18): BufferedReader, FlowerpotFormatException, RuntimeException, FlowerpotReader, LineParser, CodeRule, IntentAction, IntentCategory (+10 more)

### Community 51 - "Community 51"
Cohesion: 0.14
Nodes (14): CheckLongPressHelper, EventEnum, QsbWidgetHostView, Context, Intent, Launcher, MotionEvent, OnLongClickListener (+6 more)

### Community 52 - "Community 52"
Cohesion: 0.15
Nodes (24): getAllAppsScrimColor(), getDefaultLauncherPackageName(), getDefaultResolveInfo(), getDisplayName(), getFolderBackgroundAlpha(), getFolderPreviewAlpha(), getPrefsIfUnlocked(), getWindowCornerRadius() (+16 more)

### Community 53 - "Community 53"
Cohesion: 0.15
Nodes (21): CustomizeAppDialog(), CustomizeDialog(), ComponentKey, Composable, Drawable, Modifier, Modifier, PreferenceAdapter (+13 more)

### Community 54 - "Community 54"
Cohesion: 0.14
Nodes (9): Ecosia, GitHub, GoogleGo, Intent, Context, Intent, Launcher, QsbSearchProvider (+1 more)

### Community 55 - "Community 55"
Cohesion: 0.15
Nodes (9): ActivityContext, AppWidgetHostView, Context, FrameLayout, ImageView, Intent, PendingIntent, LawnQsbLayout (+1 more)

### Community 56 - "Community 56"
Cohesion: 0.14
Nodes (11): InvariantDeviceProfile, RememberObserver, LauncherPreviewManager, AppWidgetProviderInfo, BgDataModel, ComponentKey, Context, FrameLayout (+3 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (25): FeatureType, FEATURE_ALARM, FEATURE_BEDTIME_ROUTINE, FEATURE_CALENDAR, FEATURE_COMMUTE_TIME, FEATURE_CONSENT, FEATURE_ETA_MONITORING, FEATURE_FITNESS_TRACKING (+17 more)

### Community 58 - "Community 58"
Cohesion: 0.15
Nodes (7): AbstractSlideInView, Interpolator, ComposeBottomSheet, Context, PaddingValues, PendingAnimation, T

### Community 59 - "Community 59"
Cohesion: 0.18
Nodes (11): BitmapInfo, BubbleTextView, info, ItemInfoWithIcon, SearchActionItemInfo, ComponentKey, ComponentName, ShortcutInfo (+3 more)

### Community 60 - "Community 60"
Cohesion: 0.21
Nodes (19): Button, RequiresApi, Modifier, PermissionDialog(), PermissionRow(), WallpaperAccessPermissionDialog(), FileAccessPermissionDialog(), FileSearchProvider() (+11 more)

### Community 61 - "Community 61"
Cohesion: 0.09
Nodes (13): Bing, Intent, Brave, DuckDuckGo, Kagi, Intent, PixelSearch, Sesame (+5 more)

### Community 62 - "Community 62"
Cohesion: 0.08
Nodes (23): TokenType, AMP_AMP, ASSIGN, BAR_BAR, COMMA, EOF, EQUAL_EQUAL, EXPONENT (+15 more)

### Community 63 - "Community 63"
Cohesion: 0.16
Nodes (20): About(), Modifier, auditPermissionStatus(), Composable, Dp, Modifier, T, PreferenceGroupItem() (+12 more)

### Community 64 - "Community 64"
Cohesion: 0.23
Nodes (10): Cursor, FileInfo, IFileInfo, FileSearchProvider, Context, Flow, SearchResult, T (+2 more)

### Community 65 - "Community 65"
Cohesion: 0.13
Nodes (12): IntentFilter, Color, ColorScheme, SystemColorScheme, ColorSchemeChangeListener, BroadcastReceiver, Context, Intent (+4 more)

### Community 66 - "Community 66"
Cohesion: 0.18
Nodes (11): AppSearchProvider, AllAppsList, Context, SearchResult, filterHiddenApps(), Context, ShortcutInfo, SearchUtils (+3 more)

### Community 67 - "Community 67"
Cohesion: 0.21
Nodes (16): ActionsSectionBuilder, AppsAndShortcutsSectionBuilder, CalculationSectionBuilder, ContactsSectionBuilder, EmptyStateSectionBuilder, FilesSectionBuilder, HistorySectionBuilder, SearchSettingsSectionBuilder (+8 more)

### Community 68 - "Community 68"
Cohesion: 0.18
Nodes (18): ColorContrastWarning(), Modifier, FontPreference(), Modifier, isNotificationServiceEnabled(), Context, Modifier, NotificationAccessConfirmation() (+10 more)

### Community 69 - "Community 69"
Cohesion: 0.25
Nodes (16): Modifier, PaddingValues, T, PositionalListItem, PositionalMapper, PositionalOrderMenu(), PositionalReorderer(), ReorderableItemContainer() (+8 more)

### Community 70 - "Community 70"
Cohesion: 0.17
Nodes (13): android.util.SparseIntArray, androidx.annotation.Keep, androidx.annotation.Nullable, androidx.annotation.RequiresApi, app.lawnchair.theme.color.AndroidColor, app.lawnchair.theme.ThemeProvider, com.android.launcher3.widget.LocalColorExtractor, dev.kdrag0n.colorkt.Color (+5 more)

### Community 71 - "Community 71"
Cohesion: 0.15
Nodes (11): DefaultSearchAdapterProvider, SparseIntArray, Canvas, Rect, RecyclerView, View, SearchItemDecorator, BaseAllAppsAdapter (+3 more)

### Community 72 - "Community 72"
Cohesion: 0.15
Nodes (11): DeviceAdminReceiver, Context, Intent, Modifier, ServiceWarningDialog(), SleepDeviceAdmin, SleepGestureHandler, SleepMethod (+3 more)

### Community 73 - "Community 73"
Cohesion: 0.14
Nodes (11): IntDef, ColorsHints, WallpaperColorsCompat, WallpaperManager, OnColorsChangedListener, WallpaperManagerCompat, WallpaperManagerCompatVO, WallpaperColors (+3 more)

### Community 74 - "Community 74"
Cohesion: 0.19
Nodes (8): Converters, ComponentKey, toEntity(), FolderService, Flow, SafeCloseable, FolderInfo, withContext()

### Community 75 - "Community 75"
Cohesion: 0.16
Nodes (13): AddFoldersWithItemsTask, AllAppsList, BgDataModel, Intent, ModelTaskController, UserHandle, AllAppsList, BaseAllAppsAdapter (+5 more)

### Community 76 - "Community 76"
Cohesion: 0.16
Nodes (14): HeadlessAppWidgetHost, HeadlessAppWidgetHostView, HeadlessWidgetsManager, AppWidgetHost, AppWidgetHostView, AppWidgetProviderInfo, Context, Flow (+6 more)

### Community 77 - "Community 77"
Cohesion: 0.15
Nodes (7): BasePref, BasePreferenceManager, BoolPref, FloatPref, IntPref, SharedPreferences, StringSetPref

### Community 78 - "Community 78"
Cohesion: 0.16
Nodes (10): Activity, Bitmap, Flow, ImageView, PendingIntent, TextView, ViewGroup, SmartspaceWidgetReader (+2 more)

### Community 79 - "Community 79"
Cohesion: 0.18
Nodes (10): LayerDrawable, BcSmartspaceCard, ImageView, LinearLayout, TextView, ViewGroup, DoubleShadowIconDrawable, Bitmap (+2 more)

### Community 80 - "Community 80"
Cohesion: 0.16
Nodes (9): SearchAction, Builder, Bundle, Icon, Intent, Parcelable, PendingIntent, UserHandle (+1 more)

### Community 81 - "Community 81"
Cohesion: 0.21
Nodes (15): CreateBackupScreen(), Modifier, Modifier, restoreBackupGraph(), RestoreBackupOptions(), RestoreBackupScreen(), FlagSwitchPreference(), DummyLauncherBox() (+7 more)

### Community 82 - "Community 82"
Cohesion: 0.14
Nodes (14): SettingInfo, findSettingsByNameAndAction(), Context, Flow, SearchResult, SettingsSearchProvider, Context, Flow (+6 more)

### Community 83 - "Community 83"
Cohesion: 0.19
Nodes (18): colorStringToIntColor(), hsvValuesToIntColor(), intColorToColorString(), intColorToHsvColorArray(), FloatArray, HsbSliderType, BRIGHTNESS, HUE (+10 more)

### Community 84 - "Community 84"
Cohesion: 0.15
Nodes (10): Chroma, ChromaAdd, ChromaBound, ChromaConstant, ChromaMaxOut, ChromaMultiple, ChromaSource, CoreSpec (+2 more)

### Community 86 - "Community 86"
Cohesion: 0.21
Nodes (12): Decoder, Encoder, KSerializer, App, OpenAppTarget, Shortcut, ComponentKeySerializer, IntentSerializer (+4 more)

### Community 87 - "Community 87"
Cohesion: 0.16
Nodes (7): index, FolderInfoEntity, FolderItemEntity, FolderDao, FolderWithItems, Flow, SupportSQLiteQuery

### Community 88 - "Community 88"
Cohesion: 0.16
Nodes (11): IRootHelper, RootService, Intent, RootHelper, ComponentName, Deferred, IBinder, RootHelperManager (+3 more)

### Community 89 - "Community 89"
Cohesion: 0.14
Nodes (9): ColorStyle, Content, Expressive, FruitSalad, Monochromatic, Rainbow, Spritz, TonalSpot (+1 more)

### Community 90 - "Community 90"
Cohesion: 0.19
Nodes (7): Comparable, DataProvider, GoogleFontInfo, GoogleFontsListing, JSONObject, SafeCloseable, MockDataProvider

### Community 91 - "Community 91"
Cohesion: 0.20
Nodes (5): HandlerThread, Context, Handler, SharedPreferences, LawnchairLockedStateController

### Community 92 - "Community 92"
Cohesion: 0.14
Nodes (7): Matrix, Arch, FourSidedCookie, Context, SevenSidedCookie, T, unsafeLazy()

### Community 93 - "Community 93"
Cohesion: 0.18
Nodes (14): GestureHandlerOption, Activity, Context, NoOp, OpenApp, OpenAppDrawer, OpenAppSearch, OpenAssistant (+6 more)

### Community 94 - "Community 94"
Cohesion: 0.19
Nodes (7): AbstractFloatingView, BothAxesSwipeDetector, MotionEvent, PointF, TouchController, VerticalSwipeTouchController, TouchController

### Community 95 - "Community 95"
Cohesion: 0.21
Nodes (9): AppWidgetProvider, LauncherAppWidgetHostView, AppWidgetProviderInfo, Context, RemoteViews, View, ViewGroup, LawnchairAppWidgetHostView (+1 more)

### Community 96 - "Community 96"
Cohesion: 0.24
Nodes (4): CardView, Bitmap, LinearLayout, WallpaperCarouselView

### Community 97 - "Community 97"
Cohesion: 0.22
Nodes (10): ComposeColor, delinearized(), Color, ColorScheme, labInvf(), setLuminance(), toComposeColorScheme(), toAndroidColor() (+2 more)

### Community 98 - "Community 98"
Cohesion: 0.20
Nodes (15): ContextWrapper, Lifecycle, clipToBottomPercentage(), clipToPercentage(), clipToVisiblePercentage(), createPreviewView(), DummyLauncherLayout(), InvariantDeviceProfile (+7 more)

### Community 99 - "Community 99"
Cohesion: 0.20
Nodes (6): PagerAdapter, CardPagerAdapter, View, ViewGroup, ViewHolder, ViewHolder

### Community 100 - "Community 100"
Cohesion: 0.25
Nodes (9): BugReport, Context, Intent, Parcelable, Uri, BugReportReceiver, BroadcastReceiver, Context (+1 more)

### Community 101 - "Community 101"
Cohesion: 0.19
Nodes (11): AuditPermission, BACKGROUND_LOCATION, BATTERY, LOCATION, NOTIFICATIONS, USAGE_ACCESS, Context, auditPermissionLabel() (+3 more)

### Community 102 - "Community 102"
Cohesion: 0.24
Nodes (6): BridgeInfo, CustomBridgeInfo, FeedBridge, Context, PixelBridgeInfo, getSignatureHash()

### Community 103 - "Community 103"
Cohesion: 0.20
Nodes (12): GestureHandlerConfig, Context, NoOp, OpenApp, OpenAppDrawer, OpenAppSearch, OpenAssistant, OpenNotifications (+4 more)

### Community 104 - "Community 104"
Cohesion: 0.19
Nodes (6): CustomIconPack, ComponentName, Drawable, Flow, Intent, XmlPullParser

### Community 105 - "Community 105"
Cohesion: 0.16
Nodes (8): IconEntry, IconType, Calendar, Normal, ClockMetadata, ComponentName, Drawable, SystemIconPack

### Community 106 - "Community 106"
Cohesion: 0.20
Nodes (9): Context, Task, OverlayUICallbacks, OverlayUICallbacksImpl, TaskOverlay, TaskOverlayFactoryImpl, TaskContainer, TaskOverlayFactory (+1 more)

### Community 107 - "Community 107"
Cohesion: 0.18
Nodes (7): BcSmartspaceView, AnimatorListenerAdapter, ViewPager, Animator, AnimatorListenerAdapter, FrameLayout, repeatOnAttached()

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (15): Color, ColorScheme, MonetColorSchemeCompat, ColorScheme, Style, CLOCK, CLOCK_VIBRANT, CONTENT (+7 more)

### Community 109 - "Community 109"
Cohesion: 0.24
Nodes (8): Denied, FileAccessManager, FileAccessState, Full, Context, SafeCloseable, StateFlow, Partial

### Community 110 - "Community 110"
Cohesion: 0.15
Nodes (8): Hue, HueAdd, HueExpressiveSecondary, HueExpressiveTertiary, HueSource, HueSubtract, HueVibrantSecondary, HueVibrantTertiary

### Community 112 - "Community 112"
Cohesion: 0.19
Nodes (13): AppCompatImageView, Paint, RectF, generateColor(), Context, createTextBitmap(), getCornerRadiiCompat(), Bitmap (+5 more)

### Community 113 - "Community 113"
Cohesion: 0.21
Nodes (11): BaseDraggingActivity, ModelAppInfo, Customize, ComponentName, Context, ItemInfo, SystemShortcut, View (+3 more)

### Community 114 - "Community 114"
Cohesion: 0.25
Nodes (8): Consumer, KProperty, LifecycleOwner, SafeCloseable, T, View, PrefEntry, View

### Community 115 - "Community 115"
Cohesion: 0.23
Nodes (7): SearchSession, BaseAllAppsAdapter, Context, SearchCallback, SearchTarget, LawnchairASISearchAlgorithm, PendingQuery

### Community 116 - "Community 116"
Cohesion: 0.18
Nodes (10): ImageButton, LinearLayout, SearchResultSearchSettings, Search, Bundle, ComponentActivity, Context, Intent (+2 more)

### Community 117 - "Community 117"
Cohesion: 0.21
Nodes (16): BackupInfoGroup(), BackupInfoIntPreference(), BackupInfoOptions(), BackupInfoStringPreference(), CollectEvents(), Flow, State, RestoreNovaBackupContent() (+8 more)

### Community 118 - "Community 118"
Cohesion: 0.25
Nodes (3): ExpressionException, RuntimeException, Evaluator

### Community 120 - "Community 120"
Cohesion: 0.17
Nodes (12): android.content.BroadcastReceiver, android.content.IntentFilter, android.content.pm.PackageManager, android.os.Handler.Callback, android.os.Message, android.view.Window, android.view.WindowManager, app.lawnchair.FeedBridge.BridgeInfo (+4 more)

### Community 121 - "Community 121"
Cohesion: 0.28
Nodes (5): BackupInfo, Bitmap, Context, Uri, LawnchairBackup

### Community 122 - "Community 122"
Cohesion: 0.17
Nodes (4): DefaultLifecycleObserver, PreferenceChangeListener, LifecycleOwner, PrefLifecycleObserver

### Community 123 - "Community 123"
Cohesion: 0.23
Nodes (10): DisplayFeature, SharedPreferencesView, T, SharedPreferencesMigration, Modifier, NavHostController, Preferences(), PreferenceScreen() (+2 more)

### Community 124 - "Community 124"
Cohesion: 0.23
Nodes (4): MathContext, RoundingMode, Expressions, Token

### Community 125 - "Community 125"
Cohesion: 0.23
Nodes (7): ByteArray, SafeCloseable, WallpaperManager, WallpaperService, Wallpaper, bitmapToByteArray(), ByteArray

### Community 126 - "Community 126"
Cohesion: 0.17
Nodes (4): Flowerpot, Context, Manager, Version

### Community 127 - "Community 127"
Cohesion: 0.17
Nodes (6): GestureHandler, NoOpGestureHandler, OpenAppGestureHandler, OpenNotificationsHandler, OpenSearchGestureHandler, RecentsGestureHandler

### Community 128 - "Community 128"
Cohesion: 0.19
Nodes (6): IconPack, ClockMetadata, ComponentName, Deferred, Drawable, Flow

### Community 129 - "Community 129"
Cohesion: 0.23
Nodes (8): animateToAllApps(), Launcher, Startpage, LauncherState, PendingAnimation, SearchBarStateHandler, StateAnimationConfig, StateManager

### Community 130 - "Community 130"
Cohesion: 0.19
Nodes (7): DynamicColorScheme, Color, ColorScheme, Color, ColorScheme, MaterialYouTargets, Zcam

### Community 131 - "Community 131"
Cohesion: 0.19
Nodes (7): AlphabeticalAppsList, ItemInfo, OnIDPChangeListener, T, LawnchairAlphabeticalAppsList, categorizeAppsWithSystemAndGoogle(), Context

### Community 132 - "Community 132"
Cohesion: 0.20
Nodes (5): ApplicationInfo, FlowerpotApps, Category, CodeRules, IsGame

### Community 133 - "Community 133"
Cohesion: 0.28
Nodes (4): com, DatabaseFiles, android, LawndeckManager

### Community 134 - "Community 134"
Cohesion: 0.21
Nodes (5): IconOverride, IconOverrideDao, ComponentKey, Flow, SupportSQLiteQuery

### Community 135 - "Community 135"
Cohesion: 0.26
Nodes (7): DeviceSerial, Context, ResolvedSerial, SerialSource, DEBUG, KNOX, NONE

### Community 137 - "Community 137"
Cohesion: 0.15
Nodes (12): UsageEventType, ACTIVITY_PAUSED, ACTIVITY_RESUMED, ACTIVITY_STOPPED, DEVICE_SHUTDOWN, DEVICE_STARTUP, KEYGUARD_HIDDEN, KEYGUARD_SHOWN (+4 more)

### Community 138 - "Community 138"
Cohesion: 0.23
Nodes (6): DBGridInfo, DeviceProfileOverrides, InvariantDeviceProfile, SafeCloseable, Options, TextFactors

### Community 139 - "Community 139"
Cohesion: 0.30
Nodes (7): createPopup(), Popup, Context, Intent, OptionsPopupView, View, SmartspacerView

### Community 140 - "Community 140"
Cohesion: 0.30
Nodes (7): Dp, LayoutDirection, PaddingValues, max(), PaddingValues, minus(), PaddingValues

### Community 142 - "Community 142"
Cohesion: 0.25
Nodes (8): ContactsTarget, FilesTarget, ComponentKey, Context, Icon, ShortcutInfo, Uri, SearchLinksTarget

### Community 143 - "Community 143"
Cohesion: 0.25
Nodes (7): RecyclerView, RememberObserver, T, Recyclable, rememberViewPool(), ViewHolder, ViewPool

### Community 144 - "Community 144"
Cohesion: 0.24
Nodes (7): AppCompatButton, FontManager, FontSpec, AttributeSet, SafeCloseable, TextView, CustomButton

### Community 145 - "Community 145"
Cohesion: 0.26
Nodes (6): IBinder, Intent, Job, Service, UploaderService, requireSystemService()

### Community 146 - "Community 146"
Cohesion: 0.19
Nodes (5): FolderOrderUtils, FolderViewModel, AndroidViewModel, LiveData, StateFlow

### Community 147 - "Community 147"
Cohesion: 0.27
Nodes (7): getAppName(), Context, Flow, SafeCloseable, StatusBarNotification, NotificationManager, checkPackagePermission()

### Community 148 - "Community 148"
Cohesion: 0.17
Nodes (11): AppMatcher, MatchResult, MatchType, ALL_TOKENS_PRESENT, DIRECT_PREFIX, EXACT_MATCH, FUZZY, INITIALS (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.22
Nodes (3): GoogleWebSearchProvider, KagiWebSearchProvider, Flow

### Community 150 - "Community 150"
Cohesion: 0.26
Nodes (9): Modifier, OverflowMenu(), OverflowMenuScope, OverflowMenuScopeImpl, ClickableIcon(), Color, ImageVector, Modifier (+1 more)

### Community 151 - "Community 151"
Cohesion: 0.37
Nodes (4): ComponentName, Context, Task, TaskUtilLockState

### Community 152 - "Community 152"
Cohesion: 0.36
Nodes (11): FileMetadata, FileSystem, file2Uri(), getMetadata(), isDirectory(), isFile(), isRegularFile(), Uri (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.32
Nodes (8): HolderFactory, IntConsumer, AppWidgetHost, Context, RemoteViews, LawnchairHolderFactory, LauncherWidgetHolder, LawnchairWidgetHolder

### Community 154 - "Community 154"
Cohesion: 0.29
Nodes (8): Error, AndroidViewModel, Uri, Loading, RestoreBackupUiState, RestoreBackupViewModel, RestoreBackupViewModelState, Success

### Community 155 - "Community 155"
Cohesion: 0.44
Nodes (3): Context, Location, LocationFix

### Community 156 - "Community 156"
Cohesion: 0.24
Nodes (5): Context, Intent, LocationPermission, CoroutineScope, PreferenceCollectorScope

### Community 157 - "Community 157"
Cohesion: 0.26
Nodes (3): Context, UsageCollector, WatchedApp

### Community 158 - "Community 158"
Cohesion: 0.41
Nodes (4): ComponentName, Context, Intent, OpenAssistantHandler

### Community 159 - "Community 159"
Cohesion: 0.29
Nodes (5): IconPackProvider, ClockMetadata, Drawable, SafeCloseable, UserHandle

### Community 160 - "Community 160"
Cohesion: 0.29
Nodes (4): T, ObjectPref, StringBasedPref, StringPref

### Community 161 - "Community 161"
Cohesion: 0.38
Nodes (10): AnnouncementItem(), AnnouncementItemContent(), AnnouncementPreference(), AnnouncementPreferenceItemContent(), calculateAlpha(), InfoPreferenceWithLinkPreview(), ImageVector, Modifier (+2 more)

### Community 162 - "Community 162"
Cohesion: 0.30
Nodes (9): IconPickerGrid(), IconPickerPreference(), IconPreview(), Modifier, PaddingValues, State, LazyGridLayout, T (+1 more)

### Community 163 - "Community 163"
Cohesion: 0.24
Nodes (4): AccessibilityEvent, AccessibilityService, Intent, LawnchairAccessibilityService

### Community 164 - "Community 164"
Cohesion: 0.40
Nodes (3): K, MutableMapPref, V

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (5): OnClickListener, OverviewActionsView, LinearLayout, View, LawnchairOverviewActionsView

### Community 166 - "Community 166"
Cohesion: 0.22
Nodes (4): ScrimView, getSystemAccent(), lightenColor(), LawnchairScrimView

### Community 167 - "Community 167"
Cohesion: 0.29
Nodes (6): SpringRelativeLayout, Canvas, EdgeEffect, RecyclerView, StretchRelativeLayout, RecyclerView

### Community 168 - "Community 168"
Cohesion: 0.40
Nodes (4): DeviceSpecs, DeviceSpecsSnapshot, Context, StatFs

### Community 169 - "Community 169"
Cohesion: 0.31
Nodes (3): GestureController, Flow, Preference

### Community 170 - "Community 170"
Cohesion: 0.25
Nodes (3): InvariantDeviceProfile, ReloadHelper, TouchInteractionService

### Community 171 - "Community 171"
Cohesion: 0.27
Nodes (6): ContactsSearchProvider, Context, Flow, SearchResult, Context, SearchPermission

### Community 172 - "Community 172"
Cohesion: 0.29
Nodes (8): Acknowledgements(), Modifier, OssLibraryItem(), AcknowledgementsViewModel, AndroidViewModel, StateFlow, License, OssLibrary

### Community 173 - "Community 173"
Cohesion: 0.35
Nodes (9): createPreviewIdp(), GridOverridesPreview(), InvariantDeviceProfile, Modifier, Drawable, Modifier, wallpaperDrawable(), WallpaperPreview() (+1 more)

### Community 174 - "Community 174"
Cohesion: 0.47
Nodes (10): CustomIconShapePreferenceOption(), iconShapeEntries(), IconShapePreference(), IconShapePreview(), Color, Context, IconShape, Modifier (+2 more)

### Community 175 - "Community 175"
Cohesion: 0.35
Nodes (6): Canvas, EdgeEffect, RecyclerView, View, StretchRecyclerViewContainer, RecyclerView

### Community 177 - "Community 177"
Cohesion: 0.33
Nodes (5): Application, AndroidViewModel, LiveData, WallpaperManager, WallpaperViewModel

### Community 178 - "Community 178"
Cohesion: 0.24
Nodes (7): GridItemScope, GridItemScope, Dp, Modifier, T, verticalGridItems(), GridItemScope

### Community 179 - "Community 179"
Cohesion: 0.38
Nodes (9): item, FeatureFlagsPreference(), GestureSandboxDebugAction, IntentPreference(), Intent, Modifier, OnboardingPref, OnboardingPreference() (+1 more)

### Community 180 - "Community 180"
Cohesion: 0.27
Nodes (6): LaunchDepthController, ObjectAnimator, Animator, AnimatorListenerAdapter, AnimatorListenerAdapter, AnimatorListenerAdapter

### Community 181 - "Community 181"
Cohesion: 0.27
Nodes (5): FileProvider, Context, PingScheduler, Context, Uri

### Community 182 - "Community 182"
Cohesion: 0.27
Nodes (5): KatbinPaste, KatbinService, KatbinUploadBody, KatbinUploadResult, UploaderUtils

### Community 183 - "Community 183"
Cohesion: 0.29
Nodes (3): IconOverrideRepository, ComponentKey, SafeCloseable

### Community 184 - "Community 184"
Cohesion: 0.38
Nodes (4): Context, CoroutineWorker, Result, LauncherSyncWorker

### Community 185 - "Community 185"
Cohesion: 0.29
Nodes (5): filter(), IconPickerCategory, IconPickerItem, Parcelable, Flow

### Community 186 - "Community 186"
Cohesion: 0.36
Nodes (6): Google, AppWidgetHostView, Context, Flow, Launcher, PendingIntent

### Community 187 - "Community 187"
Cohesion: 0.29
Nodes (6): Calculation, CalculatorSearchProvider, Calculation, Context, Flow, SearchResult

### Community 188 - "Community 188"
Cohesion: 0.27
Nodes (3): CustomWebSearchProvider, Context, Flow

### Community 189 - "Community 189"
Cohesion: 0.24
Nodes (3): Context, Flow, WebSearchProvider

### Community 190 - "Community 190"
Cohesion: 0.33
Nodes (4): FrameLayout, MotionEvent, View, SmartspaceViewContainer

### Community 191 - "Community 191"
Cohesion: 0.29
Nodes (7): rememberReorderHapticFeedback(), ReorderHapticFeedback, ReorderHapticFeedback, ReorderHapticFeedbackType, END, MOVE, START

### Community 192 - "Community 192"
Cohesion: 0.38
Nodes (9): ContentType, ADD_BUTTON, FONT, FontSelection(), FontSelectionItem(), Modifier, PreferenceAdapter, removeFamilyPrefix() (+1 more)

### Community 193 - "Community 193"
Cohesion: 0.33
Nodes (5): CombinePaddingValues, Dp, LayoutDirection, PaddingValues, rememberExtendPadding()

### Community 194 - "Community 194"
Cohesion: 0.33
Nodes (5): AllAppsSearchUiDelegate, LauncherAllAppsContainerView, SearchAdapterProvider, SearchContainerView, LawnchairSearchUiDelegate

### Community 195 - "Community 195"
Cohesion: 0.36
Nodes (5): ArrayMap, AnimationUpdate, InternalListener, UpdateListener, UpdateMap

### Community 196 - "Community 196"
Cohesion: 0.36
Nodes (5): DeviceProfile, ImageView, LinearLayout, TextView, SearchResultRightLeftIcon

### Community 197 - "Community 197"
Cohesion: 0.42
Nodes (3): EventProxy, InterceptingViewPager, MotionEvent

### Community 198 - "Community 198"
Cohesion: 0.53
Nodes (8): OnBackPressedDispatcher, Composable, Modifier, PreferenceSearchScaffold(), SearchBar(), SearchTextField(), SearchTextFieldPreview(), TextStyle

### Community 200 - "Community 200"
Cohesion: 0.47
Nodes (3): Context, Location, PingCollector

### Community 201 - "Community 201"
Cohesion: 0.28
Nodes (3): UsageDayWindow, UsageEvent, UsagePackageUsage

### Community 203 - "Community 203"
Cohesion: 0.36
Nodes (6): AttributeSet, Context, LayoutInflater, SafeCloseable, View, LawnchairLayoutFactory

### Community 204 - "Community 204"
Cohesion: 0.36
Nodes (3): SearchBarInsetsHandler, WindowInsetsAnimationController, WindowInsetsAnimationControlListener

### Community 205 - "Community 205"
Cohesion: 0.33
Nodes (6): BcSmartSpaceUtil, Context, Drawable, Icon, Intent, View

### Community 206 - "Community 206"
Cohesion: 0.44
Nodes (8): Alignment, Arrangement, LazyListState, Modifier, PaddingValues, ScrollState, PreferenceColumn(), PreferenceLazyColumn()

### Community 207 - "Community 207"
Cohesion: 0.39
Nodes (9): DevicePermissionCheck(), DeviceStatusValue(), Modifier, PreferenceCategoryGroup(), PreferencesDashboard(), PreferencesDashboardTitle(), PreferencesDebugWarning(), PreferencesDeviceStatus() (+1 more)

### Community 208 - "Community 208"
Cohesion: 0.36
Nodes (4): CustomFontTextView, AppCompatTextView, Job, runOnMainThread()

### Community 209 - "Community 209"
Cohesion: 0.56
Nodes (8): checkGestureNavContract(), checkHuaweiHonorStock(), checkMeizuStock(), checkOnePlusStock(), checkOppoStock(), checkSamsungStock(), checkXiaomiStock(), getSystemProperty()

### Community 210 - "Community 210"
Cohesion: 0.22
Nodes (9): Unit, Celsius, Delisle, Fahrenheit, Kelvin, Newton, Rakine, Reaumur (+1 more)

### Community 212 - "Community 212"
Cohesion: 0.36
Nodes (5): android.app.WallpaperColors, com.android.internal.colorextraction.types.Tonal, ExtractionInfo, TonalCompat, Tonal

### Community 213 - "Community 213"
Cohesion: 0.46
Nodes (3): android.widget.OverScroller, OverScrollerCompat, SuppressWarnings

### Community 214 - "Community 214"
Cohesion: 0.36
Nodes (4): Configuration, CreateBackupViewModel, AndroidViewModel, Bitmap

### Community 215 - "Community 215"
Cohesion: 0.36
Nodes (4): QuickstepProcessInitializer, Context, LawnchairProcessInitializer, Function

### Community 216 - "Community 216"
Cohesion: 0.32
Nodes (6): S, OnResult(), createSideEffect(), P, PropsContainer, SideEffect

### Community 218 - "Community 218"
Cohesion: 0.36
Nodes (4): Context, CoroutineWorker, Result, LocationPingWorker

### Community 219 - "Community 219"
Cohesion: 0.39
Nodes (4): Context, CoroutineWorker, Result, UsageCollectWorker

### Community 221 - "Community 221"
Cohesion: 0.43
Nodes (5): A, Context, T, LawnchairSingletonHolder, SingletonHolder

### Community 222 - "Community 222"
Cohesion: 0.36
Nodes (4): IconFrame, Context, FrameLayout, ImageView

### Community 224 - "Community 224"
Cohesion: 0.52
Nodes (5): Converter, ResponseBody, Retrofit, StringConverterFactory, Type

### Community 225 - "Community 225"
Cohesion: 0.43
Nodes (4): PowerManager, BatteryOptimization, Context, Intent

### Community 226 - "Community 226"
Cohesion: 0.29
Nodes (4): Response, DuckDuckGoService, GoogleService, KagiService

### Community 227 - "Community 227"
Cohesion: 0.38
Nodes (3): LinearLayout, TextView, SearchResultText

### Community 229 - "Community 229"
Cohesion: 0.48
Nodes (4): Context, SearchResult, ShortcutInfo, ShortcutSearchProvider

### Community 230 - "Community 230"
Cohesion: 0.38
Nodes (5): OptionsPopupView, T, View, LawnchairOptionsPopUp, LawnchairPopupDialog

### Community 231 - "Community 231"
Cohesion: 0.40
Nodes (4): BaseLogic, Keep, LauncherActivityInfo, LauncherActivityCachingLogic

### Community 232 - "Community 232"
Cohesion: 0.47
Nodes (3): Context, Intent, UsagePermission

### Community 233 - "Community 233"
Cohesion: 0.53
Nodes (4): BroadcastReceiver, Context, Intent, UsageRestrictionsReceiver

### Community 234 - "Community 234"
Cohesion: 0.60
Nodes (4): AssistantIconView, Context, ImageButton, Intent

### Community 237 - "Community 237"
Cohesion: 0.47
Nodes (4): Bundle, ComponentActivity, SmartspacePreferencesShortcut, SmartspaceWidget

### Community 238 - "Community 238"
Cohesion: 0.53
Nodes (5): AndroidText(), Color, Modifier, T, toIntColor()

### Community 239 - "Community 239"
Cohesion: 0.60
Nodes (5): ColorDot(), DefaultColorDot(), Color, Modifier, T

### Community 241 - "Community 241"
Cohesion: 0.60
Nodes (3): KnoxDeviceInfo, KnoxProfile, Context

### Community 244 - "Community 244"
Cohesion: 0.40
Nodes (5): QsbSearchProviderType, APP, APP_AND_WEBSITE, LOCAL, WEBSITE

### Community 246 - "Community 246"
Cohesion: 0.70
Nodes (4): Composable, Modifier, TopBar(), TopAppBarScrollBehavior

### Community 250 - "Community 250"
Cohesion: 0.83
Nodes (3): Intent, ResolveInfo, resolveIntent()

### Community 259 - "Community 259"
Cohesion: 0.50
Nodes (4): colorpreference Subdirectory (Example), Components Directory Organization, controls Subdirectory Convention, layout Subdirectory Convention

## Knowledge Gaps
- **189 isolated node(s):** `Loading`, `Error`, `RestoreSuccess`, `RestoreError`, `Loading` (+184 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **39 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PreferenceManager2` connect `Community 12` to `Community 129`, `Community 131`, `Community 7`, `Community 8`, `Community 138`, `Community 139`, `Community 15`, `Community 16`, `Community 18`, `Community 26`, `Community 156`, `Community 30`, `Community 32`, `Community 35`, `Community 169`, `Community 170`, `Community 45`, `Community 46`, `Community 52`, `Community 54`, `Community 55`, `Community 188`, `Community 64`, `Community 65`, `Community 66`, `Community 67`, `Community 75`, `Community 82`, `Community 90`, `Community 94`, `Community 234`, `Community 240`, `Community 113`, `Community 123`, `Community 127`?**
  _High betweenness centrality (0.123) - this node is a cross-community bridge._
- **Why does `PreferenceManager` connect `Community 12` to `Community 0`, `Community 1`, `Community 129`, `Community 131`, `Community 4`, `Community 7`, `Community 138`, `Community 15`, `Community 144`, `Community 26`, `Community 160`, `Community 32`, `Community 165`, `Community 166`, `Community 52`, `Community 181`, `Community 55`, `Community 187`, `Community 64`, `Community 67`, `Community 77`, `Community 82`, `Community 88`, `Community 102`, `Community 231`, `Community 243`, `Community 115`?**
  _High betweenness centrality (0.118) - this node is a cross-community bridge._
- **Why does `LawnchairLauncher` connect `Community 15` to `Community 4`, `Community 139`, `Community 18`, `Community 153`, `Community 158`, `Community 33`, `Community 163`, `Community 36`, `Community 169`, `Community 170`, `Community 51`, `Community 190`, `Community 72`, `Community 86`, `Community 92`, `Community 94`, `Community 96`, `Community 113`, `Community 247`, `Community 126`, `Community 127`?**
  _High betweenness centrality (0.085) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `PreferenceManager2` (e.g. with `ReloadHelper` and `SharedPreferencesMigration`) actually correct?**
  _`PreferenceManager2` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `Loading`, `Error`, `RestoreSuccess` to the rest of the system?**
  _189 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.05622489959839357 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.051061388410786 - nodes in this community are weakly interconnected._