package com.wahabahmad.mula.util

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomUtil {

    fun randomDistinctInts(min: Int, max:Int, size:Int) =
        generateSequence {
            Random.nextInt(min, max)
        }.distinct()
            .take(size)
            .toSet()

}