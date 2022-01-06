<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:z="http://www.akatsuki.org/zahtev_za_sertifikat"
                xmlns:t="http://www.akatsuki.org/tipovi" version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Interesovanje</title>
                <style type="text/css">
                    body {
                        margin: 0;
                        font-family: Arial, sans-serif;
                        overflow-x: hidden;
                    }
                    h1 {
                        text-align: center;
                        padding: 20px;
                        font-weight: bold;
                    }
                    .indent-paragraph {
                        margin-left: 10vw;
                        font-size: 1.5em;
                    }
                    .indent-vaccine {
                        margin: 2px 0px 0px 20vw;
                        margin-left: 20vw;
                        font-size: 1.3em;
                        padding: 0px;
                    }
                    .indent-potpis {
                        border-top: 1px solid black;
                        width: 25%;
                        margin: 10px 0px 10vh 60%;
                        padding: 15px;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <h1 style="margin-top: 10vh;">Z A H T E V</h1>
                <h2 style="text-align: center;">za izdavanje digitalnog zelenog sertifikata</h2>
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px" margin-top="25px">-->
<!--                    U skladu sa odredbom Republike Srbije o izdavanju digitalnog zelenog-->
<!--                    sertifikata kao potvrde o izvrsenoj vakcinaciji protiv COVID-19, rezultatima testiranja na zaraznu bolest SARS-CoV-2-->
<!--                    ili oporavku od bolesti COVID-19, podnosim zahtev za izdavanje digitalnog zelenog sertifikata.-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px" margin-top="20px">-->
<!--                    Podnosilac zahteva:-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px" margin-top="10px">-->
<!--                    Ime i prezime:-->
<!--                    <fo:inline font-weight="bold">-->
<!--                        <xsl:value-of select="concat(' ', //t:ime, ' ', //t:prezime)"/>-->
<!--                    </fo:inline>-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px">-->
<!--                    Datum rodjenja:-->
<!--                    <fo:inline font-weight="bold">-->
<!--                        <xsl:value-of select="concat(' ', //t:datum_rodjenja)"/>-->
<!--                    </fo:inline>-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px">-->
<!--                    Pol:-->
<!--                    <fo:inline font-weight="bold">-->
<!--                        <xsl:value-of select="concat(' ', //t:pol)"/>-->
<!--                    </fo:inline>-->
<!--                </fo:block>-->
<!--                <xsl:choose>-->
<!--                    <xsl:when test="string-length(//t:id_broj/text()) = 13">-->
<!--                        <fo:block font-family="sans-serif" font-size="12px" padding="5px">-->
<!--                            Jedinstveni maticni broj gradjanina:-->
<!--                            <fo:inline font-weight="bold">-->
<!--                                <xsl:value-of select="concat(' ', //t:id_broj)"/>-->
<!--                            </fo:inline>-->
<!--                        </fo:block>-->
<!--                    </xsl:when>-->
<!--                    <xsl:otherwise>-->
<!--                        <fo:block font-family="sans-serif" font-size="12px" padding="5px">-->
<!--                            Broj pasosa:-->
<!--                            <fo:inline font-weight="bold">-->
<!--                                <xsl:value-of select="concat(' ', //t:id_broj)"/>-->
<!--                            </fo:inline>-->
<!--                        </fo:block>-->
<!--                    </xsl:otherwise>-->
<!--                </xsl:choose>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px" margin-top="10px">-->
<!--                    Razlog za podnosenje zahteva:-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px">-->
<!--                    <xsl:value-of select="//z:razlog_podnosenja_zahteva"/>-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="8px" padding="5px" text-align="center">-->
<!--                    (navesti sto precizniji razlog za podnosenje zahteva za izdavanje digitalnog pasosa)-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="13px" padding="5px" margin-top="35px">-->
<!--                    U-->
<!--                    <fo:inline font-weight="bold">-->
<!--                        <xsl:value-of select="concat(' ', //z:zahtev_za_sertifikat/@mesto)"/>,-->
<!--                    </fo:inline>-->
<!--                </fo:block>-->
<!--                <fo:block font-family="sans-serif" font-size="12px" padding="5px" margin-top="10px">-->
<!--                    Datum:-->
<!--                    <fo:inline font-weight="bold">-->
<!--                        <xsl:value-of select="concat(' ', //z:zahtev_za_sertifikat/@datum)"/>-->
<!--                    </fo:inline>-->
<!--                </fo:block>-->
<!--                <fo:block-container>-->
<!--                    <fo:block-container width="40%" left="60%" top="0in" position="absolute">-->
<!--                        <fo:block font-family="sans-serif" font-size="12px" text-align="center" linefeed-treatment="preserve" margin="0" border-top="1px solid black">-->
<!--                            Potpis-->
<!--                        </fo:block>-->
<!--                    </fo:block-container>-->
<!--                </fo:block-container>-->
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
