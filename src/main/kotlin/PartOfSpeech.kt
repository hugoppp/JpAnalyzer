object PartOfSpeech {

    fun ignore(partOfSpeech: String): Boolean {
        return ignoreSet.contains(partOfSpeech)
    }

    private val ignoreSet = setOf(
        "接尾辞",     //suffix
        "連体詞",     //adnominal adjective
        "空白",      //blank space
        "接続詞",     //conjunction
        "補助記号",    // supplementary symbol (number, punctuation, etc.)
        "助動詞",     //auxiliary verb
        "助詞",      //particle
        "感動詞",     //interjection
        "接頭辞",     //prefix
        //do not ignore the following
        // "動詞", //verb
        // "副詞", //adverb
        // "代名詞", //pronoun
        //"形状詞", //na-adjective
        //"形容詞", //i-adjective
        //"名詞", //noun
    )
}