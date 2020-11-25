<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version = "1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="articles">
        <articles>
            <xsl:apply-templates/>
        </articles>
    </xsl:template>

    <xsl:template match="article">
        <article>
            <xsl:element name="id_art">
                <xsl:value-of select="@id_art" />
            </xsl:element>

            <xsl:element name="name">
                <xsl:value-of select="@name" />
            </xsl:element>

            <xsl:element name="code">
                <xsl:value-of select="@code" />
            </xsl:element>

            <xsl:element name="username">
                <xsl:value-of select="@username" />
            </xsl:element>

            <xsl:element name="guid">
                <xsl:value-of select="@guid" />
            </xsl:element>
        </article>
    </xsl:template>
</xsl:stylesheet>
