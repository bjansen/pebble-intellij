package com.github.bjansen.intellij.pebble

import com.intellij.CommonBundle
import com.intellij.reference.SoftReference
import org.jetbrains.annotations.PropertyKey
import java.lang.ref.Reference
import java.util.*

/**
 * I18N messages.
 */
object PebbleBundle {
    private var ourBundle: Reference<ResourceBundle>? = null
    private const val bundleName = "messages.PebbleBundle"

    fun message(@PropertyKey(resourceBundle = bundleName) key: String, vararg params: Any): String {
        return CommonBundle.message(bundle, key, *params)
    }

    private val bundle: ResourceBundle get() {
        var resourceBundle = SoftReference.dereference(ourBundle)

        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle(bundleName)
            ourBundle = java.lang.ref.SoftReference<ResourceBundle>(resourceBundle)
        }

        return resourceBundle!!
    }
}
