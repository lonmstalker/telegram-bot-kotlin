package io.lonmstalker.bot.helper

import com.ibm.icu.text.Transliterator
import org.springframework.stereotype.Component

@Component
class TransliteratorHelper {
    private val ciryllicTransliterator = Transliterator.getInstance(CYRILLIC_TO_LATIN)
    private val latinTransliterator = Transliterator.getInstance(LATIN_TO_CYRILLIC)

    fun transliterateCiryllic(arg: String): String = ciryllicTransliterator.transliterate(arg)

    fun transliterateLatin(arg: String): String = latinTransliterator.transliterate(arg)

    companion object {
        private const val CYRILLIC_TO_LATIN = "Cyrillic-Latin"
        private const val LATIN_TO_CYRILLIC = "Latin-Cyrillic"
    }
}