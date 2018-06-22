package ch08

// Chapter 10 Case Study: Data Validation

// input to meet criteria, auth is a specialized form of validation
// examples age over 18, string id parsable as int, int must be valid record id, auction bid must have
// positive value and item association, username must have four chars and be alphanumeric
// email must have single @ and left of @ must be non empty, right of @ must have 3 chars then dot

// meaningful message with each validation failure
// combine small checks into larger ones, ex username is length and alphanumeric check
// transform data while checking it, string to int
// accumulate all failures in one go

// combine checks across multiple pieces of data, login form is username and pass

// 10.1 Sketching the Library Structure
// check individual data - 1 associate useful error messages with check failure
// A -> F<A> validation check
// combine checks - monoid, define sensible identity, always passing check, two binary combination ops and/or
// (A->F<A>, A->F<A>).tupled -> A->F<(A,A)> Applicative combo check
// A->F<A> |+| A->F<A> -> A->F<A> Monoid combo check
// A->F<B> map B->C -> A->F<C>
// A->F<B> flatmap B->(A->F<C>) -> A->F<C>

// switching between two monoids for combining rules is annoying, thus we use two separet methods and and or

// Accum errors - Stored in List or NonEmptyList
// Transforming data as we check it - goal of transforming it - fmap or flatMap depending on transform can fail or not
// thus checks to be a monad

