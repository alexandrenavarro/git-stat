#!/usr/bin/env kscript

import java.io.File
import java.util.concurrent.TimeUnit

println("Hello from Kotlin!")
println("git status".runCommand())

fun String.runCommand(
        workingDir: File = File("."),
        timeoutAmount: Long = 60,
        timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String? = try {
    ProcessBuilder(split("\\s".toRegex()))
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().apply { waitFor(timeoutAmount, timeoutUnit) }
            .inputStream.bufferedReader().readText()
} catch (e: java.io.IOException) {
    e.printStackTrace()
    null
}

// cd project1
//git log --pretty=format:"project1,%an%x2c%ad%x2c"  --date=short --stat --stat-count=-1|sed "s/ file/,file/"|sed "s/ insertion/,insertion/"|sed "s/ deletion/,deletion/" |sed ':a;N;$!ba;s/)\n\n/)\n/g' |sed ':a;N;$!ba;s/\n \.\.\.\n/ /g' >> stats.csv