import PartOfSpeech.ignore
import com.worksap.nlp.sudachi.*
import com.worksap.nlp.sudachi.Tokenizer.SplitMode
import java.io.File


fun main(args: Array<String>) {

    val resourcesDirectory = """C:\Users\hugop\Sync\sudachi-0.5.3\"""
    val settings = File("""C:\Users\hugop\Sync\sudachi-0.5.3\sudachi_fulldict.json""").readText()
    val input = File("""C:\Users\hugop\Sync\sudachi-0.5.3\input""").readText()

    val dict = DictionaryFactory().create(resourcesDirectory, settings, false)
    val tokenizer = dict.create()

    val count = findWordOccurrences(
        tokenizer, SplitMode.C, input
    )

    val mostCommon = count.entries
        .sortedByDescending { occurrences -> occurrences.value.size }
        .take(50)
        .toList()

    val string =
        mostCommon.joinToString(System.lineSeparator()) { (word, occurrences) ->
            "${occurrences.size} $word " +
                    "(${
                        occurrences
                            .groupByPartOfSpeech()
                            .entries.sortedByDescending { it.value.size }
                            .joinToString { "${it.value.size}x${it.key}" }
                    })"
        }

    println(string)
}

class Occurrence(val line: String, val morph: Morpheme)


fun findWordOccurrences(tokenizer: Tokenizer, mode: SplitMode?, input: String): Map<String, ArrayList<Occurrence>> {

    val occurrences = HashMap<String, ArrayList<Occurrence>>()

    for (line in input.lines()) for (sentence in tokenizer.tokenizeSentences(mode, line)) {
        for (morpheme in sentence.filter { !ignore(it.partOfSpeech()[0]) }) {
            occurrences.getOrPut(morpheme.dictionaryForm()) { ArrayList() }
                .add(Occurrence(line, morpheme))
        }
    }
    return occurrences
}

fun List<Occurrence>.groupByPartOfSpeech(): Map<List<String>, List<Occurrence>> {
    return this.groupBy { it.morph.partOfSpeech() }

}