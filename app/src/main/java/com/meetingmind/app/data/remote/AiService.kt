package com.meetingmind.app.data.remote

import android.util.Log
import com.google.gson.Gson
import com.meetingmind.app.domain.model.Keyword
import com.meetingmind.app.domain.model.TodoItem
import com.meetingmind.app.domain.model.TranscriptSegment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiService @Inject constructor(
    private val qwenApiService: QwenApiService,
    private val gson: Gson
) {
    companion object {
        private const val TAG = "AiService"
    }

    /**
     * Generate meeting summary from transcript segments
     */
    suspend fun generateSummary(segments: List<TranscriptSegment>): String? {
        val transcriptText = formatTranscript(segments)
        if (transcriptText.isBlank()) return null

        val prompt = """你是一个专业的会议纪要助手。请根据以下会议转写内容，生成一份结构化的会议摘要。

要求：
1. 使用Markdown格式输出
2. 包含以下部分：
   - ## 会议概要（2-3句话概括会议核心内容）
   - ## 主要议题（列出讨论的主要议题）
   - ## 关键决定（列出会议中做出的重要决定）
   - ## 后续行动（如有明确的后续行动安排）
3. 简洁明了，重点突出
4. 保持客观，忠实原文

会议转写内容：
$transcriptText"""

        return callQwen(prompt)
    }

    /**
     * Extract todo items from transcript
     */
    suspend fun extractTodos(segments: List<TranscriptSegment>): List<TodoItem> {
        val transcriptText = formatTranscript(segments)
        if (transcriptText.isBlank()) return emptyList()

        val prompt = """你是一个专业的会议纪要助手。请从以下会议转写内容中提取所有的待办事项和行动计划。

要求：
1. 以JSON数组格式输出
2. 每个待办事项包含：content（内容）、assignee（负责人，如果提到的话）
3. 只提取明确的行动项，不要推测
4. 格式示例：[{"content":"完成项目方案","assignee":"张三"},{"content":"准备下周报告","assignee":null}]

会议转写内容：
$transcriptText

请直接输出JSON数组，不要添加其他文字或markdown代码块标记："""

        val result = callQwen(prompt)
        return parseTodos(result)
    }

    /**
     * Extract keywords from transcript
     */
    suspend fun extractKeywords(segments: List<TranscriptSegment>): List<Keyword> {
        val transcriptText = formatTranscript(segments)
        if (transcriptText.isBlank()) return emptyList()

        val prompt = """你是一个专业的文本分析助手。请从以下会议转写内容中提取关键词。

要求：
1. 提取5-15个核心关键词
2. 以JSON数组格式输出
3. 每个关键词包含：word（关键词）、description（在本次会议中的上下文含义说明）、count（大概出现次数）
4. 关键词应包括：专业术语、项目名称、重要概念、人名等
5. 按重要程度排序
6. 格式示例：[{"word":"AI模型","description":"讨论的核心技术方案","count":5}]

会议转写内容：
$transcriptText

请直接输出JSON数组，不要添加其他文字或markdown代码块标记："""

        val result = callQwen(prompt)
        return parseKeywords(result)
    }

    private fun formatTranscript(segments: List<TranscriptSegment>): String {
        return segments.joinToString("\n") { segment ->
            "${segment.displaySpeaker}: ${segment.text}"
        }
    }

    private suspend fun callQwen(prompt: String): String? {
        return try {
            val request = ChatCompletionRequest(
                messages = listOf(
                    ChatMessage(role = "user", content = prompt)
                )
            )
            val response = qwenApiService.chatCompletion(request)
            response.choices?.firstOrNull()?.message?.content
        } catch (e: Exception) {
            Log.e(TAG, "Qwen API call failed: ${e.message}", e)
            null
        }
    }

    private fun parseTodos(json: String?): List<TodoItem> {
        if (json.isNullOrBlank()) return emptyList()
        return try {
            val cleanJson = json.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()
            val type = object : com.google.gson.reflect.TypeToken<List<TodoItem>>() {}.type
            gson.fromJson(cleanJson, type) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse todos: ${e.message}")
            emptyList()
        }
    }

    private fun parseKeywords(json: String?): List<Keyword> {
        if (json.isNullOrBlank()) return emptyList()
        return try {
            val cleanJson = json.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()
            val type = object : com.google.gson.reflect.TypeToken<List<Keyword>>() {}.type
            gson.fromJson(cleanJson, type) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse keywords: ${e.message}")
            emptyList()
        }
    }
}
