package com.mouthmidi.app

import android.content.Context
import org.json.JSONObject
import java.io.File

class PresetFileManager(
    private val context: Context
) {

    private val presetDir: File
        get() = File(
            context.getExternalFilesDir(null),
            "Presets"
        ).apply { mkdirs() }

    fun savePreset(
        name: String,
        preset: PresetSettings
    ) {

        val json = JSONObject()

        json.put("jawClosedCalibration", preset.jawClosedCalibration)
        json.put("jawOpenCalibration", preset.jawOpenCalibration)

        json.put("minCC", preset.minCC)
        json.put("maxCC", preset.maxCC)

        json.put("midiCC", preset.midiCC)
        json.put("midiChannel", preset.midiChannel)

        json.put("attackSpeed", preset.attackSpeed)
        json.put("releaseSpeed", preset.releaseSpeed)

        json.put("smoothing", preset.smoothing)
        json.put("deadZone", preset.deadZone)

        json.put("invert", preset.invert)
        json.put("holdLastValue", preset.holdLastValue)

        json.put("themeColor", preset.themeColor)

        File(
            presetDir,
            "$name.json"
        ).writeText(json.toString(4))
    }

    fun loadPreset(
        name: String
    ): PresetSettings? {

        val file = File(
            presetDir,
            "$name.json"
        )

        if (!file.exists()) {
            return null
        }

        val json = JSONObject(
            file.readText()
        )

        return PresetSettings(

            jawClosedCalibration =
                json.optDouble(
                    "jawClosedCalibration",
                    0.01
                ).toFloat(),

            jawOpenCalibration =
                json.optDouble(
                    "jawOpenCalibration",
                    0.80
                ).toFloat(),

            minCC =
                json.optInt(
                    "minCC",
                    0
                ),

            maxCC =
                json.optInt(
                    "maxCC",
                    127
                ),

            midiCC =
                json.optInt(
                    "midiCC",
                    1
                ),

            midiChannel =
                json.optInt(
                    "midiChannel",
                    1
                ),

            attackSpeed =
                json.optDouble(
                    "attackSpeed",
                    0.0
                ).toFloat(),

            releaseSpeed =
                json.optDouble(
                    "releaseSpeed",
                    0.0
                ).toFloat(),

            smoothing =
                json.optDouble(
                    "smoothing",
                    0.0
                ).toFloat(),

            deadZone =
                json.optDouble(
                    "deadZone",
                    0.0
                ).toFloat(),

            invert =
                json.optBoolean(
                    "invert",
                    false
                ),

            holdLastValue =
                json.optBoolean(
                    "holdLastValue",
                    true
                ),

            themeColor =
                json.optString(
                    "themeColor",
                    "pink"
                )
        )
    }

    fun deletePreset(
        name: String
    ) {

        File(
            presetDir,
            "$name.json"
        ).delete()
    }

    fun getPresetNames(): List<String> {

        return presetDir
            .listFiles()
            ?.filter {
                it.extension == "json"
            }
            ?.map {
                it.nameWithoutExtension
            }
            ?.sorted()
            ?: emptyList()
    }
}
