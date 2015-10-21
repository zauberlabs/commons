/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.repository.aggregate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import ar.com.zauber.commons.repository.query.aggreate.AggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.AggregateFunctionVisitor;
import ar.com.zauber.commons.repository.query.aggreate.AveragePropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.CompositeAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.CountDistinctPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.CountPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.GroupPropertyAggregateFilter;
import ar.com.zauber.commons.repository.query.aggreate.MaxPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.MinPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.ParameterlessAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.PropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.aggreate.RowCountAggregateFilter;
import ar.com.zauber.commons.repository.query.aggreate.SumPropertyAggregateFunction;
import ar.com.zauber.commons.repository.query.visitor.CriteriaFilterVisitor;

/**
 * Implementación de {@link AggregateFunctionVisitor} para Hibernate.
 * Sigue el diseño de {@link CriteriaFilterVisitor}.
 *
 * @author Juan F. Codagnone
 * @since Jun 7, 2008
 */
public class ProjectionAggregateFunctionVisitor implements AggregateFunctionVisitor {
    private Projection projection;

    
    /** @see AggregateFunctionVisitor#visitCompositeAggregateFilter(
     * CompositeAggregateFunction) */
    public final void visitCompositeAggregateFilter(
            final CompositeAggregateFunction caf) {

        final ProjectionList pj = Projections.projectionList();
        ProjectionList last = pj;
        for(final AggregateFunction function : caf.getFunctions()) {
            final Projection p = createProjection(function);
            last = last.add(p);
        }
        projection = pj;
    }

    /** @see AggregateFunctionVisitor#visitParameterlessAggregateFilter(
     * ParameterlessAggregateFunction) */
    public final void visitParameterlessAggregateFilter(
            final ParameterlessAggregateFunction paf) {
        projection = createParameterlessProjection(paf);
    }


    /** @see AggregateFunctionVisitor#visitPropertyAggregateFilter(
     * PropertyAggregateFunction) */
    public final void visitPropertyAggregateFilter(
            final PropertyAggregateFunction paf) {
        projection = createPropertyProjection(paf);
    }

    /** crea projections */
    private static Projection createProjection(final AggregateFunction af) {
        final Projection ret;
        if(af instanceof ParameterlessAggregateFunction) {
            ret = createParameterlessProjection((ParameterlessAggregateFunction) af);
        } else if(af instanceof PropertyAggregateFunction) { 
            ret = createPropertyProjection((PropertyAggregateFunction) af);
        } else {
            throw new IllegalArgumentException("don't know how to process "
                    + af.getClass());
        }
        return ret;
    }
    /** crea projecciones en base a {@link ParameterlessAggregateFunction}. */
    private static Projection createParameterlessProjection(
            final ParameterlessAggregateFunction paf) {
        final Projection projection;
        if(paf instanceof RowCountAggregateFilter) {
            projection = Projections.rowCount();
        } else {
            throw new IllegalArgumentException("don't know how to process "
                    + paf.getClass());
        }
        
        return projection;
    }
    
    /** crea projecciones en base a {@link PropertyAggregateFunction}. */
    private static Projection createPropertyProjection(
            final PropertyAggregateFunction paf) {
        final String propertyName = paf.getProperty();
        final Projection projection;
        
        if(paf instanceof AveragePropertyAggregateFunction) {
            projection = Projections.avg(propertyName);
        } else if(paf instanceof CountDistinctPropertyAggregateFunction) {
            projection = Projections.countDistinct(propertyName);
        } else if(paf instanceof CountPropertyAggregateFunction) {
            projection = Projections.count(propertyName);
        } else if(paf instanceof MaxPropertyAggregateFunction) {
            projection = Projections.max(propertyName);
        } else if(paf instanceof MinPropertyAggregateFunction) {
            projection = Projections.min(propertyName);
        } else if(paf instanceof SumPropertyAggregateFunction) {
            projection = Projections.sum(propertyName);
        } else if(paf instanceof GroupPropertyAggregateFilter) {
            projection = Projections.groupProperty(propertyName);
        } else {
            throw new IllegalArgumentException("don't know how to process "
                    + paf.getClass());
        }
        
        return projection;
    }

    public final Projection getProjection() {
        return projection;
    }
}
