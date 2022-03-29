object PartOfSpeech {

    fun ignore(partOfSpeech: String): Boolean {
        return ignoreSet.contains(partOfSpeech)
    }

    private val ignoreSet = setOf(
        "接尾辞",
        "連体詞",
        "空白",
        "接続詞",
        "補助記号",
        "助動詞",
        "形容詞",
        "助詞",
        "感動詞",
        "接頭辞"
        //do not ignore the following
        // "動詞", //verb
        // "副詞", //adverb
        // "代名詞", //pronoun
        //"形状詞", //adjective
        //"名詞", //noun
    )
}