package ar.com.zauber.commons.dao;

/**
 * Similar a {@link Transformer} con la salvedad que la salida puede ser procesada
 * en forma asincrónica por un {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since May 21, 2010
 * @param <I> tipo de parámetro de entrada
 * @param <O> tipo de parámetro de salida
 */
public interface AsyncronymousTransformer<I, O> {

    /** Procesa un parametro, y deja un resultado en un closure */
    void transform(I input, Closure<O> closure);
}
