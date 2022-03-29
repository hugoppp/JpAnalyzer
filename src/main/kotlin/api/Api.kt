package api

import Analyzer
import com.worksap.nlp.sudachi.Tokenizer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Api

@RestController
class JpTextController {

    val analyzer = Analyzer(
        """C:\Users\hugop\Sync\sudachi-0.5.3\""",
        """C:\Users\hugop\Sync\sudachi-0.5.3\sudachi_fulldict.json"""
    )

    class MostCommonWord(val name: String, val count: Int, val partOfSpeach: List<PartOfSpeechStats>)

    class PartOfSpeechStats(val pos: String, val count: Int) {
        constructor(pos: List<String>, count: Int) : this(pos.joinToString(separator = ","), count)
    }

    @GetMapping("/mostcommon")
    fun getMostCommon(count: Int, @RequestBody input: String): List<MostCommonWord> {
        val occurrences = analyzer.findWordOccurrences(Tokenizer.SplitMode.C, input)

        val mostCommonOccurrences = occurrences.entries
            .sortedByDescending { occ -> occ.value.size }
            .take(count)
            .toList()

        val mostCommon: List<MostCommonWord> =
            mostCommonOccurrences.map { occ ->
                MostCommonWord(occ.key,
                    occ.value.size,
                    occ.value
                        .groupBy { it.morph.partOfSpeech() }
                        .map { PartOfSpeechStats(it.key, it.value.size) }
                        .sortedByDescending { it.count }
                )
            }
        return mostCommon
    }

}

fun main(args: Array<String>) {
    runApplication<Api>(*args)
}
