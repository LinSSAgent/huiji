package com.meetingmind.app.ui.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0005H\u0003\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0007H\u0003\u001a\u0010\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\tH\u0003\u001a\b\u0010\n\u001a\u00020\u0001H\u0003\u001a\u001a\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0011H\u0003\u001a\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0013H\u0003\u001a\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0015H\u0002\u001a\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\rH\u0003\u00a8\u0006\u001b"}, d2 = {"BlockQuoteBlock", "", "block", "Lcom/meetingmind/app/ui/components/MarkdownBlock$BlockQuote;", "BulletItemBlock", "Lcom/meetingmind/app/ui/components/MarkdownBlock$BulletItem;", "CodeBlockBlock", "Lcom/meetingmind/app/ui/components/MarkdownBlock$CodeBlock;", "HeadingBlock", "Lcom/meetingmind/app/ui/components/MarkdownBlock$Heading;", "HorizontalRuleBlock", "MarkdownContent", "markdown", "", "modifier", "Landroidx/compose/ui/Modifier;", "NumberedItemBlock", "Lcom/meetingmind/app/ui/components/MarkdownBlock$NumberedItem;", "ParagraphBlock", "Lcom/meetingmind/app/ui/components/MarkdownBlock$Paragraph;", "parseBlocks", "", "Lcom/meetingmind/app/ui/components/MarkdownBlock;", "lines", "parseInline", "Landroidx/compose/ui/text/AnnotatedString;", "text", "app_debug"})
public final class MarkdownContentKt {
    
    /**
     * Custom Markdown renderer for Compose that properly styles content
     * with Material3 theming. Supports: headings (h1-h6), bold, italic,
     * inline code, bullet lists, numbered lists, and code blocks.
     */
    @androidx.compose.runtime.Composable()
    public static final void MarkdownContent(@org.jetbrains.annotations.NotNull()
    java.lang.String markdown, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.util.List<com.meetingmind.app.ui.components.MarkdownBlock> parseBlocks(java.util.List<java.lang.String> lines) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final androidx.compose.ui.text.AnnotatedString parseInline(java.lang.String text) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HeadingBlock(com.meetingmind.app.ui.components.MarkdownBlock.Heading block) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ParagraphBlock(com.meetingmind.app.ui.components.MarkdownBlock.Paragraph block) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void BulletItemBlock(com.meetingmind.app.ui.components.MarkdownBlock.BulletItem block) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NumberedItemBlock(com.meetingmind.app.ui.components.MarkdownBlock.NumberedItem block) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CodeBlockBlock(com.meetingmind.app.ui.components.MarkdownBlock.CodeBlock block) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void BlockQuoteBlock(com.meetingmind.app.ui.components.MarkdownBlock.BlockQuote block) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HorizontalRuleBlock() {
    }
}