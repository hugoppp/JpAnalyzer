import PartOfSpeech.ignore
import com.worksap.nlp.sudachi.*
import com.worksap.nlp.sudachi.Tokenizer.SplitMode
import java.io.File

class Occurrence(val line: String, val morph: Morpheme)

class Analyzer private constructor(private val dictionary: Dictionary) {
    private val tokenizer: Tokenizer = dictionary.create()

    constructor(resourceDirectory: String, settingsPath: String) : this(
        DictionaryFactory().create(resourceDirectory, File(settingsPath).readText())
    )

    fun findWordOccurrences(mode: SplitMode?, input: String): Map<String, ArrayList<Occurrence>> {

        val occurrences = HashMap<String, ArrayList<Occurrence>>()

        for (line in input.lines()) for (sentence in tokenizer.tokenizeSentences(mode, line)) {
            for (morpheme in sentence.filter { !ignore(it.partOfSpeech()[0]) }) {
                occurrences.getOrPut(morpheme.dictionaryForm()) { ArrayList() }
                    .add(Occurrence(line, morpheme))
            }
        }
        return occurrences
    }


}
