<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:t="http://www.akatsuki.org/tipovi"
                xmlns:i="http://www.akatsuki.org/interesovanje" version="2.0">
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
                <h1 style="margin-top: 10vh;">Iskazivanje interesovanja za vakcinisanje protiv COVID-19</h1>
                <xsl:if test="//i:drzavljanstvo/text() = 'srpsko'">
                    <p class="indent-paragraph">Drzavljanstvo:
                        <b>Drzavljanin Republike Srbije</b>
                    </p>
                </xsl:if>
                <xsl:if test="//i:drzavljanstvo/text() = 'strano sa boravkom'">
                    <p class="indent-paragraph">Drzavljanstvo:
                        <b>Strani drzavljanin sa boravkom u RS</b>
                    </p>
                </xsl:if>
                <xsl:if test="//i:drzavljanstvo/text() = 'strano bez boravka'">
                    <p class="indent-paragraph">Drzavljanstvo:
                        <b>Strani drzavljanin bez boravka u RS</b>
                    </p>
                </xsl:if>
                <xsl:choose>
                    <xsl:when test="string-length(//t:id_broj/text()) = 13">
                        <p class="indent-paragraph">JMBG:
                            <b>
                                <xsl:value-of select="concat(' ', //t:id_broj)"/>
                            </b>
                        </p>
                    </xsl:when>
                    <xsl:otherwise>
                        <p class="indent-paragraph">Broj pasosa:
                            <b>
                                <xsl:value-of select="concat(' ', //t:id_broj)"/>
                            </b>
                        </p>
                    </xsl:otherwise>
                </xsl:choose>
                <p class="indent-paragraph">Ime:
                    <b>
                        <xsl:value-of select="//i:podnosilac/t:ime"/>
                    </b>
                </p>
                <p class="indent-paragraph">Prezime:
                    <b>
                        <xsl:value-of select="//i:podnosilac/t:prezime"/>
                    </b>
                </p>
                <p class="indent-paragraph">Adresa elektronske poste:
                    <b>
                        <xsl:value-of select="//i:podnosilac/t:email"/>
                    </b>
                </p>
                <p class="indent-paragraph">Broj mobilnog telefona (navesti broj u formatu 06X... bez razmaka i crtica):
                    <b>
                        <xsl:value-of select="//i:podnosilac/t:mobilni_telefon"/>
                    </b>
                </p>
                <p class="indent-paragraph">Broj fiksnog telefona (navesti broj u formatu (DDD) DDD-DDD):
                    <b>
                        <xsl:value-of select="//i:podnosilac/t:fiksni_telefon"/>
                    </b>
                </p>
                <p class="indent-paragraph">Lokacija:
                    <b>
                        <xsl:value-of select="//i:podnosilac/t:lokacija"/>
                    </b>
                </p>
                <p class="indent-paragraph" style="margin-right: 10vw;">
                    Iskazujem interesovanje da primim iskljucivo vakcinu sledecih proizvodjaca za koji Agencija
                    za lekove i medicinska sredstva potvrdi bezbednost, efikasnost i kvalitet i izda dozvolu za upotrebu
                    leka:
                </p>
                <xsl:for-each select="//i:vakcine/i:vakcina">
                    <p class="indent-vaccine">
                        <b>
                            <xsl:value-of select="@nazivVakcine"/>
                        </b>
                    </p>
                </xsl:for-each>
                <p class="indent-paragraph">Da li ste dobrovoljni davalac krvi:</p>
                <xsl:choose>
                    <xsl:when test="//i:dobrovoljni_davalac_krvi = true()">
                        <p class="indent-vaccine">
                            <b>Da</b>
                        </p>
                    </xsl:when>
                    <xsl:otherwise>
                        <p class="indent-vaccine">
                            <b>Ne</b>
                        </p>
                    </xsl:otherwise>
                </xsl:choose>
                <p class="indent-potpis">Potpis</p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
