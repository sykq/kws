package io.psc.kws.utils

import java.util.function.Function;

fun trimToLowerCase(): Function<String?, String> = Function {
    it?.trim()?.toLowerCase() ?: ""
}

fun trimToUpperCase(): Function<String?, String> = Function {
    it?.trim()?.toUpperCase() ?: ""
}