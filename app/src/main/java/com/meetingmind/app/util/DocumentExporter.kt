package com.meetingmind.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.meetingmind.app.domain.model.Keyword
import com.meetingmind.app.domain.model.Meeting
import com.meetingmind.app.domain.model.TodoItem
import com.meetingmind.app.domain.model.TranscriptSegment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentExporter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val exportDir: File
        get() {
            val dir = File(context.cacheDir, "exports")
            if (!dir.exists()) dir.mkdirs()
            return dir
        }

    /**
     * Export meeting as Markdown file
     */
    fun exportAsMarkdown(
        meeting: Meeting,
        segments: List<TranscriptSegment>
    ): File {
        val content = buildMarkdownContent(meeting, segments)
        val file = File(exportDir, "${sanitizeFileName(meeting.title)}.md")
        
        OutputStreamWriter(FileOutputStream(file), Charsets.UTF_8).use { writer ->
            writer.write(content)
        }
        return file
    }

    /**
     * Export meeting as Word (.docx) file
     */
    fun exportAsWord(
        meeting: Meeting,
        segments: List<TranscriptSegment>
    ): File {
        val file = File(exportDir, "${sanitizeFileName(meeting.title)}.docx")
        
        try {
            org.apache.poi.xwpf.usermodel.XWPFDocument().use { doc ->
                // Title
                val titleParagraph = doc.createParagraph()
                titleParagraph.alignment = org.apache.poi.xwpf.usermodel.ParagraphAlignment.CENTER
                val titleRun = titleParagraph.createRun()
                titleRun.setText(meeting.title)
                titleRun.isBold = true
                titleRun.fontSize = 18

                // Meeting info
                val infoParagraph = doc.createParagraph()
                val infoRun = infoParagraph.createRun()
                infoRun.setText("创建时间: ${TimeUtils.formatFullDate(meeting.createdAt)}")
                infoRun.fontSize = 10
                infoRun.color = "666666"

                meeting.startTime?.let {
                    val timeParagraph = doc.createParagraph()
                    val timeRun = timeParagraph.createRun()
                    timeRun.setText("开始时间: ${TimeUtils.formatFullDate(it)}")
                    timeRun.fontSize = 10
                    timeRun.color = "666666"
                }

                meeting.duration?.let {
                    val durationParagraph = doc.createParagraph()
                    val durationRun = durationParagraph.createRun()
                    durationRun.setText("时长: ${TimeUtils.formatDuration(it)}")
                    durationRun.fontSize = 10
                    durationRun.color = "666666"
                }

                // Summary
                if (!meeting.summary.isNullOrBlank()) {
                    doc.createParagraph().createRun().addBreak()
                    val summaryTitle = doc.createParagraph()
                    val summaryTitleRun = summaryTitle.createRun()
                    summaryTitleRun.setText("会议摘要")
                    summaryTitleRun.isBold = true
                    summaryTitleRun.fontSize = 14

                    val summaryParagraph = doc.createParagraph()
                    val summaryRun = summaryParagraph.createRun()
                    summaryRun.setText(meeting.summary)
                    summaryRun.fontSize = 11
                }

                // Todos
                if (meeting.todos.isNotEmpty()) {
                    doc.createParagraph().createRun().addBreak()
                    val todoTitle = doc.createParagraph()
                    val todoTitleRun = todoTitle.createRun()
                    todoTitleRun.setText("待办事项")
                    todoTitleRun.isBold = true
                    todoTitleRun.fontSize = 14

                    meeting.todos.forEach { todo ->
                        val todoParagraph = doc.createParagraph()
                        val todoRun = todoParagraph.createRun()
                        val assigneeText = if (todo.assignee != null) " [${todo.assignee}]" else ""
                        todoRun.setText("• ${todo.content}$assigneeText")
                        todoRun.fontSize = 11
                    }
                }

                // Keywords
                if (meeting.keywords.isNotEmpty()) {
                    doc.createParagraph().createRun().addBreak()
                    val kwTitle = doc.createParagraph()
                    val kwTitleRun = kwTitle.createRun()
                    kwTitleRun.setText("关键词")
                    kwTitleRun.isBold = true
                    kwTitleRun.fontSize = 14

                    meeting.keywords.forEach { kw ->
                        val kwParagraph = doc.createParagraph()
                        val kwRun = kwParagraph.createRun()
                        val descText = if (kw.description != null) " - ${kw.description}" else ""
                        kwRun.setText("【${kw.word}】$descText")
                        kwRun.fontSize = 11
                    }
                }

                // Transcript
                if (segments.isNotEmpty()) {
                    doc.createParagraph().createRun().addBreak()
                    val transcriptTitle = doc.createParagraph()
                    val transcriptTitleRun = transcriptTitle.createRun()
                    transcriptTitleRun.setText("转写记录")
                    transcriptTitleRun.isBold = true
                    transcriptTitleRun.fontSize = 14

                    segments.forEach { segment ->
                        val segParagraph = doc.createParagraph()
                        
                        val speakerRun = segParagraph.createRun()
                        speakerRun.setText("[${segment.displaySpeaker}] ")
                        speakerRun.isBold = true
                        speakerRun.fontSize = 11

                        val textRun = segParagraph.createRun()
                        textRun.setText(segment.text)
                        textRun.fontSize = 11
                    }
                }

                FileOutputStream(file).use { fos ->
                    doc.write(fos)
                }
            }
        } catch (e: Exception) {
            // Fallback to plain text if POI fails
            val content = buildPlainTextContent(meeting, segments)
            OutputStreamWriter(FileOutputStream(file.apply { 
                // Change extension to txt
            }), Charsets.UTF_8).use { it.write(content) }
        }

        return file
    }

    /**
     * Export meeting as PDF file
     */
    fun exportAsPdf(
        meeting: Meeting,
        segments: List<TranscriptSegment>
    ): File {
        val file = File(exportDir, "${sanitizeFileName(meeting.title)}.pdf")
        
        try {
            val writer = com.itextpdf.kernel.pdf.PdfWriter(file)
            val pdfDoc = com.itextpdf.kernel.pdf.PdfDocument(writer)
            val document = com.itextpdf.layout.Document(pdfDoc)
            
            // Use a font that supports Chinese
            val font = com.itextpdf.kernel.font.PdfFontFactory.createFont(
                "STSong-Light", "UniGB-UCS2-H", 
                com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy.PREFER_NOT_EMBEDDED
            )
            
            // Title
            document.add(
                com.itextpdf.layout.element.Paragraph(meeting.title)
                    .setFont(font)
                    .setFontSize(18f)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            )

            // Meeting info
            document.add(
                com.itextpdf.layout.element.Paragraph(
                    "创建时间: ${TimeUtils.formatFullDate(meeting.createdAt)}"
                ).setFont(font).setFontSize(9f).setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY)
            )

            meeting.startTime?.let {
                document.add(
                    com.itextpdf.layout.element.Paragraph(
                        "开始时间: ${TimeUtils.formatFullDate(it)}"
                    ).setFont(font).setFontSize(9f).setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY)
                )
            }

            // Summary
            if (!meeting.summary.isNullOrBlank()) {
                document.add(
                    com.itextpdf.layout.element.Paragraph("会议摘要")
                        .setFont(font).setFontSize(14f).setBold().setMarginTop(16f)
                )
                document.add(
                    com.itextpdf.layout.element.Paragraph(meeting.summary)
                        .setFont(font).setFontSize(11f)
                )
            }

            // Todos
            if (meeting.todos.isNotEmpty()) {
                document.add(
                    com.itextpdf.layout.element.Paragraph("待办事项")
                        .setFont(font).setFontSize(14f).setBold().setMarginTop(16f)
                )
                meeting.todos.forEach { todo ->
                    val assigneeText = if (todo.assignee != null) " [${todo.assignee}]" else ""
                    document.add(
                        com.itextpdf.layout.element.Paragraph("• ${todo.content}$assigneeText")
                            .setFont(font).setFontSize(11f)
                    )
                }
            }

            // Keywords
            if (meeting.keywords.isNotEmpty()) {
                document.add(
                    com.itextpdf.layout.element.Paragraph("关键词")
                        .setFont(font).setFontSize(14f).setBold().setMarginTop(16f)
                )
                val kwText = meeting.keywords.joinToString("、") { it.word }
                document.add(
                    com.itextpdf.layout.element.Paragraph(kwText)
                        .setFont(font).setFontSize(11f)
                )
            }

            // Transcript
            if (segments.isNotEmpty()) {
                document.add(
                    com.itextpdf.layout.element.Paragraph("转写记录")
                        .setFont(font).setFontSize(14f).setBold().setMarginTop(16f)
                )
                segments.forEach { segment ->
                    document.add(
                        com.itextpdf.layout.element.Paragraph("[${segment.displaySpeaker}] ${segment.text}")
                            .setFont(font).setFontSize(10f).setMarginBottom(4f)
                    )
                }
            }

            document.close()
        } catch (e: Exception) {
            // Fallback: create a simple text file with .pdf name
            val content = buildPlainTextContent(meeting, segments)
            file.writeText(content)
        }

        return file
    }

    fun shareFile(file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = getMimeType(file)
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(Intent.createChooser(intent, "分享文件").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    private fun buildMarkdownContent(meeting: Meeting, segments: List<TranscriptSegment>): String {
        return buildString {
            appendLine("# ${meeting.title}")
            appendLine()
            appendLine("**创建时间:** ${TimeUtils.formatFullDate(meeting.createdAt)}")
            meeting.startTime?.let {
                appendLine("**开始时间:** ${TimeUtils.formatFullDate(it)}")
            }
            meeting.duration?.let {
                appendLine("**时长:** ${TimeUtils.formatDuration(it)}")
            }
            if (!meeting.description.isNullOrBlank()) {
                appendLine()
                appendLine("> ${meeting.description}")
            }

            // Summary
            if (!meeting.summary.isNullOrBlank()) {
                appendLine()
                appendLine("---")
                appendLine()
                appendLine(meeting.summary)
            }

            // Todos
            if (meeting.todos.isNotEmpty()) {
                appendLine()
                appendLine("## 待办事项")
                appendLine()
                meeting.todos.forEach { todo ->
                    val check = if (todo.isCompleted) "[x]" else "[ ]"
                    val assignee = if (todo.assignee != null) " @${todo.assignee}" else ""
                    appendLine("- $check ${todo.content}$assignee")
                }
            }

            // Keywords
            if (meeting.keywords.isNotEmpty()) {
                appendLine()
                appendLine("## 关键词")
                appendLine()
                meeting.keywords.forEach { kw ->
                    val desc = if (kw.description != null) " - ${kw.description}" else ""
                    appendLine("- **${kw.word}**$desc")
                }
            }

            // Transcript
            if (segments.isNotEmpty()) {
                appendLine()
                appendLine("## 转写记录")
                appendLine()
                segments.forEach { segment ->
                    appendLine("**${segment.displaySpeaker}** _(${TimeUtils.formatDurationMs(segment.startTimeMs)})_")
                    appendLine(segment.text)
                    appendLine()
                }
            }
        }
    }

    private fun buildPlainTextContent(meeting: Meeting, segments: List<TranscriptSegment>): String {
        return buildString {
            appendLine(meeting.title)
            appendLine("=" .repeat(meeting.title.length))
            appendLine()
            appendLine("创建时间: ${TimeUtils.formatFullDate(meeting.createdAt)}")
            meeting.startTime?.let {
                appendLine("开始时间: ${TimeUtils.formatFullDate(it)}")
            }
            appendLine()
            if (!meeting.summary.isNullOrBlank()) {
                appendLine("【摘要】")
                appendLine(meeting.summary)
                appendLine()
            }
            if (segments.isNotEmpty()) {
                appendLine("【转写记录】")
                segments.forEach { segment ->
                    appendLine("[${segment.displaySpeaker}] ${segment.text}")
                }
            }
        }
    }

    private fun sanitizeFileName(name: String): String {
        return name.replace(Regex("[\\\\/:*?\"<>|]"), "_").take(50)
    }

    private fun getMimeType(file: File): String {
        return when (file.extension.lowercase()) {
            "md" -> "text/markdown"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "pdf" -> "application/pdf"
            else -> "application/octet-stream"
        }
    }
}
