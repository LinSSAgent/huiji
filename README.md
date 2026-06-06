<div align="center">
  <h1>🎙️ 会记 MeetingMind</h1>
  <p><strong>智能会议记录 · 让每一场会议都被记住</strong></p>
  <img src="https://img.shields.io/badge/Android-3DC96A?style=flat-square&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Material%20Design%203-FF7043?style=flat-square" />
  <img src="https://img.shields.io/badge/Version-1.0.0-2196F3?style=flat-square" />
</div>

---

## 📖 应用简介

**会记** 是一款基于 Android 平台的智能会议记录应用，融合阿里云语音识别与大语言模型技术，实现会议全过程的自动化记录：从实时语音转文字，到 AI 智能摘要生成、待办事项自动提取，彻底解放你的会议笔记负担。

---

## ✨ 核心功能

### 🎤 实时录音转写
- 基于**阿里云 Paraformer 实时语音识别**模型，边录边转，低延迟呈现会议内容
- 支持中英文混合识别，自动添加标点符号
- 录音过程中可实时查看转写文本，支持暂停/继续

### 🤖 AI 智能分析
- 录音结束后，一键调用**阿里云百炼·千问大模型**对会议内容进行深度分析
- 自动生成结构化会议摘要（支持 Markdown 精美渲染）
- 智能提取会议待办事项（负责人、截止日期、任务描述）

### 📂 会议管理
- 按时间倒序展示所有会议记录，支持状态筛选（进行中/已完成/已分析）
- 完整会议详情：转写文本、AI 摘要、待办事项三大板块 Tab 切换查看
- 支持按关键词搜索历史会议

### 🔊 录音播放
- 会议详情页内置音频播放器，支持播放/暂停/拖动进度
- 播放进度实时显示，可反复回听关键片段

### 📤 文档导出
- 支持将会议记录导出为 **Word (.docx)** 文档
- 支持导出为 **PDF** 格式，适合存档和分享

### 🎨 现代化 UI
- 遵循 **Material Design 3** 设计规范
- 支持深色/浅色主题自适应
- 流畅动画与优雅交互

---

## 🏗️ 技术架构

```
会记 MeetingMind
├── UI 层          Jetpack Compose + Material Design 3 + Hilt Navigation
├── ViewModel 层   MVVM + StateFlow + Kotlin Coroutines
├── Domain 层      Repository 模式 + 领域模型
├── Data 层        Room 数据库 + Retrofit + WebSocket
├── Service 层     前台服务（录音保活）+ WebSocket 长连接
└── AI 服务        阿里云 DashScope（Paraformer + Qwen）
```

| 技术 | 说明 |
|------|------|
| **Kotlin 1.9.22** | 主要开发语言 |
| **Jetpack Compose** | 声明式 UI 框架 |
| **Hilt** | 依赖注入 |
| **Room** | 本地数据库持久化 |
| **OkHttp WebSocket** | 实时语音流传输 |
| **Retrofit** | REST API 调用（AI 分析） |
| **DashScope API** | 阿里云语音识别 + 大模型服务 |
| **Apache POI** | Word 文档导出 |
| **iText** | PDF 文档导出 |

---

## 🚀 快速开始

### 环境要求

| 项目 | 要求 |
|------|------|
| Android Studio | Hedgehog (2023.1.1) 或以上 |
| JDK | 17 |
| Android SDK | minSdk 26 / targetSdk 34 |
| Gradle | 8.x（Android Studio 自带） |

### 配置 API Key

在 `app/build.gradle.kts` 中找到以下行，替换为你自己的阿里云 DashScope API Key：

```kotlin
buildConfigField("String", "DASHSCOPE_API_KEY", "\"sk-your-api-key-here\"")
```

> **获取 Key：** 登录 [阿里云 DashScope 控制台](https://dashscope.console.aliyun.com/) 创建并复制 API Key。

### 编译运行

```bash
# 克隆项目后，在 Android Studio 中打开根目录
# 等待 Gradle Sync 完成，点击 ▶ Run 或执行：
./gradlew assembleDebug
```

Debug APK 输出路径：`app/build/outputs/apk/debug/app-debug.apk`

---

## 📱 使用指南

### 第一步：创建会议

1. 打开应用，进入首页
2. 点击右上角 **「+」** 按钮
3. 输入会议标题（如"产品需求评审会"），点击**创建**

### 第二步：开始录音

1. 进入会议详情页，点击 **「开始录音」** 按钮
2. 首次使用会弹出**麦克风权限**申请，请点击"允许"
3. 录音开始后，屏幕底部会实时显示转写文字
4. 可随时点击**暂停**或**停止**录音

### 第三步：AI 分析

1. 录音停止后，转写文本自动保存
2. 切换到 **「AI 分析」** Tab，点击 **「生成智能摘要」**
3. AI 将自动分析会议内容，生成：
   - 📝 **会议摘要**：核心议题、结论、关键决策
   - ✅ **待办事项**：自动提取任务、负责人、截止日期

### 第四步：查看与导出

1. 切换到 **「转写记录」** Tab 查看完整对话记录
2. 切换到 **「待办事项」** Tab 查看 AI 提取的任务列表
3. 点击顶部工具栏的**分享**按钮，导出 Word / PDF 文档

---

## 📁 项目结构

```
app/src/main/java/com/meetingmind/app/
├── MainActivity.kt          # 入口 Activity
├── MeetingMindApp.kt        # Hilt Application
├── data/
│   ├── local/               # Room 数据库、Entity、DAO
│   ├── remote/              # WebSocket 客户端、AI API 接口
│   └── repository/          # Repository 实现
├── di/                      # Hilt 依赖注入模块
├── domain/
│   ├── model/               # 领域模型（Meeting、TranscriptSegment 等）
│   └── repository/          # Repository 接口
├── service/
│   └── RecordingService.kt  # 前台录音服务
├── ui/
│   ├── home/                # 首页（会议列表）
│   ├── meeting/             # 会议详情页（含播放、转写、摘要 Tab）
│   ├── recording/           # 录音中实时转写界面
│   ├── search/              # 会议搜索
│   ├── settings/            # 设置页
│   ├── components/          # 通用 UI 组件（Markdown 渲染器等）
│   ├── navigation/          # Navigation 路由配置
│   └── theme/               # Material3 主题配色
└── util/                    # 工具类（音频、时间、文件等）
```

---

## ⚙️ 打包发布

### Debug APK（立即可用）
```
Android Studio → Build → Build Bundle(s) / APK(s) → Build APK(s)
```

### Release APK（用于分发）
1. 生成签名密钥：
   ```bash
   keytool -genkey -v -keystore app/meetingmind.jks -keyalg RSA -keysize 2048 -validity 10000 -alias meetingmind
   ```
2. 在 `app/build.gradle.kts` 的 `signingConfigs` 中填入密钥密码
3. 取消注释 `release` 中的 `signingConfig` 行
4. 执行：
   ```bash
   ./gradlew assembleRelease
   ```

---

## 🔒 权限说明

| 权限 | 用途 |
|------|------|
| `RECORD_AUDIO` | 录制会议音频，进行语音识别 |
| `POST_NOTIFICATIONS`（Android 13+） | 前台服务录音状态通知 |
| `FOREGROUND_SERVICE` | 后台保活录音服务 |
| `WAKE_LOCK` | 防止长时间录音时 CPU 休眠 |
| `INTERNET` | 连接阿里云语音识别服务 |

---

## 📝 注意事项

- 录音转写功能需要**稳定网络连接**（需访问阿里云 DashScope 服务）
- 单次录音时长建议不超过 **2 小时**，过长可能影响识别稳定性
- AI 摘要生成依赖大模型 API，响应时间视网络情况（通常 10~30 秒）
- 录音文件以 WAV 格式本地保存，卸载应用会随之删除

---

## 📄 开源协议

本项目仅供学习参考使用。
