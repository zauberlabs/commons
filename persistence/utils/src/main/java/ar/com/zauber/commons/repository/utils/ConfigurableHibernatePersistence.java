package ar.com.zauber.commons.repository.utils;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernatePersistence;

/**
 * Extended Hibernate EJB3 persistence provider implementation que permite 
 * utilizar interceptores que tienen contructor.
 * 
 * @author Juan F. Codagnone
 * @since Jul 22, 2010
 */
public class ConfigurableHibernatePersistence extends HibernatePersistence {
    private Interceptor interceptor;

    public final Interceptor getInterceptor() {
        return interceptor;
    }

    public final void setInterceptor(final Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    /** @see HibernatePersistence#createContainerEntityManagerFactory(
     *      PersistenceUnitInfo, Map)
     */
    @Override
    public final EntityManagerFactory createContainerEntityManagerFactory(
            final PersistenceUnitInfo info, final Map map) {
        final Ejb3Configuration cfg = new Ejb3Configuration();
        final Ejb3Configuration configured = cfg.configure(info, map);
        postprocessConfiguration(info, map, configured);
        return configured != null ? configured.buildEntityManagerFactory()
                : null;
    }

    /** inject! */
    protected final void postprocessConfiguration(final PersistenceUnitInfo info, 
            final Map map, final Ejb3Configuration configured) {
        if (interceptor != null) {
            if (configured.getInterceptor() == null
                    || EmptyInterceptor.class.equals(configured
                            .getInterceptor().getClass())) {
                configured.setInterceptor(interceptor);
            } else {
                throw new IllegalStateException(
                        "Hibernate interceptor already set in persistence.xml ("
                                + configured.getInterceptor() + ")");
            }
        }
    }
}
