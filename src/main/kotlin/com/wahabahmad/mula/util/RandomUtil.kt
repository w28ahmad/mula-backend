package com.wahabahmad.mula.util

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomUtil {
    fun randomDistinctInts(min: Int, max: Int, size: Int) =
        generateSequence {
            Random.nextInt(min, max)
        }.distinct()
            .take(size)
            .toSet()
            .shuffled()

    fun randomNumberNotInSet(min: Int, max: Int, size: Int, notInThisSet: Set<Int>): Set<Int> =
        ((min..max).toSet() - notInThisSet).shuffled().take(size).toSet()

    fun randomNumbersInSet(set: Set<Int>, size: Int): Set<Int> =
        set.toList().shuffled().take(size).toSet()

    fun randomNumberInSetButNotInOtherSet(set: Set<Int>, otherSet: Set<Int>, size: Int): Set<Int> =
        (set - otherSet).shuffled().take(size).toSet()

}