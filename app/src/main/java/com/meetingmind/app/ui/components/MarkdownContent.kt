package com.meetingmind.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Custom Markdown renderer for Compose that properly styles content
 * with Material3 theming. Supports: headings (h1-h6), bold, italic,
 * inline code, bullet lists, numbered lists, and code blocks.
 */
@Composable
fun MarkdownContent(
    markdown: String,
    modifier: Modifier = Modifier
) {
    val lines = markdown.lines()
    val blocks = parseBlocks(lines)

    SelectionContainer {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            blocks.forEach { block ->
                when (block) {
                    is MarkdownBlock.Heading -> HeadingBlock(block)
                    is MarkdownBlock.Paragraph -> ParagraphBlock(block)
                    is MarkdownBlock.BulletItem -> BulletItemBlock(block)
                    is MarkdownBlock.NumberedItem -> NumberedItemBlock(block)
                    is MarkdownBlock.CodeBlock -> CodeBlockBlock(block)
                    is MarkdownBlock.BlockQuote -> BlockQuoteBlock(block)
                    is MarkdownBlock.HorizontalRule -> HorizontalRuleBlock()
                    is MarkdownBlock.Empty -> {} // skip
                }
            }
        }
    }
}

// ── Block types ────────────────────────────────────────────────────────────────

private sealed class MarkdownBlock {
    data class Heading(val level: Int, val text: String) : MarkdownBlock()
    data class Paragraph(val text: String) : MarkdownBlock()
    data class BulletItem(val text: String, val indent: Int = 0) : MarkdownBlock()
    data class NumberedItem(val number: String, val text: String) : MarkdownBlock()
    data class CodeBlock(val code: String, val language: String = "") : MarkdownBlock()
    data class BlockQuote(val text: String) : MarkdownBlock()
    object HorizontalRule : MarkdownBlock()
    object Empty : MarkdownBlock()
}

// ── Block parser ───────────────────────────────────────────────────────────────

private fun parseBlocks(lines: List<String>): List<MarkdownBlock> {
    val blocks = mutableListOf<MarkdownBlock>()
    var i = 0

    while (i < lines.size) {
        val line = lines[i]

        // Code block (fenced with ```)
        if (line.trimStart().startsWith("```")) {
            val lang = line.trimStart().removePrefix("```").trim()
            val codeLines = mutableListOf<String>()
            i++
            while (i < lines.size && !lines[i].trimStart().startsWith("```")) {
                codeLines.add(lines[i])
                i++
            }
            blocks.add(MarkdownBlock.CodeBlock(codeLines.joinToString("\n"), lang))
            i++ // skip closing ```
            continue
        }

        // Heading (#, ##, ###, etc.)
        val headingMatch = Regex("^(#{1,6})\\s+(.+)$").matchEntire(line.trim())
        if (headingMatch != null) {
            val level = headingMatch.groupValues[1].length
            val text = headingMatch.groupValues[2]
            blocks.add(MarkdownBlock.Heading(level, text))
            i++
            continue
        }

        // Horizontal rule
        if (Regex("^[-*_]{3,}$").matches(line.trim())) {
            blocks.add(MarkdownBlock.HorizontalRule)
            i++
            continue
        }

        // Block quote
        if (line.trimStart().startsWith("> ")) {
            blocks.add(MarkdownBlock.BlockQuote(line.trimStart().removePrefix("> ")))
            i++
            continue
        }

        // Bullet list item (-, *, +)
        val bulletMatch = Regex("^(\\s*)[*\\-+]\\s+(.+)$").matchEntire(line)
        if (bulletMatch != null) {
            val indent = bulletMatch.groupValues[1].length / 2
            val text = bulletMatch.groupValues[2]
            blocks.add(MarkdownBlock.BulletItem(text, indent))
            i++
            continue
        }

        // Numbered list item (1., 2., etc.)
        val numberedMatch = Regex("^(\\d+)\\.\\s+(.+)$").matchEntire(line.trim())
        if (numberedMatch != null) {
            blocks.add(MarkdownBlock.NumberedItem(numberedMatch.groupValues[1], numberedMatch.groupValues[2]))
            i++
            continue
        }

        // Empty line
        if (line.isBlank()) {
            blocks.add(MarkdownBlock.Empty)
            i++
            continue
        }

        // Regular paragraph (accumulate consecutive non-empty lines)
        val paraLines = mutableListOf(line)
        i++
        while (i < lines.size && lines[i].isNotBlank() &&
            !lines[i].trimStart().startsWith("```") &&
            !Regex("^#{1,6}\\s+").containsMatchIn(lines[i]) &&
            !Regex("^[-*_]{3,}$").matches(lines[i].trim()) &&
            !Regex("^(\\s*)[*\\-+]\\s+").matches(lines[i]) &&
            !Regex("^\\d+\\.\\s+").containsMatchIn(lines[i]) &&
            !lines[i].trimStart().startsWith("> ")
        ) {
            paraLines.add(lines[i])
            i++
        }
        blocks.add(MarkdownBlock.Paragraph(paraLines.joinToString(" ")))
    }

    return blocks
}

// ── Inline styled text parser ──────────────────────────────────────────────────

@Composable
private fun parseInline(text: String): AnnotatedString {
    val primary = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    return buildAnnotatedString {
        var i = 0
        while (i < text.length) {
            // Bold: **text** or __text__
            val boldMatch = Regex("(\\*\\*|__)(.+?)\\1").find(text, i)
            // Italic: *text* or _text_
            val italicMatch = Regex("(?<![*_])(\\*|_)(?![*_])(.+?)(?<![*_])\\1(?![*_])").find(text, i)
            // Inline code: `code`
            val codeMatch = Regex("`([^`]+)`").find(text, i)

            val nextMatch = listOfNotNull(boldMatch, italicMatch, codeMatch)
                .minByOrNull { it.range.first }

            if (nextMatch == null) {
                // No more inline formatting, append rest as plain text
                append(text.substring(i))
                break
            }

            // Append plain text before the match
            if (nextMatch.range.first > i) {
                append(text.substring(i, nextMatch.range.first))
            }

            when {
                nextMatch === boldMatch -> {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = onSurface)) {
                        append(nextMatch.groupValues[2])
                    }
                }
                nextMatch === codeMatch -> {
                    withStyle(SpanStyle(
                        fontFamily = FontFamily.Monospace,
                        background = surfaceVariant,
                        color = primary,
                        fontSize = 13.sp
                    )) {
                        append(" ${nextMatch.groupValues[1]} ")
                    }
                }
                nextMatch === italicMatch -> {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = onSurface)) {
                        append(nextMatch.groupValues[2])
                    }
                }
            }

            i = nextMatch.range.last + 1
        }
    }
}

// ── Block renderers ────────────────────────────────────────────────────────────

@Composable
private fun HeadingBlock(block: MarkdownBlock.Heading) {
    val (style, spacer) = when (block.level) {
        1 -> MaterialTheme.typography.headlineMedium to 12.dp
        2 -> MaterialTheme.typography.headlineSmall to 10.dp
        3 -> MaterialTheme.typography.titleLarge to 8.dp
        4 -> MaterialTheme.typography.titleMedium to 6.dp
        else -> MaterialTheme.typography.titleSmall to 4.dp
    }
    Spacer(modifier = Modifier.height(spacer))
    Text(
        text = parseInline(block.text),
        style = style,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ParagraphBlock(block: MarkdownBlock.Paragraph) {
    Text(
        text = parseInline(block.text),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        lineHeight = 24.sp
    )
}

@Composable
private fun BulletItemBlock(block: MarkdownBlock.BulletItem) {
    Row(
        modifier = Modifier.padding(start = (block.indent * 16).dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(16.dp)
        )
        Text(
            text = parseInline(block.text),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NumberedItemBlock(block: MarkdownBlock.NumberedItem) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "${block.number}.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(24.dp)
        )
        Text(
            text = parseInline(block.text),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CodeBlockBlock(block: MarkdownBlock.CodeBlock) {
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(surfaceVariant, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        if (block.language.isNotBlank()) {
            Text(
                text = block.language,
                style = MaterialTheme.typography.labelSmall,
                color = onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Text(
            text = block.code,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace,
                lineHeight = 20.sp
            ),
            color = onSurfaceVariant
        )
    }
}

@Composable
private fun BlockQuoteBlock(block: MarkdownBlock.BlockQuote) {
    val primary = MaterialTheme.colorScheme.primary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(20.dp)
                .background(primary, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = parseInline(block.text),
            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HorizontalRuleBlock() {
    Spacer(modifier = Modifier.height(4.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
    Spacer(modifier = Modifier.height(4.dp))
}
