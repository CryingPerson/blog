package com.blog.domain;

import lombok.Generated;
import lombok.Getter;

@Getter
public class PostEditor {
    private final String title;
    private final String content;

    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;

    }

    @Generated
    public static PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    @Generated
    public String getTitle() {
        return this.title;
    }

    @Generated
    public String getContent() {
        return this.content;
    }

    @Generated
    public static class PostEditorBuilder {
        @Generated
        private String title;
        @Generated
        private String content;

        @Generated
        PostEditorBuilder() {
        }

        @Generated
        public PostEditorBuilder title(final String title) {
            if(title != null) {
                this.title = title;
            }
            return this;
        }

        @Generated
        public PostEditorBuilder content(final String content) {
            if(content != null) {
                this.content = content;
            }
            return this;

        }

        @Generated
        public PostEditor build() {
            return new PostEditor(this.title, this.content);
        }

        @Generated
        public String toString() {
            return "PostEditor.PostEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}

