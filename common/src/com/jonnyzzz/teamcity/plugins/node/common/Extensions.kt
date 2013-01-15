package com.jonnyzzz.teamcity.plugins.node.common

import java.io.File
import org.apache.log4j.Logger
import java.io.IOException


/**
 * Created by Eugene Petrenko (eugene.petrenko@gmail.com)
 * Date: 12.01.13 0:41
 */

fun String?.isEmptyOrSpaces() : Boolean = com.intellij.openapi.util.text.StringUtil.isEmptyOrSpaces(this)

fun String.splitHonorQuotes() : List<String> = jetbrains.buildServer.util.StringUtil.splitHonorQuotes(this).filterNotNull()

fun Collection<String>.join(sep : String) : String = jetbrains.buildServer.util.StringUtil.join(sep, this)!!
fun Array<String>.join(sep : String) : String = jetbrains.buildServer.util.StringUtil.join(sep, this)!!

fun String?.fetchArguments() : Collection<String> {
  if (this == null || this.isEmptyOrSpaces()) return listOf<String>()

  return this
          .split("[\\r\\n]+")
          .map { it.trim() }
          .filter { !it.isEmptyOrSpaces() }
          .flatMap{ it.splitHonorQuotes() }
}


fun File.resolve(relativePath : String) : File = jetbrains.buildServer.util.FileUtil.resolvePath(this, relativePath)

data class TempFileName(val prefix : String, val suffix : String)
fun File.tempFile(details : TempFileName) : File = com.intellij.openapi.util.io.FileUtil.createTempFile(this, details.prefix, details.suffix, true) ?: throw IOException("Failed to create temp file under ${this}")

fun File.smartDelete() = com.intellij.openapi.util.io.FileUtil.delete(this)

//we define this category to have plugin logging without logger configs patching
inline fun log4j<T>(clazz : Class<T>) : Logger = Logger.getLogger("jetbrains.buildServer.${clazz.getName()}")!!
