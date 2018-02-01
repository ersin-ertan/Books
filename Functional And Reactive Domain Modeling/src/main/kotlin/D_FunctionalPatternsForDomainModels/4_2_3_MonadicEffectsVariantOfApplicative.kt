package D_FunctionalPatternsForDomainModels

// 4.2.3 Monadic effects—a variant on the applicative pattern

// monad extends applicative
/* applicative effect preserves the shape of the computation.
Both help core domain model to remain clean boilerplate free.

If you have f:A->F<B> and g:B->F<C> and F is a monad, you can compose them as A->F<C>, composition of monadic functions.
Kleisli composition. Ex stat monad, compose stateful functions and manages threading of the state through them auto.

*/