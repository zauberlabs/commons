/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.conversion.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * Similar a {@link PropertyExtractorConverter} pero con una expression
 * EL de spring.
 *
 * @param <S>
 * @param <SF>
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class ExpressionExtractorConverter<S, SF> implements Converter<S, SF> {
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private final Expression expression;
    
    /** @param expression una expresion EL {root.size} */
    public ExpressionExtractorConverter(final String expression) {
        Validate.isTrue(StringUtils.isNotBlank(expression));
        this.expression = PARSER.parseExpression(expression);
    }



    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    @SuppressWarnings("unchecked")
    public final SF convert(final S source, final ConversionContext ctx) {
        Validate.notNull(source);
        return (SF) expression.getValue(source);
    }

}
