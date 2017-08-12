package com.github.bjansen.intellij.pebble.lang

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class PebbleFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(PebbleFileType.INSTANCE, "peb")
        consumer.consume(PebbleFileType.INSTANCE, "html.peb")
    }
}
