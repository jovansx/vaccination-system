<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:i="http://www.akatsuki.org/interesovanje"
    xmlns:t="http://www.akatsuki.org/tipovi"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="bookstore-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="bookstore-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="sans-serif" font-size="24px" font-weight="bold" padding="20px" text-align="center">
                        Interesovanje
                    </fo:block>
                    <fo:block text-indent="20px" font-size="14px" padding="10px">
                       Podnosilac interesovanja:
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="//i:podnosilac/t:ime"/>
                            <xsl:value-of select="concat(' ', //i:podnosilac/t:prezime)"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block text-indent="20px" font-size="14px" padding="10px">
                        Adresa elektronske poste:
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="//i:podnosilac/t:email"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block text-indent="20px" font-size="14px" padding="10px">
                        Mobilni telefon:
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="//i:podnosilac/t:mobilni_telefon"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block text-indent="20px" font-size="14px" padding="10px">
                        Fiksni telefon:
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="//i:podnosilac/t:fiksni_telefon"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block text-indent="20px" font-size="14px" padding="10px">
                        Lokacija:
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="//i:podnosilac/t:lokacija"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="20px" padding="20px">
                        Zahtevane vakcine:
                    </fo:block>
                    <xsl:for-each select="//i:vakcine/i:vakcina">
                        <fo:block font-size="14px" text-indent="40px" padding="5px">
                            -
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="@nazivVakcine"/>
                            </fo:inline>
                        </fo:block>
                    </xsl:for-each>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
