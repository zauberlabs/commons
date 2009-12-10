<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="UTF-8" indent="yes" xalan:indent-amount="2"  xmlns:xalan="http://xml.apache.org/xalan"/>
    
    <xsl:template match="html">
        <string><xsl:value-of select="/html/body/text()"/></string>
    </xsl:template>
    
</xsl:stylesheet>