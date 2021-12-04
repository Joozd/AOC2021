package common.extensions

fun String.words() = this.split(" ").filter{ it.isNotBlank()}