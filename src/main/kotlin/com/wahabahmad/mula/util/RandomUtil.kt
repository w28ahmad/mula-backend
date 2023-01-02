package com.wahabahmad.mula.util

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomUtil {

    fun getRandomInt(min: Int, max: Int) = Random.nextInt(max - min + 1) + min

    fun randomDistinctInts(min: Int, max: Int, size: Int) =
        generateSequence {
            Random.nextInt(min, max)
        }.distinct()
            .take(size)
            .toSet()
            .shuffled()

    fun randomNumberNotInSet(min: Int, max: Int, notInThisSet: Set<Int>): Int =
        ((min..max).toSet() - notInThisSet).random()
}