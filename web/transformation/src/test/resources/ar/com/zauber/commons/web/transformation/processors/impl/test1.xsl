<?xml version="1.0"?>
<!--

    Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    
    <xsl:template name="foo" match="html">
        <xsl:apply-templates select='//*[@id="lista"]'/>
    </xsl:template>
    
    <xsl:template name="bar" match='//*[@id="lista"]'>
        <xsl:variable name="valor" select="./@valor"/>
        <xsl:call-template name="valor">
            <xsl:with-param name="valor" select="$valor"/>
        </xsl:call-template>
        <xsl:for-each select='./*[@id="x"]'>
            <string>
            <xsl:attribute name="y">
                <xsl:value-of select='../*[@id="y"]'/>
            </xsl:attribute>
            <xsl:value-of select="."/>
            </string>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="valor">
        <xsl:param name="valor"/>
        <string><xsl:value-of select="$valor"/></string>
    </xsl:template>
    
</xsl:stylesheet>