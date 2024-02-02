package com.modernlibrary.generativecontentservice.model.enums


enum class ComponentType {
    TITLE, BODY, SUMMARY, PHOTO;

    companion object {
        fun findValueFromString(type: String): ComponentType? {
            return ComponentType.values().find { it.name == type }
        }
    }
}