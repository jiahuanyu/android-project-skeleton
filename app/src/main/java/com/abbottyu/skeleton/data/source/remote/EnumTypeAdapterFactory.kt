package com.abbottyu.skeleton.data.source.remote

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * prop 必须为 Int 类型
 *
 * enum class EnumC(val prop: Int, xxxx) {
 * }
 *
 */
class EnumTypeAdapterFactory(private val prop: String = "code") : TypeAdapterFactory {

    override fun <T : Any> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        if (gson == null || type == null) {
            return null
        }

        // 不是枚举类型，默认交给 Gson 处理
        if (!type.rawType.isEnum) {
            return null
        }

        return try {
            createTypeAdapter(type)
        } catch (e: Exception) {
            null
        }
    }

    private fun <T : Any> createTypeAdapter(type: TypeToken<T>): TypeAdapter<T>? {
        // 存取枚举（key）的 code 数值
        val enumCodeValueMap = mutableMapOf<T, Int>()

        var hasSerializedName = false
        var hasError = false

        type.rawType.enumConstants?.filterNotNull()?.forEach { enumConstant ->
            val enum = enumConstant as T
            // 转成枚举类型
            enum as Enum<*>

            // 如果标记了 serializeName 的注解，则直接使用默认 Gson 解析
            val serializedName =
                enum.javaClass.getField(enum.name).getAnnotation(SerializedName::class.java)

            if (serializedName != null) {
                hasSerializedName = true
                return@forEach
            }

            try {
                val codeField = enum.javaClass.getDeclaredField(prop)
                codeField.isAccessible = true
                enumCodeValueMap[enum] = codeField.getInt(enum)
            } catch (e: Exception) {
                hasError = true
            }
        }

        // 出错则直接走 Gson 默认解析
        if (hasSerializedName || hasError) {
            return null
        }

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter, value: T?) {
                if (value == null) {
                    out.nullValue()
                } else {
                    val codeValue = enumCodeValueMap[value]
                    if (codeValue == null) {
                        out.nullValue()
                    } else {
                        out.value(codeValue)
                    }
                }
            }

            override fun read(reader: JsonReader): T? {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return null
                } else {
                    val source = reader.nextString()
                    var enum: T? = null
                    enumCodeValueMap.forEach { entry ->
                        if (entry.value.toString() == source) {
                            enum = entry.key
                            return@forEach
                        }
                    }
                    return enum
                }
            }
        }
    }
}
