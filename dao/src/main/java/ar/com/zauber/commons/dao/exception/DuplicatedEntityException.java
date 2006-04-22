package ar.com.zauber.commons.dao.exception;


/**
 * The entry is duplicated
 * 
 * @author Juan F. Codagnone
 * @since Sep 1, 2005
 */
public final class DuplicatedEntityException extends AbstractEntityException {
    /**
     * @see AbstractEntityException#AbstractEntityException(Object, Throwable)
     */
    public DuplicatedEntityException(final Object entity, final Throwable th) {
        super(entity, th);
    }

    /**
     * @see AbstractEntityException#AbstractEntityException(Object)
     */
    public DuplicatedEntityException(final Object entity) {
        super(entity);
    }
}
