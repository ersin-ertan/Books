package ch01

import arrow.typeclasses.Eq
import arrow.typeclasses.Show

interface Show<in A> {

    /**
     * Given an object [this@show] of type [A] it returns its textual representation.
     *
     * @receiver object of type [A].
     * @returns a [String] representing [this@show].
     */
    fun A.show(): String

    companion object {

        /**
         * Construct a [Show] instance from a function `(A) -> String`
         *
         * @param fshow function that defines a textual representation for type [A].
         * @returns a [Show] instance that is defined by the [fshow] function.
         */
        inline operator fun <A> invoke(crossinline fshow: A.() -> String): Show<A> = object : Show<A> {
            override fun A.show(): String =
                    fshow(this)
        }

        /**
         * Construct a [Show] instance using object `toString`.
         *
         * @returns a [Show] instance that is defined by the [A] `toString` method.
         */
        fun <A> fromToString(): Show<A> = object : Show<A> {
            override fun A.show(): String =
                    toString()
        }

        /**
         * Retrieve a [Show] that defines the textual representation as toString() for type [A].
         */
        fun any(): Show<Any?> = ShowAny

        private object ShowAny : Show<Any?> {
            override fun Any?.show(): String =
                    toString()
        }
    }
}

interface Eq<in F> {

    /**
     * Compares two instances of [F] and returns true if they're considered equal for this instance.
     *
     * @receiver object to compare with [b]
     * @param b object to compare with [this@eqv]
     * @returns true if [this@eqv] and [b] are equivalent, false otherwise.
     */
    fun F.eqv(b: F): Boolean

    /**
     * Compares two instances of [F] and returns true if they're considered not equal for this instance.
     *
     * @receiver object to compare with [b]
     * @param b object to compare with [this@neqv]
     * @returns false if [this@neqv] and [b] are equivalent, true otherwise.
     */
    fun F.neqv(b: F): Boolean = !eqv(b)

    companion object {

        /**
         * Construct an [Eq] from a function `(F, F) -> Boolean`.
         *
         * @param feqv function that defines if two instances of type [F] are equal.
         * @returns an [Eq] instance that is defined by the [feqv] function.
         */
        inline operator fun <F> invoke(crossinline feqv: F.(F, F) -> Boolean): Eq<F> = object : Eq<F> {
            override fun F.eqv(b: F): Boolean =
                    feqv(this, b)
        }

        /**
         * Retrieve an [Eq] that defines all instances as equal for type [F].
         *
         * @returns an [Eq] instance wherefore all instances of type [F] are equal.
         */
        fun any(): Eq<Any?> = EqAny

        private object EqAny : Eq<Any?> {
            override fun Any?.eqv(b: Any?): Boolean = this == b

            override fun Any?.neqv(b: Any?): Boolean = this != b
        }
    }
}